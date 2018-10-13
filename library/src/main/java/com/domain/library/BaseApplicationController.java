package com.domain.library;

import android.content.Context;

/**
 * Created by andrew.tong on 2017/8/9.
 */

public class BaseApplicationController {

    private static Context context;
    private static boolean isDebug;

    public static void init(Context context, boolean isDebug) {
        BaseApplicationController.context = context;
        BaseApplicationController.isDebug = isDebug;

    }

    public static Context getContext() {
        return BaseApplicationController.context;
    }

    public static boolean getDebug() {
        return isDebug;
    }
}
