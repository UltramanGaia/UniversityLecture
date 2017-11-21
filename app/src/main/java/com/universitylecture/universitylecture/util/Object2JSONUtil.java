package com.universitylecture.universitylecture.util;

import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

/**
 * Created by helloworld on 2017/9/13.
 */

public class Object2JSONUtil {

    public static String login(String phoneNumber,String password) {
        JSONObject js = new JSONObject();

        try {
            js.put("phoneNumber", phoneNumber);
            js.put("password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String verifyCode(String phoneNumber,String code) {
        JSONObject js = new JSONObject();

        try {
            js.put("phoneNumber", phoneNumber);
            js.put("code",code);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String register(User user) {

        JSONObject js = new JSONObject();

        try {
            js.put("phoneNumber", user.getPhoneNumber());
            js.put("password", user.getPassword());
            js.put("sex",user.getSex());
            js.put("name",user.getName());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String isLecturePublisher(String phoneNumber) {
        JSONObject js = new JSONObject();

        try {
            js.put("phoneNumber", phoneNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String selectLecture(String dateTime,String institute,String page) {
        JSONObject js = new JSONObject();

        try {
            js.put("dateTime",dateTime);
            js.put("institute",institute);
            js.put("page",page);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String addLecture(Lecture lecture) {

        JSONObject js = new JSONObject();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            js.put("title",lecture.getTitle());
            js.put("dateTime", lecture.getTime());
            js.put("classroom",lecture.getClassroom());
            js.put("institute",lecture.getInstitute());
            js.put("introduction",lecture.getIntroduction());
            js.put("lecturer",lecture.getLecturer());
            js.put("credit",lecture.getCredit());
            js.put("content",lecture.getContent());
            js.put("sponsor",lecture.getSponsor());
            js.put("co_sponsor",lecture.getCo_sponsor());
            js.put("image",lecture.getImagePath());
            js.put("latitude",lecture.getLatitude());
            js.put("longitude",lecture.getLongitude());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return String.valueOf(js);
    }

    public static String joinLecture(String lectureId,String userId) {
        JSONObject js = new JSONObject();

        try {
            js.put("userId", Integer.valueOf(userId));
            js.put("lectureId", Integer.valueOf(lectureId));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String myLecture(Integer userId) {
        JSONObject js = new JSONObject();

        try {
            js.put("userId", userId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return String.valueOf(js);
    }

    public static String selectTopics(String page) {
        JSONObject js = new JSONObject();
        try {
            js.put("page",page);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return String.valueOf(js);
    }

    public static String selectComments(Integer topicID, String page) {
        JSONObject js = new JSONObject();
        try {
            js.put("page",page);
            js.put("topicID", topicID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return String.valueOf(js);
    }
}
