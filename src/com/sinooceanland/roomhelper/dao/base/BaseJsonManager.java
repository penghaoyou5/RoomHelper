package com.sinooceanland.roomhelper.dao.base;

import java.lang.reflect.Constructor;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.sinooceanland.roomhelper.dao.utils.RoomHelperDaoUtil;

/**
 * 该类是操作Json数据的抽象基类
 * 主要作用是定义共用的方法，提高代码的复用性
 * 在该项目中只有一个子类BigJsonManager，专门操作该项目中的大Json数据
 * 
 * 项目后期如果添加了别的类型大Json数据，需要管理Json数据，可以继承该基类
 * @author Administrator
 */
public class BaseJsonManager {
	public Context mContext;
	protected String JSONKEY = "json-key" + this.getClass().getName();
	public BaseJsonManager(Context context, String json){
		this.mContext = context;
		saveJson(json);
	}
	
	public BaseJsonManager(Context context, String key, String json){
		this.mContext = context;
		this.JSONKEY = key;
		saveJson(json);
	}
	
	/**
	 * 通过key获取jsonManger
	 * @param context
	 * @param key
	 * @param clazz 
	 * @return
	 */
	public static <T> T findManagerByKey(Context context, String key, Class<T> clazz){
		String json = RoomHelperDaoUtil.getStringFromSp(context, key);
		T t = null;
		if(!TextUtils.isEmpty(json)){
			try{
				Constructor<T> constructor = clazz.getConstructor(Context.class,String.class,String.class);
				t = (T)constructor.newInstance(context,key,json);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return t;
	}
	
	//定义共用的保存json数据的方法
	//优化保存json的方法，json存在就不保存了
	private void saveJson(String json){
		if(TextUtils.isEmpty(obtainJson())){
			resetJson(json);
		}
	}
	
	//获取托管的json数据
	public String obtainJson(){
		return RoomHelperDaoUtil.getStringFromSp(mContext, this.JSONKEY);
	}
	
	//重新设置保存json数据
	public void resetJson(String json){
		RoomHelperDaoUtil.putStringToSP(mContext, this.JSONKEY, json);
	}
	
	//删除原来key保存的json数据，重新以新key保存json数据
	public void resetKey(String key){
		String json = obtainJson();
		RoomHelperDaoUtil.removeDataByKey(mContext, JSONKEY);
		this.JSONKEY = key;
		resetJson(json);
	}
	
	//清除所有json数据
	public void clearJsonData(){
		RoomHelperDaoUtil.clearAllData(mContext);
	}
	
	//将Bean对象转为json
	public <T> String beanToJson(T t){
		return RoomHelperDaoUtil.bean2Json(t);
	}
	
	//将json解析为Bean对象
	public <T> T jsonToBean(String json, Class<T> clazz){
		return new Gson().fromJson(json, clazz);
	}
}