package com.domain.library.recycleview.holder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Project Name : UIKit
 * description:recyclerView的通用的ViewHolder
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 17:12
 */
public class ViewHolder extends RecyclerView.ViewHolder {
  /**
   * 用于缓存已有的view
   * 这是一个map，key是Integer类型 节约内存,更加高效 原因:{@link SparseArray}
   */
  private SparseArray<View> mViews;

  private Context mContext;

  /**
   * 创建viewHolder
   *
   * @param context 上下文
   * @param itemView itemView
   */
  public ViewHolder(Context context, @NonNull View itemView) {
    super(itemView);
    mContext = context;
    mViews = new SparseArray<>();
  }

  /**
   * 创建viewHolder
   *
   * @param context 上下文
   * @param itemView itemView
   * @return viewHolder
   */
  public static ViewHolder createViewHolder(Context context, View itemView) {
    ViewHolder holder = new ViewHolder(context, itemView);
    return holder;
  }

  /**
   * 创建viewHolder
   *
   * @param context 上下文
   * @param layoutId itemViewId
   * @param parent parent
   * @return viewHolder
   */
  public static ViewHolder createViewHolder(Context context, ViewGroup parent, int layoutId) {
    View itemView = LayoutInflater.from(context)
                                  .inflate(layoutId, parent, false);
    ViewHolder holder = new ViewHolder(context, itemView);
    return holder;
  }

  /**
   * 节约findViewById的书写和次数
   *
   * @param id viewId
   * @param <T> view的范型
   * @return 返回view实例化对象
   */
  public final <T extends View> T getView(int id) {
    View view = mViews.get(id);
    if (null == view) {
      try {
        view = itemView.findViewById(id);
      } catch (ClassCastException ex) {
        throw ex;
      }
      mViews.put(id, view);
    }
    return (T) view;
  }

  /**
   * 只能给textView设置文本信息 否则会报错
   * 给textView设置文本信息
   *
   * @param viewId textView的id
   * @param text 文本信息
   * @return 链式调用
   */
  public ViewHolder setText(int viewId, CharSequence text) {
    TextView tv = getView(viewId);
    tv.setText(text);
    return this;
  }

  /**
   * 只能给ImageView设置图片 否则会报错
   * 设置图片
   *
   * @param viewId ImageView的id
   * @param resourceId 图片资源Id
   * @return 链式调用
   */
  public ViewHolder setImageResource(int viewId, int resourceId) {
    ImageView iv = getView(viewId);
    iv.setImageResource(resourceId);
    return this;
  }

  /**
   * 加载网络资源图片
   *
   * @param viewId 图片view的id
   * @param path path
   * @param imageLoader imageLoader
   * @return 链式调用
   */
  public ViewHolder setImagePath(int viewId, String path, ImageLoadHolder imageLoader) {
    ImageView imageView = getView(viewId);
    imageLoader.loadImage(imageView, path);
    return this;
  }

  /**
   * 设置图片
   *
   * @param viewId ImageView Id
   * @param bitmap bitmap
   * @return 链式调用
   */
  public ViewHolder setImageBitmap(int viewId, Bitmap bitmap) {
    ImageView view = getView(viewId);
    view.setImageBitmap(bitmap);
    return this;
  }

  /**
   * @param viewId ImageView Id
   * @param drawable drawable文件
   * @return 链式调用
   */
  public ViewHolder setImageDrawable(int viewId, Drawable drawable) {
    ImageView view = getView(viewId);
    view.setImageDrawable(drawable);
    return this;
  }

  /**
   * 设置背景颜色
   *
   * @param viewId viewId
   * @param color 背景颜色
   * @return 链式调用
   */
  public ViewHolder setBackgroundColor(int viewId, int color) {
    View view = getView(viewId);
    view.setBackgroundColor(color);
    return this;
  }

  /**
   * 设置背景颜色
   *
   * @param viewId viewId
   * @param backgroundRes 背景颜色
   * @return 链式调用
   */
  public ViewHolder setBackgroundRes(int viewId, int backgroundRes) {
    View view = getView(viewId);
    view.setBackgroundResource(backgroundRes);
    return this;
  }

  /**
   * 设置文字颜色
   *
   * @param viewId viewId
   * @param textColor textColor
   * @return 链式调用
   */
  public ViewHolder setTextColor(int viewId, int textColor) {
    TextView view = getView(viewId);
    view.setTextColor(textColor);
    return this;
  }

  /**
   * 设置文字颜色
   *
   * @param viewId viewId
   * @param textColorRes textColor
   * @return 链式调用
   */
  public ViewHolder setTextColorRes(int viewId, int textColorRes) {
    TextView view = getView(viewId);
    view.setTextColor(mContext.getResources()
                              .getColor(textColorRes));
    return this;
  }

  /**
   * 设置透明度
   *
   * @param viewId viewId
   * @param value alpha
   * @return 链式调用
   */
  @SuppressLint("NewApi")
  public ViewHolder setAlpha(int viewId, float value) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
      getView(viewId).setAlpha(value);
    } else {
      // Pre-honeycomb hack to set Alpha value
      AlphaAnimation alpha = new AlphaAnimation(value, value);
      alpha.setDuration(0);
      alpha.setFillAfter(true);
      getView(viewId).startAnimation(alpha);
    }
    return this;
  }

  /**
   * @param viewId //
   * @param visible //
   * @return 链式调用
   */
  public ViewHolder setVisible(int viewId, boolean visible) {
    View view = getView(viewId);
    view.setVisibility(visible ? View.VISIBLE : View.GONE);
    return this;
  }

  /**
   * link设置
   *
   * @param viewId viewId
   * @return 链式调用
   */
  public ViewHolder linkify(int viewId) {
    TextView view = getView(viewId);
    Linkify.addLinks(view, Linkify.ALL);
    return this;
  }

  /**
   * 事件点击
   *
   * @param viewId viewId
   * @param listener listener
   * @return 链式调用
   */
  public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
    View view = getView(viewId);
    view.setOnClickListener(listener);
    return this;
  }

  /**
   * 触摸事件
   *
   * @param viewId viewId
   * @param listener listener
   * @return 链式调用
   */
  public ViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
    View view = getView(viewId);
    view.setOnTouchListener(listener);
    return this;
  }

  /**
   * 长按事件
   *
   * @param viewId viewId
   * @param listener listener
   * @return 链式调用
   */
  public ViewHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
    View view = getView(viewId);
    view.setOnLongClickListener(listener);
    return this;
  }
}
