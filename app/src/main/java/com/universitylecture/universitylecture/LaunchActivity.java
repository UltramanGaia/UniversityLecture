package com.universitylecture.universitylecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/14.
 */

public class LaunchActivity extends AppCompatActivity {

    private EditText lectureTitle;
    private EditText lectureClassroom;
    private EditText lectureIntroduction;
    private EditText lectureLecturer;
    private EditText lectureCredit;
    private EditText lectureContent;
    private EditText lectureSponsor;
    private EditText lectureCoSponsor;

    private TextView lectureTimeText;
    private TextView lectureInstituteText;

    private ImageView lecture_poster;

    //time和institude的linearlayout,用来处理点击事件
    private View TimeInLaunch;
    private View InstituteInLaunch;

    //标题栏部件
    private Button back;
    private TextView title_in_title_bar_of_launch;
    private Button submit;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_launch);

        initView();
        initButton();
    }

    private void initButton(){
        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaunchActivity.super.onBackPressed();
            }
        });


        submit = (Button) findViewById(R.id.save_information);
        submit.setVisibility(View.VISIBLE);
        submit.setText("提交");
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v ){
                String title = lectureTitle.getText().toString();
                String classroom = lectureClassroom.getText().toString();
                String introduction = lectureIntroduction.getText().toString();
                String lecturer = lectureLecturer.getText().toString();
                String credit = lectureCredit.getText().toString();
                String content = lectureContent.getText().toString();
                String sponsor = lectureSponsor.getText().toString();
                String co_sponsor = lectureCoSponsor.getText().toString();
                String time = lectureTimeText.getText().toString();
                String institute = lectureInstituteText.getText().toString();

                //上传数据到服务器的数据写在这里


                Toast.makeText( getApplicationContext() , "发布成功" , Toast.LENGTH_SHORT).show();
            }
        });


        TimeInLaunch = (LinearLayout)findViewById(R.id.time_in_launch);
        TimeInLaunch.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent( LaunchActivity.this , SelectInformationForLaunch.class);
                startActivityForResult(intent , 1);
            }
        });

        InstituteInLaunch = (LinearLayout)findViewById(R.id.institute_in_launch);
        InstituteInLaunch.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent( LaunchActivity.this , selectInstituteForLaunch.class);
                startActivityForResult(intent , 2);
            }
        });

        lecture_poster = (ImageView) findViewById(R.id.lecture_poster);
        lecture_poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加载图片的逻辑写这里

                Toast.makeText(getApplicationContext(),"点击成功" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(){
        lectureTitle = (EditText) findViewById(R.id.lecture_title);
        lectureClassroom = (EditText) findViewById(R.id.lecture_classroom);
        lectureIntroduction = (EditText) findViewById(R.id.lecture_introduction);
        lectureLecturer = (EditText) findViewById(R.id.lecture_lecturer);
        lectureCredit = (EditText) findViewById(R.id.lecture_credit);
        lectureContent = (EditText) findViewById(R.id.lecture_content);
        lectureCredit = (EditText) findViewById(R.id.lecture_credit);
        lectureSponsor = (EditText) findViewById(R.id.lecture_sponsor);
        lectureCoSponsor = (EditText) findViewById(R.id.lecture_cosponsor);

        lectureTimeText = (TextView) findViewById(R.id.time_text_in_launch);
        lectureInstituteText = (TextView) findViewById(R.id.institute_text_in_launch);

        title_in_title_bar_of_launch = (TextView) findViewById(R.id.title_in_title_bar);
        title_in_title_bar_of_launch.setText("发布讲座");


    }

    //@Override
    protected void onActivityResult( int requestCode , int resultCode , Intent data){
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK){
                    String returnTime = data.getStringExtra("time");
                    lectureTimeText.setText(returnTime);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    String returnInstitute = data.getStringExtra("institute");
                    lectureInstituteText.setText(returnInstitute);
                }
                break;
        }
    }
}
