package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

/**
 * Created by fengqingyundan on 2017/10/8.
 */

public class Comment implements Serializable {
    private String ID;
    private String topicLecture;
    private String question;
    private String description;
    private String askerId;

    public Comment(String mid , String mtopicLecture , String mquestion , String mdescription , String maskerId){
        ID = mid;
        topicLecture = mtopicLecture;
        question = mquestion;
        description = mdescription;
        askerId = maskerId;
    }

    public String getAsker() {
        return askerId;
    }

    public void setAsker(String askerId) {
        this.askerId = askerId;
    }

    public String getID(){
        return ID;
    }

    public String getQuestion() {
        return question;
    }

    public String getDescription() {
        return description;
    }

    public String getTopicLecture(){
        return topicLecture;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setTopicLecture(String topicLecture) {
        this.topicLecture = topicLecture;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
