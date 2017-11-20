package com.universitylecture.universitylecture.util;

import com.universitylecture.universitylecture.pojo.Comment;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.Topic;
import com.universitylecture.universitylecture.pojo.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by helloworld on 2017/9/13.
 */

public class JSON2ObjectUtil {

    public static User login(String content) {
        if (content == null)
            return null;
        User user = new User();
        try {
            JSONObject object = new JSONObject(content);
            user.setId(object.getInt("id"));
            user.setPhoneNumber(object.getString("phoneNumber"));
            user.setName(object.getString("name"));
            user.setPassword(object.getString("password"));
            user.setStudentNumber(object.getString("studentNumber"));
            user.setSex(object.getString("sex"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static String getMessage(String content) {
        try {
            JSONObject object = new JSONObject(content);
            return object.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Lecture> getLectures(String content) {

        ArrayList<Lecture> lectures = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0;i < jsonArray.length();i++) {
                Lecture lecture = new Lecture();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                lecture.setID(jsonObject.getString("id"));
                lecture.setTitle(jsonObject.getString("title"));
                lecture.setTime(sdf.format(Long.parseLong(jsonObject.getString("dateTime"))));
                lecture.setClassroom(jsonObject.getString("classroom"));
                lecture.setInstitute(jsonObject.getString("institute"));
                lecture.setIntroduction(jsonObject.getString("introduction"));
                lecture.setLecturer(jsonObject.getString("lecturer"));
                lecture.setCredit(jsonObject.getString("credit"));
                lecture.setContent(jsonObject.getString("content"));
                lecture.setSponsor(jsonObject.getString("sponsor"));
                lecture.setCo_sponsor(jsonObject.getString("co_sponsor"));
                lecture.setImagePath(jsonObject.getString("image"));

                lectures.add(lecture);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lectures;

    }

    public static ArrayList<Topic> getTopics(String content) {

        ArrayList<Topic> topics = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0;i < jsonArray.length();i++) {
                Topic topic = new Topic();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                topic.setLecture_id(Integer.valueOf(jsonObject.getString("lectureId")));
                topic.setTitle(jsonObject.getString("title"));
                topic.setTime(sdf.format(Long.parseLong(jsonObject.getString("time"))));
                topic.setDescription(jsonObject.getString("description"));
                topic.setUser_id(Integer.valueOf(jsonObject.getString("userId")));
                topics.add(topic);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return topics;

    }

    public static ArrayList<Comment> getComments(String content) {

        ArrayList<Comment> comments = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0;i < jsonArray.length();i++) {
                Comment comment = new Comment();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                comment.setTopic_id(Integer.valueOf(jsonObject.getString("topicId")));
                comment.setUser_id(Integer.valueOf(jsonObject.getString("userId")));
                comment.setContent(jsonObject.getString("content"));
                comment.setUsername(jsonObject.getString("username"));
                comment.setTime(sdf.format(Long.parseLong(jsonObject.getString("time"))));
                comments.add(comment);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return comments;

    }
}
