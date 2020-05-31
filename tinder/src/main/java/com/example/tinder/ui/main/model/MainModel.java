package com.example.tinder.ui.main.model;

import com.example.tinder.mvp.BaseModel;

public class MainModel extends BaseModel {
    private int id;
    private String photo;
    private String user_name;
    private String user_age;
    private String user_work;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_age() {
        return user_age;
    }

    public void setUser_age(String user_age) {
        this.user_age = user_age;
    }

    public String getUser_work() {
        return user_work;
    }

    public void setUser_work(String user_work) {
        this.user_work = user_work;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
