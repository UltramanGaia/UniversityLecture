package com.universitylecture.universitylecture.pojo;

import java.util.ArrayList;

/**
 * Created by fengqingyundan on 2017/10/27.
 */
//学校类，掌管其学校举办的讲座

public class School {
    private String ID;
    private String name;
    private ArrayList<Lecture> lectures;

    public School(String pID , String pname ){
        ID = pID;
        name = pname;
        lectures = new ArrayList<>();
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

    public ArrayList<Lecture> getLectures(){
        return lectures;
    }
}
