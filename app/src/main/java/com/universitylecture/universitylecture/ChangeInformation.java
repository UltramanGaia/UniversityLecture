package com.universitylecture.universitylecture;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.Window.FEATURE_NO_TITLE;

/**
 * Created by fengqingyundan on 2017/6/15.
 */
//修改个人信息页面
public class ChangeInformation extends BaseActivity {
    //活动传回来的数据
    private String data;

    private EditText changedContent;

    //标题栏
    private Button back;
    private Button save;
    private TextView title;

    //editText前的标题
    private TextView changingItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(FEATURE_NO_TITLE);
        setContentView(R.layout.activitu_change_information);

        initData();//初始化除按键外的信息
        initButton();//初始化按键
    }

    private void initButton(){

        //按下标题栏返回键
        back = (Button) findViewById(R.id.go_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeInformation.super.onBackPressed();
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

                    //与后台交互更新的逻辑写这里
                }
                else if( data.equals("sex") ){
                    //更新性别

                    //与后台交互更新的逻辑写这里


                    PersonalInformation.sex = changed;
                }
                else if( data.equals("phoneNumber") ){
                    //更新手机号码

                    //服务器验证逻辑写在这里


                    PersonalInformation.phoneNumber = changed;
                }
                else if( data.equals("studentNumber")){
                    //更新学号

                    //与后台交互更新的逻辑写这里

                    PersonalInformation.studentNumber = changed;
                }

                //保存成功后返回上一页面
                ChangeInformation.super.onBackPressed();
                Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initData(){
        data = getIntent().getStringExtra("data");

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
        }else
            changedContent.setHint("请输入新的学号");
    }


    private String getTitleContent(String data){
        if(data.equals("name")){
            return "姓名";
        }else if( data.equals("sex") ){
            return "性别";
        }else if( data.equals("phoneNumber")){
            return "电话";
        }else
            return "学号";
    }
}
