package com.universitylecture.universitylecture.view.functionActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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

    /**
     * 用于控制NavigationBar的隐藏和显示
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                      | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
