package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

/**
 * Created by fengqingyundan on 2017/11/20.
 */

public class Comment implements Serializable {
    private Integer ID;
    private String content;
    private Integer topic_id;
    private Integer user_id;
    private String username;
    private String time;

    public Comment(Integer ID, String content, Integer topic_id, Integer user_id, String username, String time) {
        this.ID = ID;
        this.content = content;
        this.topic_id = topic_id;
        this.user_id = user_id;
        this.username = username;
        this.time = time;
    }

    public Comment() {

    }

    public Comment(String content, Integer topic_id, Integer user_id, String username, String time) {
        this.content = content;
        this.topic_id = topic_id;
        this.user_id = user_id;
        this.username = username;
        this.time = time;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(Integer topic_id) {
        this.topic_id = topic_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
