package com.universitylecture.universitylecture.view.functionActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.util.UploadUtil;
import com.universitylecture.universitylecture.view.sidebar.LaunchActivity;
import com.universitylecture.universitylecture.view.tool.BaseActivity;

import java.io.File;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/10/9.
 */

public class AskQuestionActivity extends BaseActivity {
    private EditText topicLecture;
    private EditText question;
    private EditText description;

    //标题栏部件
    private Button back;
    private Button submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);

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

        setContentView(R.layout.activity_ask_question);

        initView();//初始化view
        initButton();//初始化按键
    }

    private void initView(){
        topicLecture = (EditText) findViewById(R.id.topicLectureOfQuestion);
        question = (EditText) findViewById(R.id.questionOfQuestion);
        description = (EditText) findViewById(R.id.description_of_question);
    }

    private  void initButton(){
        back = (Button) findViewById(R.id.go_back_in_ask_question_title);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AskQuestionActivity.super.onBackPressed();
            }
        });


        submit = (Button) findViewById(R.id.save_information_in_ask_question_title);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v ){
                String topicLectureToString = topicLecture.getText().toString();
                String questionToQuestion = question.getText().toString();
                String descriptionToString = description.getText().toString();
                String askerId = getIntent().getStringExtra("user_id");
                //上传数据到服务器的数据写在这里


            }
        });
    }
}
