package com.domain.library.utils;


import android.text.InputFilter;

import com.domain.library.utils.inputfilter.EmojiFilter;
import com.domain.library.utils.inputfilter.NormalFilter;


/**
 * Project Name : Test2
 * description:input过滤
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/9/18 14:34
 */
public class EditInputFilterUtil {
    /**
     * 过滤emoji表情
     *
     * @return
     */
    public static InputFilter getEmojiFilter() {
        return new EmojiFilter();
    }

    /**
     * 排除emoji和颜文字
     *
     * @return
     */
    public static InputFilter getNormalFilter() {
        return new NormalFilter();
    }
}
