package com.domain.operationrobot.im.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.domain.operationrobot.im.listener.IHolderView;

public abstract class BaseHolderRV<T> extends RecyclerView.ViewHolder implements IHolderView {

    protected int     mPosition;
    protected int     mPositionOffSet;
    protected T       mDataBean;
    protected Context mContext;
    protected View    mItemView;

    public BaseHolderRV(View itemView) {
        super(itemView);
        mItemView = itemView;
        mContext = itemView.getContext();
    }

    public void setData(T t, int position, int offset) {
        this.mDataBean = t;
        mPosition = position;
        mPositionOffSet = offset;
        bindData();
    }

    /**
     * 填充数据
     */
    protected abstract void bindData();


    public void onCreate() {

    }

    public void onStart() {

    }


    public void onResume() {

    }


    public void onPause() {

    }


    public void onStop() {

    }


    public void onDestroy() {

    }

}
