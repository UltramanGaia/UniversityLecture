package com.universitylecture.universitylecture.view.functionActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import static android.view.Window.FEATURE_NO_TITLE;

//import com.universitylecture.universitylecture.pojo.SimpleDividerItemDecoration;

/**
 * Created by fengqingyundan on 2017/8/23.
 */
//二维码页面的二维码
public class MyTwoDCodeActivity extends Activity {
    private ImageView myTwoDCode;
    private TextView title;
    private Button back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //supportRequestWindowFeature(FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏不会被隐藏但activity布局会扩展到导航栏所在位置
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //半透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //半透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_my_two_d_code);

        initView();
    }

    private void initView(){
        //生成二维码照片
        myTwoDCode = (ImageView) findViewById(R.id.myTwoDCode);
        Intent intent = getIntent();
        String content = intent.getStringExtra("user_id");//二维码内容
        Bitmap mBitmap = CodeUtils.createImage(content, 400, 400, null);
        myTwoDCode.setImageBitmap(mBitmap);

        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTwoDCodeActivity.super.onBackPressed();
            }
        });
    }
}
