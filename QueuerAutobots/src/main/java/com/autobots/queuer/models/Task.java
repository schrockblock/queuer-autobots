package com.autobots.queuer.models;

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

    public Task(int id, String name, int project_id, int local_id, int position, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
        this.local_id = local_id;
        this.position = position;
        this.created_at = created_at;
        this.updated_at = updated_at;
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

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }


    public int getLocal_id() {

        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
