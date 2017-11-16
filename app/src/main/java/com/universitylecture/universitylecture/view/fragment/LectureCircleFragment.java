package com.universitylecture.universitylecture.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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
import com.universitylecture.universitylecture.adapter.CommentAdapter;
import com.universitylecture.universitylecture.adapter.LectureAdapter;
import com.universitylecture.universitylecture.pojo.Comment;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.view.tool.LectureSystem;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;

import java.util.ArrayList;
import java.util.List;

//讲座圈界面
public class LectureCircleFragment extends Fragment {
    private ArrayList<Comment> comments = new ArrayList<>();
    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private CommentAdapter adapter;
    private RecyclerView comments_recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView noLecture;
    View footer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init(inflater,container);//初始化界面
        setSwipeRefreshLayout();//设置下拉刷新的逻辑
        setUpOnScrollRefresh();//设置上拉刷新的逻辑
        //View view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);

        // Inflate the layout for this fragment
        return view;
    }

    public  void setUpOnScrollRefresh(){
        comments_recyclerView.addOnScrollListener(new UpOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                //此处设置更新逻辑
                footer.findViewById(R.id.footer_layout).setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //更新逻辑写在此处
                        try{
                            Thread.sleep(2000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        Comment comment = new Comment("002" , "lulaal" ,"alaljdjf" , "jasofja" , "cf");
                        comments.add(comment);
                        adapter.setmCommentList(comments);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        });
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
        new Thread(new Runnable() {
            @Override
            public void run() {

                //更新逻辑写在此处
                Comment comment = new Comment("003" , "bababa" ,"babab" , "babab" , "cf");
                comments.add(comment);
                adapter.setmCommentList(comments);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();

//        if(comments.size() == 0 ){
//            swipeRefresh.setVisibility(View.GONE);
//            noLecture.setVisibility(View.VISIBLE);
//        }else {
//            swipeRefresh.setVisibility(View.VISIBLE);
//            noLecture.setVisibility(View.GONE);
//        }
    }

    public void init(LayoutInflater inflater,ViewGroup container){
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);

        //配置recylerview三部曲
        comments_recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_in_lecture_circle);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        comments_recyclerView.setLayoutManager(layoutManager);

        //样例数据
        Comment comment = new Comment("001","宣讲会","老师叫什么名字","想问一下那个老师的具体信息加啥酒店附近安居房加拉斯砥砺奋进辣椒水的分类加就龙舒杰打飞机阿拉斯加放假啊六级啊受到了放假啦寄顺丰","cf");
        for(int i = 0 ; i < 10 ; i++){
            comments.add(comment);
        }

        //设置adapter,对数据进行填充
        adapter = new CommentAdapter(comments,getActivity());
        comments_recyclerView.setAdapter(adapter);
        //comments_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
        setFooterView(comments_recyclerView);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layoyt_in_lecture_circle);
    }
    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refrest_rooter, recyclerView, false);
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }
}
