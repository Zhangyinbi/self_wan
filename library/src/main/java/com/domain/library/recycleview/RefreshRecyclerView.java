package com.domain.library.recycleview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.domain.library.recycleview.creator.LoadViewCreator;
import com.domain.library.recycleview.creator.RefreshViewCreator;

/**
 * Project Name : UIKit
 * description:下拉刷新上啦加载的RecycleView
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 21:05
 */
public class RefreshRecyclerView extends WrapRecyclerView {
  // 默认状态
  public static int REFRESH_STATUS_NORMAL            = 0x0011;
  // 下拉刷新状态 上拉加载更多状态
  public static int REFRESH_STATUS_PULL_DOWN_REFRESH = 0x0022;
  // 松开刷新状态 加载更多状态
  public static int REFRESH_STATUS_LOOSEN_REFRESHING = 0x0033;
  // 正在刷新状态 正在加载更多状态
  public static int REFRESH_STATUS_REFRESHING        = 0x0044;
  // 下拉刷新的辅助类
  private RefreshViewCreator mRefreshCreator;
  private LoadViewCreator    mLoadCreator;
  // 下拉刷新头部的高度
  private int mRefreshViewHeight  = 0;
  // 下拉刷新头部的高度
  private int mLoadMoreViewHeight = 0;
  // 下拉刷新的头部View
  private View mRefreshView;
  // 下拉刷新的头部View
  private View mLoadMoreView;
  // 手指按下的Y位置
  private int  mFingerDownY;
  // 手指拖拽的阻力指数
  private float   mDragIndex   = 0.35f;
  // 当前是否正在拖动
  private boolean mCurrentDrag = false;
  // 当前的状态
  private int                mCurrentRefreshStatus;
  private int                mCurrentLoadStatus;
  // 处理刷新回调监听
  private OnRefreshListener  mRefreshListener;
  private OnLoadMoreListener mLoadMoreListener;

  /**
   * 构造
   *
   * @param context context
   */
  public RefreshRecyclerView(Context context) {
    super(context);
  }

  /**
   * 构造
   *
   * @param context context
   * @param attrs attrs
   */
  public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  /**
   * 构造
   *
   * @param context context
   * @param attrs attrs
   * @param defStyle defStyle
   */
  public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
  }

  /**
   * 先处理下拉刷新，同时考虑刷新列表的不同风格样式，确保通用
   * 所以我们不能直接添加View，需要利用辅助类
   *
   * @param refreshCreator refreshCreator
   */
  public void addRefreshViewCreator(RefreshViewCreator refreshCreator) {
    this.mRefreshCreator = refreshCreator;
    addRefreshView();
  }

  /**
   * 先处理下拉刷新，同时考虑刷新列表的不同风格样式，确保通用
   * 所以我们不能直接添加View，需要利用辅助类
   *
   * @param loadViewCreator loadViewCreator
   */
  public void addLoadViewCreator(LoadViewCreator loadViewCreator) {
    this.mLoadCreator = loadViewCreator;
    addLoadView();
  }

  /**
   * 设置adapter
   *
   * @param adapter 真实的adapter
   */
  @Override
  public void setAdapter(RecyclerView.Adapter adapter) {
    super.setAdapter(adapter);
    addRefreshView();
    addLoadView();
  }

  /**
   * 处理按下事件
   *
   * @param ev 事件
   * @return 是否处理分发
   */
  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    switch (ev.getAction()) {
      case MotionEvent.ACTION_DOWN:
        // 记录手指按下的位置 ,之所以写在dispatchTouchEvent那是因为如果我们处理了条目点击事件，
        // 那么就不会进入onTouchEvent里面，所以只能在这里获取
        mFingerDownY = (int) ev.getRawY();
        break;

      case MotionEvent.ACTION_UP:
        if (mCurrentDrag) {
          restoreRefreshView();
          restoreLoadMoreView();
        }
        break;
      default:
        break;
    }
    return super.dispatchTouchEvent(ev);
  }

  /**
   * 重置当前刷新状态状态
   */
  private void restoreRefreshView() {
    if (mRefreshView == null) {
      return;
    }
    int currentTopMargin = ((ViewGroup.MarginLayoutParams) mRefreshView.getLayoutParams()).topMargin;
    int finalTopMargin = -mRefreshViewHeight + 1;
    if (mCurrentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
      finalTopMargin = 0;
      mCurrentRefreshStatus = REFRESH_STATUS_REFRESHING;
      if (mRefreshCreator != null) {
        mRefreshCreator.onRefreshing();
      }
      if (mRefreshListener != null) {
        mRefreshListener.onRefresh();
      }
    }

    int distance = currentTopMargin - finalTopMargin;

    // 回弹到指定位置
    ValueAnimator animator = ObjectAnimator.ofFloat(currentTopMargin, finalTopMargin)
                                           .setDuration(distance);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float currentTopMargin = (float) animation.getAnimatedValue();
        setRefreshViewMarginTop((int) currentTopMargin);
      }
    });
    animator.start();
    mCurrentDrag = false;
  }

  /**
   * 重置当前刷新状态状态
   */
  private void restoreLoadMoreView() {
    if (mLoadMoreView == null) {
      return;
    }
    int currentBottomMargin = ((ViewGroup.MarginLayoutParams) mLoadMoreView.getLayoutParams()).bottomMargin;
    int finalBottomMargin = -mLoadMoreViewHeight + 1;
    if (mCurrentLoadStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
      finalBottomMargin = 0;
      mCurrentLoadStatus = REFRESH_STATUS_REFRESHING;
      if (mLoadCreator != null) {
        mLoadCreator.onLoading();
      }
      if (mLoadMoreListener != null) {
        mLoadMoreListener.onLoadMore();
      }
    }

    int distance = currentBottomMargin - finalBottomMargin;

    // 回弹到指定位置
    ValueAnimator animator = ObjectAnimator.ofFloat(currentBottomMargin, finalBottomMargin)
                                           .setDuration(distance);
    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
      @Override
      public void onAnimationUpdate(ValueAnimator animation) {
        float currentBottomMargin = (float) animation.getAnimatedValue();
        setLoadMoreViewMarginBottom((int) currentBottomMargin);
      }
    });
    animator.start();
    mCurrentDrag = false;
  }

  /**
   * 处理移动手指
   *
   * @param e 移动事件
   * @return 是否处理
   */
  @Override
  public boolean onTouchEvent(MotionEvent e) {
    switch (e.getAction()) {
      case MotionEvent.ACTION_MOVE:

        // 如果是在最顶部才处理，否则不需要处理
        if ((canScroll() || mCurrentRefreshStatus == REFRESH_STATUS_REFRESHING)) {
          if (!canScrollVertically(1)) {//滑动到了最低不
            if (mCurrentLoadStatus == REFRESH_STATUS_REFRESHING) {
              return super.onTouchEvent(e);
            }
            int distanceY = (int) ((mFingerDownY - (e.getRawY())) * mDragIndex);
            if (distanceY > 0) {
              int marginBottom = distanceY - mLoadMoreViewHeight;
              setLoadMoreViewMarginBottom(marginBottom);
              updateLoadMoreStatus(marginBottom);
              mCurrentDrag = true;
              return false;
            }
          }
          // 如果没有到达最顶端，也就是说还可以向上滚动就什么都不处理
          return super.onTouchEvent(e);
        }

        // 解决下拉刷新自动滚动问题
        if (mCurrentDrag) {
          scrollToPosition(0);
        }

        // 获取手指触摸拖拽的距离
        int distanceY = (int) ((e.getRawY() - mFingerDownY) * mDragIndex);
        // 如果是已经到达头部，并且不断的向下拉，那么不断的改变refreshView的marginTop的值
        if (distanceY > 0) {
          int marginTop = distanceY - mRefreshViewHeight;
          setRefreshViewMarginTop(marginTop);
          updateRefreshStatus(marginTop);
          mCurrentDrag = true;
          return false;
        }
        break;
      default:
        break;
    }

    return super.onTouchEvent(e);
  }

  /**
   * 是否滑动到底部或者顶部
   * canScrollVertically(1);返回false表示不能往上滑动，即代表到底部了；
   * canScrollVertically(-1);返回false表示不能往下滑动，即代表到顶部了；
   * @param direction direction
   * @return 是否滑动到底部或者顶部
   */
  @Override
  public boolean canScrollVertically(int direction) {
    final int offset = computeVerticalScrollOffset();
    final int range = computeVerticalScrollRange() - computeVerticalScrollExtent();
    if (range == 0) {
      return false;
    }
    if (direction < 0) {
      return offset > 0;
    } else {
      return offset < range - 1;
    }
  }

  /**
   * 更新刷新的状态
   *
   * @param marginTop 上边距
   */
  private void updateRefreshStatus(int marginTop) {
    if (marginTop <= -mRefreshViewHeight) {
      mCurrentRefreshStatus = REFRESH_STATUS_NORMAL;
    } else if (marginTop < 0) {
      mCurrentRefreshStatus = REFRESH_STATUS_PULL_DOWN_REFRESH;
    } else {
      mCurrentRefreshStatus = REFRESH_STATUS_LOOSEN_REFRESHING;
    }

    if (mRefreshCreator != null) {
      mRefreshCreator.onPull(marginTop, mRefreshViewHeight, mCurrentRefreshStatus);
    }
  }

  /**
   * 更新加载更多
   *
   * @param marginBottom 下边距
   */
  private void updateLoadMoreStatus(int marginBottom) {
    if (marginBottom <= -mLoadMoreViewHeight) {
      mCurrentLoadStatus = REFRESH_STATUS_NORMAL;
    } else if (marginBottom < 0) {
      mCurrentLoadStatus = REFRESH_STATUS_PULL_DOWN_REFRESH;
    } else {
      mCurrentLoadStatus = REFRESH_STATUS_LOOSEN_REFRESHING;
    }

    if (mLoadCreator != null) {
      mLoadCreator.onPull(marginBottom, mLoadMoreViewHeight, mCurrentLoadStatus);
    }
  }

  /**
   * 添加头部的刷新View
   */
  private void addRefreshView() {
    RecyclerView.Adapter adapter = getAdapter();
    if (adapter != null && mRefreshCreator != null) {
      // 添加头部的刷新View
      View refreshView = mRefreshCreator.getRefreshView(getContext(), this);
      if (refreshView != null) {
        addHeaderView(refreshView);
        this.mRefreshView = refreshView;
      }
    }
  }

  /**
   * 添加底部的刷新View
   */
  private void addLoadView() {
    RecyclerView.Adapter adapter = getAdapter();
    if (adapter != null && mLoadCreator != null) {
      // 添加底部的刷新View
      View loadView = mLoadCreator.getLoadView(getContext(), this);
      if (loadView != null) {
        addFooterView(loadView);
        this.mLoadMoreView = loadView;
      }
    }
  }

  /**
   * 测量
   *
   * @param changed 是否可以改变
   * @param l l
   * @param t t
   * @param r r
   * @param b b
   */
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  @Override
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
    if (changed) {
      if (mRefreshView != null && mRefreshViewHeight <= 0) {
        // 获取头部刷新View的高度
        mRefreshViewHeight = mRefreshView.getMeasuredHeight();
        mRefreshView.setTranslationZ(-1);
        if (mRefreshViewHeight > 0) {
          // 隐藏头部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
          setRefreshViewMarginTop(-mRefreshViewHeight + 1);
        }
      }
      if (mLoadMoreView != null && mLoadMoreViewHeight <= 0) {
        // 获取头部刷新View的高度
        mLoadMoreViewHeight = mLoadMoreView.getMeasuredHeight();
        mLoadMoreView.setTranslationZ(-1);
        if (mLoadMoreViewHeight > 0) {
          // 隐藏头部刷新的View  marginTop  多留出1px防止无法判断是不是滚动到头部问题
          setLoadMoreViewMarginBottom(-mLoadMoreViewHeight + 1);
        }
      }
    }
  }

  /**
   * 设置加载更多View的marginBottom
   * @param marginBottom  marginBottom
   */
  private void setLoadMoreViewMarginBottom(int marginBottom) {
    if (mLoadMoreView == null) {
      return;
    }

    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLoadMoreView.getLayoutParams();
    if (marginBottom < -mLoadMoreViewHeight + 1) {
      marginBottom = -mLoadMoreViewHeight + 1;
    }
    params.bottomMargin = marginBottom;
    mLoadMoreView.setLayoutParams(params);
  }

  /**
   * 设置刷新View的marginTop
   * @param marginTop  marginTop
   */
  public void setRefreshViewMarginTop(int marginTop) {
    if (mRefreshView == null) {
      return;
    }
    ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mRefreshView.getLayoutParams();
    if (marginTop < -mRefreshViewHeight + 1) {
      marginTop = -mRefreshViewHeight + 1;
    }
    params.topMargin = marginTop;
    mRefreshView.setLayoutParams(params);
  }

  /**
   * @return Whether it is possible for the child view of this layout to
   * scroll up. Override this if the child view is a custom view.
   * 判断是不是滚动到了最顶部，这个是从SwipeRefreshLayout里面copy过来的源代码
   */
  public boolean canScroll() {
    if (Build.VERSION.SDK_INT < 14) {
      return ViewCompat.canScrollVertically(this, -1) || this.getScrollY() > 0;
    } else {
      return ViewCompat.canScrollVertically(this, -1);
    }
  }

  /**
   * 停止刷新
   */
  public void onComplete() {
    mCurrentRefreshStatus = REFRESH_STATUS_NORMAL;
    restoreRefreshView();
    if (mRefreshCreator != null) {
      mRefreshCreator.onComplete();
    }

    restoreLoadMoreView();
    mCurrentLoadStatus = REFRESH_STATUS_NORMAL;
    if (mLoadCreator != null) {
      mLoadCreator.onComplete();
    }
  }

  /**
   * 设置刷新
   *
   * @param listener listener
   */
  public void setOnRefreshListener(OnRefreshListener listener) {
    this.mRefreshListener = listener;
  }

  /**
   * 设置加载更多
   *
   * @param mLoadMoreListener 设置加载更多
   */
  public void setmLoadMoreListener(OnLoadMoreListener mLoadMoreListener) {
    this.mLoadMoreListener = mLoadMoreListener;
  }

  /**
   * 刷新
   */
  public interface OnRefreshListener {
    /**
     * 刷新
     */
    void onRefresh();
  }

  /**
   * 加载
   */
  public interface OnLoadMoreListener {
    /**
     * 加载
     */
    void onLoadMore();
  }
}
