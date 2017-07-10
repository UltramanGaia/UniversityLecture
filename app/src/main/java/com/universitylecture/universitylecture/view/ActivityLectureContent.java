package com.universitylecture.universitylecture.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/25.
 */

public class ActivityLectureContent extends BaseActivity {
    private ImageView lectureImage;
    private TextView lectureName;
    private TextView lectureTime;
    private TextView lectureClassroom;
    private TextView lectureLecturer;
    private TextView lectureCredit;
    private TextView lectureContent;      //内容
    private TextView lectureSponsor;      //讲座主办方
    private TextView lectureCo_sponsor;

    private Lecture lecture;//传进来的讲座

    //标题栏部件
    private Button back;
    private TextView title_in_title_bar_of_launch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lecture_content);

        lecture = (Lecture) getIntent().getSerializableExtra("lecture_item");

        initView();
        initButton();
    }

    private void initButton(){
        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityLectureContent.super.onBackPressed();
            }
        });
    }

    private void initView(){
        lectureImage = (ImageView) findViewById(R.id.lecture_image_in_lecture_content);
        //lectureImage.setImageResource(lecture.getImageId());

        lectureName = (TextView) findViewById(R.id.lecture_name_in_lecture_content);
        lectureName.setText("讲座题目：" + lecture.getTitle());

        lectureTime = (TextView) findViewById(R.id.lecture_time_in_lecture_content);
        lectureTime.setText("讲座时间：" + lecture.getTime());

        lectureClassroom = (TextView) findViewById(R.id.lecture_classroom_in_lecture_content);
        lectureClassroom.setText("讲座地点：" + lecture.getClassroom());

        lectureLecturer = (TextView) findViewById(R.id.lecture_lecturer_in_lecture_content);
        lectureLecturer.setText("主讲人：" + lecture.getLecturer());

        lectureCredit = (TextView) findViewById(R.id.lecture_credit_in_lecture_content);
        lectureCredit.setText("学分：" + lecture.getCredit());

        lectureContent = (TextView) findViewById(R.id.lecture_content_in_lecture_content);
        lectureContent.setText( lecture.getContent());

        lectureSponsor = (TextView) findViewById(R.id.lecture_sponsor_in_lecture_content);
        lectureSponsor.setText("主办方：" + lecture.getSponsor());

        lectureCo_sponsor = (TextView) findViewById(R.id.lecture_co_sponsor_in_lecture_content);
        lectureCo_sponsor.setText("协办方：" + lecture.getCo_sponsor());

        title_in_title_bar_of_launch = (TextView) findViewById(R.id.title_in_title_bar);
        title_in_title_bar_of_launch.setText("");
    }
}
