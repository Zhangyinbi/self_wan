package com.domain.operationrobot.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
    //屏幕宽度
    private int mScreenWidth;

    //划出的菜单
    private ViewGroup mMenu;

    //主界面
    private ViewGroup mMain;

    private int mMenuRightPadding = 100;

    private int mMenuWidth;

    private boolean isInit = true;


    public SlidingMenu(Context context, AttributeSet attrs) {
        super(context, attrs);

        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        setHorizontalScrollBarEnabled(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInit) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);

            mMenu = (ViewGroup) wrapper.getChildAt(0);
            mMain = (ViewGroup) wrapper.getChildAt(1);

            mMenuWidth = (int) (mScreenWidth *0.8);

            mMenu.getLayoutParams().width = mMenuWidth;
            mMain.getLayoutParams().width = mScreenWidth;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            isInit = false;
        }
        scrollTo(mMenuWidth, 0);
        super.onLayout(changed, l, t, r, b);
    }

    private float downX;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_UP:
                //抬起时 滑动的距离
                float dx = ev.getX() - downX;

                //如果滑动的距离小于屏幕宽度的1/3
                if (dx < mScreenWidth / 3) {
                    smoothScrollTo(mMenuWidth, 0);
                } else {
                    smoothScrollTo(0, 0);
                }
                return true;
            default:
                break;

        }
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //滑动的百分比
        float factor = (float) l / mMenuWidth;

        //平移
        mMenu.setTranslationX(mMenuWidth * factor * 0.6f);

        //缩放
        float menuScale = 1 - 0.4f * factor;
        //mMenu.setScaleX(menuScale);
        //mMenu.setScaleY(menuScale);

        //透明度
    }
}