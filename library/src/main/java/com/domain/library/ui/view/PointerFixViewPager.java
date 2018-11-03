package com.domain.library.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class PointerFixViewPager extends ViewPager {

  public PointerFixViewPager(Context context) {
    super(context);
  }

  public PointerFixViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    try {
      return super.onTouchEvent(ev);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    return false;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    try {
      return super.onInterceptTouchEvent(ev);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    return false;
  }
}