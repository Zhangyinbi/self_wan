package com.domain.library.recycleview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import com.domain.library.recycleview.wrapper.WrapRecyclerAdapter;

/**
 * Project Name : UIKit
 * description:可扩展的recycleView
 * 可以添加头部和底部的RecyclerView
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 14:16
 */
public class WrapRecyclerView extends RecyclerView {
    private WrapRecyclerAdapter mWrapRecyclerAdapter;

    /**
     * 可扩展的
     *
     * @param context context
     */
    public WrapRecyclerView(@NonNull Context context) {
        super(context);
    }

    /**
     * 可扩展的
     *
     * @param context context
     * @param attrs   attrs
     */
    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 可扩展的
     *
     * @param context  context
     * @param attrs    attrs
     * @param defStyle defStyle
     */
    public WrapRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 获取adapter
     *
     * @return mWrapRecyclerAdapter
     */
    @Nullable
    @Override
    public Adapter getAdapter() {
        return mWrapRecyclerAdapter;
    }

    /**
     * 设置adapter
     *
     * @param adapter 真实adapter
     */
    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        mWrapRecyclerAdapter = new WrapRecyclerAdapter(adapter);
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                mWrapRecyclerAdapter.notifyDataSetChanged();
            }
        });
        super.setAdapter(mWrapRecyclerAdapter);
    }

    /**
     * 添加头部view
     * 必须是setAdapter之后才可以
     *
     * @param view 视图
     */
    public void addHeaderView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.addHeaderView(view);
        }
    }

    /**
     * 添加底部View
     * 必须是setAdapter之后才可以
     *
     * @param view 视图
     */
    public void addFooterView(View view) {
        if (null != mWrapRecyclerAdapter) {
            mWrapRecyclerAdapter.addFooterView(view);
        }
    }
}
