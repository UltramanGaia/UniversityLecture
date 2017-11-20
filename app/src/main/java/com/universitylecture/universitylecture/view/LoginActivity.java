package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.view.tool.BaseActivity;

//登录界面
public class LoginActivity extends BaseActivity {

    private EditText phoneNumberText;
    private EditText passwordText;
    private Button loginBtn;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneNumberText = (EditText) findViewById(R.id.phone_number_login);
        passwordText = (EditText) findViewById(R.id.login_password);
        loginBtn = (Button) findViewById(R.id.login_btn);
        registerBtn = (Button) findViewById(R.id.register);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(loginTask).start();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterMobileActivity.class);
                startActivity(intent);
            }
        });

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

    }
    Runnable loginTask = new Runnable() {
        @Override
        public void run() {
            String phoneNumber = phoneNumberText.getText().toString();
            String password = passwordText.getText().toString();

            if(phoneNumber.isEmpty()) {
                Looper.prepare();
                Toast.makeText(LoginActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            else if (password.isEmpty()) {
                Looper.prepare();
                Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
            else {
//                User returnUser =(User) HttpUtil.doPost(sendUser, "LoginServlet");
                User returnUser = JSON2ObjectUtil.login(HttpUtilJSON.doPost(
                        Object2JSONUtil.login(phoneNumber,password), "login"));

                if (returnUser != null) {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("user",returnUser);
                    startActivity(intent);
                    Looper.loop();
                }
                else {
                    OutputMessage.outputMessage("手机号或密码错误");
                }
            }
        }
    };

}
