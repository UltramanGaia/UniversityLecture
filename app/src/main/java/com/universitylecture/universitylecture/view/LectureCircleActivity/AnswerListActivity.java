package com.universitylecture.universitylecture.view.LectureCircleActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lcodecore.extextview.ExpandTextView;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.AnswerAdapter;
import com.universitylecture.universitylecture.adapter.CommentAdapter;
import com.universitylecture.universitylecture.pojo.Answer;
import com.universitylecture.universitylecture.pojo.Comment;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.view.functionActivity.LectureContentActivity;
import com.universitylecture.universitylecture.view.map.NaviBaseWalkActivity;
import com.universitylecture.universitylecture.view.tool.BaseActivity;

import java.util.ArrayList;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/10/18.
 */

//评论回复页面
public class AnswerListActivity extends BaseActivity {
    private Comment comment;

    private SwipeRefreshLayout swipeRefreshLayout;
    private View footer;

    //提问内容
    private TextView topicOfQuestion;
    private TextView titleOfQuestion;
    private ExpandTextView content;

    //页面顶部栏
    private Button back;
    private TextView answerQuestion;

    //recyclerView部分
    private ArrayList<Answer> answers = new ArrayList<>();
    private RecyclerView answerRecyclerView;
    private AnswerAdapter adapter;
    private LinearLayoutManager layoutManager;

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

        setContentView(R.layout.activity_answer_list);

        comment = (Comment) getIntent().getSerializableExtra("comment_item");
        initView();//初始化view
        initButton();//初始化按键
    }

    private void initView(){
        topicOfQuestion = (TextView) findViewById(R.id.topic_of_question);
        titleOfQuestion = (TextView) findViewById(R.id.title_of_question);
        content = (ExpandTextView) findViewById(R.id.content_of_question);

        topicOfQuestion.setText(comment.getTopicLecture());
        titleOfQuestion.setText(comment.getQuestion());
        content.setText(comment.getDescription());

        //recyclerView三步曲
        answerRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_in_question_answer_list);
        layoutManager = new LinearLayoutManager(this);
        answerRecyclerView.setLayoutManager(layoutManager);

        //初始化数据，这里我使用了样例数据
        Answer answer = new Answer("很好看",001,"阮子琦","2017-11-11");
        for(int i = 0 ; i < 10 ; i++){
            answers.add(answer);
        }

        //设置adapter,对数据进行填充
        adapter = new AnswerAdapter(answers);
        answerRecyclerView.setAdapter(adapter);
        setFooterView(answerRecyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layoyt_in_question_answer_list);


    }

    private void initButton(){
        back = (Button) findViewById(R.id.go_back_in_comment);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnswerListActivity.super.onBackPressed();
            }
        });

        answerQuestion = (TextView) findViewById(R.id.answer_question_in_comment);
        answerQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnswerListActivity.this,AnswerQuestionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refrest_rooter, recyclerView, false);
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }
}
