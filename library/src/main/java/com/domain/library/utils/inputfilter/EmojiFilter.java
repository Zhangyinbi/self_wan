package com.domain.library.utils.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Project Name : Test2
 * description : 限制输入emoji表情
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/9/18 14:42
 */
public class EmojiFilter implements InputFilter {
    /**
     * 检测是否有emoji表情
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart,
                               int dend) {
        if (containsEmoji(source.toString())) {
            return "";
        }
        return source;
    }
}
