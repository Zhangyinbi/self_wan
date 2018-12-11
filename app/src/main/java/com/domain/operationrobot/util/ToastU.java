package com.domain.operationrobot.util;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.domain.operationrobot.R;

/**
 * Project Name : OperationRobot
 * description:null
 *
 * @author : yinbi.zhang.o
 * Create at : 2018/11/24 13:56
 */
public class ToastU {

  private static Toast toast;

  public static void ToastLoginSussMessage(Activity activity, String text) {
    LayoutInflater inflater = activity.getLayoutInflater();
    View view = inflater.inflate(R.layout.toast_style, null);
    TextView tv_hint_text = view.findViewById(R.id.tv_hint_text);
    tv_hint_text.setText(text);
    if (toast == null) {
      toast = new Toast(activity.getApplicationContext());
    }
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.setDuration(Toast.LENGTH_SHORT);
    toast.setView(view);
    toast.show();
  }
}
