package com.autobots.queuer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.RearrangementListener;
import com.autobots.queuer.models.Project;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by mammothbane on 1/17/14.
 */
public class FeedAdapter extends BaseAdapter implements RearrangementListener {
    private Context context;
    private ArrayList<Project> projects = new ArrayList<Project>();

    public FeedAdapter(Context context, ArrayList<Project> projects) {
        this.context = context;
        this.projects = projects;
    }

    public void remove(int position) {
        projects.remove(position);
        notifyDataSetChanged();
    }

    public boolean isEmpty() {
        return projects.isEmpty();
    }

    @Override
    public int getCount() {
        return projects.size();
    }

    @Override
    public Project getItem(int i) {
        return projects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Nullable
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_project, null);
        }

        ((TextView)view.findViewById(R.id.tv_title)).setText((getItem(i)).getTitle());
        view.findViewById(R.id.ll_project).setBackgroundColor(getItem(i).getColor());
        if(getItem(i).hasTasks())
            ((TextView)view.findViewById(R.id.first_task)).setText(getItem(i).getTaskList().get(0).getName());
        else
            ((TextView)view.findViewById(R.id.first_task)).setVisibility(View.GONE);

        return view;
    }


    @Override
    public void onStartedRearranging() {

    }

    @Override
    public void swapElements(int indexOne, int indexTwo) {
        Project a = projects.get(indexOne);
        Project b = projects.get(indexTwo);

        projects.remove(indexOne);
        projects.add(indexOne, b);

        projects.remove(indexTwo);
        projects.add(indexTwo, a);
    }

    @Override
    public void onFinishedRearranging() {

    }
}
