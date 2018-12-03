package com.domain.library.recycleview.interfaces;

import com.domain.library.recycleview.holder.ViewHolder;

/**
 * Project Name : UIKit
 * description:item的点击事件监听
 *
 * @param <T></> 数据范型
 * Create at : 2018/12/2 18:39
 * @author : yinbi.zhang.o
 */
public interface ItemClickListener<T> {
  /**
   * item点击事件
   *
   * @param viewHolder viewHolder
   * @param itemData itemData被惦记的数据
   * @param position position
   */
  void onItemClick(ViewHolder viewHolder, T itemData, int position);
}
