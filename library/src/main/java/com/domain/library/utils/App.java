package com.domain.library.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.domain.library.BaseApplicationController;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/11 21:02
 */
public class App {
    /**
     * 获取App安装包信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo() {
        String packageName = BaseApplicationController.getContext().getPackageName();
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }
        PackageInfo info = null;
        try {
            info = BaseApplicationController.getContext().getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

}
