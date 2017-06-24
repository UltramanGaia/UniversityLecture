package com.universitylecture.universitylecture.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtil;
import com.universitylecture.universitylecture.util.OutputMessage;

public class LoginActivity extends Activity{

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
    }

    Runnable loginTask = new Runnable() {
        @Override
        public void run() {
            String phoneNumber = phoneNumberText.getText().toString();
            String password = passwordText.getText().toString();
            User sendUser = new User(phoneNumber,password);

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
                User returnUser =(User) HttpUtil.doPost(sendUser, "LoginServlet");

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
