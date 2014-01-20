package com.autobots.queuer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.autobots.queuer.R;
import com.autobots.queuer.interfaces.RearrangementListener;
import com.autobots.queuer.models.Task;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Moseph on 1/19/14.
 */
public class ProjectAdapter extends BaseAdapter implements RearrangementListener
{
    private ArrayList<Task> tasks = new ArrayList<Task>();
    private Context context;

    public ProjectAdapter(Context context, ArrayList<Task> tasks) {
        this.tasks = tasks;
        this.context = context;
    }

    public void remove(int position){
        tasks.remove(position);
        notifyDataSetChanged();
    }

    public void insert(Task task, int position){
        tasks.add(position, task);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Task getItem(int i) {
        return tasks.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public boolean isEmpty(){
        return tasks.isEmpty();
    }

    @Nullable
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_task, null);
        }

        ((TextView)view.findViewById(R.id.tl_name)).setText((getItem(position)).getName());



        return view;
    }

    public boolean hasStableIds(){
        return true;
    }

    @Override
    public void onStartedRearranging() {

    }

    @Override
    public void swapElements(int indexOne, int indexTwo) {
        Task temp1 = tasks.get(indexOne);
        Task temp2 = tasks.get(indexTwo);

        tasks.remove(indexOne);
        tasks.add(indexOne, temp2);

        tasks.remove(indexTwo);
        tasks.add(indexTwo, temp1);
    }

    @Override
    public void onFinishedRearranging() {

    }
}
