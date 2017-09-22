//package com.universitylecture.universitylecture.view;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.GridView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.universitylecture.universitylecture.R;
//import com.universitylecture.universitylecture.adapter.ConstellationAdapter;
//import com.universitylecture.universitylecture.adapter.GirdDropDownAdapter;
//import com.universitylecture.universitylecture.adapter.ListDropDownAdapter;
//import com.universitylecture.universitylecture.view.tool.BaseActivity;
//import com.universitylecture.universitylecture.view.tool.DropDownMenu;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//
//import static android.view.Window.FEATURE_NO_TITLE;
//
///**
// * Created by fengqingyundan on 2017/6/17.
// */
//
//public class SelectInformationForLaunch extends BaseActivity {
//    //private View view ;
//    @InjectView(R.id.dropDownMenu)
//    DropDownMenu mDropDownMenu;
//    //public static DropDownMenu mDropDownMenu ;
//
//    private String timeHeaders[] = {"年", "月","日" ,"时间"};
//    private List<View> popupViews = new ArrayList<>();
//
//    private GirdDropDownAdapter yearAdapter;//cityAdapter;
//    private ListDropDownAdapter monthAdapter;//timeAdapter;
//    private ListDropDownAdapter dateAdapter;
//    private ConstellationAdapter timeAdapter;//institudeAdapter;
//
//    private String years[] = {"不限","2017" , "2018" , "2019" };
//    private String months[] = {"不限","01","02","03","04","05","06","07","08","09","10","11","12"};
//    private String dates[] = { "不限","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31"};
//    //private String date30[] = { "01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30"};
//    private String times[] = { "不限","08:00:00","08:30:00","09:00:00","09:30:00","10:00:00","10:30:00","11:00:00","11:30:00","12:00:00","12:30:00","13:00:00","13:30:00","14:00:00","14:30:00",
//            "15:00:00","15:30:00","16:00:00","16:30:00","17:00:00","17:30:00","18:00:00","18:30:00","19:00:00","19:30:00","20:00:00","20:30:00","21:00:00","21:30:00","22:00:00","22:30:00"};
//
//    private int constellationPosition = 0;
//
//    private String selectedYear;
//    private String selectedMonth;
//    private String selectedDate;
//    private String selectedTime;
//
//    private String data;
//
//    private Button back;
//    private TextView title_in_title_bar_of_launch;
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_select_information_for_launch);
//        ButterKnife.inject(this);
//        String data = getIntent().getStringExtra("data");
//
//        initView();
//
//    }
//
//    private void initView() {
//        //init city menu
//        final ListView yearView = new ListView(this);
//        yearAdapter = new GirdDropDownAdapter(this, Arrays.asList(years));
//        yearView.setDividerHeight(0);
//        yearView.setAdapter(yearAdapter);
//
//        //init age menu
//        final ListView monthView = new ListView(this);
//        monthView.setDividerHeight(0);
//        monthAdapter = new ListDropDownAdapter(this, Arrays.asList(months));
//        monthView.setAdapter(monthAdapter);
//
//        //init sex menu
//        final ListView dateView = new ListView(this);
//        dateView.setDividerHeight(0);
//        dateAdapter = new ListDropDownAdapter(this, Arrays.asList(dates));
//        dateView.setAdapter(dateAdapter);
//
//        //init constellation
//        final View timeView = getLayoutInflater().inflate(R.layout.custom_layout, null);
//        GridView time = ButterKnife.findById(timeView, R.id.constellation);
//        timeAdapter = new ConstellationAdapter(this, Arrays.asList(times));
//        time.setAdapter(timeAdapter);
//        TextView ok = ButterKnife.findById(timeView, R.id.ok);
//        ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mDropDownMenu.setTabText(constellationPosition == 0 ? timeHeaders[3] : times[constellationPosition]);
//                Intent intent = new Intent();
//                String selectedTime = constellationPosition == 0 ? "" : times[constellationPosition];
//                intent.putExtra("time", selectedYear + ":" + selectedMonth + ":" + selectedDate + " " + selectedTime);
//                setResult(RESULT_OK,intent);
//                mDropDownMenu.closeMenu();
//                finish();
//            }
//        });
//
//        popupViews.add(yearView);
//        popupViews.add(monthView);
//        popupViews.add(dateView);
//        popupViews.add(timeView);
//
//
//        //add item click event
//        yearView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                yearAdapter.setCheckItem(position);
//                selectedYear = position == 0 ? "" : years[position];
//                mDropDownMenu.setTabText(position == 0 ? timeHeaders[0] : years[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                monthAdapter.setCheckItem(position);
//                selectedMonth = position == 0 ? "" : months[position];
//                mDropDownMenu.setTabText(position == 0 ? timeHeaders[1] : months[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        dateView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                dateAdapter.setCheckItem(position);
//                selectedDate = position == 0 ? "" : dates[position];
//                mDropDownMenu.setTabText(position == 0 ? timeHeaders[2] : dates[position]);
//                mDropDownMenu.closeMenu();
//            }
//        });
//
//        time.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                timeAdapter.setCheckItem(position);
//                constellationPosition = position;
//            }
//        });
//
//        //init context view
//        TextView contentView = new TextView(this);
//        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        contentView.setText("内容显示区域");
//        contentView.setGravity(Gravity.CENTER);
//        contentView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//        contentView.setVisibility(View.GONE);
//
//        //init dropdownview
//        mDropDownMenu.setDropDownMenu(Arrays.asList(timeHeaders), popupViews, contentView);
//
//        title_in_title_bar_of_launch = (TextView) findViewById(R.id.title_in_title_bar);
//        title_in_title_bar_of_launch.setText("选择时间");
//
//        back = (Button) findViewById(R.id.go_back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SelectInformationForLaunch.super.onBackPressed();
//            }
//        });
//    }
//    @Override
//    public void onBackPressed() {
//        //退出activity前关闭菜单
//        if (mDropDownMenu != null &&mDropDownMenu.isShowing()) {
//            mDropDownMenu.closeMenu();
//        } else {
//            super.onBackPressed();
//        }
//    }
//}
