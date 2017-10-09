package com.universitylecture.universitylecture.pojo;

/**
 * Created by fengqingyundan on 2017/10/8.
 */

public class Comment {
    private String ID;
    private String topicLecture;
    private String question;
    private String description;
    private String asker;

    public Comment(String mid , String mtopicLecture , String mquestion , String mdescription , String masker){
        ID = mid;
        topicLecture = mtopicLecture;
        question = mquestion;
        description = mdescription;
        asker = masker;
    }

    public String getAsker() {
        return asker;
    }

    public void setAsker(String asker) {
        this.asker = asker;
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
