package com.universitylecture.universitylecture.adapter;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengqingyundan on 2017/6/26.
 */
//活动的管理器，用来点击退出应用时退出所有活动
public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for( Activity activity : activities ){
            if( !activity.isFinishing()){
                activity.finish();
            }
        }
    }


}
