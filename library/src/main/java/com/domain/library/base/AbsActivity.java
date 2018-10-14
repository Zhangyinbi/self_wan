package com.domain.library.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.domain.library.R;
import com.domain.library.utils.bar.LightStatusBarCompat;
import com.domain.library.utils.bar.StatusBarCompat;
import com.domain.library.utils.ToastUtils;
import com.domain.library.utils.bar.TransStatusBarHelper;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Project Name : Invoice
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/9/20 17:07
 */
public abstract class AbsActivity extends AppCompatActivity implements BaseView {

    public BasePresenter<BaseView> p;
    public boolean mEnableStatusBarDarkText = true;
    public boolean mEnableFitWindowSystem = true;
    public CompositeDisposable compositeDisposable;
    private View mLoadingContainer;
    private ViewGroup mRootView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        statusBarSetting();
        compositeDisposable = new CompositeDisposable();
        mLoadingContainer = LayoutInflater.from(this).inflate(R.layout.invoice_loading_layout, null);
        mRootView = findViewById(android.R.id.content);
        newInstancePresenter();
        initView();
        initData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        translucentStatusBar();
        setLightStatusBar();
    }

    public void setLightStatusBar() {
        LightStatusBarCompat.setLightStatusBar(getWindow(), false);
    }

    private void statusBarSetting() {
        //关闭原先的设置
        mEnableFitWindowSystem = false;
        mEnableStatusBarDarkText = false;
        StatusBarCompat.setStatusBarColor(getWindow(), Color.TRANSPARENT);
    }

    /**
     * 沉浸式，必须在setcontentview之后调用，建议在onStart中调用
     */
    public void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= 19 && mEnableFitWindowSystem) {
            ViewGroup contentLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            if (contentLayout != null) {
                View view = contentLayout.getChildAt(0);
                if (view != null) {
                    view.setFitsSystemWindows(true);
                }
            }
        }
        if (mEnableStatusBarDarkText) {
            TransStatusBarHelper.StatusBarLightMode(this);
        }
    }

    protected abstract int getLayoutId();

    protected abstract void newInstancePresenter();

    protected abstract void initView();

    protected abstract void initData();


    @Override
    public void showToast(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void setPresenter(BasePresenter presenter) {
        p = presenter;
    }

    @Override
    public void hideProgress() {
        if (mRootView == null || mLoadingContainer == null || mRootView.indexOfChild(mLoadingContainer) == -1)
            return;
        mRootView.removeView(mLoadingContainer);
    }

    @Override
    public void showProgress() {
        if (mRootView == null || mLoadingContainer == null || mRootView.indexOfChild(mLoadingContainer) != -1)
            return;
        mRootView.addView(mLoadingContainer, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        FragmentManager fm = getSupportFragmentManager();

        for (int index = 0; index < fm.getFragments().size(); index++) {
            Fragment fragment = fm.getFragments().get(index); //找到第一层Fragment
            if (fragment != null)
                handleResult(fragment, requestCode, resultCode, data);
        }
    }

    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
        fragment.onActivityResult(requestCode, resultCode, data);
        List<Fragment> childFragment = fragment.getChildFragmentManager().getFragments();
        if (childFragment != null)
            for (Fragment f : childFragment)
                if (f != null) handleResult(f, requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        if (null != p) {
            p.destroy();
        }
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
            compositeDisposable = null;
        }
        super.onDestroy();

    }

    public boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    @Override
    protected void onStop() {
        hideInput();
        super.onStop();

    }

    public boolean hideInput() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            return inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }
}
