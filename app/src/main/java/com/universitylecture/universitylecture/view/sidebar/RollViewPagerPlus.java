package com.universitylecture.universitylecture.view.sidebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jude.rollviewpager.RollPagerView;

/**
 * Created by Nickole on 2017/10/16.
 */

public class RollViewPagerPlus extends RollPagerView{

    private SlideMenu slideMenu;

    public RollViewPagerPlus(Context context){
        super(context,null);
    }

    public RollViewPagerPlus(Context context, AttributeSet attrs) {
        super(context, attrs, 0);

    }

    public RollViewPagerPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOutter(SlideMenu outter){
        slideMenu = outter;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        slideMenu.requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
