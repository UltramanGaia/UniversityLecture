package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

/**
 * Created by fengqingyundan on 2017/11/20.
 */

public class Topic implements Serializable {
    private Integer ID;
    private String title;
    private Integer lecture_id;
    private String description;
    private Integer user_id;
    private String time;

    public Topic(Integer ID, String title, String description, Integer user_id, String time) {
        this.ID = ID;
        this.title = title;
        this.lecture_id = 1;
        this.description = description;
        this.user_id = user_id;
        this.time = time;
    }

    public Topic(String title, String description, Integer user_id, String time) {
        this.title = title;
        this.description = description;
        this.user_id = user_id;
        this.time = time;
        this.lecture_id = 1;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getLecture_id() {
        return lecture_id;
    }

    public void setLecture_id(Integer lecture_id) {
        this.lecture_id = lecture_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}