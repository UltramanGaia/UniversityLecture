package com.universitylecture.universitylecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/14.
 */
//设置页面活动
public class SettingActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
    }
}
