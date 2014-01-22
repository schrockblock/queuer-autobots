package com.autobots.queuer.models;

/**
 * Created by Moseph on 1/19/14.
 */
public class Task {
    private int id;
    private String name;

    public Task() {
        id = 0;
        name = "";
    }

    public Task(int id, String name) {
        this.id = id;
        this.name = name;
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
}
