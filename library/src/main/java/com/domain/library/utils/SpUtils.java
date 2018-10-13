package com.domain.library.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.SharedPreferencesCompat;

import com.domain.library.BaseApplicationController;
import com.google.gson.Gson;

/**
 * SpUtils, easy to get or put data
 * <ul>
 * <strong>Preference Name</strong>
 * <li>you can change preference name by {@link #PREFERENCE_NAME}</li>
 * </ul>
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-3-6
 */
public class SpUtils {

    public static String PREFERENCE_NAME = "sp_data";
    private static Context context = BaseApplicationController.getContext();

    private SpUtils() {
        throw new AssertionError();
    }

    /**
     * put string preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return syncCommit(editor);
    }

    /**
     * get string preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with this
     * name that is not a string
     * @see #getString(String, String)
     */
    public static String getString(String key) {
        return getString(key, null);
    }

    /**
     * get string preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a string
     */
    public static String getString(String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return syncCommit(editor);
    }

    /**
     * get int preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(String, int)
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * get int preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return syncCommit(editor);
    }

    /**
     * get long preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(String, long)
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * get long preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return syncCommit(editor);
    }

    /**
     * get float preferences
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(String, float)
     */
    public static float getFloat(String key) {
        return getFloat(key, -1);
    }

    /**
     * get float preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param key   The name of the preference to modify
     * @param value The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return syncCommit(editor);
    }

    /**
     * get boolean preferences, default is false
     *
     * @param key The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(String, boolean)
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * get boolean preferences
     *
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    /**
     * remove this key
     *
     * @param key
     */
    public static boolean remove(String key) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        return syncCommit(editor);
    }

    /**
     * remove all data
     *
     * @param context
     */
    public static boolean clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        return syncCommit(editor);
    }

    public static boolean syncCommit(SharedPreferences.Editor editor) {
        try {
            editor.apply();
            return true;
        } catch (Exception e) {

        }
        return editor.commit();
    }

    public static void put(String key, Object obj) {
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        try {
            if (obj == null) {
                remove(key);
                return;
            }

            Gson gson = new Gson();
            String toSave = gson.toJson(obj);
            putString(key, toSave);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static <T> T get(String key, Class<T> cla) {
        try {
            Gson gson = new Gson();
            String temp = getString(key, (String) null);
            if (temp != null && temp.trim().length() != 0) {
                T result = gson.fromJson(temp, cla);
                return result;
            } else {
                return null;
            }
        } catch (Exception var5) {
            return null;
        }
    }

}