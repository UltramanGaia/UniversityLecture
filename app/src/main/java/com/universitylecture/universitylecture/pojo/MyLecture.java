package com.universitylecture.universitylecture.pojo;

import java.io.Serializable;

public class MyLecture implements Serializable{
	
	private String userID;
	private String lectureID;
	
	public MyLecture(String uID,String lID) {
		
		userID = uID;
		lectureID = lID;
	}
	
	public MyLecture() {
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLectureID() {
		return lectureID;
	}

	public void setLectureID(String lectureID) {
		this.lectureID = lectureID;
	}
	
	
}
