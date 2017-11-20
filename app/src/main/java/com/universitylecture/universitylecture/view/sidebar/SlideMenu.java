package com.universitylecture.universitylecture.view.sidebar;

/**
 * Created by Nickole on 2017/10/15.
 */
import android.content.Context;
import android.graphics.Color;
import android.view.MotionEvent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Scroller;

import com.universitylecture.universitylecture.R;

public class SlideMenu extends FrameLayout {
    private View menuView,mainView;
    private int menuWidth,mainWidth;
    private Scroller scroller;
    private int slideState = 0;  //记录当前滑动状态

    public SlideMenu(Context context) {
        super(context);
        init();
    }
    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        scroller = new Scroller(getContext());
    }
    /**
     * 当1级子view全部加载完调用，可以用初始化子view引用
     * 注意这里无法获取子view的宽高
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
        menuWidth = menuView.getLayoutParams().width;
        mainWidth = mainView.getLayoutParams().width;
    }

    //使Menu也具有滑动功能
    public boolean onInterceptTouchEvent(MotionEvent ev){

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if (slideState == 0 && downX > 100)
                    return false;
                int deltaX = (int) (ev.getX() - downX);

                if (Math.abs(deltaX) > 8){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (slideState == 1 && (int) ev.getX() == downX)
                    closeMenu();
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * s设置两个子view在页面上的布局
     * @param l:当前子view的左边在父view的坐标系的x坐标
     * @param t:当前子view的顶边在父view的坐标系的y坐标
     * @param r:当前子view的宽
     * @param b:当前子view的高
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(-menuWidth, 0, 0, b);
        mainView.layout(0, 0, r, b);
    }

    /**
     * 处理屏幕滑动事件
     */
    private int downX;
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) event.getX();
                int deltaX = moveX - downX;
                int newScrollX = getScrollX() - deltaX;
                if (newScrollX < -menuWidth) newScrollX = -menuWidth;
                if (newScrollX > 0) newScrollX = 0;
                scrollTo(newScrollX, 0);
                downX = moveX;
                break;
            case MotionEvent.ACTION_UP:
                //当滑动距离小于Menu宽度的一半时，平滑滑动到主页面
                if (getScrollX() > -menuWidth / 2) {
                    closeMenu();
                } else {
                    //当滑动距离大于Menu宽度的一半时，平滑滑动到Menu页面
                    openMenu();
                }
                break;
        }
        return true;
    }
    //关闭menu
    public void closeMenu(){
        scroller.startScroll(getScrollX(),0,0-getScrollX(),0,400);
        slideState = 0;
        invalidate();
        ImageView transBackground = (ImageView) findViewById(R.id.transparent_bg);
        transBackground.setVisibility(GONE);
    }
    //打开menu
    public void openMenu(){
        scroller.startScroll(getScrollX(),0,-menuWidth-getScrollX(),0,400);
        slideState = 1;
        invalidate();
        ImageView transBackground = (ImageView) findViewById(R.id.transparent_bg);
        transBackground.setVisibility(VISIBLE);
        transBackground.setOnClickListener(null);
    }
    /**
     * Scroller不主动去调用这个方法
     * 而invalidate()可以调用这个方法
     * invalidate->draw->computeScroll
     */
    public void computeScroll(){
        super.computeScroll();
        if(scroller.computeScrollOffset()){
            //返回true,表示动画没结束
            scrollTo(scroller.getCurrX(),0);
            invalidate();
        }
    }

    /**
     * 切换菜单的开和关
     */
    public void switchMenu(){
        if(getScrollX()==0){
            openMenu();
        }else {
            closeMenu();
        }
    }
}
