package com.domain.library.utils.bar;

import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.domain.library.utils.bar.LightStatusBarCompat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TransStatusBarHelper {

    public static final int CAN_NOT_SET_STATUS_DARK_TEXT = 0;
    public static final int FLYME_DARK_TEXT = 2;
    public static final int MIUI_DARK_TEXT = 1;
    public static final int M_DARK_TEXT = 3;
    public static int systemMode = -1;

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 0:not support 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int setStatusBarDarkTitle(Activity activity) {
        switch (systemMode) {
            case CAN_NOT_SET_STATUS_DARK_TEXT:
                break;
            case MIUI_DARK_TEXT:
                setMIUISetStatusBarLightMode(activity.getWindow(), true);
                break;
            case FLYME_DARK_TEXT:
                setFlymeSetStatusBarLightMode(activity.getWindow(), true);
                break;
            case M_DARK_TEXT:
                try {
                    activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case -1:
                int result = 0;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (setMIUISetStatusBarLightMode(activity.getWindow(), true)) {
                        result = MIUI_DARK_TEXT;
                    } else if (setFlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                        result = FLYME_DARK_TEXT;
                    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        try {
                            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                            result = MIUI_DARK_TEXT;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                systemMode = result;
                break;
        }
        return systemMode;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setFlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏字体图标为深色，需要MIUIV6以上
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    private static boolean setMIUISetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 设置状态栏黑色字体图标，
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity
     * @return 0:not support 1:MIUUI 2:Flyme 3:android6.0
     */
    public static int getStatusBarLightMode(Activity activity) {
        if (systemMode != -1) return systemMode;
        int result = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (setMIUISetStatusBarLightMode(activity.getWindow(), true)) {
                result = 1;
            } else if (setFlymeSetStatusBarLightMode(activity.getWindow(), true)) {
                result = 2;
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                result = 3;
            }
        }
        systemMode = result;
        return result;
    }

    /**
     * 已知系统类型时，设置状态栏黑色字体图标。
     * 适配4.4以上版本MIUIV、Flyme和6.0以上版本其他Android
     *
     * @param activity type     1:MIUUI 2:Flyme 3:android6.0
     */
    public static void StatusBarLightMode(Activity activity) {
        if (systemMode == -1) getStatusBarLightMode(activity);
        int type = systemMode;
        if (type == 1) {
            //setMIUISetStatusBarLightMode(activity.getWindow(), true);
            LightStatusBarCompat.setLightStatusBar(activity.getWindow(), true);
        } else if (type == 2) {
            //setFlymeSetStatusBarLightMode(activity.getWindow(), true);
            LightStatusBarCompat.setLightStatusBar(activity.getWindow(), true);
        } else if (type == 3) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}
