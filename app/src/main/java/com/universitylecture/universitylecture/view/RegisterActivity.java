/*
package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtil;
import com.universitylecture.universitylecture.util.OutputMessage;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.universitylecture.universitylecture.R.id.sex;

public class RegisterActivity extends AppCompatActivity{

    EditText userNameText;
    EditText passwordText;
    EditText comfirmPasswordText;
    EditText studentNumberText;
    EditText phoneNumberText;
    EditText codeText;
    RadioGroup sexGroup;
    RadioButton male;
    RadioButton female;
    Button sendCodeBtn;
    Button registerBtn;
    Button cancelBtn;
    int time = 60;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        final User user = new User();
        sexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int id = group.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);
                user.setSex(rb.getText().toString());
            }
        });
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
                    String phoneNumber = phoneNumberText.getText().toString();
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

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SMSSDK.submitVerificationCode("86",phoneNumber,codeText.getText().toString());
                Log.e("register", "onClick: ");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String userName = userNameText.getText().toString();
                        String studentNumber = studentNumberText.getText().toString();
                        String phoneNumber = phoneNumberText.getText().toString();
                        String password = passwordText.getText().toString();
                        String confirmPassword = comfirmPasswordText.getText().toString();
                        String code = codeText.getText().toString();
                        Log.d("Sex", "onCreate: " + user.getSex());
                        User sendUser = new User(userName,studentNumber,password,user.getSex(),phoneNumber);
                        sendUser.setCode(code);
                        if(userName.isEmpty())
                            OutputMessage.outputMessage("用户名不能为空");
                        else {
                            if (!judgePhoneNums(phoneNumber))
                                return;
                            else {
                                User returnUser =(User) HttpUtil.doPost(sendUser, "VerifyCodeServlet");
                                if (returnUser.getMessage().equals("OK")) {
                                    if (!password.equals(confirmPassword))
                                        OutputMessage.outputMessage("输入密码不一致");
                                    else {
                                        if (returnUser.getSex().isEmpty())
                                           OutputMessage.outputMessage("请选择性别");
                                        else {
                                            returnUser =(User) HttpUtil.doPost(sendUser, "RegisterServlet");
                                            if (returnUser != null) {
                                                Looper.prepare();
                                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(intent);
                                                Looper.loop();
                                            } else {
                                                OutputMessage.outputMessage("注册失败");
                                            }
                                        }
                                    }
                                }
                                else
                                    OutputMessage.outputMessage(returnUser.getMessage());
                            }
                        }

                    }
                }).start();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    public void init() {
        userNameText = (EditText) findViewById(R.id.userName);
        phoneNumberText = (EditText) findViewById(R.id.phone_number_register);
        passwordText = (EditText) findViewById(R.id.register_password);
        comfirmPasswordText = (EditText) findViewById(R.id.verify_register_password);
        codeText = (EditText) findViewById(R.id.verify_code);
        studentNumberText = (EditText) findViewById(R.id.studentNumber);
        sexGroup = (RadioGroup) findViewById(sex);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        sendCodeBtn = (Button) findViewById(R.id.send_code);
        registerBtn = (Button) findViewById(R.id.register_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
    }

    */
/*Runnable registerTask = new Runnable() {
        @Override
        public void run() {
            String userName = userNameText.getText().toString();
            String phoneNumber = phoneNumberText.getText().toString();
            String password = passwordText.getText().toString();
            String confirmPassword = comfirmPasswordText.getText().toString();
            final User sendUser = new User(userName,password,null,phoneNumber);
            Log.e("sex", "run: " + sendUser.getSex());
            if(!password.equals(confirmPassword))
                Toast.makeText(RegisterActivity.this,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
            else {

                User returnUser = HttpUtil.doPost(sendUser,"RegisterServlet");

                if(returnUser != null) {

                    Looper.prepare();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    createProgressBar();
                    //Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                    //startActivity(intent);
                    Looper.loop();
                }
                else {
                    Looper.prepare();
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }

        }
    };*//*


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
                        Toast.makeText(RegisterActivity.this,"验证码已发送",Toast.LENGTH_SHORT).show();
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
        OutputMessage.outputMessage("手机号输入有误");
        return false;

    }

    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
*/
