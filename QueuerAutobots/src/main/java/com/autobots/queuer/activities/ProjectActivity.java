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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;

import com.autobots.queuer.R;
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return super.onCreateOptionsMenu(menu);

        //return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_feed);
        TextView tView = (TextView) findViewById(R.id.no_tasks);
        tView.setVisibility(View.GONE);

        Project project = (Project) getIntent().getSerializableExtra("PROJECT");
        int projectId = project.getId();
        getActionBar().setTitle(project.getName());
        LinearLayout layout = (LinearLayout) findViewById(R.id.project_feed);
        layout.setBackgroundColor(project.getColor());

        TaskDataSource tds = new TaskDataSource(this);
        try {
            tds.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<Task> tasks = tds.getProjectTasks(projectId);
        tds.close();

        EnhancedListView listView = (EnhancedListView)findViewById(R.id.lv_tasks);
        ProjectAdapter adapter = new ProjectAdapter(this, tasks);
        listView.setAdapter(adapter);

        if (adapter.isEmpty()){
            listView.setVisibility(View.GONE);
            tView.setTextSize(26);
            tView.setVisibility(View.VISIBLE);
        }


        //listView.setDismissCallback(new EnhancedListView.OnDismissCallback()) {


        //}

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ProjectActivity.this, "Clicked on item " + adapter.getItem(i), Toast.LENGTH_SHORT).show();
            }
        });

        //listView.enableSwipeToDismiss();
        listView.enableRearranging();

    }
}
