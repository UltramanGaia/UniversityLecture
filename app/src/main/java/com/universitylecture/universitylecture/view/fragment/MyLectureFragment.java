package com.universitylecture.universitylecture.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.LectureAdapter;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.view.tool.LectureSystem;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;
import com.universitylecture.universitylecture.util.Object2JSONUtil;

import java.util.ArrayList;
import java.util.List;

//我的讲座界面
public class MyLectureFragment extends Fragment {
    private ArrayList<Lecture> lectures = new ArrayList<>();
    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private LectureAdapter adapter;
    private   RecyclerView lectures_recyclerView;
    private LinearLayoutManager layoutManager;
    private TextView noLecture;
    View footer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        init(inflater,container);//初始化界面，加载数据
        setSwipeRefreshLayout();//设置下拉刷新的逻辑
        setUpOnScrollRefresh();//设置上拉刷新的逻辑

        return view;
    }

    public  void setUpOnScrollRefresh(){
        lectures_recyclerView.addOnScrollListener(new UpOnScrollListener(layoutManager) {
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

                        String m = Object2JSONUtil.myLecture(PersonalInformation.id);
                        String s = HttpUtilJSON.doPost(m,"myLecture");
                        lectures = JSON2ObjectUtil.getLectures(s);
                        adapter.setmLectureLIst(lectures);

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
                String m = Object2JSONUtil.myLecture(PersonalInformation.id);
                String s = HttpUtilJSON.doPost(m,"myLecture");
                lectures = JSON2ObjectUtil.getLectures(s);
                adapter.setmLectureLIst(lectures);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();

//        if(lectures.size() == 0 ){
//            swipeRefresh.setVisibility(View.GONE);
//            noLecture.setVisibility(View.VISIBLE);
//        }else {
//            swipeRefresh.setVisibility(View.VISIBLE);
//            noLecture.setVisibility(View.GONE);
//        }
    }

    private void init(LayoutInflater inflater, ViewGroup container){

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragement_my_lecture, container, false);
        //配置recylerview三部曲
        lectures_recyclerView = (RecyclerView) view.findViewById(R.id.lectures_of_myLectures_recyclerview);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        lectures_recyclerView.setLayoutManager(layoutManager);
        LectureSystem lectureSystem = LectureSystem.getLectureSystem();
        List schools = lectureSystem.getSchools();
        School school = (School) schools.get(0);
        //lectures = school.getLectures();  //加载用户数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                String m = Object2JSONUtil.myLecture(PersonalInformation.id);
                String s = HttpUtilJSON.doPost(m,"myLecture");
                lectures = JSON2ObjectUtil.getLectures(s);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new LectureAdapter(lectures,getActivity(),"unset");
                        lectures_recyclerView.setAdapter(adapter);
                        lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
                        setFooterView(lectures_recyclerView);
                    }
                });
            }
        }).start();

        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layoyt);

//        noLecture = (TextView) view.findViewById(R.id.no_lecture_in_my_lecturelist);
//        if(lectures.size() == 0 ){
//            swipeRefresh.setVisibility(View.GONE);
//            noLecture.setVisibility(View.VISIBLE);
//        }


        //设置rooter

        //setHeaderView(lectures_recyclerView);
    }

    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refrest_rooter, recyclerView, false);
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }

}


