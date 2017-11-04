package com.re_coded.example.android.taskapp;

/**
 * Created by Lenovo on 11/1/2017.
 */

public class Task {
    private int id;
    private String title;
    private String description;
    private Long date;
    private Boolean done;

    public Task(int id, String title, String description, long date, Boolean done) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getDate() {
        return date;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }
}
