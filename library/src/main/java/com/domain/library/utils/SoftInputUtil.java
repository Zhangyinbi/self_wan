package com.domain.library.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by ning.li.o on 2018/1/11.
 */

public class SoftInputUtil {

  public static void showSoftInput(View view) {
    InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      view.requestFocus();
      imm.showSoftInput(view, 0);
    }
  }

  public static void hideSoftInput(View view) {
    InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    if (imm != null) {
      imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
}
