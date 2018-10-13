package com.domain.library.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.domain.library.R;
import com.domain.library.utils.ToastUtils;

import io.reactivex.disposables.CompositeDisposable;


/**
 * Project Name : Invoice
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/9/20 17:54
 */
public abstract class AbsFragment extends Fragment implements BaseView<BasePresenter> {

    public BasePresenter p;
    private View mLoadingContainer;
    private ViewGroup mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLoadingContainer = LayoutInflater.from(getContext()).inflate(R.layout.invoice_loading_layout, null);
        if (null != getActivity()) {
            mRootView = getActivity().findViewById(android.R.id.content);
        }
        newInstancePresenter();
        initView(view);
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void newInstancePresenter();

    protected abstract void initView(View view);

    protected abstract void initData();

    public abstract void back();

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
    public void onDestroy() {
        if (null != p) {
            p.destroy();
        }
        super.onDestroy();
    }
}
