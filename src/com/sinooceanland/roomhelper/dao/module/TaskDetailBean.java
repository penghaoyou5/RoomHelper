package com.sinooceanland.roomhelper.dao.module;
import java.util.List;

import com.sinooceanland.roomhelper.dao.base.BaseBean;
/**
 * @author peng
 * 任务详情
 */
public class TaskDetailBean extends BaseBean {

//	public String SuccessMsg;
//	public int ErrorMsg;
	public List<HouseMessage> list;
	public int downloadState = 0;
	public int finishState = 0;
	public int uploadState = 0;
	public String TaskState;
	
	
	public String getTaskState() {
		return TaskState;
	}
	public void setTaskState(String taskState) {
		TaskState = taskState;
	}
	public String getSuccessMsg() {
		return SuccessMsg;
	}
	public void setSuccessMsg(String successMsg) {
		SuccessMsg = successMsg;
	}
	public List<HouseMessage> getList() {
		return list;
	}
	public void setList(List<HouseMessage> list) {
		this.list = list;
	}
//	public int getErrorMsg() {
//		return this.ErrorMsg;
//	}
//	public void setErrorMsg(int errorMsg) {
//		ErrorMsg = errorMsg;
//	}
}
