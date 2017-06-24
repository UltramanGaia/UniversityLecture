package com.universitylecture.universitylecture.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.util.UploadUtil;

import java.io.File;

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
    private String picPath = null;

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
                /*String title = lectureTitle.getText().toString();
                String classroom = lectureClassroom.getText().toString();
                String introduction = lectureIntroduction.getText().toString();
                String lecturer = lectureLecturer.getText().toString();
                String credit = lectureCredit.getText().toString();
                String content = lectureContent.getText().toString();
                String sponsor = lectureSponsor.getText().toString();
                String co_sponsor = lectureCoSponsor.getText().toString();
                String time = lectureTimeText.getText().toString();
                String institute = lectureInstituteText.getText().toString();*/
                String title = "as";
                String classroom = "asfas";
                String introduction = "asf";
                String lecturer = "scv";
                String credit = "xzv";
                String content = "sacxz";
                String sponsor = "vzx";
                String co_sponsor = "asc";
                String time = "2017:03:04 18:30:00";
                String institute = "asf";

                //上传数据到服务器的数据写在这里
                if (title.isEmpty() || classroom.isEmpty() || introduction.isEmpty() || lecturer.isEmpty() || credit.isEmpty() || content.isEmpty() ||
                        sponsor.isEmpty() || co_sponsor.isEmpty() || time.isEmpty() || institute.isEmpty())
                    Toast.makeText(LaunchActivity.this,"请填写相关信息",Toast.LENGTH_SHORT).show();
                else {
                    if (picPath == null)
                        Toast.makeText(LaunchActivity.this, "请选择图片！", Toast.LENGTH_SHORT).show();
                    else {
                        final File file = new File(picPath);
                        if (file != null) {
                            final Lecture sendLecture = new Lecture(title, time, classroom, institute, introduction, lecturer,
                                    credit, content, sponsor, co_sponsor, picPath);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    /*Lecture returnLecture = (Lecture) HttpUtil.doPost(sendLecture, "AddLectureServlet");*/
                                    Lecture returnLecture = UploadUtil.uploadFile(file,sendLecture,"UploadImageServlet");
                                    if (returnLecture == null)
                                        OutputMessage.outputMessage("发布失败");
                                    else
                                        OutputMessage.outputMessage("发布成功");
                                }
                            }).start();
                        }
                    }
                }

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
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 3);
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
            case 3:
                if (resultCode == Activity.RESULT_OK) {
                    /**
                     * 当选择的图片不为空的话，在获取到图片的途径
                     */
                    Uri uri = data.getData();
                    //Log.e(TAG, "uri = " + uri);
                    try {
                        String[] pojo = { MediaStore.Images.Media.DATA };

                        Cursor cursor = managedQuery(uri, pojo, null, null, null);
                        if (cursor != null) {
                            ContentResolver cr = this.getContentResolver();
                            int colunm_index = cursor
                                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(colunm_index);
                            /***
                             * 这里加这样一个判断主要是为了第三方的软件选择，比如：使用第三方的文件管理器的话，你选择的文件就不一定是图片了，
                             * 这样的话，我们判断文件的后缀名 如果是图片格式的话，那么才可以
                             */
                            if (path.endsWith("jpg") || path.endsWith("png")) {
                                picPath = path;
                                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                                lecture_poster.setImageBitmap(bitmap);
                            } else {
                                alert();
                            }
                        } else {
                            alert();
                        }

                    } catch (Exception e) {
                    }
                }

                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void alert() {
        Dialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("您选择的不是有效的图片")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        picPath = null;
                    }
                }).create();
        dialog.show();
    }
}
