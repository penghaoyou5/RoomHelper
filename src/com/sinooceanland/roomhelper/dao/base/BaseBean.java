package com.sinooceanland.roomhelper.dao.base;

import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * @author peng
 * 基础bean类要不要模仿application？
 */
public  class BaseBean implements Serializable {

	/**
	 * 成功信息：true ，false
	 */
	public String SuccessMsg;
	public String ErrorMsg;

	@Override
	public String toString() {
		String s = "";
		Field[] arr = this.getClass().getFields();
		for (Field f : getClass().getFields()) {
			try {
				s += f.getName() + "=" + f.get(this) + "\n,";
			} catch (Exception e) {
			}
		}
		return getClass().getSimpleName() + "["
				+ (arr.length == 0 ? s : s.substring(0, s.length() - 1)) + "]";
	}
}
