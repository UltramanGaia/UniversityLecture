package com.universitylecture.universitylecture;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import butterknife.InjectView;

import static android.view.Window.FEATURE_NO_TITLE;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // 我的讲座的fragment
    private MyLectureFragment myLectureFragment;
    // 讲座列表的fragment
    private LectureListFragment lectureListFragment;
    // 讲座圈的fragment
    private LectureCircleFragment lectureCircleFragment;

    // 底部对应的ImageButton
    private ImageButton myLectureImageButton;
    private ImageButton lectureListImageButton;
    private ImageButton lectureCircleImageButton;

    // drawer layout
    private DrawerLayout drawerLayout;
    private CircleImageView drawerToggleImageButton;
    private NavigationView navigationView;

   // 搜索
    private ImageButton searchImageButton;

    // fragment管理器
    private FragmentManager fragmentManager;
    //@InjectView(R.id.dropDownMenu) DropDownMenu mDropDownMenu;//筛选列表

    //抽屉栏头像下的个人信息
    private View headerView;
    private TextView userName;
    private TextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initPersonalInformation();
        initView();
        updatePersonalInformation();

        fragmentManager = getFragmentManager();

        setTabSelection(0);


    }

    private void initPersonalInformation(){
        PersonalInformation.id = 0;
        PersonalInformation.name = "龙哥";
        PersonalInformation.phoneNumber="13631432757";
        PersonalInformation.sex = "男";
        PersonalInformation.studentNumber = "201530612668";
    }

    private void initView() {
        myLectureImageButton = (ImageButton) findViewById(R.id.my_lecture_image_button);
        myLectureImageButton.setOnClickListener(this);
        lectureListImageButton = (ImageButton) findViewById(R.id.lecture_list_image_button);
        lectureListImageButton.setOnClickListener(this);
        lectureCircleImageButton = (ImageButton) findViewById(R.id.lecture_circle_image_button);
        lectureCircleImageButton.setOnClickListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggleImageButton = (CircleImageView) findViewById(R.id.toggle_drawer_open);
        drawerToggleImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        searchImageButton = (ImageButton) findViewById(R.id.search_activity);
        searchImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });

        //抽屉栏
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);//设置抽屉ITEM图标为原来的颜色
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Intent intent;

                switch(id){
                    case R.id.my_information_in_nav:
                        intent = new Intent( MainActivity.this , MyInformation.class );
                        startActivity(intent);
                        break;
                    case R.id.launch_in_nav:
                        if( confirmIdentification(PersonalInformation.phoneNumber) ){
                            intent = new Intent( MainActivity.this , LaunchActivity.class );
                            startActivity(intent);
                        } else {
                            Toast.makeText( getApplicationContext() , "只有管理员才能发布讲座" , Toast.LENGTH_SHORT );
                        }
                        break;
                    case R.id.setting_in_nav:
                        intent = new Intent( MainActivity.this , SettingActivity.class );
                        startActivity(intent);
                        break;

                }

                return true;
            }
        });

        View headerView=navigationView.getHeaderView(0);
        userName = (TextView) headerView.findViewById(R.id.username);
        phoneNumber = (TextView) headerView.findViewById(R.id.phoneNumber);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_lecture_image_button:
                resetAllImage();
                myLectureImageButton.setImageResource(R.drawable.my_lecture_pressed);
                setTabSelection(0);
                break;

            case R.id.lecture_list_image_button:
                resetAllImage();
                lectureListImageButton.setImageResource(R.drawable.lecture_list_pressed);
                setTabSelection(1);
                break;

            case R.id.lecture_circle_image_button:
                resetAllImage();
                lectureCircleImageButton.setImageResource(R.drawable.lecture_cricle_pressed);
                setTabSelection(2);
                break;

        }
    }

    private void resetAllImage(){
        myLectureImageButton.setImageResource(R.drawable.my_lecture_normal);
        lectureListImageButton.setImageResource(R.drawable.lecture_list_normal);
        lectureCircleImageButton.setImageResource(R.drawable.lecture_circle_normal);
    }

    private void setTabSelection(int index) {

        // 开启一个fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // 隐藏所有fragment
        hideFragments(transaction);

        switch (index) {
            case 0:
                if (myLectureFragment == null) {
                    myLectureFragment = new MyLectureFragment();
                    transaction.add(R.id.content,  myLectureFragment);
                } else {
                    transaction.show(myLectureFragment);
                }
                break;
            case 1:
                if (lectureListFragment == null) {
                    lectureListFragment = new LectureListFragment();
                    transaction.add(R.id.content, lectureListFragment);
                } else {
                    transaction.show(lectureListFragment);
                }
                break;

            case 2:
                if (lectureCircleFragment == null) {
                    lectureCircleFragment = new LectureCircleFragment();
                    transaction.add(R.id.content, lectureCircleFragment);
                } else {
                    transaction.show(lectureCircleFragment);
                }
                break;
        }

        transaction.commit();

    }

    private void hideFragments(FragmentTransaction transaction) {
        if (myLectureFragment != null)
            transaction.hide(myLectureFragment);
        if (lectureListFragment != null)
            transaction.hide(lectureListFragment);
        if (lectureCircleFragment != null)
            transaction.hide(lectureCircleFragment);
    }

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (LectureListFragment.mDropDownMenu != null && LectureListFragment.mDropDownMenu.isShowing()) {
            LectureListFragment.mDropDownMenu.closeMenu();
        } else if(drawerLayout.isDrawerOpen(Gravity.START)){
            drawerLayout.closeDrawers();
        }else{
            super.onBackPressed();
        }
    }

    private void updatePersonalInformation(){
        userName.setText(PersonalInformation.name);
        phoneNumber.setText(PersonalInformation.phoneNumber);
    }

    protected void onResume(){
        super.onResume();
        updatePersonalInformation();//更新抽屉栏个人信息
    }

    //验证是不是管理员
    private boolean confirmIdentification(String phoneNumber){
        //验证逻辑写这里

        return true;
    }
}
