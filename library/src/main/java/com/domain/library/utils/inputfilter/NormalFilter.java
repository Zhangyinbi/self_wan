package com.domain.library.utils.inputfilter;

import android.text.InputFilter;
import android.text.Spanned;


import com.domain.library.R;
import com.domain.library.utils.ToastUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Project Name : Test2
 * description:过滤掉emoji表情和颜文字
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/9/18 14:58
 */
public class NormalFilter implements InputFilter {
    Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        Matcher matcher = pattern.matcher(charSequence);
        if (!matcher.find()) {
            return null;
        } else {
            ToastUtils.showToast(R.string.input_error);
            return "";
        }

    }
}

