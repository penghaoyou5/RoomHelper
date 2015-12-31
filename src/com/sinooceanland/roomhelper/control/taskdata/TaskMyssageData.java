package com.sinooceanland.roomhelper.control.taskdata;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import android.content.Context;
import android.text.TextUtils;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.test.TestNet;
import com.sinooceanland.roomhelper.control.util.GetAssertUtil;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.TasMessagetUtil;
import com.sinooceanland.roomhelper.dao.BigJsonManager;
import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;

/**
 * @author peng 这是点击任务进入房间信息类
 */
public class TaskMyssageData {

	private static Map<String, BigJsonManager> mapBigJson = new HashMap<String, BigJsonManager>();
	/**
	 * 最后推出调用  保存json 
	 */
	public void saveModifyBigJson(){
		for (Map.Entry<String, BigJsonManager> entry : mapBigJson.entrySet()) {
			BigJsonManager value = entry.getValue();
			entry.getValue().resetJson(value.beanToJson(value.getTaskDetailBean()));;
		}
	}
	
	
	
	/**
	 * 通过 key 找 BigJsonManager 如果已经存在就不会再次寻找
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public static BigJsonManager getBigJson(String key) {
		if(BaseNet.isTest){
			key="test";
			if(mapBigJson.get(key)==null){
				StringBuffer buffer = new StringBuffer();
				
//				try {
//					InputStream open = context.getAssets().open("taskdetail.txt");
//					
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				String str = GetAssertUtil.readAssertResource(context, "taskdetail.txt");
				//String str = "{\"SuccessMsg\":\"\",\"ErrorMsg\":0,\"TaskState\":0,\"list\":[{\"BuildingCode\":\"A152D0E9-1270-43A0-97A8-539F0DC24178\",\"PreBuildingName\":\"18\",\"ActBuildingName\":\"18\",\"UnitCode\":\"31C24C65-0226-4D40-9239-191A63106E13\",\"PreUnitName\":\"2\",\"ActUnitName\":\"2\",\"HouseCode\":\"034E777D-95E9-43BD-AB3A-86F74C355AB7\",\"PreHouseName\":\"2302\",\"ActHouseName\":\"2302\",\"PreHouseFullName\":\"18#2-2302\",\"ActHouseFullName\":\"18#2-2302\",\"PropertTypeName\":\"普通房\",\"OwnerNames\":\"\",\"CheckRound\":2,\"SpaceLayoutList\":[{\"SpaceLayoutCode\":2,\"SpaceLayoutName\":\"套内空间\",\"SpaceLayoutFullName\":\"套内空间\"}]}]}";
				BigJsonManager bigJsonManager =new BigJsonManager(context, str);
				
				
				
				mapBigJson.put(key, bigJsonManager);
			}
		}
		
		BigJsonManager bigJsonManager = mapBigJson.get(key);
		if(bigJsonManager==null){
			bigJsonManager = ((BigJsonManager) BaseJsonManager
					.findManagerByKey(context, key,BigJsonManager.class));
			mapBigJson.put(key, bigJsonManager);
		}
		return bigJsonManager;
	}
	
	public TaskDetailBean getTaskDetail(){
		
		if(BaseNet.isTest)return TestNet.getTaskDetail();
		return null;
	}
	
	private TaskMessage taskMessage;
	private static Context context;

	public TaskMyssageData(Context context, TaskMessage taskMessage) {
		// 存储当前任务信息
		SpUtil.putString(SpKey.CURRENTTASKMESSAGE, "");
		this.taskMessage = taskMessage;
		this.context = context;
	}

	// 以方法名定义成员计数
	private int getHomeListCount;
	private List<HouseMessage> messages;

	/**
	 * 获得房间列表
	 * 
	 * @param index
	 *            请求页数
	 * @param CheckStauts
	 *            房屋验收状态
	 * @return
	 */
	public List<HouseMessage> getHomeList(final int index) {
		if(BaseNet.isTest){
			BigJsonManager bigJsonManager = getBigJson("");
			List<HouseMessage> taskList = bigJsonManager.getTaskList();
			messages = taskList;
			return messages;
		}
		
		getHomeListCount = 0;
		messages = null;
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				getHomeListCount++;
				if (index == getHomeListCount) {
					BigJsonManager bigJsonManager = getBigJson(key);
					messages = bigJsonManager.getTaskList();
					return true;
				}
				return false;
			}
		};
		return messages;
	}

	/**
	 * 得到楼栋信息的双级列表 第一层楼栋信息 第二层房间信息
	 * 
	 * @return
	 */
	public ArrayList<ChooseHouseBean> getBuildingInformation() {
		
		if(BaseNet.isTest){
			ArrayList<ChooseHouseBean> arrayList = new ArrayList<ChooseHouseBean>();
			for (int i = 0; i < 5; i++) {
				ChooseHouseBean bean = new ChooseHouseBean();
				bean.build = i;
				bean.buildSize=5;
				bean.houseCode = new ArrayList<Integer>();
				for (int j = 0; j < 6; j++) {
					bean.houseCode.add(j);
				}
				arrayList.add(bean);
			}
			return arrayList;
		}
		
		
		
		
		String string = SpUtil.getString(SpKey.getBuildInfoKey(taskMessage.TaskCode), null);
		//判断是否已经有此任务对应的楼栋列表信息
//		if(!TextUtils.isEmpty(string)){
//			return BaseNet.getGson().fromJson(string, BuildInfo.class);
//		}
		
		final Map<Integer, TreeSet<Integer>> map = new TreeMap<Integer, TreeSet<Integer>>();
		if(BaseNet.isTest){
			BuildInfo buildInfo = new BuildInfo();
			buildInfo.builds = new ArrayList<Integer>();
			for (int i = 0; i < 6; i++) {
				
				for (int j = 0; j < 7; j++) {
					
				}
				
			}
		}
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				addBuildInfo(map, key);
				return false;
			}
		};
		return null;
	}

	private void addBuildInfo(Map<Integer, TreeSet<Integer>> map, String key) {
		BigJsonManager bigJsonManager = getBigJson(key);
		List<HouseMessage> taskList = bigJsonManager.getTaskList();
		for (int i = 0; i < taskList.size(); i++) {
			HouseMessage houseMessage = taskList.get(i);
			Integer actBuildingName = Integer
					.valueOf(houseMessage.ActBuildingName);
			Integer actHouseName = Integer.valueOf(houseMessage.ActHouseName);
			TreeSet<Integer> treeSet = map.get(actBuildingName);
			if (treeSet == null) {
				treeSet = new TreeSet<Integer>();
				map.put(actBuildingName, treeSet);
			}
			treeSet.add(actHouseName);
		}
	}

	/**
	 * @author peng
	 * 楼栋信息类
	 */
	public class BuildInfo {
		public BuildInfo(Map<Integer, TreeSet<Integer>> map) {
			builds = new ArrayList<Integer>();
			houres = new TreeMap<Integer, List<Integer>>();
			for (Map.Entry<Integer, TreeSet<Integer>> entry : map.entrySet()) {
				Integer key = entry.getKey();
				TreeSet<Integer> value = entry.getValue();
				builds.add(key);
				houres.put(key, new ArrayList<Integer>(value));
			}
			SpUtil.putString(SpKey.getBuildInfoKey(taskMessage.TaskCode), BaseNet.getGson().toJson(this));
		}
		
		public  BuildInfo(){}
		/**
		 * 楼栋编号 
		 */
		public List<Integer> builds;

		/**
		 * 楼栋对应房间集合
		 */
		public Map<Integer, List<Integer>> houres;
	}
	
	public ArrayList<StatusBean> getStatus(){
		ArrayList<StatusBean> arrayList = new ArrayList<StatusBean>();
		for (int i = 0; i < 4; i++) {
			StatusBean statusBean = new StatusBean();
			arrayList.add(statusBean);
			statusBean.date = "已验收";
			statusBean.id = String.valueOf(i);
			switch (i) {
			case 0:
				statusBean.date = "全部";
				break;
			case 1:
				statusBean.date = "已验收";
				break;
			case 2:
				statusBean.date = "已验收未通过";
				break;
			case 3:
				statusBean.date = " 已验收已通过";
				break;

			default:
				break;
			}
		}
		return arrayList;
	}
	

	/**
	 * 根据楼房状态进行筛选
	 * @param messages 要进行筛选的楼房列表
	 * @param Statue 楼房状态 0 未通过 1 通过
	 * @return  符合信息的楼房列表
	 */
	public List<HouseMessage> getListByStatue(List<HouseMessage> messages,String Statue){
		List<HouseMessage> houseMessagesStatue = new ArrayList<HouseMessage>();
		for (int i = 0; i < messages.size(); i++) {
			HouseMessage houseMessage = messages.get(i);
			String checkStauts = houseMessage.CheckStauts;
			if("0".equals(checkStauts)){
				houseMessagesStatue.add(houseMessage);
			}else if("1".equals(checkStauts)){
				houseMessagesStatue.add(houseMessage);
			}
		}
		return houseMessagesStatue;
	}
	
	HouseMessage message;
	/**
	 * 点击楼栋信息筛选使用
	 * @param buildName 楼栋号
	 * @param Housename 房间号
	 * @return 一个房间
	 */
	public HouseMessage getHouseByBuildNameAndHouseName(final String buildName,final String Housename){
		if(BaseNet.isTest)
		return getHomeList(0).get(0);
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				BigJsonManager jsonManager = getBigJson(key);
				List<HouseMessage> taskList = jsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					if(buildName.equals( houseMessage.ActBuildingName)&&Housename.equals(houseMessage.ActHouseName)){
						message = houseMessage;
						return true;
					}
				}
				return false;
			}
		};
		return message;
	}
	
	/**
	 * 根据房间号得到房间列表集合
	 * @param Housename
	 * @return
	 */
	public List<HouseMessage> getHouseByHouseName(final String Housename){
		if(BaseNet.isTest){
			return getHomeList(2);
		}
		final List<HouseMessage> messages = new ArrayList<HouseMessage>(); 
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				BigJsonManager jsonManager = getBigJson(key);
				List<HouseMessage> taskList = jsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					if(Housename.equals(houseMessage.ActHouseName)){
						messages.add(houseMessage);
						return false;
					}
				}
				return false;
			}
		};
		return messages;
	}
}
