package com.universitylecture.universitylecture.view.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.TopicAdapter;
import com.universitylecture.universitylecture.pojo.Topic;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;

import java.util.ArrayList;

//讲座圈界面
public class LectureCircleFragment extends Fragment {
    private ArrayList<Topic> topics = new ArrayList<>();

    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private TopicAdapter adapter;
    private RecyclerView topics_recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView noLecture;
    View footer;

    private CircleImageView drawerToggleImageButton;
    private SlideMenu slideMenu;
    private Button moreButton;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);

        //左上角头像框点击事件
        slideMenu = (SlideMenu) getActivity().findViewById(R.id.slideMenu);
        drawerToggleImageButton = (CircleImageView) view.findViewById(R.id.toggle_drawer_open_in_lecture_circle);
        drawerToggleImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });

        //右上角加号头像框点击事件
        moreButton = (Button)  view.findViewById(R.id.more_in_lecture_circle);
        user = new User(PersonalInformation.id, PersonalInformation.name, PersonalInformation.password, PersonalInformation.sex, PersonalInformation.phoneNumber);
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopWindowAboutMoreButton popWindow = new PopWindowAboutMoreButton(getActivity(), user);
                popWindow.showPopupWindow( view.findViewById(R.id.more_in_lecture_circle));
            }
        });

        init(inflater,container);//初始化界面
        setSwipeRefreshLayout();//设置下拉刷新的逻辑
        setUpOnScrollRefresh();//设置上拉刷新的逻辑
        //View view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);


        // Inflate the layout for this fragment
        return view;
    }

    public  void setUpOnScrollRefresh(){
        topics_recyclerView.addOnScrollListener(new UpOnScrollListener(layoutManager) {
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

                        Topic Topic = new Topic(002  ,"alaljdjf" , "jasofja" , 01,"2017-10-06 19:58:52");
                        topics.add(Topic);
                        adapter.setmCommentList(topics);

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
                Topic Topic = new Topic(003 , "babab" , "babab" ,01 ,"2017-10-06 19:58:52");
                topics.add(Topic);
                adapter.setmCommentList(topics);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();

//        if(topics.size() == 0 ){
//            swipeRefresh.setVisibility(View.GONE);
//            noLecture.setVisibility(View.VISIBLE);
//        }else {
//            swipeRefresh.setVisibility(View.VISIBLE);
//            noLecture.setVisibility(View.GONE);
//        }
    }

    public void init(LayoutInflater inflater,ViewGroup container){
        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_lecture_circle, container, false);

        //配置recylerview三部曲
        topics_recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_in_lecture_circle);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        topics_recyclerView.setLayoutManager(layoutManager);

        //样例数据
        Topic Topic = new Topic(001,"老师叫什么名字","想问一下那个老师的具体信息加啥酒店附近安居房加拉斯砥砺奋进辣椒水的分类加就龙舒杰打飞机阿拉斯加放假啊六级啊受到了放假啦寄顺丰",01,"2017-10-06 19:58:52");
        for(int i = 0 ; i < 10 ; i++){
            topics.add(Topic);
        }

        //设置adapter,对数据进行填充
        adapter = new TopicAdapter(topics,getActivity());
        topics_recyclerView.setAdapter(adapter);
        //topics_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
        setFooterView(topics_recyclerView);

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layoyt_in_lecture_circle);
    }

    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refrest_rooter, recyclerView, false);
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }

}
