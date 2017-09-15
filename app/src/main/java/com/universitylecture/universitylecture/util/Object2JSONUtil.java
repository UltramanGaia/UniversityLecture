package com.universitylecture.universitylecture.util;

import com.universitylecture.universitylecture.pojo.User;

import org.json.JSONException;
import org.json.JSONObject;

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
}
