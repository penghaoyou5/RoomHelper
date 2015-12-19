package com.sinooceanland.roomhelper.dao.module;

import java.util.List;

import com.sinooceanland.roomhelper.dao.base.BaseBean;

/**
 * @author peng
 * 任务列表
 */
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
