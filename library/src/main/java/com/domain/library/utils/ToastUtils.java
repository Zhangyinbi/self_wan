package com.domain.library.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;


import com.domain.library.BaseApplicationController;

import java.util.HashMap;
import java.util.Map;


/**
 * Toast的工具类
 *
 * @author wilson.wu
 */
public class ToastUtils {
    /**
     * toast显示间隔时间
     */
    private static final long INTERVAL_TIME = 1000L;
    private static Toast sToast = null;
    private static Map<Object, Long> sLastMap = new HashMap<>();

    /**
     * 显示toast
     *
     * @param context
     * @param text
     * @param duration
     */
    public static void showToast(final Context context, final String text, final int duration) {
        if (TextUtils.isEmpty(text) || context == null) {
            return;
        }

        new Handler(Looper.getMainLooper()).post(new Runnable() {

            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();

                if (sLastMap.isEmpty() || !sLastMap.containsKey(text)
                        || currentTime - sLastMap.get(text) > INTERVAL_TIME) {
                    if (sToast != null) {
                        sToast.setText(text);
                        sToast.setDuration(duration);
                        sToast.setGravity(Gravity.CENTER, 0, 0);
                    } else {
                        sToast = Toast.makeText(context.getApplicationContext(), text, duration);
                        sToast.setGravity(Gravity.CENTER, 0, 0);
                    }
                    sLastMap.put(text, currentTime + duration);
                    sToast.show();
                }
            }
        });
    }

    /**
     * Toast.LENGTH_SHORT 显示toast
     *
     * @param context
     * @param text
     */
    public static void showToast(Context context, String text) {
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    /**
     * @see #showToast(Context, String, int)
     */
    public static void showToast(Context context, @StringRes int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }

    /**
     * Toast.LENGTH_SHORT 显示toast
     *
     * @param context
     * @param resId
     */
    public static void showToast(Context context, @StringRes int resId) {
        showToast(context, context.getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showToast(final String text, final int duration) {
        Context context = BaseApplicationController.getContext();
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        showToast(context, text, duration);
    }

    public static void showToast(String text) {
        Context context = BaseApplicationController.getContext();
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        showToast(context, text, Toast.LENGTH_SHORT);
    }

    public static void showToast(@StringRes int resId, int duration) {
        Context context = BaseApplicationController.getContext();
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        showToast(context, ResUtils.getString(resId), duration);
    }

    public static void showToast(@StringRes int resId) {
        Context context = BaseApplicationController.getContext();
        if (null == context) {
            throw new NullPointerException("context is null");
        }
        showToast(context, ResUtils.getString(resId), Toast.LENGTH_SHORT);
    }
}
