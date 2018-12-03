package com.domain.library.recycleview.holder;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by zhy on 16/6/28.
 * {@Link *https://github.com/hongyangAndroid/baseAdapter}
 */
public final class WrapperUtils {
  private WrapperUtils() {
  }

  /**
   * copy 解决头部尾部被当成普通的item处理的问题
   *
   * @param innerAdapter 真正的adapter
   * @param recyclerView recyclerView
   * @param callback callback
   */
  public static void onAttachedToRecyclerView(RecyclerView.Adapter innerAdapter, RecyclerView recyclerView, final SpanSizeCallback callback) {
    innerAdapter.onAttachedToRecyclerView(recyclerView);

    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
      final GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();

      gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
        @Override
        public int getSpanSize(int position) {
          return callback.getSpanSize(gridLayoutManager, spanSizeLookup, position);
        }
      });
      gridLayoutManager.setSpanCount(gridLayoutManager.getSpanCount());
    }
  }

  /**
   * 设置全部
   *
   * @param holder holder
   */
  public static void setFullSpan(RecyclerView.ViewHolder holder) {
    ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();

    if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {

      StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;

      p.setFullSpan(true);
    }
  }

  /**
   * 外部回调
   */
  public interface SpanSizeCallback {
    /**
     * @param layoutManager layoutManager
     * @param oldLookup oldLookup
     * @param position position
     * @return 返回数量
     */
    int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position);
  }
}
