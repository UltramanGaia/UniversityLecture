package com.universitylecture.universitylecture;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.lang.Runnable;

import java.util.List;

//我的讲座界面
public class MyLectureFragment extends Fragment {
    private List lectures;
    private View view;
    private SwipeRefreshLayout swipeRefresh;
    private LectureAdapterTwo adapter;
    private RecyclerView lectures_recyclerView;
    private LinearLayoutManager layoutManager;
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
                        lectures.add( lectures.get(2) );
                        lectures.add( lectures.get(2) );
                        lectures.add( lectures.get(2) );

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
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layoyt);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
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
                addLecture();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void addLecture(){
        lectures.add( lectures.get(1) );
    }

    private void init(LayoutInflater inflater, ViewGroup container){
        LectureSystem lectureSystem = LectureSystem.getLectureSystem();
        List schools = lectureSystem.getSchools();
        School school = (School) schools.get(0);
        lectures = school.getLectures();


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragement_my_lecture_recycleview, container, false);
        //配置recylerview三部曲
        lectures_recyclerView = (RecyclerView) view.findViewById(R.id.lectures_of_myLectures_recyclerview);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        lectures_recyclerView.setLayoutManager(layoutManager);
        adapter = new LectureAdapterTwo(lectures);
        lectures_recyclerView.setAdapter(adapter);
        //lectures_recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        //设置rooter
        setFooterView(lectures_recyclerView);
        //setHeaderView(lectures_recyclerView);
    }

//    private void setHeaderView(RecyclerView view){
//        View header = LayoutInflater.from(MyApplication.getContext()) .inflate(R.layout.header, view, false);
//        adapter.setHeaderView(header);
//    }
    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refrest_rooter, recyclerView, false);
        footer.findViewById(R.id.footer_layout).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }

}

