package com.domain.library.utils;

import android.content.Context;
import android.content.SharedPreferences;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

/**
 * SpUtils, easy to get or put data
 * <ul>
 * <strong>Preference Name</strong>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-3-6
 */
public class SpUtils {
  private static final String TAG = SpUtils.class.getSimpleName();
  private static Context           context;
  private static String            name;
  private static SharedPreferences mSp;

  /**
   * @param name SharedPreferences文件名
   */
  public SpUtils(Context context, String name) {
    SpUtils.context = context;
    SpUtils.name = name;
    mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
  }

  public static void init(Context context) {
    SpUtils.context = context;
    SpUtils.name = "root";
    mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
  }

  public static void putString(String key, String object) {
    SharedPreferences.Editor edit = mSp.edit();
    edit.putString(key, object);
    edit.apply();
    edit.commit();
  }

  public static String getString(String key) {
    return mSp.getString(key, "");
  }

  public static void putInt(String key, int object) {
    SharedPreferences.Editor edit = mSp.edit();
    edit.putInt(key, object);
    edit.apply();
    edit.commit();
  }

  public static int getInt(String key, int object) {
    return mSp.getInt(key, object);
  }

  public static void putBoolean(String key, boolean object) {
    SharedPreferences.Editor edit = mSp.edit();
    edit.putBoolean(key, object);
    edit.apply();
    edit.commit();
  }

  public static boolean getBoolean(String key, boolean object) {
    return mSp.getBoolean(key, object);
  }

  /**
   * 针对复杂类型存储<对象>
   */
  public static void setObject(String key, Object object) {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream out = null;
    try {

      out = new ObjectOutputStream(baos);
      out.writeObject(object);
      String objectVal = new String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT));
      SharedPreferences.Editor editor = mSp.edit();
      editor.putString(key, objectVal);
      editor.commit();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (baos != null) {
          baos.close();
        }
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @SuppressWarnings("unchecked")
  public static <T> T getObject(String key, Class<T> clazz) {
    if (mSp.contains(key)) {
      String objectVal = mSp.getString(key, null);
      byte[] buffer = android.util.Base64.decode(objectVal, android.util.Base64.DEFAULT);
      ByteArrayInputStream bais = new ByteArrayInputStream(buffer);
      ObjectInputStream ois = null;
      try {
        ois = new ObjectInputStream(bais);
        T t = (T) ois.readObject();
        return t;
      } catch (StreamCorruptedException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } finally {
        try {
          if (bais != null) {
            bais.close();
          }
          if (ois != null) {
            ois.close();
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return null;
  }

  //删除
  public static void clearData() {
    mSp.edit()
       .clear()
       .commit();
  }

  //删除
  public static void removeData(String key) {
    mSp.edit()
       .remove(key)
       .commit();
  }

  /**
   * 保存List
   */
  public static <T> void setDataList(String tag, ArrayList<T> datalist) {
    if (null == datalist || datalist.size() <= 0) {
      return;
    }

    Gson gson = new Gson();
    //转换成json数据，再保存
    String strJson = gson.toJson(datalist);
    putString(tag, strJson);
  }

  /**
   * 获取List
   */
  public static String getDataList(String tag) {
    String strJson = getString(tag);
    if (null == strJson) {
      return "";
    }
    return strJson;
  }

  /**
   * 根据key和预期的value类型获取value的值
   */
  public <T> T getValue(String key, Class<T> clazz) {
    if (context == null) {
      throw new RuntimeException("请先调用带有context，name参数的构造！");
    }
    return getValue(key, clazz, mSp);
  }

  /**
   * 对于外部不可见的过渡方法
   */
  @SuppressWarnings("unchecked")
  private <T> T getValue(String key, Class<T> clazz, SharedPreferences sp) {
    T t;
    try {

      t = clazz.newInstance();

      if (t instanceof Integer) {
        return (T) Integer.valueOf(sp.getInt(key, 0));
      } else if (t instanceof String) {
        return (T) sp.getString(key, "");
      } else if (t instanceof Boolean) {
        return (T) Boolean.valueOf(sp.getBoolean(key, false));
      } else if (t instanceof Long) {
        return (T) Long.valueOf(sp.getLong(key, 0L));
      } else if (t instanceof Float) {
        return (T) Float.valueOf(sp.getFloat(key, 0L));
      }
    } catch (InstantiationException e) {
      e.printStackTrace();
      Log.e(TAG, "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      Log.e(TAG, "类型输入错误或者复杂类型无法解析[" + e.getMessage() + "]");
    }
    Log.e(TAG, "无法找到" + key + "对应的值");
    return null;
  }
}