package com.autobots.queuer.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.autobots.queuer.R;
import com.autobots.queuer.adapters.FeedAdapter;
import com.autobots.queuer.adapters.ProjectAdapter;
import com.autobots.queuer.databases.ProjectDataSource;
import com.autobots.queuer.databases.TaskDataSource;
import com.autobots.queuer.managers.LoginManager;
import com.autobots.queuer.models.Project;
import com.autobots.queuer.models.Task;
import com.autobots.queuer.views.EnhancedListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mammothbane on 1/17/14.
 */
public class FeedActivity extends ActionBarActivity {
    private FeedAdapter adapter;

    private Context context;
    private ArrayList<Project> projects;
    private ArrayList<Project> emptyProjects = new ArrayList<Project>();
    private ArrayList<Task> tasks;
    private boolean isLast = false; //TODO: fix this, please. we're working on the whole feedActivity, not one project
    ProjectDataSource pds = new ProjectDataSource(this);
    TaskDataSource tds = new TaskDataSource(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_feed);
        setTitle("Task Manager");

        try {
            pds.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        projects = pds.getAllProjects();
        if(projects.isEmpty()){
            pds.createProject("Project1", Color.CYAN, 0, new Date(1,1,1), new Date(1,1,1) );
            projects = pds.getAllProjects();
        }
        pds.close();

        for (int i = 0; i < projects.size(); i++) {
            ProjectAdapter pAdapter = new ProjectAdapter(FeedActivity.this, null);
            try {
                tds.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            tasks = tds.getProjectTasks(adapter.getItemId(i));
            tds.close();
            adapter.getItem(i).setTaskList(tasks);

            if(pAdapter.isEmpty()) {

                try {
                    tds.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                Task taskOne = tds.createTask("Task1", adapter.getItemId(i),0,0, false );
                Task taskTwo = tds.createTask("Task2", adapter.getItemId(i),1,1, false );
                tds.close();
            }
        }
        adapter = FeedAdapter.getFeedAdapter(this, projects);

    }

    @Override
    public void onResume() {
        if (!LoginManager.isLoggedIn()) {
            startActivity(new Intent(FeedActivity.this, LoginActivity.class));
        }


        try {
            pds.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        projects = pds.getAllProjects();
        pds.close();
        //projects.get(0).setTaskList(tasks);



        if(projects.size() != 0) findViewById(R.id.msg_noProjects).setVisibility(View.GONE);

        try {
            tds.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tds.close();

        for (int i = 0; i < projects.size(); i++) {
            if(!adapter.isEmpty()){

                try {
                    tds.open(); // App crashes here.
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tasks = tds.getProjectTasks(adapter.getItemId(0));
                tds.close();

                adapter.getItem(0).setTaskList(tasks);

            }
        }

        for(int k = 0; k < projects.size(); k++){
            if(!projects.get(k).hasTasks()){
                emptyProjects.add(projects.get(k));
                projects.remove(k);
                k--;
            }
        }

        final EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
        //adapter = new FeedAdapter(this, projects);
        listView.setAdapter(adapter);

        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                //if(!adapter.getItem(position).hasTasks())
                //    return null;
                final Task task = adapter.getItem(position).getTaskList().get(0);
                adapter.getItem(position).getTaskList().remove(0);
                if(!adapter.getItem(position).hasTasks()) isLast = true;  //if the project has no more tasks, set isLast to true //TODO: this doesn't make any sense. we're working on FeedActivity.
                adapter.notifyDataSetChanged();
                try {
                    tds.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tds.deleteTask(task);
                tds.close();
                checkForEmpty(position);
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        if(getIsLast()){ //if the project was empty, move it from emptyProjects to projects in the same position as previously
                            projects.add(position,emptyProjects.get(emptyProjects.size()-1));
                            emptyProjects.remove(emptyProjects.size() - 1);
                            findViewById(R.id.msg_noProjects).setVisibility(View.GONE);
                            try {
                                tds.open();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            tds.createTask(task.getName(), task.getProjectId(), task.getId(), position, task.isComplete());
                            tds.close();
                            adapter.notifyDataSetChanged();
                            adapter.getItem(position).getTaskList().add(0, task);
                        }
                    }
                };
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                Intent intent = new Intent(FeedActivity.this, ProjectActivity.class);
                intent.putExtra("PROJECT", adapter.getItem(position));
                //intent.putExtra("ADAPTER", )
                startActivity(intent);

                //Toast.makeText(FeedActivity.this, "Clicked on item " + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        listView.enableSwipeToDismiss();
        listView.enableRearranging();

        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface arg0, int arg1) {
                        moveTaskToBack(true);
                        finish();
                        com.autobots.queuer.managers.LoginManager.setLoggedIn(false);
                    }
                }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feed, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.action_settings:
                //open settings activity
                return true;*/
            case R.id.action_logout:
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                com.autobots.queuer.managers.LoginManager.setLoggedIn(false);
                                startActivity(new Intent(FeedActivity.this, LoginActivity.class));
                            }
                        }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void checkForEmpty(int pos){
        if(projects.size() == 0) {
            findViewById(R.id.msg_noProjects).setVisibility(View.VISIBLE);
            return;
        }
        if(!projects.get(pos).hasTasks()){
           emptyProjects.add(projects.get(pos));
           projects.remove(pos);
        }

    }

    public boolean getIsLast(){
        return isLast;
    }

}