package com.sinooceanland.roomhelper.dao.base;

import android.content.Context;

import com.google.gson.Gson;
import com.sinooceanland.roomhelper.dao.utils.RoomHelperDaoUtil;

/**
 * 该类是操作Json数据的基类
 * 主要作用是定义共用的方法，提高代码的复用性
 * 在该项目中只有一个子类BigJsonManager，专门操作该项目中的大Json数据
 * 
 * 项目后期如果添加了别的类型大Json数据，需要管理Json数据，可以继承该基类
 * @author Administrator
 */
public abstract class BaseJsonManager {
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
	
	public void saveJson(String json){
		this.mJson = json;
		RoomHelperDaoUtil.putStringToSP(mContext, this.JSONKEY, json);
	}
	
	public String obtainJson(){
		return RoomHelperDaoUtil.getStringFromSp(mContext, this.JSONKEY);
	}
	
	public void resetJson(String json){
		this.mJson = json;
		saveJson(json);
	}
	
	public void resetKey(String key){
		RoomHelperDaoUtil.removeDataByKey(mContext, JSONKEY);
		this.JSONKEY = key;
		saveJson(mJson);
	}
	
	public void clearJsonData(){
		RoomHelperDaoUtil.clearAllData(mContext);
	}
	
	public <T> String beanToJson(T t){
		return RoomHelperDaoUtil.bean2Json(t);
	}
	
	public <T> T jsonToBean(String json, Class<T> clazz){
		return new Gson().fromJson(json, clazz);
	}
}