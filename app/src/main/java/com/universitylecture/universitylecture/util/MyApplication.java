package com.universitylecture.universitylecture.util;

import android.app.Application;
import android.content.Context;

/**
 * Created by helloworld on 2017/6/5.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

}
