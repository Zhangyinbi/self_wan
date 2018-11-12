package com.domain.library.ui;

import android.content.Context;
import android.support.v7.app.AppCompatDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import com.domain.library.R;

/**
 * Project Name : Do-Feidian-widget-android
 * description:默认样式的弹框 title  取消 和 确定   文字可以自定义，点击时间自定义
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/10/15 17:23
 */
public class CommonDialog extends AppCompatDialog {

  private String          title;
  private String          cancelText;
  private String          sureText;
  private String          content;
  private SureInterface   sureImpl;
  private CancelInterface cancelImpl;
  private TextView        mTvSure;
  private TextView        mTvCancel;
  private View            mVSpace;
  private TextView        mTvContent;
  private TextView        mTvTitle;

  /**
   * builder构造函数
   *
   * @param context 上下文
   * @param builder builder
   */
  public CommonDialog(Context context, Builder builder) {
    super(context, R.style.Weight_Dialog);
    this.cancelText = builder.cancelText;
    this.sureText = builder.sureText;
    this.title = builder.title;
    this.sureImpl = builder.sureImpl;
    this.cancelImpl = builder.cancelImpl;
    this.content = builder.content;
    initView();
    setUpUI();
  }

  /**
   * 初始化布局
   */
  private void initView() {
    setContentView(R.layout.default_dialog_view);
    getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
    mTvTitle = findViewById(R.id.tv_title);
    mTvContent = findViewById(R.id.tv_content);
    mVSpace = findViewById(R.id.v_space);
    mTvCancel = findViewById(R.id.tv_cancel);
    mTvSure = findViewById(R.id.tv_sure);
    mTvContent.setText(content);
  }

  /**
   * 更新UI
   */
  private void setUpUI() {
    mTvSure.setText(TextUtils.isEmpty(sureText) ? "确定" : sureText);

    if (TextUtils.isEmpty(cancelText)) {
      mTvCancel.setVisibility(View.GONE);
      mVSpace.setVisibility(View.GONE);
    } else {
      mTvCancel.setText(cancelText);
      mTvCancel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          dismiss();
          if (cancelImpl != null) {
            cancelImpl.onCancelClick();
          }
        }
      });
    }
    if (TextUtils.isEmpty(title)) {
      mTvTitle.setVisibility(View.GONE);
    } else {
      mTvTitle.setText(title);
    }
    mTvSure.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
        if (sureImpl != null) {
          sureImpl.onSureClick();
        }
      }
    });
  }

  /**
   * builder构造模式
   */
  public static class Builder {
    private String          title;
    private String          cancelText;
    private String          sureText;
    private String          content;
    private SureInterface   sureImpl;
    private CancelInterface cancelImpl;
    private Context         context;

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public Builder(Context context) {
      this.context = context;
    }

    /**
     * 取消
     *
     * @param cancelImpl 取消实现
     * @return builder
     */
    public Builder setCancelImpl(CancelInterface cancelImpl) {
      this.cancelImpl = cancelImpl;
      return this;
    }

    /**
     * 确定
     *
     * @param sureImpl 确定
     * @return builder
     */
    public Builder setSureImpl(SureInterface sureImpl) {
      this.sureImpl = sureImpl;
      return this;
    }

    /**
     * 取消文字
     *
     * @param cancelText 取消文字
     * @return builder
     */
    public Builder setCancelText(String cancelText) {
      this.cancelText = cancelText;
      return this;
    }

    /**
     * 取消文字
     *
     * @param cancelText 取消文字
     * @param cancelImpl 取消实现
     * @return builder
     */
    public Builder setCancelText(String cancelText, CancelInterface cancelImpl) {
      this.cancelText = cancelText;
      this.cancelImpl = cancelImpl;
      return this;
    }

    /**
     * 去定文字
     *
     * @return builder
     */
    public Builder setSureText(String sureText) {
      this.sureText = sureText;
      return this;
    }

    /**
     * 去定文字
     *
     * @return builder
     */
    public Builder setSureText(String sureText, SureInterface sureImpl) {
      this.sureText = sureText;
      this.sureImpl = sureImpl;
      return this;
    }

    /**
     * 设置标题
     *
     * @return builder
     */
    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    /**
     * 设置标题
     *
     * @return builder
     */
    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    /**
     * 构造
     *
     * @return dialog
     */
    public CommonDialog build() {
      return new CommonDialog(context, this);
    }
  }
}


