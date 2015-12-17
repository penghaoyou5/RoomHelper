package com.roomhelper.control.bean;

import com.roomhelper.control.base.BaseBean;

/**
 * @author peng
 * 用户登录信息
 */
public class LoginBean  extends BaseBean{
	 /**
	 * 成功信息：true  ，false 
	 * 用户登录是否成功
	 */
	public boolean  SuccessMsg;
	/**
	 * 提示信息
	 */
	public String  ErrorMsg;
	/**
	 * userID
	 */
	public String userID;
}
