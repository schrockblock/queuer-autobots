package com.autobots.queuer.activities;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.autobots.queuer.R;
import com.autobots.queuer.adapters.FeedAdapter;
import com.autobots.queuer.adapters.ProjectAdapter;
import com.autobots.queuer.databases.TaskDataSource;
import com.autobots.queuer.models.Project;
import com.autobots.queuer.models.Task;
import com.autobots.queuer.views.EnhancedListView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by mammothbane on 1/17/14.
 */
public class ProjectActivity extends ActionBarActivity {

    private ProjectAdapter adapter;
    final TaskDataSource tds = new TaskDataSource(this);
    private ArrayList<Task> tasks = new ArrayList<Task>(10);
    private LinearLayout layout;
    private Project project;
    private int projectId;
    private EnhancedListView listView;
    private TextView tView;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Activity)this).setContentView(R.layout.project_feed);
        project = (Project) getIntent().getSerializableExtra("PROJECT");
        projectId = project.getId();
        getActionBar().setTitle(project.getName());
        layout = (LinearLayout) findViewById(R.id.project_feed);



    }

    protected void onResume() {


        layout.setBackgroundColor(project.getColor());
        tView = (TextView) findViewById(R.id.no_tasks);
        tView.setVisibility(View.GONE);

        try {
            tds.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tasks = tds.getProjectTasks(projectId);
        tds.close();

        listView = (EnhancedListView)findViewById(R.id.lv_tasks);
        adapter = new ProjectAdapter(this, tasks);
        listView.setAdapter(adapter);

        if (adapter.isEmpty()){
            listView.setVisibility(View.GONE);
            tView.setTextSize(26);
            tView.setVisibility(View.VISIBLE);
        }

        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
                if(adapter.isEmpty())
                    return null;
                final Task task = adapter.getItem(position);
                adapter.remove(position);
                adapter.notifyDataSetChanged();
                try {
                    tds.open();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                tds.deleteTask(task);
                tds.close();
                FeedAdapter.getFeedAdapter().notifyDataSetChanged();
                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        try {
                            tds.open();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        tds.createTask(task.getName(), task.getProjectId(), task.getId(), position, task.isComplete());
                        tds.close();
                        adapter.insert(task, position);
                        adapter.notifyDataSetChanged();
                        FeedAdapter.getFeedAdapter().notifyDataSetChanged();
                    }
                };
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ProjectActivity.this, "Clicked on item " + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        listView.enableSwipeToDismiss();
        listView.enableRearranging();

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

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
                                startActivity(new Intent(ProjectActivity.this, LoginActivity.class));
                            }
                        }).create().show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}



