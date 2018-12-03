package com.domain.library.recycleview.holder;

import android.widget.ImageView;

/**
 * Project Name : UIKit
 * description:图片加载的帮助类
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 18:25
 */
public abstract class ImageLoadHolder {
    /**
     * 图片加载
     *
     * @param imageView view
     * @param path      图片资源路径
     */
    public abstract void loadImage(ImageView imageView, String path);
}
