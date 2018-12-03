package com.domain.library.recycleview.interfaces;

/**
 * Project Name : UIKit
 * description:多布局的支持
 *
 * @param <T></> 数据范型
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 19:06
 */
public interface MultiTypeSupport<T> {
  /**
   * 多布局支持
   *
   * @param data 数据
   * @return 布局id
   */
  int getLayoutId(T data);
}
