package com.domain.library.recycleview.creator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.domain.library.R;
import java.text.SimpleDateFormat;

import static com.domain.library.recycleview.RefreshRecyclerView.REFRESH_STATUS_LOOSEN_REFRESHING;
import static com.domain.library.recycleview.RefreshRecyclerView.REFRESH_STATUS_PULL_DOWN_REFRESH;

/**
 * Project Name : UIKit
 * description:默认样式的头部刷新 或者自己继承去实现
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/12/2 21:08
 */
public class DefaultRefreshCreator implements RefreshViewCreator {
    private static final String FIRST_TIME_TEXT = "更新时间：";
    private static final String NOT_FIRST_TIME_TEXT = "上次更新：";
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    // 加载数据的ImageView
    private ImageView mRefreshIv;
    private Context mContext;
    private TextView mTvRefreshText;
    private TextView mTvRefreshTime;
    private String mLastTime;

    private boolean isFirst;

    /**
     * 日期格式化
     *
     * @param time 时间格式化
     * @return 返回格式化的时间
     */
    public static String formatTimeMillis(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
        return sdf.format(time);
    }

    /**
     * 初始化复制
     *
     * @param context 上下文
     * @param parent  RecyclerView
     * @return view
     */
    @Override
    public View getRefreshView(Context context, ViewGroup parent) {
        View refreshView = LayoutInflater.from(context).inflate(R.layout.layout_refresh_header_view, parent, false);
        mRefreshIv = refreshView.findViewById(R.id.iv_refresh_icon);
        mTvRefreshText = refreshView.findViewById(R.id.tv_refresh_text);
        mTvRefreshTime = refreshView.findViewById(R.id.tv_refresh_time);
        mLastTime = formatTimeMillis(System.currentTimeMillis());
        mTvRefreshTime.setText(FIRST_TIME_TEXT + mLastTime);
        isFirst = true;
        mContext = context;
        return refreshView;
    }

    /**
     * 正在下拉pull
     *
     * @param currentDragHeight    当前拖动的高度
     * @param refreshViewHeight    总的刷新高度 即刷新view 的高度
     * @param currentRefreshStatus 当前状态
     */
    @Override
    public void onPull(int currentDragHeight, int refreshViewHeight, int currentRefreshStatus) {
        float rotate = ((float) currentDragHeight) / refreshViewHeight;
        if (currentRefreshStatus == REFRESH_STATUS_LOOSEN_REFRESHING) {
            if (isFirst) {
                mTvRefreshTime.setText(FIRST_TIME_TEXT + mLastTime);
            } else {
                mTvRefreshTime.setText(NOT_FIRST_TIME_TEXT + mLastTime);
            }
        }
        if (currentRefreshStatus == REFRESH_STATUS_PULL_DOWN_REFRESH) {
            mTvRefreshText.setText(R.string.uikit_com_refresh_status_pull_refreshing_hint_text);
        }
        if (rotate >= 0.0002) {
            mRefreshIv.setRotation(0);
            mRefreshIv.clearAnimation();
            mTvRefreshText.setText(R.string.uikit_com_refresh_status_loosen_refreshing_hint_text);
            return;
        }

        // 不断下拉的过程中不断的旋转图片
        mRefreshIv.setRotation(rotate * 180);
    }

    /**
     * 正在刷新中
     */
    @Override
    public void onRefreshing() {
        mTvRefreshText.setText(R.string.uikit_com_refresh_status_refreshing_hint_text);
        mRefreshIv.setImageResource(R.drawable.uikit_com_refreshing_icon);
        Animation operatingAnim = AnimationUtils.loadAnimation(mContext, R.anim.uikit_com_rotate_anim);
        LinearInterpolator lin = new LinearInterpolator();
        operatingAnim.setInterpolator(lin);
        mRefreshIv.startAnimation(operatingAnim);
    }

    /**
     * 停止刷新
     */
    @Override
    public void onComplete() {
        isFirst = false;
        mRefreshIv.postDelayed(new Runnable() {
            @Override
            public void run() {
                reset();
            }
        }, 500);
    }

    /**
     * 初始化刷新view设置
     */
    private void reset() {
        mLastTime = formatTimeMillis(System.currentTimeMillis());
        mTvRefreshTime.setText(NOT_FIRST_TIME_TEXT + mLastTime);
        mTvRefreshText.setText(R.string.uikit_com_refresh_status_loosen_refreshing_hint_text);
        mRefreshIv.setImageResource(R.drawable.uikit_com_pre_refresh_icon);
        // 停止加载的时候清除动画
        mRefreshIv.clearAnimation();
    }
}
