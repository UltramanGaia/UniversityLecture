package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

/**
 * Created by fengqingyundan on 2017/11/15.
 */

public class Answer implements Serializable {
    private String ID;
    private String answerContent;
    private Integer answererId;
    private String nameOfAnswer;
    private String time;

    public Answer(String ID, String answerContent, Integer answererId, String nameOfAnswer, String time) {
        this.ID = ID;
        this.answerContent = answerContent;
        this.answererId = answererId;
        this.nameOfAnswer = nameOfAnswer;
        this.time = time;
    }

    public Answer(String answerContent, Integer answererId, String nameOfAnswer, String time) {
        this.answerContent = answerContent;
        this.answererId = answererId;
        this.nameOfAnswer = nameOfAnswer;
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public Integer getAnswererId() {
        return answererId;
    }

    public void setAnswererId(Integer answererId) {
        this.answererId = answererId;
    }

    public String getNameOfAnswer() {
        return nameOfAnswer;
    }

    public void setNameOfAnswer(String nameOfAnswer) {
        this.nameOfAnswer = nameOfAnswer;
    }
}
