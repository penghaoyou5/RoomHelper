package com.sinooceanland.roomhelper.control.taskdata;


import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.TaskListBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.SpUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peng
 * 获得当前用户登录的任务列表
 * 
 */
public class TaskList {
	

	private TaskListBean taskListBean;
	
	private List<TaskMessage> alreadLoad = new ArrayList<TaskMessage>();
	private List<TaskMessage> unLoad = new ArrayList<TaskMessage>();
	
	/**
	 * 获取当前
	 */
	public TaskList() {
		taskListBean = BaseNet.getGson().fromJson(SpKey.getTaskList(), TaskListBean.class);
		if(taskListBean==null||taskListBean.list==null||taskListBean.list.size()<=0){
			return;
		}
		List<TaskMessage> list = taskListBean.list;
		for (int i = 0; i < list.size(); i++) {
			TaskMessage taskMessage = list.get(i);
			String taskCode = taskMessage.TaskCode;
			boolean load = false;
			//是否已完成
			taskMessage.isFinish = SpUtil.getBoolean(taskCode+SpKey.TASKSTATUE, false);
			//根据任务编码是否已下载
			
			//如果图片数相等
			if(SpUtil.getInt(taskCode+"imagecurrent", 0)>=SpUtil.getInt(taskCode+"imagecount", 0)){
				//根据json进行已完成未完成判断
				load = SpUtil.getBoolean(taskCode, false);
			}
			if(load){
				alreadLoad.add(taskMessage);
			}else{
				unLoad.add(taskMessage);
			}
		}
	}
	
	public List<TaskMessage> getAlreadLoad(){
		return alreadLoad;
		
	}
	
	public List<TaskMessage> getUnLoad(){
		return unLoad;
		
	}
	
	
}
