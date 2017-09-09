package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

/**
 * Created by fengqingyundan on 2017/5/27.
 */
//学生用户类
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String password;
    private String sex;
    private String phoneNumber;
    private String studentNumber;
    private String code;
    private String message;
    private String isLecturePublisher;

    public String getIsLecturePublisher() {
        return isLecturePublisher;
    }

    public void setIsLecturePublisher(String isLecturePublisher) {
        this.isLecturePublisher = isLecturePublisher;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public User(Integer uID , String uName , String uPassword , String uSex , String uPhoneNumber)
    {
        id = uID;
        name = uName;
        password = uPassword;
        sex = uSex;
        phoneNumber = uPhoneNumber;
    }

    public User(String uName,String ustudentNumber,String uPassword,String uSex,String uPhoneNumber) {
        name = uName;
        studentNumber = ustudentNumber;
        password = uPassword;
        sex = uSex;
        phoneNumber = uPhoneNumber;
    }

    public User(Integer uID , String uName , String uPassword , String uSex , String uPhoneNumber,String uStudentNumber)
    {
        id = uID;
        name = uName;
        password = uPassword;
        sex = uSex;
        phoneNumber = uPhoneNumber;
        studentNumber = uStudentNumber;
    }

    public User(String uPhoneNumber,String uPassword) {
        phoneNumber = uPhoneNumber;
        password = uPassword;
    }

    public User() {

    }
}
