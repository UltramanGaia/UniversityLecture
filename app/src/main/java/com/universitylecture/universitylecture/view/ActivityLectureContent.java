package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.PopWindowInLectureContent;
import com.universitylecture.universitylecture.util.Constant;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
    private String alarm;//判断是否要使闹钟设置按钮可见

    //标题栏部件
    private Button back;
    private TextView title_in_title_bar_of_launch;
    private Button alarmSet;
    //导航去这个讲座
    private Button bt_davi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lecture_content);

        lecture = (Lecture) getIntent().getSerializableExtra("lecture_item");
        alarm = getIntent().getStringExtra("alarm");

        initView();
        initButton();
    }

    private void initButton(){
        back = (Button) findViewById(R.id.go_back_in_lecture_information);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityLectureContent.super.onBackPressed();
            }
        });

        bt_davi = (Button) findViewById(R.id.bt_navigation);
        bt_davi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ////
                Intent intent = new Intent(ActivityLectureContent.this,NaviBaseWalkActivity.class);
                intent.putExtra("positionx",113.4075616300106);
                intent.putExtra("positiony",23.04584466695405);
                startActivity(intent);
            }
        });

        if( alarm.equals("set") ){
            alarmSet = (Button) findViewById(R.id.alarmSet);
            alarmSet.setVisibility(View.VISIBLE);
            alarmSet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopWindowInLectureContent popWindow = new PopWindowInLectureContent( ActivityLectureContent.this , lecture);
                    popWindow.showPopupWindow(findViewById(R.id.alarmSet));
                }
            });
        }
    }

    private void initView(){
        lectureImage = (ImageView) findViewById(R.id.lecture_image_in_lecture_content);
        //lectureImage.setImageResource(lecture.getImageId());

        lectureName = (TextView) findViewById(R.id.lecture_name_in_lecture_content);
        lectureName.setText("" + lecture.getTitle());

        lectureTime = (TextView) findViewById(R.id.lecture_time_in_lecture_content);
        lectureTime.setText("" + lecture.getTime().split(":")[0] + ":" + lecture.getTime().split(":")[1]);

        lectureClassroom = (TextView) findViewById(R.id.lecture_classroom_in_lecture_content);
        lectureClassroom.setText("" + lecture.getClassroom());

        lectureLecturer = (TextView) findViewById(R.id.lecture_lecturer_in_lecture_content);
        lectureLecturer.setText("" + lecture.getLecturer());

        lectureCredit = (TextView) findViewById(R.id.lecture_credit_in_lecture_content);
        lectureCredit.setText("" + lecture.getCredit());

        lectureContent = (TextView) findViewById(R.id.lecture_content_in_lecture_content);
        lectureContent.setText( lecture.getContent());

        lectureSponsor = (TextView) findViewById(R.id.lecture_sponsor_in_lecture_content);
        lectureSponsor.setText("" + lecture.getSponsor());

        lectureCo_sponsor = (TextView) findViewById(R.id.lecture_co_sponsor_in_lecture_content);
        lectureCo_sponsor.setText("" + lecture.getCo_sponsor());

        title_in_title_bar_of_launch = (TextView) findViewById(R.id.title_in_title_bar);
        title_in_title_bar_of_launch.setText("");

        Glide.with(MyApplication.getContext()).load(Constant.IMAGE_URI + lecture.getImagePath()).into(lectureImage);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1 ) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //获取了扫描结果，进行处理
                    final String lectureId = lecture.getID();
                    final String result = bundle.getString(CodeUtils.RESULT_STRING);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String m = Object2JSONUtil.joinLecture(lectureId,result);
                            String t = HttpUtilJSON.doPost(m,"joinLecture");
                            String message = JSON2ObjectUtil.getMessage(t);
//                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                            if(message.equals("OK"))
                                OutputMessage.outputMessage("扫码成功");
                            else
                                OutputMessage.outputMessage("扫码失败");
                        }
                    }).start();



                } else if (bundle.getInt(CodeUtils.RESULT_TYPE)== CodeUtils.RESULT_FAILED) {
                    Toast.makeText(ActivityLectureContent.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
