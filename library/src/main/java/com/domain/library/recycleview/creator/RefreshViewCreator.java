package com.domain.library.recycleview.creator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Project Name : UIKit
 * description:下拉刷新的辅助类,为了匹配所有效果
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 21:04
 */
public interface RefreshViewCreator {
  /**
   * 获取下拉刷新的View
   * 在里面做一些初始化的操作
   *
   * @param context 上下文
   * @param parent RecyclerView
   * @return view
   */
  View getRefreshView(Context context, ViewGroup parent);

  /**
   * 正在下拉pull
   *
   * @param currentDragHeight 当前拖动的高度
   * @param refreshViewHeight 总的刷新高度 即刷新view 的高度
   * @param currentRefreshStatus 当前状态
   */
  void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus);

  /**
   * 正在刷新中
   */
  void onRefreshing();

  /**
   * 停止刷新
   */
  void onComplete();
}
