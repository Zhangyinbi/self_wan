package com.domain.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.domain.library.ui.PictureActivity;
import java.util.ArrayList;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/3 15:07
 */
public class GalleryUtil {
  public static final String GALLERY_SELECTED_PATH    = "GALLERY_SELECTED_PATH";
  public static final String GALLERY_ALREADY_PATH     = "GALLERY_ALREADY_PATH";
  public static final String GALLERY_CURRENT_POSITION = "GALLEY_CURRENT_POSITION";
  public static final String GALLERY_JUST_PREVIEW     = "GALLEY_JUST_PREVIEW";
  public static final String GALLERY_DELETE_SUPPORT   = "GALLERY_DELETE_SUPPORT";
  private Context mContext;

  public static GalleryUtil with(@NonNull Context activity) {
    GalleryUtil galleryFinal = new GalleryUtil();
    galleryFinal.mContext = activity;
    return galleryFinal;
  }

  public void launchPicturePreview(ArrayList<String> paths, int position) {
    if (mContext == null || paths == null || paths.isEmpty()) {
      return;
    }

    int pos = (position >= paths.size()) ? 0 : position;
    Intent intent = new Intent(mContext, PictureActivity.class);
    intent.putStringArrayListExtra(GALLERY_SELECTED_PATH, paths);
    intent.putExtra(GALLERY_CURRENT_POSITION, pos);
    intent.putExtra(GALLERY_JUST_PREVIEW, false);
    intent.putExtra(GALLERY_DELETE_SUPPORT, false);
    mContext.startActivity(intent);
  }
}
