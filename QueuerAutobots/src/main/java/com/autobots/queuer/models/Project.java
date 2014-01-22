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
    private String name;
    private int color;
    private ArrayList<Task> tasks;

    
    public Project(int id, String name, int color) {
        this.id = id;
        this.name = name;
        this.color = color;
        
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

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
