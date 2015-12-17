package com.roomhelper.control.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences工具类
 * 
 * @author Fsq
 * @date 2015-6-11 上午10:47:40
 * 
 */
public class SpUtil {

	private static Context mContext;

	public static void init(Context context){
		mContext  = context;
	}

	public static String getString(String key, String defaultValue) {
		return getSharedPref().getString(key, defaultValue);
	}

	public static void putString(String key, String value) {
		getSharedPref().edit().putString(key, value).commit();
	}

	public static boolean getBoolean(String key, boolean defaultValue) {
		return getSharedPref().getBoolean(key, defaultValue);
	}

	public static void putBoolean(String key, boolean value) {
		getSharedPref().edit().putBoolean(key, value).commit();
	}

	public static int getInt(String key, int defaultValue) {
		return getSharedPref().getInt(key, defaultValue);
	}

	public static void putInt(String key, int value) {
		getSharedPref().edit().putInt(key, value).commit();
	}

	public static float getFloat(String key, float defaultValue) {
		return getSharedPref().getFloat(key, defaultValue);
	}

	public static void putFloat(String key, float value) {
		getSharedPref().edit().putFloat(key, value).commit();
	}

	public static long getLong(String key, long defaultValue) {
		return getSharedPref().getLong(key, defaultValue);
	}

	public static void putLong(String key, long value) {
		getSharedPref().edit().putLong(key, value).commit();
	}
	
	public static void remove(String key){
		getSharedPref().edit().remove(key).commit();
	}

	/**
	 * 查询key是否存在
	 * 
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean hasKey(String key) {
		return getSharedPref().contains(key);
	}

	/**
	 * 获得应用默认SharedPreferences
	 * 
	 * @param context
	 * @return
	 */
	public static SharedPreferences getSharedPref() {
		return mContext.getSharedPreferences("RoomHelper",
				Context.MODE_PRIVATE);
	}

	/**
	 * 清除应用默认SharedPreferences中的数据
	 * 
	 * @param context
	 */
	public static void clearPreference() {
		getSharedPref().edit().clear().commit();
	}
}
