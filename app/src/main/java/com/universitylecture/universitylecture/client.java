package com.universitylecture.universitylecture;

/**
 * Created by fengqingyundan on 2017/5/27.
 */
//学生用户类
public class client {
    private String ID;
    private String name;
    private String studentID;
    private String school;
    private String institude;
    private String email;

    public client( String pid , String pname , String pstudentID , String pschool , String pinstitude , String pemail )
    {
        ID = pid;
        name = pname;
        studentID = pstudentID;
        school = pschool;
        institude = pinstitude;
        email = pemail;
    }

    public void setID(String pid){
        ID = pid;
    }

    public String getID(){
        return ID;
    }

    public void setName(String pname){
        name = pname;
    }

    public String getName()
    {
        return  name;
    }

    public void setStudentID( String pstudentID){
        studentID = pstudentID;
    }

    public String getStudentID(){
        return studentID;
    }

    public void setSchool(String pschool){
        school = pschool;
    }

    public String getSchool(){
        return school;
    }

    public void setInstitude(String pinstitude){
        institude = pinstitude;
    }

    public String getInstitude(){
        return institude;
    }

    public void setEmail(String pemail){
        email = pemail;
    }

    public String getEmail(){
        return email;
    }

}
