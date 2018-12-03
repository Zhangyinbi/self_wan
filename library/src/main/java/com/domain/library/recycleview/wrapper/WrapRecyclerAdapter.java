package com.domain.library.recycleview.wrapper;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.recycleview.holder.WrapperUtils;

/**
 * Project Name : UIKit
 * description:可以增加头部和尾部 自己使用,用来包装真实的adapter
 * 自己写了一个删除更新有问题 这个直接copy的弘扬大神的{@link *https://github.com/hongyangAndroid/baseAdapter}
 * 装饰设计模式  参考HeaderListViewAdapter设计{@link android.widget.HeaderViewListAdapter}
 *
 * @param <T></> 数据范型
 * @author : yinbi.zhang.o
 * Create at : 2018/12/3 10:45
 */
public class WrapRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int BASE_ITEM_TYPE_HEADER = 100000;
  private static final int BASE_ITEM_TYPE_FOOTER = 200000;

  private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
  private SparseArrayCompat<View> mFootViews   = new SparseArrayCompat<>();

  private RecyclerView.Adapter mInnerAdapter;

  /**
   * 包装真实的adapter
   *
   * @param adapter 真实的adapter
   */
  public WrapRecyclerAdapter(RecyclerView.Adapter adapter) {
    mInnerAdapter = adapter;
  }

  /**
   * 创建viewHolder
   *
   * @param parent parent
   * @param viewType viewType
   * @return viewHolder
   */
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    if (mHeaderViews.get(viewType) != null) {
      ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mHeaderViews.get(viewType));
      return holder;
    } else if (mFootViews.get(viewType) != null) {
      ViewHolder holder = ViewHolder.createViewHolder(parent.getContext(), mFootViews.get(viewType));
      return holder;
    }
    return mInnerAdapter.onCreateViewHolder(parent, viewType);
  }

  /**
   * 设置多type
   *
   * @param position position
   * @return viewType
   */
  @Override
  public int getItemViewType(int position) {
    if (isHeaderViewPos(position)) {
      return mHeaderViews.keyAt(position);
    } else if (isFooterViewPos(position)) {
      return mFootViews.keyAt(position - getHeadersCount() - getRealItemCount());
    }
    return mInnerAdapter.getItemViewType(position - getHeadersCount());
  }

  /**
   * 获取真正的item数量
   *
   * @return 真正的item数量
   */
  private int getRealItemCount() {
    return mInnerAdapter.getItemCount();
  }

  /**
   * 数据绑定
   *
   * @param holder holder
   * @param position position
   */
  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (isHeaderViewPos(position)) {
      return;
    }
    if (isFooterViewPos(position)) {
      return;
    }
    mInnerAdapter.onBindViewHolder(holder, position - getHeadersCount());
  }

  /**
   * item数量
   *
   * @return item数量
   */
  @Override
  public int getItemCount() {
    return getAllItemCount();
  }

  /**
   * 获取item数量
   *
   * @return item数量
   */
  private int getAllItemCount() {
    return getHeadersCount() + getFootersCount() + getRealItemCount();
  }

  /**
   * 处理特殊的headerView和footerView
   *
   * @param recyclerView recyclerView
   */
  @Override
  public void onAttachedToRecyclerView(RecyclerView recyclerView) {
    WrapperUtils.onAttachedToRecyclerView(mInnerAdapter, recyclerView, new WrapperUtils.SpanSizeCallback() {
      @Override
      public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup oldLookup, int position) {
        int viewType = getItemViewType(position);
        if (mHeaderViews.get(viewType) != null) {
          return layoutManager.getSpanCount();
        } else if (mFootViews.get(viewType) != null) {
          return layoutManager.getSpanCount();
        }
        if (oldLookup != null) {
          return oldLookup.getSpanSize(position);
        }
        return 1;
      }
    });
  }

  /**
   * 处理特殊的headerView和footerView
   *
   * @param holder holder
   */
  @Override
  public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
    mInnerAdapter.onViewAttachedToWindow(holder);
    int position = holder.getLayoutPosition();
    if (isHeaderViewPos(position) || isFooterViewPos(position)) {
      WrapperUtils.setFullSpan(holder);
    }
  }

  /**
   * 是不是头部
   *
   * @param position position
   * @return true是headerView  false不是
   */
  private boolean isHeaderViewPos(int position) {
    return position < getHeadersCount();
  }

  /**
   * 是不是尾部
   *
   * @param position position
   * @return true是footerView  false不是
   */
  private boolean isFooterViewPos(int position) {
    return position >= getHeadersCount() + getRealItemCount();
  }

  /**
   * 增加头部
   *
   * @param view view
   */
  public void addHeaderView(View view) {
    mHeaderViews.put(mHeaderViews.size() + BASE_ITEM_TYPE_HEADER, view);
  }

  /**
   * 增加footer
   *
   * @param view view
   */
  public void addFooterView(View view) {
    mFootViews.put(mFootViews.size() + BASE_ITEM_TYPE_FOOTER, view);
  }

  /**
   * 获取头部view数量
   *
   * @return 头部view数量
   */
  public int getHeadersCount() {
    return mHeaderViews.size();
  }

  /**
   * 获取尾部view数量
   *
   * @return 尾部view数量
   */
  public int getFootersCount() {
    return mFootViews.size();
  }
}