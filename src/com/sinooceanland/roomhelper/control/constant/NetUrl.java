package com.sinooceanland.roomhelper.control.constant;

import com.google.gson.Gson;

/**
 * @author peng
 *
 */
public class NetUrl {
	
	public static final String BASEURL = "";

	/**
	 * 登陆地址
	 */
	public static final String LOGIN = "http://mtest.sinooceanland.com/HouseAcceptance/HouseAcceptance/SignInCheckForPreCheckApp";
	
	/**
	 * 获取模板列表
	 */
//	public static final String TASK_LIST = BASEURL +"http://localhost/MCSWebApp/SinoOcean.Seagull2.HouseDelivery/PreCheckAppHandler/Pre	CheckMobileApp.ashx";
	
	public static final String TASK_LIST ="http://mtest.sinooceanland.com/HouseAcceptance/HouseAcceptance/PreCheckMobileApp";
	
	/**
	 * 获取模板详情 
	 */
//	public static final String TASK_DETAIL = BASEURL +"http://localhost/MCSWebApp/SinoOcean.Seagull2.HouseDelivery/PreCheckAppHandler/Get	PreCheckTemplateDetail.ashx";
	public static final String TASK_DETAIL = "http://mtest.sinooceanland.com/HouseAcceptance/HouseAcceptance/GetPreCheckTemplateDetail";
	
	/**
	 * 获取工程问题
	 */
	public static final String PROJECT_PROBLEM = BASEURL +"";
	
	/**
	 * 进行照片的下载
	 */
	public static final String PICTURE_DOWN = "http://mtest.sinooceanland.com/HouseAcceptance/HouseAcceptance/DownLoad";
	
	/**
	 * 进行文件的上传
	 */
	public static final String PICTURE_UP ="http://mtest.sinooceanland.com/HouseAcceptance/HouseAcceptance/Upload";
	
	
	/**
	 * 进行大json上传的接口  
	 */
	public static final String UP_PROTECT = "http://mtest.sinooceanland.com/HouseAcceptance/HouseAcceptance/CallBackPerCheckInfo";
}
