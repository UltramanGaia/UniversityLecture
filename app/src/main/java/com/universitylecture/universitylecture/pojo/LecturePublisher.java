package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

/**
 * Created by fengqingyundan on 2017/5/28.
 */
//讲座发布者类
public class LecturePublisher implements Serializable{

    private static final long serialVersionUID = 1L;

    private String ID;
    private String phoneNumber;
    private String password;

    public LecturePublisher(String pphoneNumber , String ppassword)
    {
        phoneNumber = pphoneNumber;
        password = ppassword;
    }

    public LecturePublisher() {

    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
