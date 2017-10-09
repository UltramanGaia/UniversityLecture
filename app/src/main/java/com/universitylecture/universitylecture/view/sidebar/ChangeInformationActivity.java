package com.universitylecture.universitylecture.view.sidebar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.view.tool.BaseActivity;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;

import static android.view.Window.FEATURE_NO_TITLE;
import static com.universitylecture.universitylecture.view.tool.PersonalInformation.phoneNumber;

/**
 * Created by fengqingyundan on 2017/6/15.
 */
//修改个人信息页面
public class ChangeInformationActivity extends BaseActivity {
    //活动传回来的数据
    private String data;

    private EditText changedContent;

    //标题栏
    private Button back;
    private Button save;
    private TextView title;

    //editText前的标题
    private TextView changingItem;
    private User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activity_change_information);

        initData();//初始化除按键外的信息
        initButton();//初始化按键
    }

    private void initButton(){

        //按下标题栏返回键
        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInformationActivity.super.onBackPressed();
            }
        });

        //按下标题栏保存键
        save = (Button) findViewById(R.id.save_information);
        save.setVisibility(View.VISIBLE);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String changed = changedContent.getText().toString();

                //根据传过来的data决定要改的是哪个信息
                if( data.equals("name") ){
                    //要改的是名字
                    PersonalInformation.name = changed;
                    user.setName(changed);
                    new Thread(changeInformationTask).start();
                    //与后台交互更新的逻辑写这里
                }
                else if( data.equals("sex") ){
                    //更新性别
                    //与后台交互更新的逻辑写这里
                    PersonalInformation.sex = changed;
                    user.setSex(changed);
                    new Thread(changeInformationTask).start();
                }
                else if( data.equals("phoneNumber") ){
                    //更新手机号码

                    //服务器验证逻辑写在这里
                    phoneNumber = changed;
                    user.setPhoneNumber(changed);
                    new Thread(changeInformationTask).start();
                }
                //保存成功后返回上一页面
                ChangeInformationActivity.super.onBackPressed();
            }

        });
    }

    private void initData(){
        data = getIntent().getStringExtra("data");
        user = (User) getIntent().getSerializableExtra("user");



        changedContent = (EditText) findViewById(R.id.changing_information);
        setContentHint();

        String titleContent = getTitleContent(data);

        title = (TextView) findViewById(R.id.title_in_title_bar);
        title.setText(titleContent);

        changingItem = (TextView) findViewById(R.id.changing_item);
        changingItem.setText(titleContent);
    }

    private void setContentHint(){
        if(data.equals("name")){
            changedContent.setHint("请输入新的姓名");
        }else if( data.equals("sex") ){
            changedContent.setHint("请输入新的性别");
        }else if( data.equals("phoneNumber")){
            changedContent.setHint("请输入新的电话");
        }
    }


    private String getTitleContent(String data){
        if(data.equals("name")){
            return "姓名";
        }else if( data.equals("sex") ){
            return "性别";
        }else {
            return "电话";
        }

    }

    Runnable changeInformationTask = new Runnable() {
        @Override
        public void run() {
//            User returnUser =(User) HttpUtil.doPost(user,"UpdateUserServlet");

            User returnUser = JSON2ObjectUtil.login(HttpUtilJSON.doPost(
                    Object2JSONUtil.register(user), "update"));
            if (returnUser != null)
                OutputMessage.outputMessage("保存成功");
            else
                OutputMessage.outputMessage("保存失败");
        }
    };
}
