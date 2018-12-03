package com.domain.library.recycleview.interfaces;

import com.domain.library.recycleview.holder.ViewHolder;

/**
 * Project Name : UIKit
 * description:item的长按事件
 *
 * @param <T></> 数据范型
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 18:47
 */
public interface ItemLongClickListener<T> {
  /**
   * item长按事件
   *
   * @param viewHolder viewHolder
   * @param itemData itemData被惦记的数据
   * @param position position
   * @return true false
   */
  boolean onItemLongClick(ViewHolder viewHolder, T itemData, int position);
}
