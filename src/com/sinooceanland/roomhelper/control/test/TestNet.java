package com.sinooceanland.roomhelper.control.test;

import java.util.ArrayList;

import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;

public class TestNet {
	public static TaskDetailBean getTaskDetail(){
		TaskDetailBean taskDetailBean = new TaskDetailBean();
		
		taskDetailBean.list = new ArrayList<HouseMessage>();

		for (int i = 0; i < 50; i++) {
			taskDetailBean.list.add(homeMessage(i));
			
		}
//		String json = BaseNet.getGson().toJson(taskDetailBean);
		return taskDetailBean;
	}

	private static HouseMessage homeMessage(int i) {
		HouseMessage houseMessage = new HouseMessage();
		
		houseMessage.ActBuildingName = "18";
		houseMessage.ActHouseName = "2302"+i;
		houseMessage.ActUnitName="2";
		houseMessage.ActHouseFullName = "18#2-230"+i;
		houseMessage.PropertTypeName = "普通房";
		houseMessage.CheckRound = "2";
		houseMessage.SpaceLayoutList = new ArrayList<HouseMessage.SpaceLayoutList>();
		for (int j = 0; j < 5; j++) {
			SpaceLayoutList layout = layout(j);
			houseMessage.SpaceLayoutList.add(layout);
		}
		return houseMessage;
	}

	private static SpaceLayoutList layout(int i) {
		SpaceLayoutList layoutList = new SpaceLayoutList();
		layoutList.SpaceLayoutName= "厨房1"+i;
		layoutList.SpaceLayoutFullName = "犯贱6小厨房1"+i;
		layoutList.AttachmentIDS = new ArrayList<String>();
		return layoutList;
	}

}
