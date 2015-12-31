package com.sinooceanland.roomhelper.control.test;
import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.base.BaseNet.BaseCallBack;
import com.sinooceanland.roomhelper.control.base.BaseNet.RequestType;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.net.RequestNet;
import com.sinooceanland.roomhelper.control.taskdata.TaskList;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;


public class testTTT extends AndroidTestCase {

		//登录假数据成功   47b70051-b1eb-820e-4614-18f2ebddd17a
		public void testLogin() throws Exception{
			iniSp();
			System.out.println("ggggggggfffui");
			 RequestNet requestNet = new RequestNet(mContext);
			 requestNet.login("v-gouying", "", new BaseCallBack<LoginBean>() {
				
				@Override
				public void messageResponse(RequestType requestType, LoginBean bean,
						String message) {
					System.out.println("gggggg"+bean.userID);
//					Toast.makeText(mContext, bean.userID, 1).show();
				}
			});
		}
		
		
	
		private void iniSp() {
			SpUtil.init(mContext);
			SpUtilCurrentTaskInfo.init(mContext);
		}
		
		//任务列表页数据请求
		public void testTaskListMessage(){
			iniSp();
			new RequestNet(mContext).taskList(new BaseCallBack<TaskList>() {
				@Override
				public void messageResponse(RequestType requestType,
						TaskList bean, String message) {
					
				}
			});
		}


		public void  testTaskList(){
			iniSp();
			TaskMessage message = new TaskMessage();
			new RequestNet(mContext).downTask(mContext, message, new BaseCallBack<String>() {
				
				@Override
				public void messageResponse(RequestType requestType, String bean,
						String message) {
					// TODO Auto-generated method stub
					
				}
			}, null);
		}
		
public void  testTaskDETAIL(){
			iniSp();
	new RequestNet(mContext).getTaskDetail("d", "", "", new BaseCallBack<String>() {
		
		@Override
		public void messageResponse(RequestType requestType, String bean,
				String message) {
			TaskDetailBean fromJson = BaseNet.getGson().fromJson(bean, TaskDetailBean.class);
			System.out.println(fromJson);
		}
	});
	
	TaskMyssageData.getBigJson("");
	
		}
	
	
//	 public void testAdd() throws Exception {  
//	       RequestNet requestNet = new RequestNet(mContext);
//	       requestNet.downTask(mContext, null, new BaseCallBack<String>(){
//
//			@Override
//			public void messageResponse(RequestType requestType, String bean,
//					String message) {
//				System.out.println("sunn");
//			}
//	    	   
//	       }, null);
//	    }  
@SuppressWarnings("unused")
public void testTesDetial(){
	iniSp();
	TaskMyssageData taskMyssageData = new TaskMyssageData(mContext, new TaskMessage());
	ArrayList<ChooseHouseBean> buildingInformation = taskMyssageData.getBuildingInformation();
	System.out.println(buildingInformation);
	HouseMessage houseByBuildNameAndHouseName = taskMyssageData.getHouseByBuildNameAndHouseName("", "");
	System.out.println("测试详情");
	List<HouseMessage> houseByHouseName = taskMyssageData.getHouseByHouseName("");
	
	List<HouseMessage> homeList = taskMyssageData.getHomeList(0);
	TaskDetailBean taskDetail = new TaskMyssageData(mContext, null).getTaskDetail();
	String json = BaseNet.getGson().toJson(new TaskDetailBean());
//	BaseNet.getGson().fromJson(json, TestClassttt.class);
	
	
}
}
