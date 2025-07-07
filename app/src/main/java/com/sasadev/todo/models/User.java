package com.sasadev.todo.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private String username;

    private byte[] password;
    private String mobile;
    private byte[] salt;
    private RealmList<TodoItem> todoItems = new RealmList<>();

    public RealmList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void setTodoItems(RealmList<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }






}
