package com.universitylecture.universitylecture;

import android.app.Application;
import android.content.Context;

/**
 * Created by fengqingyundan on 2017/6/5.
 */
//为了获得context而建立的类
public class MyApplication extends Application{
    private static Context context;

    public void onCreate(){
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
