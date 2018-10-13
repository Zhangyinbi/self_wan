package com.domain.library.utils;


import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project Name : Invoice
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/7 15:01
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

    public static String removePrefex(String num) {
        if (num == null) return null;
        String step1 = num.replace(" ", "");
        String step2 = step1.replace("-", "");
        String result;
        if (step2.indexOf("+86") == 0) {
            result = step2.replace("+86", "");
        } else if (num.indexOf("86") == 0) {
            result = step2.replace("86", "");
        } else {
            result = step2;
        }

        return result;
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

    /**
     * emoij表情过滤
     *
     * @param text
     * @return
     */
    public static String filterEmoij(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }

        Pattern emoji = Pattern.compile(
                "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = emoji.matcher(text);
        StringBuffer sb = new StringBuffer();
        // 使用 find() 方法查找第一个匹配的对象
        boolean result = matcher.find();
        // 使用循环将句子里所有的表情找出并替换再将内容加到 sb 里
        while (result) {
            matcher.appendReplacement(sb, "");
            // 继续查找下一个匹配对象
            result = matcher.find();
        }
        // 最后调用 appendTail() 方法将最后一次匹配后的剩余字符串加到 sb 里；
        matcher.appendTail(sb);
        return sb.toString();
    }
}
