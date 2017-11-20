package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.adapter.ConstellationAdapter;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.DropDownMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/17.
 */

public class selectInstituteForLaunch extends BaseActivity {
    @InjectView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;


    private String instituteHeader = "学院";

    private ConstellationAdapter instituteAdapter;
    private List<View> popupViews = new ArrayList<>();

    private String institutes[] = { "不限" , "计算机学院" , "软件学院" , "数学学院" , "外国语学院" , "体育学院" , "物理学院" , "艺术学院"
            , "机械学院" , "建筑学院" , "电子与信息学院" , "材料学院" , "轻工学院" , "经贸学院" , "自动化学院"
            , "电力学院" , "环境学院" , "工商管理学院" , "公管学院" , "马克思主义学院" , "经贸学院" , "外国语学院"
            , "法学院" , "新传学院" , "医学院"};

    private int constellationPosition = 0;

    private String selectedInstitude;

    private Button back;
    private  TextView title_in_title_bar_of_launch;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);

            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//状态栏不会被隐藏但activity布局会扩展到状态栏所在位置
                    // | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION//导航栏不会被隐藏但activity布局会扩展到导航栏所在位置
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //window.setNavigationBarColor(Color.TRANSPARENT);
        }
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            //半透明导航栏
            //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            //半透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_select_information_for_launch);
        ButterKnife.inject(this);
        String data = getIntent().getStringExtra("data");

        initView();
    }

    private void initView() {
        final View instituteView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        final GridView institute  = ButterKnife.findById(instituteView, R.id.constellation);
        instituteAdapter = new ConstellationAdapter(this, Arrays.asList(institutes));
        institute.setAdapter(instituteAdapter);
        TextView instituteOk = ButterKnife.findById(instituteView, R.id.ok);
        instituteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellationPosition == 0 ? instituteHeader : institutes[constellationPosition]);
                Intent intent = new Intent();
                intent.putExtra("institute",constellationPosition == 0 ? "" : institutes[constellationPosition]);
                setResult(RESULT_OK,intent);
                mDropDownMenu.closeMenu();
                finish();
                }
            });

        //init popupViews
        popupViews.add(instituteView);

        institute.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                instituteAdapter.setCheckItem(position);
                constellationPosition = position;
            }
        });

        //init context view
        TextView contentView = new TextView(this);
        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentView.setText("内容显示区域");
        contentView.setGravity(Gravity.CENTER);
        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        contentView.setVisibility(View.GONE);

        //init dropdownview
            mDropDownMenu.setDropDownMenu(Arrays.asList(instituteHeader), popupViews, contentView);


        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInstituteForLaunch.super.onBackPressed();
            }
        });

        title_in_title_bar_of_launch = (TextView) findViewById(R.id.title_in_title_bar);
        title_in_title_bar_of_launch.setText("选择学院");
    }
    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu != null &&mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

}
