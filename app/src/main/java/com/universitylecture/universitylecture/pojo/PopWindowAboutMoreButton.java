package com.universitylecture.universitylecture.pojo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.util.HttpUtilJSON;
import com.universitylecture.universitylecture.util.JSON2ObjectUtil;
import com.universitylecture.universitylecture.util.Object2JSONUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.view.MainActivity;
import com.universitylecture.universitylecture.view.functionActivity.AskQuestionActivity;
import com.universitylecture.universitylecture.view.functionActivity.MyTwoDCodeActivity;
import com.universitylecture.universitylecture.view.sidebar.LaunchActivity;
import com.universitylecture.universitylecture.view.tool.PersonalInformation;

/**
 * Created by fengqingyundan on 2017/10/22.
 */

public class PopWindowAboutMoreButton extends PopupWindow{
    private View conentView;
    private Context mContext;
    private User user;
    private Button myTwoDCode;
    private Button publishLecture;
    private Button askQuestion;

    public PopWindowAboutMoreButton(final Activity context, User user ){
        this.user = user;
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window_about_more_button, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();

        myTwoDCode = (Button) conentView.findViewById(R.id.myTwoDCode);
        Drawable drawable1 = conentView.getResources().getDrawable(R.drawable.mytwodcode);
        drawable1.setBounds(0,0,60,60);
        myTwoDCode.setCompoundDrawables(drawable1,null,null,null);

        publishLecture = (Button) conentView.findViewById(R.id.publishLecture);
        Drawable drawable2 = conentView.getResources().getDrawable(R.drawable.publishlecture);
        drawable2.setBounds(0,0,60,60);
        publishLecture.setCompoundDrawables(drawable2,null,null,null);

        askQuestion = (Button) conentView.findViewById(R.id.askQuestion);
        Drawable drawable3 = conentView.getResources().getDrawable(R.drawable.question);
        drawable3.setBounds(0,0,60,60);
        askQuestion.setCompoundDrawables(drawable3,null,null,null);

        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w / 2 - 100);
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

        final User inner_user = user;
//        conentView.findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                final User inner_inner_user = inner_user;
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        User returnUser = (User) HttpUtil.doPost(inner_inner_user,"VerifyLecturePublisherServlet");
//                        if(returnUser != null) {
//                            Intent  intent = new Intent(mContext, CaptureActivity.class);
//                            ((Activity) mContext).startActivityForResult(intent, 1 );
//                        }
//                        else
//                            OutputMessage.outputMessage("你没有权限进行扫描");
//                    }
//                }).start();
//
//
//                PopWindowAboutMoreButton.this.dismiss();
//            }
//        });

        conentView.findViewById(R.id.myTwoDCode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext , MyTwoDCodeActivity.class);
                intent.putExtra("user_id",inner_user.getId() + "");
                ((Activity) mContext).startActivity(intent);
                PopWindowAboutMoreButton.this.dismiss();
            }
        });

        conentView.findViewById(R.id.publishLecture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                                User returnUser = (User) HttpUtil.doPost(user,"VerifyLecturePublisherServlet");

                        String data = HttpUtilJSON.doPost(Object2JSONUtil.isLecturePublisher(PersonalInformation.phoneNumber),"verifyLecturePublisher");
                        String message = JSON2ObjectUtil.getMessage(data);
                        if(message.equals("OK")) {
                            Intent intent = new Intent( mContext , LaunchActivity.class );
                            ((Activity) mContext).startActivity(intent);
                        }
                        else
                            OutputMessage.outputMessage(message);
                    }
                }).start();

                PopWindowAboutMoreButton.this.dismiss();
            }
        });

        conentView.findViewById(R.id.askQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , AskQuestionActivity.class);
                intent.putExtra("user_id",inner_user.getId() + "");
                ((Activity) mContext).startActivity(intent);
                PopWindowAboutMoreButton.this.dismiss();
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

}
