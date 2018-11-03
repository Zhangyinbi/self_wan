package com.domain.library.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.ResultReceiver;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressLint("NewApi")
public class DisplaysUtil {
    private static float mDip = -1;
    private static boolean ENFORCE_PHONE_SCREEN = false;
    /**
     * 判断是否为平板的一个阀值
     */
    public final static int LARGE_SCREEN_DPI = 600;

    public static float getWindowHeight(Activity context) {
        if (null != context.getWindow().getDecorView()) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.height();
        }
        return 0;
    }

    public static float getWindowWidth(Activity context) {
        if (null != context.getWindow().getDecorView()) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            return rect.width();
        }
        return 0;
    }

    public static Rect getWindowRect(Activity context) {
        Rect rect = new Rect();
        if (null != context.getWindow().getDecorView()) {
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        }

        return rect;
    }

    /**
     * 系统状态栏是否可见
     */
    public static boolean isStatusBarVisible(Activity context) {
        WindowManager.LayoutParams params = context.getWindow().getAttributes();
        int paramsFlag = params.flags & (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return paramsFlag == params.flags;
    }

    /**
     * 隐藏系统状态栏
     */
    public static void hideStatusBar(Activity context) {
        //该属性保证状态栏的隐藏和显示不会挤压Activity向下，状态栏覆盖在Activity之上
        Window window = context.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);

        WindowManager.LayoutParams params = window.getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        window.setAttributes(params);
    }

    /**
     * 显示系统状态栏
     */
    public static void showStatusBar(Activity context) {
        Window window = context.getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR);

        WindowManager.LayoutParams params = window.getAttributes();
        params.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setAttributes(params);
    }

    /**
     * 切换系统状态栏状态
     */
    public static void toggleStatusBar(Activity context) {
        if (isStatusBarVisible(context)) {
            hideStatusBar(context);
        } else {
            showStatusBar(context);
        }
    }

    public static int getDisplayWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getWidth();
    }

    public static int getDisplayHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        return display.getHeight();
    }

    public static int getScreenSize(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return config.screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
    }

    public static boolean isSmallScreenSize(Context context) {
        return Configuration.SCREENLAYOUT_SIZE_SMALL == getScreenSize(context);
    }

    public static boolean isNormalScreenSize(Context context) {
        return Configuration.SCREENLAYOUT_SIZE_NORMAL == getScreenSize(context);
    }

    public static boolean isLargeScreenSize(Context context) {
        return Configuration.SCREENLAYOUT_SIZE_LARGE == getScreenSize(context);
    }

    public static boolean isXLargeScreenSize(Context context) {
        return 0x04 /*Configuration.SCREENLAYOUT_SIZE_XLARGE*/ == getScreenSize(context);//API 9
    }

    public static float getDensity(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.density;
    }

    public static int getDensityDpi(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    public static boolean screenCompareWith(int dpi, Context context) {
        return Math.min(getDisplayWidth(context), getDisplayHeight(context)) >= dpi;
    }

    public static boolean isXHighDensity(Context context) {
        return getDensityDpi(context) == DisplayMetrics.DENSITY_XHIGH;
    }

    public static boolean isHighDensity(Context context) {
        return getDensityDpi(context) == DisplayMetrics.DENSITY_HIGH;
    }

    public static boolean isMediumDensity(Context context) {
        return getDensityDpi(context) == DisplayMetrics.DENSITY_MEDIUM;
    }

    public static boolean isLowDensity(Context context) {
        return getDensityDpi(context) == DisplayMetrics.DENSITY_LOW;
    }

    public static boolean isDefaultDensity(Context context) {
        return getDensityDpi(context) == DisplayMetrics.DENSITY_DEFAULT;
    }

    public static boolean isPhoneScreen(Context context) {
        return !isPadScreen(context);
    }

    public static boolean isPadScreen(Context context) {
        if (ENFORCE_PHONE_SCREEN) {
            return false;
        }

        if (Build.VERSION.SDK_INT < 9) {
            return Math.min(getDisplayWidth(context), getDisplayHeight(context)) >= LARGE_SCREEN_DPI * getDensity(context);
        } else if (isXLargeScreenSize(context)) {
            return true;
        } else if (isLargeScreenSize(context)) {
            //TODO 7寸版适配机制，目前设备趋势认为不是DENSITY_HIGH即为平板。
            return getDensityDpi(context) != DisplayMetrics.DENSITY_HIGH;//注意：和build.sh里cp values-large 的逻辑一致
        }
        return false;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }

    public static float getContextDecorViewTop(Activity context) {
        if (null != context.getWindow().getDecorView()) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int contextDecorViewTop = rect.top;
            return contextDecorViewTop;
        }
        return 0;
    }

    public static float getContextDecorViewWidth(Activity context) {
        if (null != context.getWindow().getDecorView()) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int contextDecorViewWidth = rect.right - rect.left;
            return contextDecorViewWidth;
        }
        return 0;
    }

    public static float getContextDecorViewHeight(Activity context) {
        if (null != context.getWindow().getDecorView()) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int contextDecorViewHeight = rect.bottom - rect.top;
            return contextDecorViewHeight;
        }
        return 0;
    }

    //dialog算高度时，把顶部的状态栏也算上。
    public static float getContextDecorViewHeightInDialog(Activity context) {
        if (null != context.getWindow().getDecorView()) {
            Rect rect = new Rect();
            context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            int contextDecorViewHeight = rect.bottom;
            return contextDecorViewHeight;
        }
        return 0;
    }

    public static void toggleSoftInput(final View targetView) {
        if (null != targetView) {
            final InputMethodManager imm = (InputMethodManager) targetView.getContext()
                                                                          .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void showSoftKeyBoard(View targetView) {
        if (null != targetView) {
            InputMethodManager imm = (InputMethodManager) targetView.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(targetView, 0);
        }
    }

    public static void hideSoftKeyBoard(View targetView) {
        hideSoftKeyBoard(targetView, null);
    }

    public static boolean hideSoftKeyBoard(View targetView, ResultReceiver resultReceiver) {
        if (null != targetView) {
            InputMethodManager imm = (InputMethodManager) targetView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (null != imm) {
                boolean result = imm.hideSoftInputFromWindow(targetView.getWindowToken(), 0, resultReceiver);
                return result;
            }
        }
        return false;
    }

    /**
     * 4.0 关闭硬件加速None
     */
    public static void setLayerTypeNone(View view, Paint paint) {
        setLayerType(view, paint, 0);
    }

    /**
     * 4.0 关闭硬件加速Soft
     */
    public static void setLayerTypeSoft(View view, Paint paint) {
        setLayerType(view, paint, 1);
    }

    /**
     * 4.0 硬件加速
     */
    public static void setLayerTypeHard(View view, Paint paint) {
        setLayerType(view, paint, 2);
    }

    private static void setLayerType(View view, Paint paint, int type) {
        if (Build.VERSION.SDK_INT < 14) {
            return;
        }
        int softwareType = type;
        Class<View> c = View.class;
        Method setLayerType = null;
        @SuppressWarnings("rawtypes")
        Class[] cargs = new Class[2];
        Object[] inArgs = new Object[2];
        try {
            // 获取所有public/private/protected/默认
            // 方法的函数，如果只需要获取public方法，则可以调用getMethod.
            cargs[0] = int.class;
            cargs[1] = Paint.class;
            inArgs[0] = softwareType;
            inArgs[1] = paint;
            setLayerType = c.getMethod("setLayerType", cargs);
            // 将要执行的方法对象设置是否进行访问检查，也就是说对于public/private/protected/默认
            // 我们是否能够访问。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false
            // 则指示反射的对象应该实施 Java 语言访问检查。
            setLayerType.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (setLayerType != null) {
            try {
                setLayerType.invoke(view, inArgs);
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
    }

    /**
     * 判断是否开启了强制硬件加速 ，view的接口不可用，需要用canvas
     */
    public static boolean isHardwareAccelerated(Canvas canvas) {
        if (Build.VERSION.SDK_INT < 11) { // Canvas.isHardwareAccelerated()的api level为11
            return false;
        }
        Class<Canvas> c = Canvas.class;
        Method isHardwareAcceleratedMethod = null;
        try {
            // 获取所有public/private/protected/默认
            // 方法的函数，如果只需要获取public方法，则可以调用getMethod.
            isHardwareAcceleratedMethod = c.getMethod("isHardwareAccelerated", (Class[]) null);
            // 将要执行的方法对象设置是否进行访问检查，也就是说对于public/private/protected/默认
            // 我们是否能够访问。值为 true 则指示反射的对象在使用时应该取消 Java 语言访问检查。值为 false
            // 则指示反射的对象应该实施 Java 语言访问检查。
            isHardwareAcceleratedMethod.setAccessible(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (isHardwareAcceleratedMethod != null) {
            try {
                return (Boolean) isHardwareAcceleratedMethod.invoke(canvas, (Object[]) null);
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        }
        return false;
    }

    public static boolean isLand(Context context) {
        return Configuration.ORIENTATION_LANDSCAPE == context.getResources().getConfiguration().orientation;
    }

    public static void setEnforcePhoneScreen(boolean isPhoneScreen) {
        ENFORCE_PHONE_SCREEN = isPhoneScreen;
    }

    public static void setFullScreenFlags(Activity activity) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if (isPadScreen(activity))
//		{
//			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//		}
    }

    public static void clearFullScreenFlags(Activity activity) {
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		if (isPadScreen(activity))
//		{
//			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//		}
    }

    public static boolean isSamsung() {
        return "samsung".equals(Build.BRAND) && Build.VERSION.SDK_INT >= 16;
    }

    public static boolean isFullScreenVersion(Context context) {
        if ("Amazon".equals(Build.BRAND)) {
            return false;
        }

        if ("MI PAD".equals(Build.MODEL)) {
            return false;
        }

        //只有大于等于4.4的系统需要自动全屏
        return (Build.VERSION.SDK_INT >= 19) && isPadScreen(context);
    }

    public static void setWindowLayoutAllFlags(Activity activity) {
        if (isSamsung()) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        }
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    public static void clearWindowLayoutAllFlags(Activity activity) {
        if (isSamsung()) {
            activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    //全屏状态改为内容位置不被挤压出屏幕范围
    public static void setLayoutInsetDecorFlagsInFullScreen(Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    }

    public static void clearLayoutInsetDecorFlagsInFullScreen(Activity activity) {
        activity.getWindow().setFlags(~WindowManager.LayoutParams.FLAG_LAYOUT_INSET_DECOR,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
    }

    /**
     * 得到 1dip的长度
     */
    public static float getDip(Context context) {
        if (mDip <= 0) {
            mDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, // 1dip;
                    context.getResources().getDisplayMetrics());
        }
        return mDip;
    }

    /**
     * 获取通知栏的高度
     *
     * @param context 上下文对象
     * @return 通知栏高度
     */
    public static float getStatusBarHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density * 25;
    }

    /**
     * 根据手机的分辨率从dp 的单位 转成为px(像素)
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从px(像素) 的单位 转成为dp
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据设计标准1280*720的分辨率px转换为实际手机的px
     *
     * @return
     */
    public static int basePx2DevicePxH(Context context, float pxHeightValue) {
        int minPx = 1;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int deviceH = displayMetrics.heightPixels;
        int baseH = 1280;
        if (pxHeightValue * deviceH / baseH > 1) {
            minPx = (int) (pxHeightValue * deviceH / baseH);
        }
        return minPx;
    }

    /**
     * 根据设计标准1280*720的分辨率px转换为实际手机的px
     *
     * @return
     */
    public static int basePx2DevicePxW(Context context, float pxWidthValue) {
        int minPx = 1;
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int deviceH = displayMetrics.widthPixels;
        int baseH = 720;
        if (pxWidthValue * deviceH / baseH > 1) {
            minPx = (int) (pxWidthValue * deviceH / baseH);
        }
        return minPx;
    }

    /**
     * 调整滚动位置，用来显示完整展示内容
     *
     * @param sv
     * @param itemView
     * @param expandHeight
     */
    public static void scrollViewIfNecessacy(final ScrollView sv,
                                             View itemView, int expandHeight) {
        Rect svRect = new Rect();
        sv.getGlobalVisibleRect(svRect);

        Rect itemRect = new Rect();
        itemView.getGlobalVisibleRect(itemRect);

        final int scrollY = itemRect.top + expandHeight - svRect.bottom;

        if (scrollY > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sv.smoothScrollBy(0, scrollY);
                }
            }, 100);

        }
    }

    /**
     * 调整滚动位置，用来显示完整展示内容
     *
     * @param sv
     * @param itemView
     * @param expandHeight
     */
    public static void scrollListViewIfNecessacy(final ListView sv, View itemView, int expandHeight) {
        Rect svRect = new Rect();
        sv.getGlobalVisibleRect(svRect);

        Rect itemRect = new Rect();
        itemView.getGlobalVisibleRect(itemRect);

        final int scrollY = itemRect.top + expandHeight - svRect.bottom;

        if (scrollY > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    sv.smoothScrollBy(scrollY, 250);
                    // sv.smoothScrollBy(0, scrollY);
                }
            }, 100);

        }
    }

    /**
     * 从attribute获取属性值
     *
     * @param context
     * @param attrs
     * @param key
     * @return
     */
    public static String getValueFromAttrs(Context context, AttributeSet attrs, String key) {
        if (attrs != null) {
            int count = attrs.getAttributeCount();
            for (int i = 0; i < count; i++) {
                String name = attrs.getAttributeName(i);
                if (!TextUtils.isEmpty(name) && name.equals(key)) {
                    return attrs.getAttributeValue(i);
                }
            }
        }
        return null;
    }


    public static String getTextFromAttrs(Context context, AttributeSet attrs) {
        return getTextFromAttrs(context, attrs, "text");
    }

    public static String getTextFromAttrs(Context context, AttributeSet attrs, String name) {
        String value = getValueFromAttrs(context, attrs, name);
        if (!TextUtils.isEmpty(value)) {
            if (value.startsWith("@")) {
                int res = str2Int(value.substring(1), -1, 10);
                if (res != -1) {
                    try {
                        value = context.getString(res);
                    } catch (Exception e) {

                    }
                }
            }
        }
        return value;
    }

    public static int getDimenFromAttrs(Context context, AttributeSet attrs, String name) {
        String value = getValueFromAttrs(context, attrs, name);
        if (!TextUtils.isEmpty(value)) {
            if (value.startsWith("@")) {
                int res = str2Int(value.substring(1), -1, 10);
                if (res != -1) {
                    try {
                        return context.getResources().getDimensionPixelSize(res);
                    } catch (Exception e) {

                    }
                }
            }
        }
        return -1;
    }

    public static Drawable getDrawableFromAttrs(Context context, AttributeSet attrs, String key) {
        String value = getValueFromAttrs(context, attrs, key);
        if (!TextUtils.isEmpty(value)) {
            if (value.startsWith("@")) {
                int res = str2Int(value.substring(1), -1, 10);
                if (res != -1) {
                    try {
                        Drawable drawable = context.getResources().getDrawable(res);
                        return drawable;
                    } catch (Exception e) {

                    }
                }
            }
        }
        return null;
    }

    public static int str2Int(String str, int defValue, int radix) {
        int ret = defValue;
        try {
            if (!TextUtils.isEmpty(str)) {
                ret = Integer.parseInt(str.trim(), radix);
            }
        } catch (Exception ex) {
        }
        return ret;
    }

    public static int getTextViewLines(TextView tv, int measuredWidth) {
        if (measuredWidth != 0 && View.VISIBLE == tv.getVisibility()) {
            int line = (int) ((tv.getPaint().measureText(tv.getText().toString()) + (measuredWidth - 1))
                    / measuredWidth);
            return line;
        }
        return 0;
    }

    public static float dpToPx(Resources resources, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
    }
}