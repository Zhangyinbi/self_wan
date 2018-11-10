package com.domain.operationrobot.util;

import android.util.Log;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONObject;

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
    uploadManager = new UploadManager();
  }

  public static FileUpLoadUtils getInstance() {
    return FileUpLoadUtils.SingletonHolder.instance;
  }

  public String getToken() {
    return Auth.create(accessKey, secretKey)
               .uploadToken(bucket);
  }

  public void upLoadFile(String filePath, IUpLoadCallBack callBack, int outWidth, int outHeight) {
    String token = getToken();
    uploadManager.put(filePath, System.currentTimeMillis() + "", token, new UpCompletionHandler() {
      public void complete(String key, ResponseInfo rinfo, JSONObject response) {
        String fileKey = response.optString("key");
        String url = host + fileKey;
        callBack.suss(url, outWidth, outHeight);
      }
    }, new UploadOptions(null, "test-type", true, null, null));
  }

  private static class SingletonHolder {
    private static FileUpLoadUtils instance = new FileUpLoadUtils();
  }
}
