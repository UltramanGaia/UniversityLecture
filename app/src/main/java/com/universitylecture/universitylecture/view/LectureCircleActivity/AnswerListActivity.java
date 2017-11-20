package com.universitylecture.universitylecture.view.LectureCircleActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lcodecore.extextview.ExpandTextView;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.CommentAdapter;
import com.universitylecture.universitylecture.pojo.Comment;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.Topic;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;

import java.util.ArrayList;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/10/18.
 */

//评论回复页面
public class AnswerListActivity extends BaseActivity {
    private Topic topic;

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
    private ArrayList<Comment> answers = new ArrayList<>();
    private RecyclerView answerRecyclerView;
    private CommentAdapter adapter;
    private LinearLayoutManager layoutManager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_answer_list);

        topic = (Topic) getIntent().getSerializableExtra("topic_item");
        initView();//初始化view
        initButton();//初始化按键
    }

    private void initView(){
        titleOfQuestion = (TextView) findViewById(R.id.title_of_question);
        content = (ExpandTextView) findViewById(R.id.content_of_question);

//        topicOfQuestion.setText(comment.getTopicLecture());
        titleOfQuestion.setText(topic.getTitle());
        content.setText(topic.getDescription());

        //recyclerView三步曲
        answerRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_in_question_answer_list);
        layoutManager = new LinearLayoutManager(this);
        answerRecyclerView.setLayoutManager(layoutManager);

        //初始化数据，这里我使用了样例数据
        Comment comment = new Comment(01,"很好看",01,001,"阮子琦","2017-11-11 10:00:00");
        for(int i = 0 ; i < 10 ; i++){
            answers.add(comment);
        }

        //设置adapter,对数据进行填充
        adapter = new CommentAdapter(answers);
        answerRecyclerView.setAdapter(adapter);
        setFooterView(answerRecyclerView);

//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layoyt_in_question_answer_list);


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
                intent.putExtra("topic_id",topic.getID());
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
