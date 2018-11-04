package com.domain.operationrobot.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.domain.library.utils.ActivityStackManager;
import com.domain.operationrobot.R;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/3 22:00
 */
public class TitleBar extends LinearLayout {

  private ImageView mIvBack;
  private String    title;
  private TextView  mTvTitle;

  public TitleBar(Context context) {
    this(context, null);
  }

  public TitleBar(Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public TitleBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(this.getContext())
                  .inflate(R.layout.title_bar_view, this);
    initAttrs(context, attrs);
    initView();
  }

  private void initAttrs(Context context, AttributeSet attrs) {
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.robot_title_bar);
    title = typedArray.getString(R.styleable.robot_title_bar_title);
    typedArray.recycle();
  }

  private void initView() {

    mIvBack = findViewById(R.id.iv_back);
    mTvTitle = findViewById(R.id.tv_title);
    mIvBack.setOnClickListener(view -> ActivityStackManager.getInstance()
                                                           .killTopActivity());
    mTvTitle.setText(title);
  }
}
