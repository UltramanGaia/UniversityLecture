package com.universitylecture.universitylecture.view.LectureCircleActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.Comment;
import com.universitylecture.universitylecture.pojo.Topic;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/11/16.
 */

public class AnswerQuestionActivity extends BaseActivity {
    private Comment comment;

    private EditText editText;
    private Button goback;
    private Button submit;

    private Topic topic;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_answer_question);

        topic = (Topic) getIntent().getSerializableExtra("topic_item");
        initView();//初始化view
    }

    private void initView(){
        editText = (EditText) findViewById(R.id.answer_quetion);

        goback = (Button) findViewById(R.id.go_back_in_answer_question_title);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerQuestionActivity.super.onBackPressed();
            }
        });

        submit = (Button) findViewById(R.id.submit_answer_in_answer_question_title);
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v ){
                String answerContent = editText.getText().toString();
                //获取当前时间
                long time=System.currentTimeMillis();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String t=format.format(new Date(time)).split(" ")[0];
                String topic_id = getIntent().getStringExtra("topic_id");
                comment = new Comment(answerContent, Integer.parseInt(topic_id) ,PersonalInformation.id,PersonalInformation.name,t);


                //上传数据到服务器的数据写在这里


            }
        });
    }
}
