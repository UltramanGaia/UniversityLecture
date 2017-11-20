package com.universitylecture.universitylecture.view.sidebar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/14.
 */
//我的资料界面
public class MyInformation extends BaseActivity {
    //四个linearlayout
    private View nameInMyInformation ;
    private View sexInMyInformation;
    private View phoneNumberInMyInformation;

    private Intent intent;


    private TextView name;
    private TextView sex;
    private TextView phoneNumber;
    private TextView studentNumber;

    private Button back;
    private User user;

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

        setContentView(R.layout.activity_my_information);

        intent = new Intent(MyInformation.this , ChangeInformationActivity.class);

        initView();//初始化控件
        initData();//初始化信息内容
        initButton();//初始化按键

    }

    private void initButton(){
        //姓名栏点击功能
        nameInMyInformation = (LinearLayout)findViewById(R.id.name_in_my_information);
        nameInMyInformation.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                intent.putExtra("data" , "name" );
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        //性别栏点击功能
        sexInMyInformation = (LinearLayout) findViewById(R.id.sex_in_my_information);
        sexInMyInformation.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                intent.putExtra("data" , "sex" );
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

        //手机栏点击功能
        phoneNumberInMyInformation = (LinearLayout) findViewById(R.id.phonenumber_in_my_information);
        phoneNumberInMyInformation.setOnClickListener( new View.OnClickListener(){
            public void onClick(View v){
                intent.putExtra("data" , "phoneNumber" );
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });



        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInformation.super.onBackPressed();
            }
        });
    }

    private void initData(){
        name.setText(PersonalInformation.name);
        sex.setText(PersonalInformation.sex);
        phoneNumber.setText(PersonalInformation.phoneNumber);
        user = (User) getIntent().getSerializableExtra("user");
    }

    private void initView(){
        name = (TextView) findViewById(R.id.name_text_in_my_information);
        sex = (TextView) findViewById(R.id.sex_text_in_my_information);
        phoneNumber = (TextView) findViewById(R.id.phonenumber_text_in_my_information);
    }

    protected void onResume(){
        super.onResume();
        initData();
    }
}
