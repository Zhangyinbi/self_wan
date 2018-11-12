package com.domain.operationrobot.util;

import android.util.Log;
import com.domain.library.http.consumer.BaseObserver;
import com.domain.library.http.exception.BaseException;
import com.domain.library.utils.SpUtils;
import com.domain.operationrobot.BaseApplication;
import com.domain.operationrobot.http.bean.ImageFileBean;
import com.domain.operationrobot.http.data.RemoteMode;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import io.reactivex.disposables.CompositeDisposable;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

import static com.domain.library.utils.ToastUtils.showToast;
import static com.domain.operationrobot.util.Constant.USER_SP_KEY;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/5 22:40
 */
public class FileUpLoadUtils {

  public static final  String accessKey = "_4jLMJkFKjg9futXCQ0UuV-xyhd9s-HyFEULKpd8";
  private static final String secretKey = "iRDeM4UX-3shDhcwXbF1xkLhjBa3ppPGjsWLYSwe";
  private static final String bucket    = "robot";
  private static final String host      = "http://phwvb3fdu.bkt.clouddn.com/";

  private static UploadManager uploadManager;

  private FileUpLoadUtils() {
  }

  public static FileUpLoadUtils getInstance() {
    return FileUpLoadUtils.SingletonHolder.instance;
  }

  /**
   * 上传头像
   */
  public void upLoadFile(String filePath, IUpLoadCallBack callBack, int outWidth, int outHeight) {
    RemoteMode.getInstance()
              .upLoadImage(filePath,1)
              .subscribe(new BaseObserver<ImageFileBean>(new CompositeDisposable()) {
                @Override
                public void onError(BaseException e) {
                  showToast(e.getMsg());
                }

                @Override
                public void onSuss(ImageFileBean imageFileBean) {
                  callBack.suss(imageFileBean.getUploadUrl(), outWidth, outHeight);
                }

                @Override
                public void onComplete() {
                  super.onComplete();
                }
              });
  }

  private static class SingletonHolder {
    private static FileUpLoadUtils instance = new FileUpLoadUtils();
  }
}
