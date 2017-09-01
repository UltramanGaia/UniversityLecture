package com.universitylecture.universitylecture.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.PopWindow;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Window.FEATURE_NO_TITLE;
//android:theme="@style/LoginTheme"
public class MainActivity extends BaseActivity implements View.OnClickListener {

    // 我的讲座的fragment
    private MyTwoDCodeActivity.MyLectureFragment myLectureFragment;
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

   // 更多选项按钮
    private Button moreButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
        updatePersonalInformation();

        fragmentManager = getFragmentManager();

        setTabSelection(0);

        ZXingLibrary.initDisplayOpinion(this);//初始化zxing库
    }



    private void initView() {
        myLectureImageButton = (ImageButton) findViewById(R.id.my_lecture_image_button);
        myLectureImageButton.setOnClickListener(this);
        lectureListImageButton = (ImageButton) findViewById(R.id.lecture_list_image_button);
        lectureListImageButton.setOnClickListener(this);
        lectureCircleImageButton = (ImageButton) findViewById(R.id.lecture_circle_image_button);
        lectureCircleImageButton.setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("user");
        PersonalInformation.name = user.getName();
        PersonalInformation.phoneNumber = user.getPhoneNumber();
        PersonalInformation.sex = user.getSex();

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
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //右上角加号初始化
        moreButton = (Button) findViewById(R.id.more);
        moreButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                PopWindow popWindow = new PopWindow(MainActivity.this );
                popWindow.showPopupWindow(findViewById(R.id.more));
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
                        intent.putExtra("user",user);
                        startActivity(intent);
                        break;
                    case R.id.launch_in_nav:
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                User returnUser = (User) HttpUtil.doPost(user,"VerifyLecturePublisherServlet");
                                if(returnUser != null) {
                                    Intent intent = new Intent( MainActivity.this , LaunchActivity.class );
                                    startActivity(intent);
                                }
                                else
                                    OutputMessage.outputMessage("只有管理员才能发布讲座");
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
                    myLectureFragment = new MyTwoDCodeActivity.MyLectureFragment();
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == 1 ) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    //获取了扫描结果，进行处理

                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show();



                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
