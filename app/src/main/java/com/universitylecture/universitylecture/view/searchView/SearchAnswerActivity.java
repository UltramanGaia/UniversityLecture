package com.universitylecture.universitylecture.view.searchView;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.LectureAdapter;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.PopWindowAboutMoreButton;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.view.functionActivity.AskQuestionActivity;
import com.universitylecture.universitylecture.view.sidebar.SlideMenu;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.LectureSystem;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/11/20.
 */

public class SearchAnswerActivity extends BaseActivity {
    private ArrayList<Lecture> lectures = new ArrayList<>();
    private SwipeRefreshLayout swipeRefresh;
    private LectureAdapter adapter;
    private RecyclerView lectures_recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView noLecture;

    private CircleImageView drawerToggleImageButton;
    private SlideMenu slideMenu;
    private Button moreButton;
    private User user;

    View footer;
    private Button goback;

    private String searchKey;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_answer);

        init();//初始化界面，加载数据
        setSwipeRefreshLayout();//设置下拉刷新的逻辑
        setUpOnScrollRefresh();//设置上拉刷新的逻辑
    }

    public  void setUpOnScrollRefresh(){
        lectures_recyclerView.addOnScrollListener(new UpOnScrollListener(layoutManager) {
            @Override//上拉刷新的数据更新写在下面
            public void onLoadMore(int currentPage) {
                //此处设置更新逻辑
                footer.findViewById(R.id.footer_layout).setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

//                        //更新逻辑写在此处
//                        try{
//                            Thread.sleep(2000);
//                        }catch (InterruptedException e){
//                            e.printStackTrace();
//                        }
//
//                        String m = Object2JSONUtil.myLecture(PersonalInformation.id);
//                        String s = HttpUtilJSON.doPost(m,"myLecture");
//                        lectures = JSON2ObjectUtil.getLectures(s);
//                        adapter.setmLectureLIst(lectures);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
                    }
                }).start();

            }
        });

    }

    private void setSwipeRefreshLayout(){
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        //按键逻辑
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();

            }
        });
    }

    //下拉更新逻辑
    public void refresh(){

        noLecture.setVisibility(View.GONE);

        //下拉更新数据写在下面
        new Thread(new Runnable() {
            @Override
            public void run() {
//                //更新逻辑写在此处
//                String m = Object2JSONUtil.myLecture(PersonalInformation.id);
//                String s = HttpUtilJSON.doPost(m,"myLecture");
//                lectures = JSON2ObjectUtil.getLectures(s);
//                adapter.setmLectureLIst(lectures);
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter.notifyDataSetChanged();
//                        swipeRefresh.setRefreshing(false);
//                    }
//                });
            }
        }).start();


        if(lectures.size() == 0 ){
            lectures_recyclerView.setVisibility(View.GONE);
            noLecture.setVisibility(View.VISIBLE);
        }else {
            lectures_recyclerView.setVisibility(View.VISIBLE);
            noLecture.setVisibility(View.GONE);
        }

    }

    private void init(){
        //配置recylerview三部曲
        lectures_recyclerView = (RecyclerView) findViewById(R.id.lectures_in_search_answer_activity);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        lectures_recyclerView.setLayoutManager(layoutManager);
        //lectures = school.getLectures();  //加载用户数据

        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_layoyt_in_search_activity);
        noLecture = (TextView) findViewById(R.id.no_lecture_in_search_answer_activity);

        searchKey = getIntent().getStringExtra("searchKey");
        //初始化数据写在下面这个线程里，注释部分为原来myFragment里面的
        new Thread(new Runnable() {
            @Override
            public void run() {
//                String m = Object2JSONUtil.myLecture(PersonalInformation.id);
//                String s = HttpUtilJSON.doPost(m,"myLecture");
//                lectures = JSON2ObjectUtil.getLectures(s);
//
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        adapter = new LectureAdapter(lectures,getActivity(),"unset");
//                        lectures_recyclerView.setAdapter(adapter);
//                        lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
//                        setFooterView(lectures_recyclerView);
//                        if(lectures.size() == 0 ) {
//                            lectures_recyclerView.setVisibility(View.GONE);
//                            noLecture.setVisibility(View.VISIBLE);
//                        }
//                    }
//                });
            }
        }).start();


        goback = (Button) findViewById(R.id.go_back_in_title_of_search_answer);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchAnswerActivity.super.onBackPressed();
            }
        });
    }

    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refrest_rooter, recyclerView, false);
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }
}
