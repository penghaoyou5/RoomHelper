package com.roomhelper.control.bean;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import com.roomhelper.control.base.BaseBean;

public class TaskListBean extends BaseBean {

	public String message;
	public int code;
	public List<TaskMessage> list;

	public class TaskMessage extends BaseBean {

		/**
		 * 任务编码
		 */
		public String TaskCode;
		/**
		 * 任务描述
		 */
		public String TaskName;
	}
}
