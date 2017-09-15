package com.universitylecture.universitylecture.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;
import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.pojo.User;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
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
    private int time = 60;

    private String phoneNumber;
    private String code;
    private EventHandler eventHandler;
    private Handler mHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_moblie);
        init();
        MobSDK.init(this,"1e81bc5f2c43f","d76cb4cc64ce4d4ccf287d9f063a8338");
        eventHandler = new EventHandler() {
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
                new Thread(new MyCountDownTimer()).start();

                handler.sendEmptyMessage(-8);
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        phoneNumber = mobileText.getText().toString();
                        if(phoneNumber.isEmpty())
                            OutputMessage.outputMessage("请输入手机号");
                        else {
                            code = codeText.getText().toString();
                            if(code.isEmpty())
                                OutputMessage.outputMessage("请输入验证码");
                            else {
                                User sendUser = new User();
                                sendUser.setPhoneNumber(phoneNumber);
                                sendUser.setCode(code);

                                String data = HttpUtilJSON.doPost(Object2JSONUtil.verifyCode(phoneNumber,code),"verifyCode");
                                String message = JSON2ObjectUtil.getMessage(data);
//                                User returnUser =(User) HttpUtil.doPost(sendUser, "VerifyCodeServlet");
                                if (message.equals("OK")) {
                                    Intent intent = new Intent(RegisterMobileActivity.this,RegisterUserInfoActivity.class);
                                    intent.putExtra("phoneNumber",phoneNumber);
                                    startActivity(intent);
                                }
                                else
                                    OutputMessage.outputMessage(message);
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

    class MyCountDownTimer implements Runnable{

        @Override
        public void run() {

            //倒计时开始，循环
            while (time > 0) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        sendCodeBtn.setClickable(false);
                        sendCodeBtn.setText("重新发送 " + time + " s");
                    }
                });
                try {
                    Thread.sleep(1000); //强制线程休眠1秒，就是设置倒计时的间隔时间为1秒。
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time--;
            }

            //倒计时结束，也就是循环结束
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    sendCodeBtn.setClickable(true);
                    sendCodeBtn.setText("获取验证码");
                }
            });
            time = 60; //最后再恢复倒计时时长
        }
    }
}
