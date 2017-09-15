package com.universitylecture.universitylecture.pojo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.universitylecture.universitylecture.R;
import com.universitylecture.universitylecture.util.HttpUtil;
import com.universitylecture.universitylecture.util.OutputMessage;
import com.universitylecture.universitylecture.view.MyTwoDCodeActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

/**
 * Created by fengqingyundan on 2017/8/22.
 */

public class PopWindow extends PopupWindow{
    private View conentView;
    private Context mContext;
    private User user;
    public PopWindow(final Activity context, User user ){
        this.user = user;
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.popup_window, null);
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
        final User inner_user = this.user;
        conentView.findViewById(R.id.scan).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                final User inner_inner_user = inner_user;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        User returnUser = (User) HttpUtil.doPost(inner_inner_user,"VerifyLecturePublisherServlet");
                        if(returnUser != null) {
                            Intent  intent = new Intent(mContext, CaptureActivity.class);
                            ((Activity) mContext).startActivityForResult(intent, 1 );
                        }
                        else
                            OutputMessage.outputMessage("你没有权限进行扫描");
                    }
                }).start();


                PopWindow.this.dismiss();
            }
        });
        conentView.findViewById(R.id.myTwoDCode).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(mContext , MyTwoDCodeActivity.class);
                intent.putExtra("user_id",inner_user.getId() + "");
                ((Activity) mContext).startActivity(intent);
                PopWindow.this.dismiss();
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
