package com.universitylecture.universitylecture.view;

/**
 * Created by fengqingyundan on 2017/5/28.
 */
//讲座发布者类
public class LecturePublisher {
    private String ID;
    private String name;
    private String school;

    public LecturePublisher(String pID , String pname , String pschool)
    {
        ID = pID;
        name = pname;
        school = pschool;
    }

    public void setID(String pID){
        ID = pID;
    }

    public String getID(){
        return ID;
    }

    public void setName(String pname){
        name = pname;
    }

    public String getName(){
        return name;
    }

    public void setSchool(String pschool){
        school = pschool;
    }

    public  String getSchool(){
        return school;
    }
}
