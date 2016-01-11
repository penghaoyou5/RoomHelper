package com.sinooceanland.roomhelper.control.test;

import java.util.ArrayList;
import java.util.List;

import com.sinooceanland.roomhelper.control.base.BaseNet.BaseCallBack;
import com.sinooceanland.roomhelper.control.base.BaseNet.RequestType;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.net.RequestNet;
import com.sinooceanland.roomhelper.control.taskdata.TaskList;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;

import android.test.AndroidTestCase;

/**
 * @author peng 测试信息类
 */
public class TestMessageAndDetail extends AndroidTestCase {

	private void iniSp() {
		SpUtil.init(mContext);
		SpUtilCurrentTaskInfo.init(mContext);
	}
	
	public void testUpMessage(){
		
	}
	
	
	

	String code;

	// 根据任务页数获取任务详情
	public void testHouseByPotion() {
		iniSp();
		// 获取任务列表得到任务
		List<TaskMessage> unLoad = new TaskList().getAlreadLoad();
		TaskMessage message = unLoad.get(0);
		code = message.TaskCode;
		SpUtil.getBoolean(code, false);
		// 获得任务详情
//		TaskMyssageData taskMyssageData = new TaskMyssageData(mContext, message);
//		List<HouseMessage> homeList = taskMyssageData.getHomeList(1, "0");
//		System.out.println(homeList);
	}

	// 这里是进行任务的下载
	public void testTaskList() {
		iniSp();
		// 获取任务列表得到任务
		List<TaskMessage> unLoad = new TaskList().getAlreadLoad();
		TaskMessage message = unLoad.get(0);
		code = message.TaskCode;
		new RequestNet(mContext).downTask(mContext, message,
				new BaseCallBack<String>() {

					@Override
					public void messageResponse(RequestType requestType,
							String bean, String message) {
						System.out.println(SpUtil.getBoolean(code, false));
						System.out.println(bean);
					}
				}, null);
	}
	
	/**
	 * 根据任务获取楼栋信息
	 */
	public void testTaskUninfo(){
		iniSp();
		// 获取任务列表得到任务
		List<TaskMessage> unLoad = new TaskList().getAlreadLoad();
		TaskMessage message = unLoad.get(0);
		code = message.TaskCode;
		SpUtil.getBoolean(code, false);
		// 获得任务详情
//		TaskMyssageData taskMyssageData = ;
//		List<HouseMessage> homeList = taskMyssageData.getHomeList(new TaskMyssageData(mContext, message)1, "0");
//		System.out.println(homeList);
		
//		ArrayList<ChooseHouseBean> buildingInformation = taskMyssageData.getBuildingInformation();
		
//		System.out.println(buildingInformation);
	}
	//测试通过
	public void testTaskSearchByHouse(){
		iniSp();
		// 获取任务列表得到任务
		List<TaskMessage> unLoad = new TaskList().getAlreadLoad();
		TaskMessage message = unLoad.get(0);
		code = message.TaskCode;
		SpUtil.getBoolean(code, false);
//		// 获得任务详情
//		TaskMyssageData taskMyssageData = new TaskMyssageData(mContext, message);
//		List<HouseMessage> homeList = taskMyssageData.getHomeList(1, "0");
//		System.out.println(homeList);
		
		List<HouseMessage> houseByHouseName = null;
		
		System.out.println(houseByHouseName);
		
	}

	//测试通过
		public void testTaskSearchByBuildintANdHouse(){
			iniSp();
			// 获取任务列表得到任务
			List<TaskMessage> unLoad = new TaskList().getAlreadLoad();
			TaskMessage message = unLoad.get(0);
			code = message.TaskCode;
			SpUtil.getBoolean(code, false);
			// 获得任务详情
			TaskMyssageData taskMyssageData = null;
			List<HouseMessage> homeList = taskMyssageData.getHomeList(1, "0");
			System.out.println(homeList);
			
			List<HouseMessage> houseByHouseName = taskMyssageData.getHouseByHouseName("2304");
			
			System.out.println(houseByHouseName);
			
		}

}
