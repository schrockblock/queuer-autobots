package com.autobots.queuer.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.autobots.queuer.R;
import com.autobots.queuer.adapters.FeedAdapter;
import com.autobots.queuer.adapters.ProjectAdapter;
import com.autobots.queuer.models.Project;
import com.autobots.queuer.models.Task;
import com.autobots.queuer.views.EnhancedListView;

import java.util.ArrayList;

/**
 * Created by Moseph on 1/20/14.
 */
public class ProjectActivity extends ActionBarActivity {

    private ProjectAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_feed);
        TextView tView = (TextView) findViewById(R.id.no_tasks);
        tView.setVisibility(View.GONE);

        Project project = (Project) getIntent().getSerializableExtra("EXTRA_PROJECT");



        ArrayList<Task> tasks = project.getTaskList();

        for (int i = 0; i < 10; i++) {
            tasks.add(new Task(i, "Task " + i));
        }
        


        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_tasks);
        adapter = new ProjectAdapter(this, tasks);
        listView.setAdapter(adapter);

        if (!project.hasTasks()){
            tView.setVisibility(View.VISIBLE);
        }


        //listView.setDismissCallback(new EnhancedListView.OnDismissCallback()) {


        //}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ProjectActivity.this, "Clicked on item " + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        //listView.enableSwipeToDismiss();
        listView.enableRearranging();

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



