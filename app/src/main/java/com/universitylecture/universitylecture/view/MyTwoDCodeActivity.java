package com.universitylecture.universitylecture.view;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.LectureAdapterTwo;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.pojo.SimpleDividerItemDecoration;
import com.universitylecture.universitylecture.pojo.SpaceItemDecoration;
import com.universitylecture.universitylecture.util.MyApplication;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/8/23.
 */

public class MyTwoDCodeActivity extends AppCompatActivity {
    private ImageView myTwoDCode;
    private TextView title;
    private Button back;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_two_d_code);

        initView();
    }

    private void initView(){
        //生成二维码照片
        myTwoDCode = (ImageView) findViewById(R.id.myTwoDCode);


        String content = "hahaha";//二维码内容
        Bitmap mBitmap = CodeUtils.createImage(content, 400, 400, null);
        myTwoDCode.setImageBitmap(mBitmap);

        title = (TextView) findViewById(R.id.title_in_title_bar);
        title.setText("我的二维码");

        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTwoDCodeActivity.super.onBackPressed();
            }
        });
    }

    //我的讲座界面
    public static class MyLectureFragment extends Fragment {
        private ArrayList<Lecture> lectures = new ArrayList<>();
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
            lectures = school.getLectures();  //加载用户数据


            // Inflate the layout for this fragment
            view = inflater.inflate(R.layout.fragement_my_lecture_recycleview, container, false);
            //配置recylerview三部曲
            lectures_recyclerView = (RecyclerView) view.findViewById(R.id.lectures_of_myLectures_recyclerview);
            layoutManager = new LinearLayoutManager(MyApplication.getContext());
            lectures_recyclerView.setLayoutManager(layoutManager);
            adapter = new LectureAdapterTwo(lectures,getActivity(),"unset");
            lectures_recyclerView.setAdapter(adapter);
            //lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
            lectures_recyclerView.addItemDecoration(new SpaceItemDecoration(30));
            lectures_recyclerView.addItemDecoration(new SimpleDividerItemDecoration(MyApplication.getContext()));
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
}
