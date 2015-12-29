package com.sinooceanland.roomhelper.control.bean;

import java.io.Serializable;
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

	/**
	 * @author peng
	 *
	 */
	public class TaskMessage extends BaseBean {

		/**
		 * 任务编码
		 */
		public String TaskCode;
		/**
		 * 任务描述 
		 * 任务是否已经完成 true已完成 false 未完成
		 */ 
		public String TaskName;
		
		/**
		 * 0等待上传 1.正在上传
		 */
		public boolean isLoading;
		
		public boolean isFinish; 
		public List<BuildingList> buildingList;
		
		public class BuildingList implements Serializable {

			public String BuildingCode;
			public List<String> UnitCode;
		}
	}
}
