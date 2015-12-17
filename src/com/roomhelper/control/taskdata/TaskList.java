package com.roomhelper.control.taskdata;

import com.roomhelper.control.base.BaseNet;
import com.roomhelper.control.bean.TaskListBean;
import com.roomhelper.control.constant.SpKey;
import com.roomhelper.control.util.SpUtil;

/**
 * @author peng
 * 可以得到任务列表的类
 * 
 */
public class TaskList {
	

	private TaskListBean taskListBean;

	/**
	 * @param taskListBean
	 * UI返回网络qinq请求获得的数据进行展示
	 */
	public TaskList(TaskListBean taskListBean){
		this.taskListBean = taskListBean;
	}
	
	/**
	 * 网络请求调用
	 */
	public TaskList() {
		String taskList = SpUtil.getString(SpKey.TASKLIST, "");
		BaseNet.getGson().fromJson(SpKey.TASKLIST, TaskListBean.class);
		
	}
}
