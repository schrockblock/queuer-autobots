package com.autobots.queuer.models;

import android.support.v7.appcompat.R;

import java.security.PrivateKey;

/**
 * Created by mammothbane on 1/17/14.
 */
public class Project {
    private int id;
    private String title;
    private int color;

    public Project(int id, String title) {
        this.id = id;
        this.title = title;
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

    public void setColor(int color) {
        this.color = color;
    }
}
