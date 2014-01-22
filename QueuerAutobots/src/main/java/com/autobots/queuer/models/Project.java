package com.autobots.queuer.models;

import android.support.v7.appcompat.R;

import java.io.Serializable;
import java.security.PrivateKey;
import java.util.ArrayList;

/**
 * Created by mammothbane on 1/17/14.
 */
public class Project implements Serializable {
    private int id;
    private String title;
    private int color;
    private ArrayList<Task> tasks;

    public Project(int id, String title, int color) {
        this.id = id;
        this.title = title;
        this.color = color;
        tasks = new ArrayList<Task>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getColor() {
        return color;
    }

    public boolean hasTasks(){ return !tasks.isEmpty();}

    public ArrayList<Task> getTaskList() { return tasks;}

    public void setColor(int color) {
        this.color = color;
    }
}
