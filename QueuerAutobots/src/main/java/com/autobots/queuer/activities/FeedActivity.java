package com.autobots.queuer.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.autobots.queuer.R;
import com.autobots.queuer.adapters.FeedAdapter;
import com.autobots.queuer.managers.LoginManager;
import com.autobots.queuer.models.Project;
import com.autobots.queuer.views.EnhancedListView;

import java.util.ArrayList;

/**
 * Created by mammothbane on 1/17/14.
 */
public class FeedActivity extends ActionBarActivity {
    private FeedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ArrayList<Project> projects = new ArrayList<Project>(20);
        for (int i = 0; i < 20; i++) {
            projects.add(new Project(i, "Project " + i));
        }

        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_projects);
        adapter = new FeedAdapter(this, projects);
        listView.setAdapter(adapter);

        //listView.setDismissCallback(new EnhancedListView.OnDismissCallback()) {


        //}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*
                ** TODO: implement onClick() for what happens when user clicks on a project.
                 * intent() based, make project serializable ( need to google that)
                  * transfer that intent to ProjectActivity.
                 */
                Toast.makeText(FeedActivity.this, "Clicked on item " + adapter.getItem(i), Toast.LENGTH_SHORT).show();
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