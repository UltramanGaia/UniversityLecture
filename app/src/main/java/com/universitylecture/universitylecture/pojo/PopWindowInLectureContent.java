package com.universitylecture.universitylecture.pojo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.alarm.AlarmManagerUtil;
import com.universitylecture.universitylecture.view.PersonalInformation;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.util.Calendar;

/**
 * Created by fengqingyundan on 2017/9/7.
 */

public class PopWindowInLectureContent extends PopupWindow {
    private View conentView;
    private Context mContext;
    private Lecture mlecture;

    public PopWindowInLectureContent(final Activity context , final Lecture lecture){
        mContext = context;
        mlecture = lecture;

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window_in_lecture_content, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 + 40);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        //设置闹铃
        Button setAlarm = (Button) conentView.findViewById(R.id.setAlarm);
        setAlarm.setText("设置提醒");
        setAlarm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                String[] times = lecture.getTime().split("-|:| ");
                int year = Integer.parseInt(times[0]);
                int month = Integer.parseInt(times[1]) - 1;
                int day = Integer.parseInt(times[2]);
                int hour = Integer.parseInt(times[3]) - 1;
                int minute = Integer.parseInt(times[4]);
                //long triggerAtTime = getTimeDiff();
                int id = Integer.parseInt(lecture.getID());
                AlarmManagerUtil.setAlarm(mContext, 0, year , month , day , hour , minute , id , 0, "你有一个讲座要去听啦", 2);
                Toast.makeText(mContext, "闹钟设置成功，将在讲座前一小时提醒您", Toast.LENGTH_LONG).show();
                PopWindowInLectureContent.this.dismiss();
            }
        });
//
//        //取消闹铃
//        conentView.findViewById(R.id.myTwoDCode).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//
//                PopWindowInLectureContent.this.dismiss();
//            }
//        });

        int uId = PersonalInformation.id;
        String uName = PersonalInformation.name;
        String uPhone = PersonalInformation.phoneNumber;
        String uSex = PersonalInformation.sex;
        String uPassword = PersonalInformation.password;
        final User inner_user = new User( uId , uName , uPassword , uSex , uPhone );
        conentView.findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final User inner_inner_user = inner_user;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        User returnUser = (User) HttpUtil.doPost(inner_inner_user,"VerifyLecturePublisherServlet");
//                        if(returnUser != null) {
                            Intent intent = new Intent(mContext, CaptureActivity.class);
                            ((Activity) mContext).startActivityForResult(intent, 1 );
//                        }
//                        else
//                            OutputMessage.outputMessage("你没有权限进行扫描");
                    }
                }).start();
                PopWindowInLectureContent.this.dismiss();
            }
        });
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 5);
        } else {
            this.dismiss();
        }
    }

//    private long getTimeDiff(){
//        String[] times = mlecture.getTime().split("-|:| ");
//        int year = Integer.parseInt(times[0]);
//        int month = Integer.parseInt(times[1]) - 1;
//        int day = Integer.parseInt(times[2]);
//        int hour = Integer.parseInt(times[3]) - 1;
//        int minute = Integer.parseInt(times[4]);
//
//        int
//
//    }
}