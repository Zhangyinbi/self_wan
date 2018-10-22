package com.domain.operationrobot.app.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.domain.library.base.AbsActivity;
import com.domain.operationrobot.R;
import com.domain.operationrobot.im.chatroom.MainChatRoom;
import com.domain.operationrobot.listener.ThrottleLastClickListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class MainActivity extends AbsActivity {
  private static final String TAG = "------------";
  private Fragment mCurrentFragment;
  private ArrayList<Fragment> mFragments     = new ArrayList<>();
  private boolean[]           mFragmentAdded = new boolean[] { false, false };
  private TextView mTv_fragment1;
  private TextView mTv_fragment2;
  ThrottleLastClickListener listener = new ThrottleLastClickListener() {
    @Override
    public void onViewClick(View v) {
      switch (v.getId()) {
        case R.id.tv_fragment1:
          seclectFragment();
          break;
      }
    }
  };

  private void seclectFragment() {
    if (mTv_fragment1.getText()
                     .equals("服务器监控")) {
      mTv_fragment1.setText("首页");
      mTv_fragment2.setText("服务器监控");
      showContent(1);
    } else {
      mTv_fragment2.setText("首页");
      mTv_fragment1.setText("服务器监控");
      showContent(0);
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void newInstancePresenter() {
    MainChatRoom.init();
  }

  @Override
  protected void initView() {
    initFragments();
    mTv_fragment1 = findViewById(R.id.tv_fragment1);
    mTv_fragment2 = findViewById(R.id.tv_fragment2);
    mTv_fragment1.setOnClickListener(listener);
    mTv_fragment2.setOnClickListener(listener);

    showContent(0);
  }

  private void showContent(int position) {
    try {
      if (position >= mFragments.size()) {
        return;
      }

      Fragment targetFragment = mFragments.get(position);
      if (targetFragment == mCurrentFragment) {
        return;
      }

      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction transaction = fm.beginTransaction();
      if (mCurrentFragment != null) {
        transaction.hide(mCurrentFragment);
      }

      if (!targetFragment.isAdded() && !mFragmentAdded[position]) {
        transaction.add(R.id.fl_content, targetFragment)
                   .show(targetFragment);
        mFragmentAdded[position] = true;
      } else {
        transaction.show(targetFragment);
      }
      transaction.commitNowAllowingStateLoss();
      mCurrentFragment = targetFragment;
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void initFragments() {
    mFragments.add(ChatRoomFragment.newInstance());
    mFragments.add(ServerMonitorFragment.newInstance());
  }

  @Override
  protected void initData() {
  }

  @Override
  public void showEmptyView() {

  }
}
