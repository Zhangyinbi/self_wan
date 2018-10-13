package com.domain.library.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;

import com.domain.library.BaseApplicationController;


/**
 * Created by andrew.tong on 2017/8/9.
 */

public class ResUtils {

    public static String getString(@StringRes int res) {
        Context context = BaseApplicationController.getContext();
        return context.getApplicationContext().getResources().getString(res);
    }

    public static String getString(@StringRes int res, Object... formatArgs) {
        Context context = BaseApplicationController.getContext();
        return context.getApplicationContext().getResources().getString(res, formatArgs);
    }

    public static int getColor(@ColorRes int res) {
        Context context = BaseApplicationController.getContext();
        return ContextCompat.getColor(context, res);
    }

    public static float getDimension(@DimenRes int res) {
        Context context = BaseApplicationController.getContext();
        return context.getResources().getDimension(res);
    }

    public static Drawable getDrawable(@DrawableRes int res) {
        Context context = BaseApplicationController.getContext();
        return context.getResources().getDrawable(res);
    }

    public static int getDimensionPixelOffset(@DimenRes int res) {
        Context context = BaseApplicationController.getContext();/**/
        return context.getResources().getDimensionPixelOffset(res);
    }
}
