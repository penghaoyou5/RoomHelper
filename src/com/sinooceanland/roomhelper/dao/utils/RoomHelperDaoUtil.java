package com.sinooceanland.roomhelper.dao.utils;

import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 该类是项目Dao层的工具类
 * 主要作用是封装项目在dao层的工具代码
 * @author Administrator
 */
public class RoomHelperDaoUtil {
	public static final String SPNAME = "sp-json";
	public static SharedPreferences sp;
	
	/**
	 * 该方法是用来存储String类型的值到SharedPreferences中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void putStringToSP(Context context, String key, String value){
		if(sp==null){
			sp = context.getSharedPreferences(SPNAME, 0);
		}
		
		Editor edit = sp.edit();
		edit.putString(key, value);
		edit.commit();
	}
	
	/**
	 * 该方法的作用是通过key从SharedPrences中获取存储的String数据
	 * @param context
	 * @param key
	 * @return String
	 */
	public static String getStringFromSp(Context context, String key){
		String str = "";
		if(sp==null){
			sp = context.getSharedPreferences(SPNAME, 0);
		}
		
		str = sp.getString(key, str);
		return str;
	}
	
	public static void removeDataByKey(Context context, String key){
		if(sp==null){
			sp = context.getSharedPreferences(SPNAME, 0);
		}
		
		Editor edit = sp.edit();
		edit.remove(key);
		edit.commit();
	}
	
	public static void clearAllData(Context context){
		if(sp==null){
			sp = context.getSharedPreferences(SPNAME, 0);
		}
		
		sp.edit().clear().commit();
	}
	
	public static <T> T fillBeanFromJson(String json, Class<T> clazz){
		T t = new Gson().fromJson(json, clazz);
		return t;
	}
	
	public static <T> String bean2Json(T t){
		String json = "";
		json = new Gson().toJson(t);
		return json;
	}
	
	public static String fromatMethodName(String name){
		return "set".concat(String.valueOf(name.charAt(0)).toUpperCase().concat(name.substring(1)));
	}
}
