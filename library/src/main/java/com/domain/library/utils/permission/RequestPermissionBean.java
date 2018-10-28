package com.domain.library.utils.permission;

import android.app.Dialog;
import android.content.Context;
import com.domain.library.utils.MyPermissionUtils;

/**
 * Created by binbin.liu on 2018/6/14.
 */

public class RequestPermissionBean {

  private String[]                                      permissions;
  private int                                           requestCode;
  private MyPermissionUtils.OnRequestPermissionListener mOnRequestPermissionListener;
  private Dialog                                        hintDialog;
  private Context                                       context;

  public RequestPermissionBean(Context context, String[] permissions, int requestCode,
                               MyPermissionUtils.OnRequestPermissionListener listener, Dialog hintDialog) {
    this.context = context;
    this.permissions = permissions;
    this.requestCode = requestCode;
    this.mOnRequestPermissionListener = listener;
    this.hintDialog = hintDialog;
  }

  public String[] getPermissions() {
    return permissions;
  }

  public Context getContext() {
    return context;
  }

  public int getRequestCode() {
    return requestCode;
  }

  public MyPermissionUtils.OnRequestPermissionListener getOnRequestPermissionListener() {
    return mOnRequestPermissionListener;
  }

  public Dialog getHintDialog() {
    return hintDialog;
  }
}
