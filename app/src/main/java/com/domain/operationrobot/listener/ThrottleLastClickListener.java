package com.domain.operationrobot.listener;

import android.view.View;

import static com.domain.operationrobot.util.Constant.MIN_CLICK_DELAY_TIME;

/**
 * Project Name : Do-Feidian-widget-android
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/11 17:41
 */
public abstract class ThrottleLastClickListener implements View.OnClickListener, IViewClick {

    public long mMinDelay = MIN_CLICK_DELAY_TIME;

    public long mLastClickTime = 0;

    @Override
    public void onClick(View v) {
        long currentClickTime = System.currentTimeMillis();
        if (currentClickTime - mLastClickTime > mMinDelay) {
            mLastClickTime = currentClickTime;
            onViewClick(v);
        }
    }
}
