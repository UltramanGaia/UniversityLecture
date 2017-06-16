package com.universitylecture.universitylecture;

import android.os.Bundle;
import android.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

//讲座列表界面
public class LectureListFragment extends Fragment {

    private View view ;
    //@InjectView(R.id.dropDownMenu) static DropDownMenu mDropDownMenu;
    public static DropDownMenu mDropDownMenu ;


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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_lecture_list, container, false);

        mDropDownMenu = (DropDownMenu) view.findViewById(R.id.dropDownMenu);

        // Inflate the layout for this fragment
        ButterKnife.inject(this,view);
        initView();
        return view;

    }

    private void initView() {
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

                //点确定后与服务器交互操作写这里

                mDropDownMenu.closeMenu();
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


        TextView contentView = (TextView) view.findViewById(R.id.content_lecture_list);
        contentView.setText("讲座显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
    }



}
