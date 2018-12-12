package com.domain.library.recycleview.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by Mr.HE on 2018/4/3.
 * Description:http://blog.csdn.net/chen413203144/article/details/52662378
 * (new DividerItemDecoration(this.getContext(), LinearLayoutManager.HORIZONTAL, 10, ContextCompat.getColor(this.getContext(), R.color.bg)));
 * 分割线
 * 注释 ： 处理头部和尾部，不需要花分割线，前提是外部需要设置多少个头部和尾部
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
  //水平
  public static final  int   HORIZONTAL_LIST = RecyclerView.HORIZONTAL;
  //垂直
  public static final  int   VERTICAL_LIST   = RecyclerView.VERTICAL;
  //水平+垂直
  public static final  int   BOTH_SET        = 2;
  //系统自带的参数
  private static final int[] ATTRS           = new int[] {android.R.attr.listDivider};
  private Paint    mPaint;
  //取名mDivider似乎更恰当
  private Drawable mDrawable;
  //分割线高度，默认为1px
  private int mDividerHeight = 2;
  //列表的方向
  private int mOrientation;
  private int headerCount;
  private int footerCount;

  /**
   * 默认分割线：高度为2px，颜色为灰色
   *
   * @param context 上下文
   * @param orientation 列表方向
   */
  public DividerItemDecoration(Context context, int orientation) {
    this.setOrientation(orientation);
    //获取xml配置的参数
    final TypedArray a = context.obtainStyledAttributes(ATTRS);
    //typedArray.getDrawable(attr)这句是说我们可以通过我们的资源获得资源，使用我们的资源名attr去获得资源id
    //看不懂就用自己写一个分割线的图片吧，方法：ContextCompat.getDrawable(context, drawableId);
    mDrawable = a.getDrawable(0);
    //官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
    //在TypedArray后调用recycle主要是为了缓存。
    a.recycle();
  }

  /**
   * 自定义分割线
   *
   * @param context 上下文
   * @param orientation 列表方向
   * @param drawableId 分割线图片
   */
  public DividerItemDecoration(Context context, int orientation, int drawableId) {
    this.setOrientation(orientation);
    //旧的getDrawable方法弃用了，这个是新的
    mDrawable = ContextCompat.getDrawable(context, drawableId);
    mDividerHeight = mDrawable.getIntrinsicHeight();
  }

  /**
   * 自定义分割线
   *
   * @param context 上下文
   * @param orientation 列表方向
   * @param dividerHeight 分割线高度
   * @param dividerColor 分割线颜色
   */
  public DividerItemDecoration(Context context, int orientation, int dividerHeight,
    int dividerColor) {//ContextCompat.getColor(this.getContext(),R.color.bg)
    this.setOrientation(orientation);
    mDividerHeight = dividerHeight;
//        LogUtils.e("mDividerHeight", mDividerHeight + "===================");
    //抗锯齿画笔
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(dividerColor);
    //填满颜色
    mPaint.setStyle(Paint.Style.FILL);
  }
  ///**
  // * 默认分割线：高度为2px，颜色为灰色
  // *
  // * @param context 上下文
  // * @param orientation 列表方向
  // */
  //public DividerItemDecoration(Context context, int orientation,int headerCount,int footerCount) {
  //  this.setOrientation(orientation);
  //  //获取xml配置的参数
  //  final TypedArray a = context.obtainStyledAttributes(ATTRS);
  //  //typedArray.getDrawable(attr)这句是说我们可以通过我们的资源获得资源，使用我们的资源名attr去获得资源id
  //  //看不懂就用自己写一个分割线的图片吧，方法：ContextCompat.getDrawable(context, drawableId);
  //  mDrawable = a.getDrawable(0);
  //  //官方的解释是：回收TypedArray，以便后面重用。在调用这个函数后，你就不能再使用这个TypedArray。
  //  //在TypedArray后调用recycle主要是为了缓存。
  //  a.recycle();
  //}

  /**
   * 设置头部数量
   *
   * @param count 头部数量
   */
  public void setHeaderCount(int count) {
    headerCount = count;
  }

  /**
   * 设置尾部数量
   *
   * @param count 尾部数量
   */
  public void setFooterCount(int count) {
    footerCount = count;
  }

  /**
   * 设置方向
   *
   * @param orientation 方向
   */
  public void setOrientation(int orientation) {
    if (orientation < 0 || orientation > 2) {
      throw new IllegalArgumentException("invalid orientation");
    }
    mOrientation = orientation;
  }

  /**
   * 绘制分割线之后,需要留出一个外边框,就是说item之间的间距要换一下
   *
   * @param outRect outRect.set(0, 0, 0, 0);的四个参数理解成margin就好了
   * @param view 视图
   * @param parent 父级view
   * @param state state
   */
  @Override
  public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
    //下面super...代码其实调用的就是那个过时的getItemOffsets,也就是说这个方法体内容也可以通通移到那个过时的getItemOffsets中
    super.getItemOffsets(outRect, view, parent, state);
    //获取layoutParams参数
    RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
    //当前位置
    int itemPosition = layoutParams.getViewLayoutPosition();

    //ItemView数量
    int childCount = parent.getAdapter()
                           .getItemCount();
    if (itemPosition < headerCount || itemPosition >= childCount - footerCount) {
      outRect.set(0, 0, 0, 0);
      return;
    }
    switch (mOrientation) {
      case BOTH_SET:
        //获取Layout的相关参数
        int spanCount = this.getSpanCount(parent);
        if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
          // 如果是最后一行，则不需要绘制底部
          outRect.set(0, 0, mDividerHeight, 0);
        } else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
          // 如果是最后一列，则不需要绘制右边
          outRect.set(0, 0, 0, mDividerHeight);
        } else {
          outRect.set(0, 0, mDividerHeight, mDividerHeight);
        }
        break;
      case VERTICAL_LIST:
        childCount -= 1;
        //水平布局右侧留Margin,如果是最后一列,就不要留Margin了
        outRect.set(0, 0, (itemPosition != childCount) ? mDividerHeight : 0, 0);
        break;
      case HORIZONTAL_LIST:
        childCount -= 1;
        //垂直布局底部留边，最后一行不留
        outRect.set(0, 0, 0, (itemPosition != childCount) ? mDividerHeight : 0);
        break;
      default:
        break;
    }
  }

  /**
   * 绘制分割线
   *
   * @param c 画笔
   * * @param parent 父级view
   * @param state state
   */
  @Override
  public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);
    if (mOrientation == VERTICAL_LIST) {
      drawVertical(c, parent);
    } else if (mOrientation == HORIZONTAL_LIST) {
      drawHorizontal(c, parent);
    } else {
      drawHorizontal(c, parent);
      drawVertical(c, parent);
    }
  }

  /**
   * 绘制横向 item 分割线
   *
   * @param canvas 画布
   * @param parent 父容器
   */
  private void drawHorizontal(Canvas canvas, RecyclerView parent) {
    final int x = parent.getPaddingLeft();
    final int width = parent.getMeasuredWidth() - parent.getPaddingRight();
    //getChildCount()(ViewGroup.getChildCount) 返回的是显示层面上的“所包含的子 View 个数”。
    final int childSize = parent.getChildCount();
    for (int i = 0; i < childSize; i++) {
      final View child = parent.getChildAt(i);
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
      //item底部的Y轴坐标+margin值
      final int y = child.getBottom() + layoutParams.bottomMargin;
      final int height = y + mDividerHeight;
//            LogUtils.e("height", height + "===================");
      if (mDrawable != null) {
        //setBounds(x,y,width,height); x:组件在容器X轴上的起点 y:组件在容器Y轴上的起点
        // width:组件的长度 height:组件的高度
        mDrawable.setBounds(x, y, width, height);
        mDrawable.draw(canvas);
      }
      if (mPaint != null) {
        canvas.drawRect(x, y, width, height, mPaint);
      }
    }
  }

  /**
   * 绘制纵向 item 分割线
   *
   * @param canvas 画笔
   * @param parent 父布局
   */
  private void drawVertical(Canvas canvas, RecyclerView parent) {
    final int top = parent.getPaddingTop();
    final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
    final int childSize = parent.getChildCount();
    for (int i = 0; i < childSize; i++) {
      final View child = parent.getChildAt(i);
      RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
      final int left = child.getRight() + layoutParams.rightMargin;
      final int right = left + mDividerHeight;
      if (mDrawable != null) {
        mDrawable.setBounds(left, top, right, bottom);
        mDrawable.draw(canvas);
      }
      if (mPaint != null) {
        canvas.drawRect(left, top, right, bottom, mPaint);
      }
    }
  }

  /**
   * 获取列数
   *
   * @param parent 父布局
   * @return 列数
   */
  private int getSpanCount(RecyclerView parent) {
    int spanCount = -1;
    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
      spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
    }
    return spanCount;
  }

  /**
   * 是不是最后一列
   *
   * @param parent 父布局
   * @param pos 位置
   * @param spanCount span数量
   * @param childCount 所有item
   * @return true  是   false 不是
   */
  private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      int orientation = ((GridLayoutManager) layoutManager).getOrientation();
      if (orientation == StaggeredGridLayoutManager.VERTICAL) {
        // 如果是最后一列，则不需要绘制右边
        if ((pos + 1) % spanCount == 0) {
          return true;
        }
      } else {
        childCount = childCount - childCount % spanCount;
        // 如果是最后一列，则不需要绘制右边
        if (pos >= childCount) {
          return true;
        }
      }
    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
      int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
      if (orientation == StaggeredGridLayoutManager.VERTICAL) {
        // 如果是最后一列，则不需要绘制右边
        if ((pos + 1) % spanCount == 0) {
          return true;
        }
      } else {
        childCount = childCount - childCount % spanCount;
        // 如果是最后一列，则不需要绘制右边
        if (pos >= childCount) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 是不是最后一行
   *
   * @param parent 父布局
   * @param pos 位置
   * @param spanCount span数量
   * @param childCount 所有item
   * @return true  是   false 不是
   */
  private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
    int orientation;
    RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
    if (layoutManager instanceof GridLayoutManager) {
      childCount = childCount - childCount % spanCount;
      orientation = ((GridLayoutManager) layoutManager).getOrientation();
      if (orientation == StaggeredGridLayoutManager.VERTICAL) {
        // 如果是最后一行，则不需要绘制底部
        childCount = childCount - childCount % spanCount;
        if (pos >= childCount) {
          return true;
        }
      } else {// StaggeredGridLayoutManager 横向滚动
        // 如果是最后一行，则不需要绘制底部
        if ((pos + 1) % spanCount == 0) {
          return true;
        }
      }
    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
      orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
      if (orientation == StaggeredGridLayoutManager.VERTICAL) {
        // 如果是最后一行，则不需要绘制底部
        childCount = childCount - childCount % spanCount;
        if (pos >= childCount) {
          return true;
        }
      } else {// StaggeredGridLayoutManager 横向滚动
        // 如果是最后一行，则不需要绘制底部
        if ((pos + 1) % spanCount == 0) {
          return true;
        }
      }
    }
    return false;
  }
}
