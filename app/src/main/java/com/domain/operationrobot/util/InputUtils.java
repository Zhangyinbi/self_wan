package com.domain.operationrobot.util;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/12 21:34
 */
public class InputUtils {
    /**
     * 手机号验证
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNumber(String mobiles) {
        String testRegex = "[9][8][7][6][2]\\d{6}";
        String telRegex = "[1][23456789]\\d{9}";
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return (mobiles.matches(telRegex) || mobiles.matches(testRegex));
        }
    }


    /**
     * 通过正则验证邮箱格式是否正确
     *
     * @param email 邮箱地址
     * @return 验证结果
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);

        return m.matches();
    }

}
