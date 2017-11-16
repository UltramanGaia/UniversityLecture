package com.universitylecture.universitylecture.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.PopWindowAboutMoreButton;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.view.fragment.LectureCircleFragment;
import com.universitylecture.universitylecture.view.fragment.LectureListFragment;
import com.universitylecture.universitylecture.view.fragment.MyLectureFragment;
import com.universitylecture.universitylecture.view.sidebar.LaunchActivity;
import com.universitylecture.universitylecture.view.sidebar.MyInformation;
import com.universitylecture.universitylecture.view.sidebar.SlideMenu;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.DropDownMenu;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Window.FEATURE_NO_TITLE;
//主界面
public class MainActivity extends BaseActivity implements View.OnClickListener {

    // 我的讲座的fragment
    private MyLectureFragment myLectureFragment;
    // 讲座列表的fragment
    private LectureListFragment lectureListFragment;
    // 讲座圈的fragment
    private LectureCircleFragment lectureCircleFragment;

    // 底部对应的ImageButton和LinearLayout和textView
    private ImageButton myLectureImageButton;
    private ImageButton lectureListImageButton;
    private ImageButton lectureCircleImageButton;
    private LinearLayout myLectureImageLayout;
    private LinearLayout lectureListImageLayout;
    private LinearLayout lectureCircleImageLayout;
    private TextView myLectureImageText;
    private TextView lectureListImageText;
    private TextView lectureCircleImageText;

    // drawer layout
    private DrawerLayout drawerLayout;
    private CircleImageView drawerToggleImageButton;
    private NavigationView navigationView;

    // 更多选项按钮
    private Button moreButton;//顶部加号部分

    // fragment管理器
    private FragmentManager fragmentManager;
    //@InjectView(R.id.dropDownMenu) DropDownMenu mDropDownMenu;//筛选列表

    @InjectView(R.id.dropDownMenu)
    DropDownMenu mDropDownMenu;//筛选列表

    //抽屉栏头像下的个人信息
    private View headerView;
    private TextView userName;
    private TextView phoneNumber;
    private User user;

    //抽屉栏按钮
    private Button btnMyInformation;
    private Button btnMyMessage;
    private Button btnMyFavourite;
    private Button btnHelp;
    private Button btnAbout;
    private Button btnMySetting;
    private Button btnLogOut;

    private SlideMenu slideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        updatePersonalInformation();
        fragmentManager = getFragmentManager();

        setTabSelection(0);
        ZXingLibrary.initDisplayOpinion(this); //初始化zxing库

    }


    private void initView() {
        slideMenu = (SlideMenu) findViewById(R.id.slideMenu);
        myLectureImageButton = (ImageButton) findViewById(R.id.my_lecture_image_button);
        myLectureImageLayout = (LinearLayout) findViewById(R.id.my_lecture_image_layout);
        myLectureImageText = (TextView) findViewById(R.id.my_lecture_image_text);
        myLectureImageLayout.setOnClickListener(this);

        lectureListImageButton = (ImageButton) findViewById(R.id.lecture_list_image_button);
        lectureListImageLayout = (LinearLayout) findViewById(R.id.lecture_list_image_layout);
        lectureListImageText = (TextView) findViewById(R.id.lecture_list_image_text);
        lectureListImageLayout.setOnClickListener(this);

        lectureCircleImageButton = (ImageButton) findViewById(R.id.lecture_circle_image_button);
        lectureCircleImageLayout = (LinearLayout) findViewById(R.id.lecture_circle_image_layout);
        lectureCircleImageText = (TextView) findViewById(R.id.lecture_circle_image_text);
        lectureCircleImageLayout.setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("user");
        PersonalInformation.name = user.getName();
        PersonalInformation.phoneNumber = user.getPhoneNumber();
        PersonalInformation.sex = user.getSex();
        PersonalInformation.id = user.getId();
        PersonalInformation.password = user.getPassword();
        PersonalInformation.studentNumber = user.getStudentNumber();

        /*
            测试数据
        PersonalInformation.name = "cf";
        PersonalInformation.phoneNumber = "13631428910";
        PersonalInformation.sex = "男";*/

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggleImageButton = (CircleImageView) findViewById(R.id.toggle_drawer_open);
        drawerToggleImageButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                slideMenu.switchMenu();
            }
        });

        //右上角加号初始化
        moreButton = (Button) findViewById(R.id.more);
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopWindowAboutMoreButton popWindow = new PopWindowAboutMoreButton(MainActivity.this, user);
                popWindow.showPopupWindow(findViewById(R.id.more));
            }
        });


        /*
        抽屉栏
         */
        initNavIcon();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);//设置抽屉ITEM图标为原来的颜色
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            public boolean onNavigationItemSelected(MenuItem item) {
                int id = item.getItemId();
                Intent intent;

                switch(id){
                    case R.id.my_information_in_nav:
                        intent = new Intent( MainActivity.this , MyInformation.class );
                        intent.putExtra("user",user);
                        startActivity(intent);
                        break;
                    case R.id.launch_in_nav:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
//                                User returnUser = (User) HttpUtil.doPost(user,"VerifyLecturePublisherServlet");

                                String data = HttpUtilJSON.doPost(Object2JSONUtil.isLecturePublisher(user.getPhoneNumber()),"verifyLecturePublisher");
                                String message = JSON2ObjectUtil.getMessage(data);
                                if(message.equals("OK")) {
                                    Intent intent = new Intent( MainActivity.this , LaunchActivity.class );
                                    startActivity(intent);
                                }
                                else
                                    OutputMessage.outputMessage(message);
                            }
                        }).start();
                        break;
                    case R.id.setting_in_nav:
                        intent = new Intent( "com.universitylecture.universitylecture.FORCE_OFFLINE");
                        sendBroadcast(intent);
                        break;

                }

                return true;
            }
        });

        View headerView=navigationView.getHeaderView(0);
        userName = (TextView) headerView.findViewById(R.id.username);
        phoneNumber = (TextView) headerView.findViewById(R.id.phoneNumber);

    }

    private void initNavIcon() {
        btnMyInformation=(Button)findViewById(R.id.my_information_in_nav);
        btnMyMessage=(Button)findViewById(R.id.my_message_in_nav);
        btnMyFavourite=(Button)findViewById(R.id.my_favourite_in_nav);
        btnHelp=(Button)findViewById(R.id.help_and_feedback);
        btnAbout=(Button)findViewById(R.id.about_in_nav);
        btnMySetting=(Button)findViewById(R.id.setting_in_nav);
        btnLogOut=(Button)findViewById(R.id.log_out_in_nav);

        Drawable drawable1=getResources().getDrawable(R.drawable.my_information);
        drawable1.setBounds(0,0,80,80);
        btnMyInformation.setCompoundDrawables(drawable1,null,null,null);

        Drawable drawable2=getResources().getDrawable(R.drawable.message);
        drawable2.setBounds(0,0,80,80);
        btnMyMessage.setCompoundDrawables(drawable2,null,null,null);

        Drawable drawable3=getResources().getDrawable(R.drawable.favorite);
        drawable3.setBounds(0,0,80,80);
        btnMyFavourite.setCompoundDrawables(drawable3,null,null,null);

        Drawable drawable4=getResources().getDrawable(R.drawable.help);
        drawable4.setBounds(0,0,80,80);
        btnHelp.setCompoundDrawables(drawable4,null,null,null);

        Drawable drawable5=getResources().getDrawable(R.drawable.about);
        drawable5.setBounds(0,0,80,80);
        btnAbout.setCompoundDrawables(drawable5,null,null,null);

        Drawable drawable6=getResources().getDrawable(R.drawable.setting);
        drawable6.setBounds(0,0,60,60);
        btnMySetting.setCompoundDrawables(drawable6,null,null,null);

        Drawable drawable7=getResources().getDrawable(R.drawable.exit);
        drawable7.setBounds(0,0,60,60);
        btnLogOut.setCompoundDrawables(drawable7,null,null,null);
    }

    /**
     * 用于控制NavigationBar的隐藏和显示
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void showSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                      | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_lecture_image_layout:
                resetAllImage();
                myLectureImageButton.setImageResource(R.drawable.my_lecture_pressed);
                myLectureImageText.setTextColor(ContextCompat.getColor(this,R.color.purple));
                setTabSelection(0);
                break;

            case R.id.lecture_list_image_layout:
                resetAllImage();
                lectureListImageButton.setImageResource(R.drawable.lecture_list_pressed);
                lectureListImageText.setTextColor(ContextCompat.getColor(this,R.color.purple));
                setTabSelection(1);
                break;

            case R.id.lecture_circle_image_layout:
                resetAllImage();
                lectureCircleImageButton.setImageResource(R.drawable.lecture_cricle_pressed);
                lectureCircleImageText.setTextColor(ContextCompat.getColor(this,R.color.purple));
                setTabSelection(2);
                break;

        }
    }


    private void resetAllImage(){
        myLectureImageButton.setImageResource(R.drawable.my_lecture_normal);
        myLectureImageText.setTextColor(ContextCompat.getColor(this,R.color.sbc_header_text));
        lectureListImageButton.setImageResource(R.drawable.lecture_list_normal);
        lectureListImageText.setTextColor(ContextCompat.getColor(this,R.color.sbc_header_text));
        lectureCircleImageButton.setImageResource(R.drawable.lecture_circle_normal);
        lectureCircleImageText.setTextColor(ContextCompat.getColor(this,R.color.sbc_header_text));
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

    public SlideMenu getSlideMenu(){
        return slideMenu;
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent data){
//        if (requestCode == 1 ) {
//            //处理扫描结果（在界面上显示）
//            if (null != data) {
//                Bundle bundle = data.getExtras();
//                if (bundle == null) {
//                    return;
//                }
//                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
//                    //获取了扫描结果，进行处理
//
//                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
//
//
//                } else if (bundle.getInt(CodeUtils.RESULT_TYPE)== CodeUtils.RESULT_FAILED) {
//                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }


}
