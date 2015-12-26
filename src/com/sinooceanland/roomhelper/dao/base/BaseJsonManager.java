package com.sinooceanland.roomhelper.dao.base;

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
	public String mJson;
	protected String JSONKEY = "json-key" + this.getClass().getName();
	public BaseJsonManager(Context context, String json){
		this.mContext = context;
		this.mJson = json;
		saveJson(mJson);
	}
	
	public BaseJsonManager(Context context, String key, String json){
		this.mContext = context;
		this.mJson = json;
		this.JSONKEY = key;
		saveJson(mJson);
	}
	
	/**
	 * 通过key获取jsonManger
	 * @param context
	 * @param key
	 * @return
	 */
	public static BaseJsonManager findManagerByKey(Context context, String key){
		String json = RoomHelperDaoUtil.getStringFromSp(context, key);
		BaseJsonManager manager = null;
		if(!TextUtils.isEmpty(json)){
			manager = new BaseJsonManager(context,key,json);
		}
		
		return manager;
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
		this.mJson = json;
		RoomHelperDaoUtil.putStringToSP(mContext, this.JSONKEY, json);
	}
	
	//删除原来key保存的json数据，重新以新key保存json数据
	public void resetKey(String key){
		RoomHelperDaoUtil.removeDataByKey(mContext, JSONKEY);
		this.JSONKEY = key;
		saveJson(mJson);
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