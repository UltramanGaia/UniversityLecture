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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.util.UploadUtil;

import java.io.File;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/14.
 */

public class LaunchActivity extends BaseActivity {
    private static final String TAG = "LaunchActivity";

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

    private double lecture_latitude = 0;
    private double lecture_longitude = 0;

    //time和institude的linearlayout,用来处理点击事件
    private View TimeInLaunch;
    private View InstituteInLaunch;

    //标题栏部件
    private Button back;
    private TextView title_in_title_bar_of_launch;
    private Button submit;

    private TextView lecture_position;
    private Button choose_position;

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
                final String lecturer = lectureLecturer.getText().toString();
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
                String time = "2017-09-22 18:30:00";
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
                                    credit, content, sponsor, co_sponsor, picPath, lecture_latitude, lecture_longitude);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    /*Lecture returnLecture = (Lecture) HttpUtil.doPost(sendLecture, "AddLectureServlet");*/
                                    String content = UploadUtil.uploadFile(file,"upload");

                                    String imageUri = JSON2ObjectUtil.getMessage(content);
                                    if (imageUri == null)
                                        OutputMessage.outputMessage("发布讲座失败");
                                    else {
                                        sendLecture.setImagePath(imageUri);
//                                      Lecture returnLecture = (Lecture) HttpUtil.doPost(sendLecture,"AddLectureServlet");
                                        /*String m = Object2JSONUtil.addLecture(sendLecture);
                                        String t = HttpUtilJSON.doPost(m,"addLecture");*/
                                        String message = JSON2ObjectUtil.getMessage(HttpUtilJSON.doPost(Object2JSONUtil.addLecture(sendLecture),"addLecture"));
                                        if (message != null)
                                            OutputMessage.outputMessage("发布讲座成功");
                                        else
                                            OutputMessage.outputMessage("发布讲座失败");
                                        LaunchActivity.super.onBackPressed();
                                    }
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

        choose_position = (Button)findViewById(R.id.choose_position);
        choose_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( LaunchActivity.this , MapActivity.class);
                startActivityForResult(intent , 4);
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

        lecture_position = (TextView) findViewById(R.id.lecture_position);

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
                    //Uri uri = data.getData();
                    Uri uri = geturi(data);//解决小米手机图片问题

                    Log.e(TAG, "uri = " + uri);
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
                            Log.e(TAG, "image path : " + path );
                            if (path.endsWith("jpg") || path.endsWith("png")) {
                                picPath = path;
                                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                                lecture_poster.setImageBitmap(bitmap);
                            } else {
                                alert();
                            }
                        } else {
                            Log.e(TAG, "onActivityResult: cursor is null");
                            alert();
                        }

                    } catch (Exception e) {
                    }
                }
                break;
            case 4:
                if(resultCode == RESULT_OK){
                   lecture_latitude = data.getDoubleExtra("latitude",0);
                   lecture_longitude = data.getDoubleExtra("longitude",0);
                   lecture_position.setText(lecture_latitude + "," + lecture_longitude);
                    Log.d(TAG, "onActivityResult: " + lecture_position.getText().toString() );
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 解决小米手机上获取图片路径为null的情况
     * @param intent
     * @return
     */
    public Uri geturi(android.content.Intent intent) {
        Uri uri = intent.getData();
        String type = intent.getType();
        if (uri.getScheme().equals("file") && (type.contains("image/"))) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                ContentResolver cr = this.getContentResolver();
                StringBuffer buff = new StringBuffer();
                buff.append("(").append(MediaStore.Images.ImageColumns.DATA).append("=")
                        .append("'" + path + "'").append(")");
                Cursor cur = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        new String[] { MediaStore.Images.ImageColumns._ID },
                        buff.toString(), null, null);
                int index = 0;
                for (cur.moveToFirst(); !cur.isAfterLast(); cur.moveToNext()) {
                    index = cur.getColumnIndex(MediaStore.Images.ImageColumns._ID);
                    // set _id value
                    index = cur.getInt(index);
                }
                if (index == 0) {
                    // do nothing
                } else {
                    Uri uri_temp = Uri
                            .parse("content://media/external/images/media/"
                                    + index);
                    if (uri_temp != null) {
                        uri = uri_temp;
                    }
                }
            }
        }
        return uri;
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
