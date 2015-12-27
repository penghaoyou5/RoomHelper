package com.sinooceanland.roomhelper.control.util;

import com.sinooceanland.roomhelper.control.bean.TaskListBean.TaskMessage;
import com.sinooceanland.roomhelper.control.bean.TaskListBean.TaskMessage.BuildingList;

/**
 * @author peng
 * 对任务列表进行循环的方法
 */
public abstract class TasMessagetUtil {
	private TaskMessage taskMessage;
	public TasMessagetUtil(TaskMessage taskMessage) {
		this. taskMessage = taskMessage;
		xunhuan();
	}
	public  void xunhuan(){
		for (int i = 0; i < taskMessage.buildingList.size(); i++) {
			final BuildingList buildingList = taskMessage.buildingList.get(i);
			for (int j = 0; j < buildingList.UnitCode.size(); j++) {
				final String UnitCode = buildingList.UnitCode.get(j);
				boolean forKey = forKey(taskMessage.TaskCode + "+"+ buildingList.BuildingCode+ "+" + UnitCode);
				if(forKey)return;
			}
		}
	}
	
	/**
	 * 对房间遍历key的循环
	 * @param key 
	 * @return boolean 返回值true 停止循环
	 */
	public abstract boolean forKey(String key);
}
