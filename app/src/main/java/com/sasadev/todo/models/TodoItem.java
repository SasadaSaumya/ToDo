package com.sasadev.todo.models;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class TodoItem extends RealmObject {

    @PrimaryKey
    private String id;

    private String title;
    private String description;
    private String date;
    private boolean status;
    private String tag;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public TodoItem() {
    }

    public TodoItem(String title, String description, String date, boolean status, String tag) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
        this.tag = tag;
    }
}
