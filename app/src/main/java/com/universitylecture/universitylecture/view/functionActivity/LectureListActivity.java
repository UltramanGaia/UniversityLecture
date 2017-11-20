package com.universitylecture.universitylecture.view.functionActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.ConstellationAdapter;
import com.universitylecture.universitylecture.adapter.GirdDropDownAdapter;
import com.universitylecture.universitylecture.adapter.LectureAdapter;
import com.universitylecture.universitylecture.adapter.ListDropDownAdapter;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.view.sidebar.TranslucentScrollView;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.DropDownMenu;
import com.universitylecture.universitylecture.view.tool.LectureSystem;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/11/20.
 */

public class LectureListActivity extends BaseActivity {
    public static DropDownMenu mDropDownMenu;
    private String headers[] = {"城市", "时间","学院"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter timeAdapter;
    private ConstellationAdapter institudeAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String time[] = { "不限" , "一天之内" , "三天之内" , "五天之内" , "七天之内" , "七天之后" };
    private String institude[] = { "不限" , "计算机学院" , "软件学院" , "数学学院" , "外国语学院" , "体育学院" , "物理学院" , "艺术学院"
            , "机械学院" , "建筑学院" , "电子与信息学院" , "材料学院" , "轻工学院" , "经贸学院" , "自动化学院"
            , "电力学院" , "环境学院" , "工商管理学院" , "公管学院" , "马克思主义学院" , "经贸学院" , "外国语学院"
            , "法学院" , "新传学院" , "医学院" };

    private int constellationPosition = 0;

    private String selectedCity;
    private String selectedTime;
    private String selectedInstitude;
    private Integer page = 0;


    private ArrayList<Lecture> lectures = new ArrayList<Lecture>();
    private SwipeRefreshLayout swipeRefresh;
    private LectureAdapter adapter;
    private RecyclerView lectures_recyclerView;
    private LinearLayoutManager layoutManager;
    View footer;

    //toolbar透明渐变
    private TranslucentScrollView scrollView;
    private Toolbar toolbar;
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度

    //顶部栏
    private Button goBack;
    private TextView title;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lecture_list);

        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        ButterKnife.inject(this);

        initView();
        initData();
        setSwipeRefreshLayout();//设置下拉刷新的逻辑
        setUpOnScrollRefresh();//设置上拉刷新的逻辑
    }

    //本方法余下为从mylecture部分copy过来
    private void initData(){
        LectureSystem lectureSystem = LectureSystem.getLectureSystem();
        List schools = lectureSystem.getSchools();
        School school = (School) schools.get(0);
        //lectures = school.getLectures();

        new Thread(new Runnable() {
            @Override
            public void run() {
                /*final Lecture lecture = new Lecture("10","不限",5);
                lectures = (ArrayList<Lecture>) (HttpUtil.doPost(lecture,"SelectLectureServlet"));
                */

                lectures = JSON2ObjectUtil.getLectures(HttpUtilJSON.doPost(Object2JSONUtil.selectLecture("五天之内","不限",String.valueOf(0)),"selectLecture"));

                Log.e("lecture size in thread", "initData: " + lectures.size());


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //配置recylerview三部曲

                        adapter = new LectureAdapter(lectures,LectureListActivity.this,"set");
                        lectures_recyclerView.setAdapter(adapter);
                       /* //设置分隔线
//                        lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
                        lectures_recyclerView.addItemDecoration(new SpaceItemDecoration(30));
                        lectures_recyclerView.addItemDecoration(new SimpleDividerItemDecoration(MyApplication.getContext()));
                       */
                        //设置rooter
                        setFooterView(lectures_recyclerView);
                        //setHeaderView(lectures_recyclerView);
                    }
                });

            }
        }).start();

        Log.e("lecture size out thread", "initData: " + lectures.size());

    }

    private void initView() {
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);

        lectures_recyclerView = (RecyclerView) findViewById(R.id.lectures_of_lecture_list_recyclerview);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        lectures_recyclerView.setLayoutManager(layoutManager);

        //init city menu
        final ListView cityView = new ListView(MyApplication.getContext());
        cityAdapter = new GirdDropDownAdapter(MyApplication.getContext(), Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init time menu
        final ListView timeView = new ListView(MyApplication.getContext());
        timeView.setDividerHeight(0);
        timeAdapter = new ListDropDownAdapter(MyApplication.getContext(), Arrays.asList(time));
        timeView.setAdapter(timeAdapter);

        //init institude
        final View institudeView = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.custom_layout, null);
        GridView constellation = ButterKnife.findById(institudeView, R.id.constellation);
        institudeAdapter = new ConstellationAdapter(MyApplication.getContext(), Arrays.asList(institude));
        constellation.setAdapter(institudeAdapter);
        TextView ok = ButterKnife.findById(institudeView, R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDropDownMenu.setTabText(constellationPosition == 0 ? headers[2] : institude[constellationPosition]);
                selectedInstitude = constellationPosition == 0 ? "不限" : institude[constellationPosition];
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        //select lectures
                        /*Lecture lecture = new Lecture(selectedTime,selectedInstitude,5);
                        final ArrayList<Lecture> lectures = (ArrayList<Lecture>) (HttpUtil.doPost(lecture,"SelectLectureServlet"));*/

                        lectures = JSON2ObjectUtil.getLectures(HttpUtilJSON.doPost(Object2JSONUtil.selectLecture(selectedTime,selectedInstitude,String.valueOf(0)),"selectLecture"));


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //配置recylerview三部曲

                                adapter.setmLectureLIst(lectures);
                                lectures_recyclerView.setAdapter(adapter);
                                lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));

                                //设置rooter
                                setFooterView(lectures_recyclerView);
                                //setHeaderView(lectures_recyclerView);
                                mDropDownMenu.closeMenu();
                            }
                        });
                    }
                }).start();

            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(timeView);
        popupViews.add(institudeView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                selectedCity = position == 0 ? "" : citys[position];
                mDropDownMenu.closeMenu();
            }
        });

        timeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                timeAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : time[position]);
                selectedTime = position == 0 ? "" : time[position];
                mDropDownMenu.closeMenu();
            }
        });


        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                institudeAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //init context view
        TextView content = new TextView(MyApplication.getContext());
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));

        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, content);

        //设置toolbar透明渐变
        scrollView = (TranslucentScrollView) findViewById(R.id.scrollview2);
        scrollView.setOnScrollChangedListener(new TranslucentScrollView.OnScrollChangedListener() {
            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                float scrollY = who.getScrollY();
                //变化率
                float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
                float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
                //Toolbar背景色透明度
                toolbar.setBackgroundColor(Color.argb((int) (offset * 255),132,89,231));
                //header背景图Y轴偏移
                //imgHead.setTranslationY(scrollY / 2);
            }
        });
//        toolbar = (Toolbar) findViewById(R.id);
//        toolbar.setBackgroundColor(Color.argb(0, 18, 176, 242));
        initMeasure();

        String theme = getIntent().getStringExtra("theme");
        title = (TextView) findViewById(R.id.title_in_lecture_list_activity);
        title.setText(theme);

        goBack = (Button) findViewById(R.id.go_back_in_lecture_list_activity);
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LectureListActivity.super.onBackPressed();
            }
        });
    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
    }

    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refresh_rooter_in_lecture_list, recyclerView, false);
        footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }

    public  void setUpOnScrollRefresh() {
        lectures_recyclerView.addOnScrollListener(new UpOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                final int sizeBeforeRefresh = adapter.getmLectureLIst().size();
                //此处设置更新逻辑

                //以下为设置底部栏的配件
                footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.VISIBLE);
                footer.findViewById(R.id.progressBarInRooter).setVisibility(View.VISIBLE);
                footer.findViewById(R.id.load_more).setVisibility(View.VISIBLE);
                footer.findViewById(R.id.load_complete).setVisibility(View.GONE);


                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //更新逻辑写在此处
                        ArrayList<Lecture> lectureArrayList = adapter.getmLectureLIst();
//                        final Lecture lecture = new Lecture("10","不限",lectureArrayList.size() + 5);
//                        lectures = (ArrayList<Lecture>) (HttpUtil.doPost(lecture,"SelectLectureServlet"));

                        lectures = JSON2ObjectUtil.getLectures(HttpUtilJSON.doPost(Object2JSONUtil.selectLecture("五天之内","不限",String.valueOf(++page)),"selectLecture"));

                        if (lectures.size() > 0)
                            lectureArrayList.addAll(lectureArrayList.size(),lectures);
                        adapter.setmLectureLIst(lectureArrayList);


                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                int sizeAfterRefresh = adapter.getmLectureLIst().size();
//
//                                if( sizeBeforeRefresh != sizeAfterRefresh ){
//                                    footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.GONE);
//                                }else {
//                                    footer.findViewById(R.id.progressBarInRooter).setVisibility(View.GONE);
//                                    footer.findViewById(R.id.load_more).setVisibility(View.GONE);
//                                    footer.findViewById(R.id.load_complete).setVisibility(View.VISIBLE);
//                                }

                                footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.GONE);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();

            }
        });

    }

    private void setSwipeRefreshLayout(){
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_layoyt_in_lecture_list);
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
//                final Lecture lecture = new Lecture("10","不限",5);
//                lectures = (ArrayList<Lecture>) (HttpUtil.doPost(lecture,"SelectLectureServlet"));

                lectures = JSON2ObjectUtil.getLectures(HttpUtilJSON.doPost(Object2JSONUtil.selectLecture("五天之内","不限",String.valueOf(0)),"selectLecture"));
                adapter.setmLectureLIst(lectures);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        swipeRefresh.setRefreshing(false);
                    }
                });

//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.GONE);
//                    }
//                });
            }
        }).start();
    }


}
