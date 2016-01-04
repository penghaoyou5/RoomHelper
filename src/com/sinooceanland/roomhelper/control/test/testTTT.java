package com.sinooceanland.roomhelper.control.test;
import java.util.ArrayList;
import java.util.List;

import android.test.AndroidTestCase;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.base.BaseNet.BaseCallBack;
import com.sinooceanland.roomhelper.control.base.BaseNet.RequestType;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.bean.TaskListBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.NetUrl;
import com.sinooceanland.roomhelper.control.constant.SpKey;
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
					System.out.println("usernsme"+ SpUtil.getString(SpKey.USERINAME, ""));
					//得到当前用户的useID
					System.out.println("userid"+ SpKey.getUerId());
					
				}
			});
		}
		
		
	
		private void iniSp() {
			SpUtil.init(mContext);
			SpUtilCurrentTaskInfo.init(mContext);
		}
		
		//任务列表基本网络获取成功
//		public void testTaskListNet(){
//			SpUtil.init(mContext);
//			new RequestNet(mContext).baseRequest(null, NetUrl.TASK_LIST,
//					new BaseCallBack<TaskListBean>() {
//				@Override
//				public void messageResponse(RequestType requestType,
//						TaskListBean bean, String message) {
//					if (requestType == RequestType.messagetrue) {
//						// 使用用户id存储任务列表
//						SpUtil.putString(
//								SpKey.getUerId(),
//								message);
////						String uerId = SpKey.getUerId();
////						SpUtil.getString(uerId, defaultValue)
////						System.out.println(sp);
//						System.out.println("ggggg"+SpKey.getTaskList());
//					}
//				}
//			}, TaskListBean.class);
//			
//		}
		
		
		
		//任务列表页数据请求
	
		public void testTaskListMessage(){
			iniSp();
			new RequestNet(mContext).taskList(new BaseCallBack<TaskList>() {
				@Override
				public void messageResponse(RequestType requestType,
						TaskList bean, String message) {
				String uinco = 	bean.getUnLoad().get(0).BuildingList.get(0).UnitCode.get(0);
					System.out.println("ggggg"+bean.getUnLoad().size()+uinco);
					String str = message;
					System.out.println(str);
				}
			});
		}


		public static String code;
		//这里是进行任务的下载
		public void  testTaskList(){
			iniSp();
			//获取任务列表得到任务
			List<TaskMessage> unLoad = new TaskList().getUnLoad();
			 TaskMessage message = unLoad.get(0);
			 code = message.TaskCode;
			new RequestNet(mContext).downTask(mContext, message, new BaseCallBack<String>() {
				
				@Override
				public void messageResponse(RequestType requestType, String bean,
						String message) {
					System.out.println(SpUtil.getBoolean(code,false ));
					System.out.println(bean);
				}
			}, null);
		}
		
		//根据任务页数获取任务详情
		public void testHouseByPotion(){
			iniSp();
			//获取任务列表得到任务
			List<TaskMessage> unLoad = new TaskList().getAlreadLoad();
			 TaskMessage message = unLoad.get(0);
			 code = message.TaskCode;
			 SpUtil.getBoolean(code, false);
//			 //获得任务详情
//			 TaskMyssageData taskMyssageData = new TaskMyssageData(mContext, message);
//			 List<HouseMessage> homeList = taskMyssageData.getHomeList(1);
//			 System.out.println(homeList);
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
//	iniSp();
//	TaskMyssageData taskMyssageData = new TaskMyssageData(mContext, new TaskMessage());
//	ArrayList<ChooseHouseBean> buildingInformation = taskMyssageData.getBuildingInformation();
//	System.out.println(buildingInformation);
//	HouseMessage houseByBuildNameAndHouseName = taskMyssageData.getHouseByBuildNameAndHouseName("", "");
//	System.out.println("测试详情");
//	List<HouseMessage> houseByHouseName = taskMyssageData.getHouseByHouseName("");
//	
//	List<HouseMessage> homeList = taskMyssageData.getHomeList(0);
//	TaskDetailBean taskDetail = new TaskMyssageData(mContext, null).getTaskDetail();
//	String json = BaseNet.getGson().toJson(new TaskDetailBean());
//	BaseNet.getGson().fromJson(json, TestClassttt.class);
	
	
}
}
