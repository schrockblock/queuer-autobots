package com.autobots.queuer.models;

import java.util.Date;

import android.content.Context;

import com.autobots.queuer.databases.TaskDataSource;

import java.util.Date;

/**
 * Created by Moseph on 1/19/14.
 */
public class Task {
    private int id;
    private String name;
    private int project_id;
    private int local_id;
    private int position;
    private Date created_at;
    private Date updated_at;
    private boolean complete;

    public Task(Context context, int id, String name, int project_id, int position, boolean complete, Date created_at, Date updated_at) throws Exception{
        this.id = id;
        this.name = name;
        this.project_id = project_id;
        this.position = position;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.complete = complete;

        TaskDataSource datasource = new TaskDataSource(context);
        datasource.open();
        setLocalId(datasource.createTask(name,project_id, id, position, complete).local_id);
        datasource.close();
    }

    public Task() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectId() {
        return project_id;
    }

    public void setProjectId(int project_id) {
        this.project_id = project_id;
    }


    public int getLocalId() {

        return local_id;
    }

    public void setLocalId(int local_id) {
        this.local_id = local_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setComplete(boolean finished) { complete = finished; }

    public boolean isComplete() {return complete;}

}
