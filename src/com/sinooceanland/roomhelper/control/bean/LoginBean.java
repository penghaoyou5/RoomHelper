package com.sinooceanland.roomhelper.control.bean;

import com.sinooceanland.roomhelper.dao.base.BaseBean;

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
	protected String  ErrorMsg;
	/**
	 * userID
	 */
	public String userID;
}
