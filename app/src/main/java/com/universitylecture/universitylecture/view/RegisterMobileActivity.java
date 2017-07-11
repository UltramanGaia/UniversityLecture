package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtil;
import com.universitylecture.universitylecture.util.OutputMessage;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by helloworld on 2017/6/18.
 */

public class RegisterMobileActivity extends BaseActivity {

    private EditText mobileText;
    private EditText codeText;
    private Button sendCodeBtn;
    private Button nextBtn;
    private int time;

    private String phoneNumber;
    private String code;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_moblie);
        init();
        SMSSDK.initSDK(this, "1e81bc5f2c43f", "d76cb4cc64ce4d4ccf287d9f063a8338");
        EventHandler eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eventHandler);

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mobileText.getText().toString();
                if (!judgePhoneNums(phoneNumber))
                    return;
                SMSSDK.getVerificationCode("86", phoneNumber);
                sendCodeBtn.setClickable(false);
                sendCodeBtn.setText("重新发送(" + time + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (time = 60; time > 0; time--) {
                            if (time <= 0)
                                break;
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

                handler.sendEmptyMessage(-8);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if(phoneNumber.isEmpty())
                            Toast.makeText(RegisterMobileActivity.this,"请输入手机号",
                                    Toast.LENGTH_SHORT).show();
                        else {
                            code = codeText.getText().toString();
                            if(code.isEmpty())
                                Toast.makeText(RegisterMobileActivity.this,"请输入验证码",
                                        Toast.LENGTH_SHORT).show();
                            else {
                                User sendUser = new User();
                                sendUser.setPhoneNumber(phoneNumber);
                                sendUser.setCode(code);
                                User returnUser =(User) HttpUtil.doPost(sendUser, "VerifyCodeServlet");
                                if (returnUser.getMessage().equals("OK")) {
                                    Intent intent = new Intent(RegisterMobileActivity.this,RegisterUserInfoActivity.class);
                                    intent.putExtra("user",returnUser);
                                    startActivity(intent);
                                }
                                else
                                    OutputMessage.outputMessage(returnUser.getMessage());
                            }
                        }

                    }
                }).start();

            }
        });


    }

    public void init() {
        mobileText = (EditText) findViewById(R.id.mobile);
        codeText = (EditText) findViewById(R.id.identifyCode);
        sendCodeBtn = (Button) findViewById(R.id.send_code);
        nextBtn = (Button) findViewById(R.id.next_button);

        phoneNumber = "";
        code = "";
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == -9) {
                sendCodeBtn.setText("重新发送(" + time + ")");
            }
            else if (msg.what == -8) {
                sendCodeBtn.setText("获取验证码");
                sendCodeBtn.setClickable(true);
            }
            else {
                int event = msg.arg1;
                int result= msg.arg2;
                Object data = msg.obj;
                if(result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(RegisterMobileActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
                    }
                    else
                        ((Throwable) data).printStackTrace();
                }
            }
        }
    };

    private boolean judgePhoneNums(String phoneNum) {
        String telRegex = "[1][358]\\d{9}";

        if (phoneNum.matches(telRegex))
            return true;
        Toast.makeText(RegisterMobileActivity.this,"手机号输入有误",
                Toast.LENGTH_SHORT).show();
        return false;

    }

    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}