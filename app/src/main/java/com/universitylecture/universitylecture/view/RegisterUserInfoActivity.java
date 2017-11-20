package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.view.tool.BaseActivity;

/**
 * Created by helloworld on 2017/6/18.
 */

public class RegisterUserInfoActivity extends BaseActivity {

    private EditText nameText;
    private EditText passwordText;
    private EditText stuNumText;
    private Button registerBtn;
    RadioGroup sexGroup;
    RadioButton male;
    RadioButton female;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        setContentView(R.layout.activity_register_userinfo);
        init();

        final User sendUser = new User();
        sendUser.setPhoneNumber(getIntent().getStringExtra("phoneNumber"));

        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int id = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                sendUser.setSex(rb.getText().toString());
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("register", "onClick: ");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String userName = nameText.getText().toString();
                        String studentNumber = stuNumText.getText().toString();
                        String password = passwordText.getText().toString();
                        sendUser.setName(userName);
                        sendUser.setPassword(password);
                        sendUser.setStudentNumber(studentNumber);

                        if(userName.isEmpty())
                            OutputMessage.outputMessage("用户名不能为空");
                        else {
                            if (studentNumber.isEmpty())
                                OutputMessage.outputMessage("学号不能为空");
                            else {
                                if (password.isEmpty())
                                    OutputMessage.outputMessage("密码不能为空");
                                else {
                                    if (sendUser.getSex().isEmpty())
                                        OutputMessage.outputMessage("请选择性别");
                                    else {
//                                        User returnUser =(User) HttpUtil.doPost(sendUser, "RegisterServlet");
                                        User returnUser = JSON2ObjectUtil.login(HttpUtilJSON.doPost(
                                                Object2JSONUtil.register(sendUser), "register"));
                                        if (returnUser != null) {
                                            Looper.prepare();
                                            Toast.makeText(RegisterUserInfoActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterUserInfoActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            Looper.loop();
                                        } else {
                                            OutputMessage.outputMessage("注册失败");
                                        }
                                    }
                                }
                            }
                        }
                    }
                }).start();
            }
        });
    }

    private void init() {

        nameText = (EditText) findViewById(R.id.name);
        passwordText = (EditText) findViewById(R.id.password);
        stuNumText = (EditText) findViewById(R.id.studentNumber);
        sexGroup = (RadioGroup) findViewById(R.id.sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        registerBtn = (Button) findViewById(R.id.register_button);
    }
}
