package com.domain.library.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import com.domain.library.base.AbsActivity;
import com.domain.library.utils.permission.RequestPermissionBean;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 *
 * private MyPermissionUtils.OnReponsePermissionListener mOnPermissionListener;
 *
 * public void setPermissionListener(MyPermissionUtils.OnReponsePermissionListener onPermissionListener) {
 * mOnPermissionListener = onPermissionListener;
 * }
 *
 * @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
 * grantResults) {
 * super.onRequestPermissionsResult(requestCode, permissions, grantResults);
 * if (null != mOnPermissionListener && requestCode == mOnPermissionListener.getRequestCode()) {
 * mOnPermissionListener.onResponse(permissions, grantResults);
 * }
 * }
 *
 *
 * 使用样例 copy
 * MyPermissionUtils.getInstance().requestPermissions(BoxActivity.this, 1121, MyPermissionUtils.STORAGE_PERMISSION, new
 * MyPermissionUtils.OnRequestPermissionListener() {
 * @Override public void onRequest(boolean isGranted,String[] permissions) {
 * if(isGranted){
 * fileUploadDialog.show();
 * }
 * }
 * },CmnDialogManager.getPermissionHintDialog(BoxActivity.this,MyPermissionUtils.STORAGE_PERMISSION));
 */

public final class MyPermissionUtils {

  public static final int REQUEST_CODE_1001 = 1001;
  public static final int REQUEST_CODE_1002 = 1002;

  private static Map<String, String> dangerousPermissionDes;
  public static String[] STORAGE_PERMISSION = new String[] {
    Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.WRITE_EXTERNAL_STORAGE};
  public static String[] CAMERA_PERMISSION  = new String[] { Manifest.permission.CAMERA};
  private LinkedList<RequestPermissionBean> requestList;
  private boolean isRequesting = false;
  private static MyPermissionUtils mMyPermissionUtils;

  public static synchronized MyPermissionUtils getInstance() {
    if (null == mMyPermissionUtils) {
      mMyPermissionUtils = new MyPermissionUtils();
    }
    return mMyPermissionUtils;
  }

  private MyPermissionUtils() {}

  ;

  /**
   * 每个请求封加入队列
   */
  public void requestPermissions(final Context context, int requestCode, String[] permissions,
                                 final OnRequestPermissionListener listener, final Dialog neverAskHintDialog) {
    if (null == requestList) {
      requestList = new LinkedList<>();
    }
    requestList.add(new RequestPermissionBean(context, permissions, requestCode, listener, neverAskHintDialog));
    flushRequest();
  }

  /**
   * 刷新权限请求队列
   */
  private void flushRequest() {
    if (!isRequesting && requestList.size() > 0) {
      isRequesting = true;
      requestPermissions(requestList.pop());
    }
  }

  /**
   * 请求权限
   */
  @TargetApi(Build.VERSION_CODES.M)
  private void requestPermissions(RequestPermissionBean requestPermissionBean) {
    final Context context = requestPermissionBean.getContext();

    if (null == context) {
      return;
    }

    int requestCode = requestPermissionBean.getRequestCode();
    final Dialog neverAskHintDialog = requestPermissionBean.getHintDialog();
    String[] permissions = requestPermissionBean.getPermissions();
    final OnRequestPermissionListener listener = requestPermissionBean.getOnRequestPermissionListener();

    if (context instanceof Activity) {
      if (null != listener) {
        ((AbsActivity) context).setPermissionResponseListener(new OnResponsePermissionListener(requestCode) {
          @Override
          public void onResponse(String[] permissions, int[] grantResults) {
            String[] deniedPermissions = getDeniedPermissions(context, permissions);
            if (deniedPermissions.length > 0) {
              boolean rationale = shouldShowRequestPermissionRationale(context, permissions);
              if (rationale) {
                //被拒绝以后
                listener.onRequest(false, deniedPermissions);
              } else {
                //被彻底拒绝以后
                if (null != neverAskHintDialog) {
                  neverAskHintDialog.show();
                }
                listener.onRequest(false, deniedPermissions);
              }
            } else {
              listener.onRequest(true, new String[] {});
            }
            isRequesting = false;
            flushRequest();
          }
        });
      } else {
        ((AbsActivity) context).setPermissionResponseListener(new OnResponsePermissionListener(requestCode) {
          @Override
          public void onResponse(String[] permissions, int[] grantResults) {
            isRequesting = false;
            flushRequest();
          }
        });
      }

      String[] deniedPermissions = getDeniedPermissions(context, permissions);
      if (deniedPermissions.length > 0) {
        ((Activity) context).requestPermissions(deniedPermissions, requestCode);
      } else {
        if (null != listener) {
          //请求成功
          listener.onRequest(true, new String[] {});
          isRequesting = false;
          flushRequest();
        }
      }
    } else {
      throw new RuntimeException("Context must be an Activity");
    }
  }

  /**
   * 获取请求权限中需要授权的权限
   */
  public static String[] getDeniedPermissions(final Context context, final String[] permissions) {
    List<String> deniedPermissions = new ArrayList<>();
    for (String permission : permissions) {
      if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
        deniedPermissions.add(permission);
      }
    }
    return deniedPermissions.toArray(new String[deniedPermissions.size()]);
  }

  /**
   * 是否有权限需要说明提示
   */
  public static boolean shouldShowRequestPermissionRationale(final Context context, final String... deniedPermissions) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return false;
    }
    boolean rationale;
    for (String permission : deniedPermissions) {
      rationale = ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, permission);
      if (rationale) {
        // 权限被主动关闭  或 询问过没有权限
        return true;
      }
    }
    //没有询问过 或 非 询问过有权限 或 询问过被彻底拒绝
    return false;
  }

  /**
   * 请求权限者的回调
   */
  public static abstract class OnRequestPermissionListener {
    public OnRequestPermissionListener() {
    }

    /**
     * permissions  被拒绝的权限
     */
    public abstract void onRequest(boolean isGranted, String[] permissions);
  }

  /**
   * 获取申请权限结果的回调
   */

  public static abstract class OnResponsePermissionListener {
    private int requestCode;

    public OnResponsePermissionListener(int requestCode) {
      this.requestCode = requestCode;
    }

    public int getRequestCode() {
      return requestCode;
    }

    public abstract void onResponse(String[] permissions, int[] grantResults);
  }

  public static String[] getPermissionsFromTags(String[] tags) {
    Set<String> set = new HashSet<String>();
    for (int i = 0; i < tags.length; i++) {
      String[] permission = MyPermissionUtils.getPermissionFromTag(tags[i]);
      Collections.addAll(set, permission);
    }
    return set.toArray(new String[set.size()]);
  }

  public static String[] getTagsFromPermissions(String[] permissions) {
    Set<String> set = new HashSet<String>();
    for (int i = 0; i < permissions.length; i++) {
      String tag = MyPermissionUtils.getTagFromPermission(permissions[i]);
      set.add(tag);
    }

    return set.toArray(new String[set.size()]);
  }

  public static String[] getPermissionFromTag(String tag) {
    switch (tag) {
      case "LOCATION":
        return new String[] { Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
      case "PHONE":
        return new String[] { Manifest.permission.CALL_PHONE};
      case "STORAGE":
        return new String[] { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
      case "CAMERA":
        return new String[] { Manifest.permission.CAMERA,};
      default:
        return new String[] {};
    }
  }

  public static String getTagFromPermission(String tag) {
    switch (tag) {
      case Manifest.permission.ACCESS_FINE_LOCATION:
      case Manifest.permission.ACCESS_COARSE_LOCATION:
        return "LOCATION";
      case Manifest.permission.CALL_PHONE:
        return "PHONE";
      case Manifest.permission.CAMERA:
        return "CAMERA";
      case Manifest.permission.READ_EXTERNAL_STORAGE:
      case Manifest.permission.WRITE_EXTERNAL_STORAGE:
        return "STORAGE";
      default:
        return "";
    }
  }

  /**
   * 获取危险权限描述
   */
  public static String getPermissionDes(String[] permissions) {
    String permissionsDes = "";
    if (null == dangerousPermissionDes) {
      dangerousPermissionDes = new HashMap();

      dangerousPermissionDes.put(Manifest.permission.READ_CALENDAR, "日程权限");
      dangerousPermissionDes.put(Manifest.permission.WRITE_CALENDAR, "日程权限");

      dangerousPermissionDes.put(Manifest.permission.CAMERA, "相机权限");

      dangerousPermissionDes.put(Manifest.permission.BODY_SENSORS, "传感器权限");

      dangerousPermissionDes.put(Manifest.permission.RECORD_AUDIO, "录音权限");

      dangerousPermissionDes.put(Manifest.permission.READ_CONTACTS, "联系人权限");
      dangerousPermissionDes.put(Manifest.permission.WRITE_CONTACTS, "联系人权限");
      dangerousPermissionDes.put(Manifest.permission.GET_ACCOUNTS, "联系人权限");

      dangerousPermissionDes.put(Manifest.permission.ACCESS_FINE_LOCATION, "位置权限");
      dangerousPermissionDes.put(Manifest.permission.ACCESS_COARSE_LOCATION, "位置权限");

      dangerousPermissionDes.put(Manifest.permission.READ_PHONE_STATE, "电话权限");
      dangerousPermissionDes.put(Manifest.permission.CALL_PHONE, "电话权限");
      dangerousPermissionDes.put(Manifest.permission.READ_CALL_LOG, "电话权限");
      dangerousPermissionDes.put(Manifest.permission.WRITE_CALL_LOG, "电话权限");
      dangerousPermissionDes.put(Manifest.permission.ADD_VOICEMAIL, "电话权限");
      dangerousPermissionDes.put(Manifest.permission.USE_SIP, "电话权限");
      dangerousPermissionDes.put(Manifest.permission.PROCESS_OUTGOING_CALLS, "电话权限");

      dangerousPermissionDes.put(Manifest.permission.SEND_SMS, "短信权限");
      dangerousPermissionDes.put(Manifest.permission.RECEIVE_SMS, "短信权限");
      dangerousPermissionDes.put(Manifest.permission.READ_SMS, "短信权限");
      dangerousPermissionDes.put(Manifest.permission.RECEIVE_WAP_PUSH, "短信权限");
      dangerousPermissionDes.put(Manifest.permission.RECEIVE_MMS, "短信权限");

      dangerousPermissionDes.put(Manifest.permission.READ_EXTERNAL_STORAGE, "外部存储权限");
      dangerousPermissionDes.put(Manifest.permission.WRITE_EXTERNAL_STORAGE, "外部存储权限");
    }

    Set<String> set = new HashSet<>();
    for (String permission : permissions) {
      String des = dangerousPermissionDes.get(permission);
      if (TextUtils.isEmpty(des)) {
        continue;
      } else {
        set.add(des);
      }
    }

    for (String des : set) {
      permissionsDes = permissionsDes + des;
    }

    return permissionsDes;
  }
}
