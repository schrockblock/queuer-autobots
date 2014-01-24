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
import com.autobots.queuer.databases.ProjectDataSource;
import com.autobots.queuer.databases.TaskDataSource;
import com.autobots.queuer.managers.LoginManager;
import com.autobots.queuer.models.Project;
import com.autobots.queuer.models.Task;
import com.autobots.queuer.views.EnhancedListView;

import java.util.ArrayList;

/**
 * Created by mammothbane on 1/17/14.
 */
public class FeedActivity extends ActionBarActivity {
    private FeedAdapter adapter;

    private Context context;
    private ArrayList<Project> emptyProjects = new ArrayList<Project>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ProjectDataSource pds = new ProjectDataSource();
        TaskDataSource tds = new TaskDataSource(this);

        ArrayList<Project> projects = pds.getAllProjects();
        for (int i = 0; i < 20; i++) {
            projects.add(new Project(i, "Project " + i,Color.CYAN));
            if(i == 3){

            }
        }

        for(int k = 0; k < projects.size(); k++){
            if(!projects.get(k).hasTasks()){
                emptyProjects.add(projects.get(k));
                projects.remove(k);
                k--;
            }
        }

        if(projects.size() != 0)
            findViewById(R.id.msg_noProjects).setVisibility(View.GONE);

        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
        adapter = new FeedAdapter(this, projects);
        listView.setAdapter(adapter);

        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                if(!adapter.getItem(position).hasTasks())
                    return null;
                final Task task = adapter.getItem(position).getTaskList().get(0);
                adapter.getItem(position).getTaskList().remove(0);
                adapter.notifyDataSetChanged();
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        adapter.getItem(position).getTaskList().add(0, task);
                    }
                };
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                Intent intent = new Intent(FeedActivity.this, ProjectActivity.class);
                intent.putExtra("PROJECT", adapter.getItem(position));
                startActivity(intent);

                //Toast.makeText(FeedActivity.this, "Clicked on item " + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        //listView.enableSwipeToDismiss();
        listView.enableRearranging();

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
            case R.id.action_settings:
                //open settings activity
                return true;
            case R.id.action_logout:
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("Are you sure you want to logout?")
                        .setNegativeButton(android.R.string.no, null)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                finish();
                                com.autobots.queuer.managers.LoginManager.setLoggedIn(false);
                            }
                        }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}