package com.universitylecture.universitylecture.view.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;


import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.DynamicPagerAdapter;
import com.jude.rollviewpager.hintview.IconHintView;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.ConstellationAdapter;
import com.universitylecture.universitylecture.adapter.GirdDropDownAdapter;
import com.universitylecture.universitylecture.adapter.LectureAdapter;
import com.universitylecture.universitylecture.adapter.ListDropDownAdapter;
import com.universitylecture.universitylecture.pojo.Lecture;
import com.universitylecture.universitylecture.pojo.PopWindowAboutMoreButton;
import com.universitylecture.universitylecture.pojo.School;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.MyApplication;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.view.MainActivity;
import com.universitylecture.universitylecture.view.functionActivity.LectureListActivity;
import com.universitylecture.universitylecture.view.searchView.SearchActivity;
import com.universitylecture.universitylecture.view.sidebar.RollViewPagerPlus;
import com.universitylecture.universitylecture.view.sidebar.SlideMenu;
import com.universitylecture.universitylecture.view.sidebar.TranslucentScrollView;
import com.universitylecture.universitylecture.view.tool.DropDownMenu;
import com.universitylecture.universitylecture.view.tool.LectureSystem;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;
import com.universitylecture.universitylecture.view.tool.UpOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

//import com.universitylecture.universitylecture.pojo.SimpleDividerItemDecoration;


//讲座列表界面
public class LectureListFragment extends Fragment implements TranslucentScrollView.OnScrollChangedListener{
    private View view;

    private RollViewPagerPlus mRollViewPager;
    private SlideMenu slideMenu;
    private CircleImageView drawerToggleImageButton;
    private Button moreButton;
    private User user;

    //toolbar透明渐变
    private TranslucentScrollView scrollView;
    private Toolbar toolbar;
    private float headerHeight;//顶部高度
    private float minHeaderHeight;//顶部最低高度，即Bar的高度

    //搜索框
    private FrameLayout searchView;

    //设置页面的逻辑
    public Button button1,button2,button3,button4,button5;
    public Button button6,button7,button8;

    public LinearLayout linearLayout1,linearLayout2;

    //两个recyclerView
    public RecyclerView nowOpening,nearHot;
    private LectureAdapter adapter1,adapter2;
    private LinearLayoutManager layoutManager1,layoutManager2;
    private ArrayList<Lecture> lectures = new ArrayList<Lecture>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.e("TAG", "onCreateView: ");
        view = inflater.inflate(R.layout.fragment_lecture_list, container, false);
        // Inflate the layout for this fragment

        searchView = (FrameLayout) view.findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(getActivity(), SearchActivity.class));
            }
        });

//        ButterKnife.inject(this,view);
        initView();
        initLinearLayout();
        initRecyclerView();

        //图片轮播
        mRollViewPager = (RollViewPagerPlus) view.findViewById(R.id.roll_view_pager);
        //设置播放时间间隔
        mRollViewPager.setPlayDelay(5000);
        //设置透明度
        mRollViewPager.setAnimationDurtion(500);
        //设置适配器
        mRollViewPager.setAdapter(new LectureListFragment.TestNormalAdapter());
        slideMenu = (SlideMenu) getActivity().findViewById(R.id.slideMenu);
        mRollViewPager.setOutter(slideMenu);
        //设置指示器（顺序依次）
        //自定义指示器图片
        //设置圆点指示器颜色
        //设置文字指示器
        //隐藏指示器

        mRollViewPager.setHintView(new IconHintView(view.getContext(), R.drawable.navigation_bar_focus, R.drawable.navigation_bar_normal));
        //mRollViewPager.setHintView(new ColorPointHintView(view.getContext(),Color.GRAY,Color.WHITE));
        //mRollViewPager.setHintView(new TextHintView(this));
        //mRollViewPager.setHintView(null);

        return view;
    }

    private void initRecyclerView(){
        nowOpening = (RecyclerView) view.findViewById(R.id.opening_recycler_view);
        layoutManager1 = new LinearLayoutManager(MyApplication.getContext());
        nowOpening.setLayoutManager(layoutManager1);

        nearHot = (RecyclerView) view.findViewById(R.id.near_hot_recycler_view);
        layoutManager2 = new LinearLayoutManager(MyApplication.getContext());
        nearHot.setLayoutManager(layoutManager2);


        new Thread(new Runnable() {
            @Override
            public void run() {
                /*final Lecture lecture = new Lecture("10","不限",5);
                lectures = (ArrayList<Lecture>) (HttpUtil.doPost(lecture,"SelectLectureServlet"));
                */

                lectures = JSON2ObjectUtil.getLectures(HttpUtilJSON.doPost(Object2JSONUtil.selectLecture("五天之内","不限",String.valueOf(0)),"selectLecture"));

                Log.e("lecture size in thread", "initData: " + lectures.size());


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //配置recylerview三部曲

                        adapter1 = new LectureAdapter(lectures,getActivity(),"set");
                        nowOpening.setAdapter(adapter1);

                        adapter2 = new LectureAdapter(lectures,getActivity(),"set");
                        nearHot.setAdapter(adapter2);
                       /* //设置分隔线
//                        lectures_recyclerView.addItemDecoration(new DividerItemDecoration(MyApplication.getContext(), DividerItemDecoration.HORIZONTAL));
                        lectures_recyclerView.addItemDecoration(new SpaceItemDecoration(30));
                        lectures_recyclerView.addItemDecoration(new SimpleDividerItemDecoration(MyApplication.getContext()));
                       */
                        //设置rooter
                        //setHeaderView(lectures_recyclerView);
                    }
                });

            }
        }).start();
    }
    private void initLinearLayout(){
        button1 = (Button) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","学术型");
                startActivity(intent);
            }
        });

        button2 = (Button) view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","宣讲会");
                startActivity(intent);
            }
        });

        button3 = (Button) view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","比赛");
                startActivity(intent);
            }
        });

        button4 = (Button) view.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","表彰会");
                startActivity(intent);
            }
        });


        button5 = (Button) view.findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","公开课");
                startActivity(intent);
            }
        });

        button6 = (Button) view.findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","招聘会");
                startActivity(intent);
            }
        });
        button7 = (Button) view.findViewById(R.id.button7);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","文艺表演");
                startActivity(intent);
            }
        });

        button8 = (Button) view.findViewById(R.id.button8);
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","更多精彩");
                startActivity(intent);
            }
        });

        linearLayout1 = (LinearLayout) view.findViewById(R.id.linearlayout9);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","正在开办");
                startActivity(intent);
            }
        });

        linearLayout2 = (LinearLayout) view.findViewById(R.id.linearlayout10);
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LectureListActivity.class);
                intent.putExtra("theme","附近热门");
                startActivity(intent);
            }
        });
    }

    private void initView() {
       //左上角头像框点击事件
        drawerToggleImageButton = (CircleImageView) view.findViewById(R.id.toggle_drawer_open_in_home);
        drawerToggleImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });

        //右上角加号头像框点击事件
        moreButton = (Button)  view.findViewById(R.id.more_in_home);
        user = new User(PersonalInformation.id,PersonalInformation.name,PersonalInformation.password,PersonalInformation.sex,PersonalInformation.phoneNumber);
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopWindowAboutMoreButton popWindow = new PopWindowAboutMoreButton(getActivity(), user);
                popWindow.showPopupWindow( view.findViewById(R.id.more_in_home));
            }
        });
        //设置toolbar透明渐变
        scrollView = (TranslucentScrollView) view.findViewById(R.id.home_scrollview);
        scrollView.setOnScrollChangedListener(this);
        toolbar = (Toolbar) view.findViewById(R.id.title_in_home);
        toolbar.setBackgroundColor(Color.argb(0, 18, 176, 242));
        initMeasure();
    }


    //首页图片轮播
    private class TestNormalAdapter extends DynamicPagerAdapter {
        private int[] imgs = {
                R.drawable.img1,
                R.drawable.img2,
                R.drawable.img3,
                R.drawable.img4,
                R.drawable.img5,
                R.drawable.img6,
        };

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }


        @Override
        public int getCount() {
            return imgs.length;
        }
    }

    private void initMeasure() {
        headerHeight = getResources().getDimension(R.dimen.header_height);
        minHeaderHeight = getResources().getDimension(R.dimen.abc_action_bar_default_height_material);
    }

    @Override
    public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
        //Y轴偏移量
        float scrollY = who.getScrollY();
        //变化率
        float headerBarOffsetY = headerHeight - minHeaderHeight;//Toolbar与header高度的差值
        float offset = 1 - Math.max((headerBarOffsetY - scrollY) / headerBarOffsetY, 0f);
        //Toolbar背景色透明度
        toolbar.setBackgroundColor(Color.argb((int) (offset * 255),132,89,231));
        //header背景图Y轴偏移
        //imgHead.setTranslationY(scrollY / 2);
    }
}
