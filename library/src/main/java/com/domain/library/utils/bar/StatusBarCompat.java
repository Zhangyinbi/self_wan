package com.domain.library.utils.bar;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 设置状态栏沉浸式（无padding）
 *
 * @author msdx (msdx.android@qq.com)
 * @version 0.5.1
 * @since 0.1
 */

public class StatusBarCompat {

    interface IStatusBar {
        void setStatusBarColor(Window window, int color);
    }

    static final IStatusBar IMPL;
    static final boolean isEMUI;

    static {
        IStatusBar defaultStatus = new IStatusBar() {
            @Override
            public void setStatusBarColor(Window window, int color) {
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            IMPL = new StatusBarMImpl();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //IMPL = new StatusBarLollipopImpl(); //5.0暂时不设置沉浸式
            IMPL = defaultStatus;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //IMPL = new StatusBarKitkatImpl(); //4.4暂时不设置沉浸式
            IMPL = defaultStatus;
        } else {
            IMPL = defaultStatus;
        }

        isEMUI = isEMUI0();
    }

    public static void setStatusBarColor(Window window, @ColorInt int color) {
        if ((window.getAttributes().flags & WindowManager.LayoutParams.FLAG_FULLSCREEN) > 0) {
            return;
        }
        try {
            IMPL.setStatusBarColor(window, color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class StatusBarMImpl implements IStatusBar {

        @TargetApi(Build.VERSION_CODES.M)
        public void setStatusBarColor(Window window, int color) {
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
            //设置导航栏颜色
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    static class StatusBarLollipopImpl implements IStatusBar {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        public void setStatusBarColor(Window window, int color) {
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
            //设置导航栏颜色
            window.setNavigationBarColor(Color.BLACK);
        }
    }

    static class StatusBarKitkatImpl implements IStatusBar {

        @TargetApi(Build.VERSION_CODES.KITKAT)
        public void setStatusBarColor(Window window, int color) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private static boolean isEMUI0() {
        File file = new File(Environment.getRootDirectory(), "build.prop");
        if (file.exists()) {
            Properties properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                properties.load(fis);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return properties.containsKey("ro.build.hw_emui_api_level");
        }
        return false;
    }

    public static boolean isEMUI() {
        return isEMUI;
    }
}
