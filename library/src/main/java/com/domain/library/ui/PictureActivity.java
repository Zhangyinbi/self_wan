package com.domain.library.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.domain.library.GlideApp;
import com.domain.library.R;
import com.domain.library.base.AbsActivity;
import com.domain.library.ui.view.PointerFixViewPager;
import com.domain.library.utils.DisplaysUtil;
import com.domain.library.utils.GalleryUtil;
import com.domain.library.utils.ImageDownloadUtils;
import java.util.ArrayList;
import java.util.List;

public class PictureActivity extends AbsActivity implements PictureOperateListener {

  private static final int          REQUEST_CODE_PERMISSION_STORAGE = 100;
  private static       List<String> LIST_PERMISSION_STORAGE         = new ArrayList<>();

  static {
    LIST_PERMISSION_STORAGE.add(Manifest.permission.READ_EXTERNAL_STORAGE);
    LIST_PERMISSION_STORAGE.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
  }

  private PointerFixViewPager mViewPager;
  private ViewerAdapter       mAdapter;
  private ArrayList<String> mPaths = new ArrayList<>();
  private int mCurrentPos;
  private boolean mJustPreview = false;
  private View     mBottomView;
  private TextView mIndexTextView;
  private TextView mSaveTextView;

  @Override
  protected int getLayoutId() {
    return R.layout.layout_picture_view;
  }

  @Override
  protected void newInstancePresenter() {

  }

  @Override
  protected void initView() {

    mBottomView = findViewById(R.id.bottom_view);
    mViewPager = findViewById(R.id.pager);
    mIndexTextView = findViewById(R.id.tv_image_index);
    mSaveTextView = findViewById(R.id.iv_save);

    Intent intent = getIntent();
    if (intent == null) {
      finish();
    }

    ArrayList<String> paths = intent.getStringArrayListExtra(GalleryUtil.GALLERY_SELECTED_PATH);
    if (paths == null || paths.isEmpty()) {
      finish();
    }

    mJustPreview = intent.getBooleanExtra(GalleryUtil.GALLERY_JUST_PREVIEW, false);
    if (mJustPreview) {
      mBottomView.setVisibility(View.GONE);
    } else {
      mBottomView.setVisibility(View.VISIBLE);
    }
    mPaths.addAll(paths);

    mAdapter = new ViewerAdapter(getSupportFragmentManager(), mPaths);
    int position = intent.getIntExtra(GalleryUtil.GALLERY_CURRENT_POSITION, 0);
    mCurrentPos = position >= mPaths.size() ? 0 : position;
    mViewPager.setAdapter(mAdapter);
    mViewPager.setCurrentItem(mCurrentPos);
    mViewPager.setOffscreenPageLimit(4);

    mIndexTextView.setText((mCurrentPos + 1) + "/" + mPaths.size());
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {
        mCurrentPos = position;
        mIndexTextView.setText((mCurrentPos + 1) + "/" + mPaths.size());
      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });

    mSaveTextView.setOnClickListener(view -> {
      saveCurrentPicture();
    });
  }

  private void saveCurrentPicture() {
    downloadHelper();
  }

  private void downloadHelper() {
    try {
      String path = mPaths.get(mCurrentPos);
      ImageDownloadUtils.downLoadImage(getApplicationContext(), path);
    } catch (Exception e) {

    }
  }

  @Override
  protected void initData() {

  }

  @Override
  public void showEmptyView() {

  }

  @Override
  public void onPictureClick() {
    finish();
  }

  public static class ViewerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<String> mPaths = new ArrayList<>();

    public ViewerAdapter(FragmentManager fm, ArrayList<String> paths) {
      super(fm);
      if (paths != null && !paths.isEmpty()) {
        mPaths.addAll(paths);
      }
    }

    public void setData(ArrayList<String> paths) {
      mPaths.clear();
      mPaths.addAll(paths);
    }

    @Override
    public Fragment getItem(int position) {
      return PictureFragment.newInstance(mPaths.get(position));
    }

    @Override
    public int getCount() {
      return (mPaths != null ? mPaths.size() : 0);
    }

    @Override
    public int getItemPosition(Object object) {
      return POSITION_NONE;
    }
  }

  public static class PictureFragment extends Fragment {
    private static final String PICTURE_PATH = "PICTURE_PATH";
    private              int    screenHeight = 1920;

    private String mPath;

    static PictureFragment newInstance(String path) {
      PictureFragment f = new PictureFragment();
      Bundle args = new Bundle();
      args.putString(PICTURE_PATH, path);
      f.setArguments(args);
      return f;
    }

    public String getPath() {
      return mPath;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      mPath = getArguments() != null ? getArguments().getString(PICTURE_PATH, "") : "";
      screenHeight = DisplaysUtil.getScreenHeight(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.layout_one_pager, container, false);
      ImageView imageView = view.findViewById(R.id.image_page);
      ImageView imageView2 = view.findViewById(R.id.image_page_2);
      ScrollView scrollView = view.findViewById(R.id.scroll_page);
      ProgressBar progressBar = view.findViewById(R.id.progress_bar);

      if (!TextUtils.isEmpty(mPath)) {
        if (mPath.toLowerCase()
                 .endsWith(".gif")) {
          //Glide.with(getActivity()).load(mPath).asGif().error(R.drawable.gallery_network_mistake)
          //     .listener(getRequestListener(progressBar)).fitCenter().into(imageView);
        } else {
          GlideApp.with(getActivity())
                  .load(mPath)
                  .error(R.drawable.gallery_network_mistake)
                  .listener(getRequestListener(progressBar))
                  .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                      final int width = resource.getIntrinsicWidth();
                      final int height = resource.getIntrinsicHeight();
                      progressBar.setVisibility(View.GONE);
                      if (height > (screenHeight * 1.2f) && height > width * 2) { // 长图
                        scrollView.setVisibility(View.VISIBLE);
                        imageView2.setImageDrawable(resource);
                        imageView.setVisibility(View.GONE);

                        imageView2.setOnClickListener(v -> {
                          Activity activity = getActivity();
                          if (activity != null && activity instanceof PictureOperateListener) {
                            ((PictureOperateListener) activity).onPictureClick();
                          }
                        });
                      } else {
                        scrollView.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setImageDrawable(resource);

                        imageView.setOnClickListener(v -> {
                          Activity activity = getActivity();
                          if (activity != null && activity instanceof PictureOperateListener) {
                            ((PictureOperateListener) activity).onPictureClick();
                          }
                        });
                      }
                    }
                  });
        }
      }
      return view;
    }

    private <T> RequestListener<T> getRequestListener(ProgressBar bar) {
      return new RequestListener<T>() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<T> target, boolean isFirstResource) {
          bar.setVisibility(View.GONE);
          return false;
        }

        @Override
        public boolean onResourceReady(T resource, Object model, Target<T> target, DataSource dataSource, boolean isFirstResource) {
          bar.setVisibility(View.GONE);
          return false;
        }
      };
    }
  }
}
