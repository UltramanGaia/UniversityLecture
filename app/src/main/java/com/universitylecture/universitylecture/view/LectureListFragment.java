package com.universitylecture.universitylecture.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.ConstellationAdapter;
import com.universitylecture.universitylecture.adapter.GirdDropDownAdapter;
import com.universitylecture.universitylecture.adapter.LectureAdapterTwo;
import com.universitylecture.universitylecture.adapter.ListDropDownAdapter;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.util.HttpUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.OutputMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;


//讲座列表界面
public class LectureListFragment extends Fragment {
    private View view;
//    @InjectView(R.id.dropDownMenu)
//    DropDownMenu mDropDownMenu;
    public static DropDownMenu mDropDownMenu;
    private String headers[] = {"城市", "时间","学院"};
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter timeAdapter;
    private ConstellationAdapter institudeAdapter;

    private String citys[] = {"不限", "武汉", "北京", "上海", "成都", "广州", "深圳", "重庆", "天津", "西安", "南京", "杭州"};
    private String time[] = { "不限" , "一天之内" , "三天之内" , "五天之内" , "七天之内" , "七天之后" };
    private String institude[] = { "不限" , "计算机学院" , "软件学院" , "数学学院" , "外国语学院" , "体育学院" , "物理学院" , "艺术学院"
            , "计算机学院" , "软件学院" , "数学学院" , "外国语学院" , "体育学院" , "物理学院" , "艺术学院"
            , "计算机学院" , "软件学院" , "数学学院" , "外国语学院" , "体育学院" , "物理学院" , "艺术学院"
            , "计算机学院" , "软件学院" , "数学学院" , "外国语学院" , "体育学院" , "物理学院" , "艺术学院"};

    private int constellationPosition = 0;

    private String selectedCity;
    private String selectedTime;
    private String selectedInstitude;


    private List<Lecture> lectures;
    private SwipeRefreshLayout swipeRefresh;
    private LectureAdapterTwo adapter;
    private RecyclerView lectures_recyclerView;
    private LinearLayoutManager layoutManager;
    View footer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lecture_list, container, false);
        // Inflate the layout for this fragment
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
        ButterKnife.inject(this,view);
        initView();
        initData();//初始化列表数据
        setSwipeRefreshLayout();//设置下拉刷新的逻辑
        setUpOnScrollRefresh();//设置上拉刷新的逻辑

        return view;
    }

    private void initView() {
        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);
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
                selectedInstitude = constellationPosition == 0 ? "" : institude[constellationPosition];
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        //点确定后与服务器交互操作写这里
                        Lecture lecture = new Lecture(selectedTime,selectedInstitude);
                        ArrayList<Lecture> lectures = (ArrayList<Lecture>) (HttpUtil.doPost(lecture,"SelectLectureServlet"));
                        OutputMessage.outputMessage(lectures.size()+"");
                        mDropDownMenu.closeMenu();

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

    }

    //本方法余下为从mylecture部分copy过来
    private void initData(){
        LectureSystem lectureSystem = LectureSystem.getLectureSystem();
        List schools = lectureSystem.getSchools();
        School school = (School) schools.get(0);
        lectures = school.getLectures();


        //配置recylerview三部曲
        lectures_recyclerView = (RecyclerView) view.findViewById(R.id.lectures_of_lecture_list_recyclerview);
        layoutManager = new LinearLayoutManager(MyApplication.getContext());
        lectures_recyclerView.setLayoutManager(layoutManager);
        adapter = new LectureAdapterTwo(lectures,getActivity());
        lectures_recyclerView.setAdapter(adapter);
        lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));

        //设置rooter
        setFooterView(lectures_recyclerView);
        //setHeaderView(lectures_recyclerView);
    }

    public  void setUpOnScrollRefresh(){
        lectures_recyclerView.addOnScrollListener(new UpOnScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                //此处设置更新逻辑
                footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        //更新逻辑写在此处
                        try{
                            Thread.sleep(2000);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }


                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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
        swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layoyt_in_lecture_list);
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

    private void setFooterView(RecyclerView recyclerView){
        footer = LayoutInflater.from(MyApplication.getContext()).inflate(R.layout.refresh_rooter_in_lecture_list, recyclerView, false);
        footer.findViewById(R.id.footer_layout_in_lecture_list).setVisibility(View.GONE);
        adapter.setFooterView(footer);
    }

}