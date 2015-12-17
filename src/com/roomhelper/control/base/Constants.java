package com.roomhelper.control.base;

import com.google.gson.Gson;

public class Constants {
	
	public static final String BASEURL = "";

	/**
	 * 登陆地址
	 */
	public static final String LOGIN = BASEURL +"/MCSWebApp/SinoOcean.Seagull2.HouseDelivery/PreCheckAppHandler/SignInCheckForPreCheckApp.ashx";
	
	/**
	 * 获取模板列表
	 */
	public static final String TASK_LIST = BASEURL +"http://localhost/MCSWebApp/SinoOcean.Seagull2.HouseDelivery/PreCheckAppHandler/Pre	CheckMobileApp.ashx";
	
	
	/**
	 * 获取模板详情 
	 */
	public static final String TASK_DETAIL = BASEURL +"http://localhost/MCSWebApp/SinoOcean.Seagull2.HouseDelivery/PreCheckAppHandler/Get	PreCheckTemplateDetail.ashx";
	
	/**
	 * 获取工程问题
	 */
	public static final String PROJECT_PROBLEM = BASEURL +"";
}
