package com.domain.library.recycleview.creator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Project Name : UIKit
 * description:上拉加载更多的辅助类,采用接口去设计，为了拓展
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 21:34
 */
public interface LoadViewCreator {
    /**
     * 获取上啦加载更多的view
     *
     * @param context 上下文
     * @param parent  父布局
     * @return 返回上拉加载更多的view
     */
    View getLoadView(Context context, ViewGroup parent);

    /**
     * 正在下拉pull
     *
     * @param currentDragHeight 当前拖动的高度
     * @param refreshViewHeight 总的刷新高度 即刷新view 的高度
     * @param currentLoadStatus 当前状态 {@link fd.nio.com.uikit.recycleview.RefreshRecyclerView}
     */
    void onPull(int currentDragHeight, int refreshViewHeight, int currentLoadStatus);

    /**
     * 加载更多
     */
    void onLoading();

    /**
     * 完成加载
     */
    void onComplete();
}
