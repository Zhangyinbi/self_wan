package com.domain.library.recycleview.dapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.domain.library.recycleview.holder.ViewHolder;
import com.domain.library.recycleview.interfaces.ItemClickListener;
import com.domain.library.recycleview.interfaces.ItemLongClickListener;
import com.domain.library.recycleview.interfaces.MultiTypeSupport;
import java.util.List;

/**
 * Project Name : UIKit
 * description:recyclerView的通用的adapter
 *
 * @param <T> 数据范型
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 17:12
 */
public abstract class RecyclerComAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
  public  Context               mContext;
  private LayoutInflater        mInflater;
  private int                   mLayoutId;
  private List<T>               mData;
  private ItemClickListener     mItemClickListener;
  private ItemLongClickListener mItemLongClickListener;
  private MultiTypeSupport      mTypeSupport;

  /**
   * 接收必要的参数
   *
   * @param context 上下文
   * @param data 条目数据
   * @param layoutId 布局Id
   */
  public RecyclerComAdapter(Context context, List<T> data, int layoutId) {
    this.mContext = context;
    this.mData = data;
    this.mLayoutId = layoutId;
    mInflater = LayoutInflater.from(context);
  }

  /**
   * 多item布局适配
   *
   * @param context 上下文
   * @param data 条目数据
   * @param typeSupport 多布局支持
   */
  public RecyclerComAdapter(Context context, List<T> data, MultiTypeSupport<T> typeSupport) {
    this(context, data, -1);
    this.mTypeSupport = typeSupport;
  }

  /**
   * 多条目布局type直接返回layoutId
   *
   * @param position 布局位置
   * @return 返回viewType
   */
  @Override
  public int getItemViewType(int position) {
    if (mTypeSupport != null) {
      return mTypeSupport.getLayoutId(mData.get(position));
    }
    return super.getItemViewType(position);
  }

  /**
   * 设置item长按点击事件
   *
   * @param mItemLongClickListener 长按事件
   */
  public void setItemLongClickListener(ItemLongClickListener mItemLongClickListener) {
    this.mItemLongClickListener = mItemLongClickListener;
  }

  /**
   * 设置item点击事件
   *
   * @param mItemClickListener 点击事件
   */
  public void setItemOnClickListener(ItemClickListener mItemClickListener) {
    this.mItemClickListener = mItemClickListener;
  }

  /**
   * 创建viewHolder
   *
   * @param viewGroup 父布局
   * @param viewType item类型
   * @return 返回viewHolder
   */
  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
    if (mTypeSupport != null) {
      mLayoutId = viewType;
    }
    View itemView = mInflater.inflate(mLayoutId, viewGroup, false);
    ViewHolder viewHolder = new ViewHolder(viewGroup.getContext(), itemView);
    return viewHolder;
  }

  /**
   * 绑定数据
   *
   * @param viewHolder {@link #onCreateViewHolder}
   * @param position item位置
   */
  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    //处理点击事件
    viewHolder.itemView.setOnClickListener(view -> {
      if (mItemClickListener != null) {
        mItemClickListener.onItemClick(viewHolder, mData.get(position), position);
      }
    });
    //处理长按事件
    viewHolder.itemView.setOnLongClickListener(view -> {
      if (mItemLongClickListener != null) {
        return mItemLongClickListener.onItemLongClick(viewHolder, mData.get(position), position);
      }
      return false;
    });
    convert(viewHolder, mData.get(position), position);
  }

  /**
   * 外部去做数据适配
   *
   * @param viewHolder 通用的viewHolder
   * @param itemData 当前位置的数据
   * @param position 当前的position
   */
  protected abstract void convert(ViewHolder viewHolder, T itemData, int position);

  /**
   * 获取item数量
   *
   * @return 返回item数量
   */
  @Override
  public int getItemCount() {
    return mData != null ? mData.size() : 0;
  }
}
