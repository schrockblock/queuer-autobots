package com.autobots.queuer.models;

import android.content.Context;

import com.autobots.queuer.databases.ProjectDataSource;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mammothbane on 1/17/14.
 */
public class Project implements Serializable {
    private int id;
    private int localId;
    private String name;
    private int color;
    private ArrayList<Task> tasks;

    public Project(Context context, int id, String name) throws SQLException {
        this.id = id;
        this.name = name;

        ProjectDataSource projectDataSource = new ProjectDataSource(context);
        projectDataSource.open();
        localId = projectDataSource.createProject(name, 0, id, new Date(), new Date()).localId;
        projectDataSource.close();
    }

    public Project(){

    }

    public int getLocalId() {
        return localId;
    }

    public void setLocalId(int localId) {
        this.localId = localId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean hasTasks() { return !tasks.isEmpty();}

    public ArrayList<Task> getTaskList() { return tasks; }

    public void setTaskList(ArrayList<Task> tasks) { this.tasks = tasks;}



}