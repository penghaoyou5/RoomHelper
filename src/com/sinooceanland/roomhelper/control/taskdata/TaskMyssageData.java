package com.sinooceanland.roomhelper.control.taskdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import android.content.Context;
import android.text.TextUtils;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBeanList;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.test.TestNet;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
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
//	public static void saveModifyBigJson(){
//		for (Map.Entry<String, BigJsonManager> entry : mapBigJson.entrySet()) {
//			BigJsonManager value = entry.getValue();
//			entry.getValue().resetJson(value.beanToJson(value.getTaskDetailBean()));;
//		}
//	}
	
	
	
	/**
	 * 通过 key 找 BigJsonManager 如果已经存在就不会再次寻找
	 * @param key
	 * @return
	 * @throws IOException 
	 */
	public static BigJsonManager getBigJson(String key) {
		BigJsonManager bigJsonManager = mapBigJson.get(key);
		if(bigJsonManager==null){
			bigJsonManager =  BaseJsonManager
					.findManagerByKey(context, key,BigJsonManager.class);
			mapBigJson.put(key, bigJsonManager);
		}
		return bigJsonManager;
	}
	
	/**
	 * 通过 key 找 BigJsonManager 如果已经存在就不会再次寻找
	 * 寻找到不向集合中添加
	 * @param key 这是key找到大json唯一标示
	 * @param isAddBigJson 是否添加到map集合  true 如果map中不存在进行添加，false 如过不存在不添加
	 * @return
	 */
	public static BigJsonManager getBigJsonNoAddMap(String key){
		BigJsonManager bigJsonManager = mapBigJson.get(key);
		if(bigJsonManager==null){
			bigJsonManager =  BaseJsonManager
					.findManagerByKey(context, key,BigJsonManager.class);
		}
		return bigJsonManager;
	}
	
	
	
	public TaskDetailBean getTaskDetail(){
		if(BaseNet.isTest)return TestNet.getTaskDetail();
		return null;
	}
	
	private TaskMessage taskMessage;
	private static Context context;

	private static TaskMyssageData myssageData = new TaskMyssageData();
	/**
	 * 这是点击任务进入房间列表的类
	 * @param context
	 *zz @param taskMessage
	 */
//	public TaskMyssageData(Context context, TaskMessage taskMessage) {
//		// 存储当前任务信息
//		SpUtil.putString(SpKey.CURRENTTASKMESSAGE, taskMessage.TaskCode);
//		this.taskMessage = taskMessage;
//		this.context = context;
//		mapBigJson = new HashMap<String, BigJsonManager>();
//	}
	private TaskMyssageData(){}
	
	/**
	 * 最后推出调用  保存json 
	 */
	public static void saveModifyBigJson(){
		new Thread(){
			@Override
			public void run() {
				for (Map.Entry<String, BigJsonManager> entry : mapBigJson.entrySet()) {
					BigJsonManager value = entry.getValue();
					entry.getValue().resetJson(value.beanToJson(value.getTaskDetailBean()));;
				}
			}
		}.start();
		
	}

	public static void saveTaskMessage(Context context, TaskMessage taskMessage){
		if(myssageData==null){
			myssageData = new TaskMyssageData();
		}
		SpUtil.putString(SpKey.CURRENTTASKMESSAGE, taskMessage.TaskCode);
		mapBigJson = new HashMap<String, BigJsonManager>();
		myssageData.taskMessage = taskMessage;
		myssageData.context = context;
	}
	
	public static TaskMyssageData getInstance(){
		return myssageData;
	}
	
	public String getTaskName(){
		return taskMessage.TaskName;
	}

	/**以方法名定义成员计数 是第几页 从1开始*/
	private int getHomeListCount;
	private List<HouseMessage> messages;

	/**
	 * 获得房间列表
	 * 
	 * @param index
	 *            请求页数  从一开始
	 * @return
	 */
	public List<HouseMessage> getHomeList(final int index) {
		getHomeListCount = 0;
		messages = new ArrayList<HouseMessage>();
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
	 * 获得房间列表
	 * 
	 * @param index
	 *            请求页数  从一开始
	 * @param CheckStauts
	 *            房屋验收状态
	 * @return
	 */
	public List<HouseMessage> getHomeList(final int index,final String Statue) {
		getHomeListCount = 0;
		final List<HouseMessage> messages = new ArrayList<HouseMessage>();
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				getHomeListCount++;
				if (index == getHomeListCount) {
					BigJsonManager bigJsonManager = getBigJson(key);
					List<HouseMessage> taskList = bigJsonManager.getTaskList();
					//全部
					if("-1".equals(Statue)){
						messages.addAll(taskList);
						return true;
					}
					for (int i = 0; i < taskList.size(); i++) {
						HouseMessage houseMessage = taskList.get(i);
						//0 未验收 1 已验收未通过 2 已验收已通过
						String checkStauts = houseMessage.CheckStauts;
						if(Statue.equals(checkStauts)){
							messages.add(houseMessage);
						}
					}
//					messages = bigJsonManager.getTaskList();
					//TODO:当时怎么写了这句话？？
//					messages.addAll(bigJsonManager.getTaskList());
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
		/**这是得到楼栋信息的方法*/
		String str = SpUtilCurrentTaskInfo.getString(SpKey.getBuildInfoKey(taskMessage.TaskCode), null);
		//判断是否已经有此任务对应的楼栋列表信息  如果有就直接用
		if(!TextUtils.isEmpty(str)){
			 ChooseHouseBeanList chooseHouseBeanList = BaseNet.getGson().fromJson(str, ChooseHouseBeanList.class);
			return chooseHouseBeanList.date;
		}
		
		//这里是进行本地循环查询的方法       循环遍历向集合中添加元素
		final Map<Integer, TreeSet<Integer>> map = new TreeMap<Integer, TreeSet<Integer>>();
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				addChooseHouse(map, key);
				return false;
			}
		};
		//将map对象转化为ChooseHouseBean集合
		ArrayList<ChooseHouseBean> arrayList = new ArrayList<ChooseHouseBean>();
		
		
		Iterator<Integer> it = map.keySet().iterator();
		while (it.hasNext()) {
			//it.next()得到的是key，tm.get(key)得到obj
			Integer key = it.next();
			TreeSet<Integer> value = map.get(key);
			ChooseHouseBean bean = new ChooseHouseBean();
			bean.build = key;
			bean.houseCode = new ArrayList<Integer>(value);
			bean.buildSize = bean.houseCode.size();
			arrayList.add(bean);
		}
		
		//进行列表的存放
		ChooseHouseBeanList beanList = new ChooseHouseBeanList();
		beanList.date = arrayList;
		String json = BaseNet.getGson().toJson(beanList);
		SpUtilCurrentTaskInfo.putString(SpKey.getBuildInfoKey(taskMessage.TaskCode), json);
		return arrayList;
	}

	private void addChooseHouse(Map<Integer, TreeSet<Integer>> map, String key) {
		BigJsonManager bigJsonManager = getBigJsonNoAddMap(key);
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

	
	public ArrayList<StatusBean> getStatus(){
		ArrayList<StatusBean> arrayList = new ArrayList<StatusBean>();
		for (int i = 0; i < 4; i++) {
			StatusBean statusBean = new StatusBean();
			arrayList.add(statusBean);
			statusBean.date = "已验收";
			statusBean.id = String.valueOf(i-1);
			switch (i) {
			case 0:
				statusBean.date = "全部";
				break;
			case 1:
				statusBean.date = "未验收";
				break;
			case 2:
				statusBean.date = "已验收未通过";
				break;
			case 3:
				statusBean.date = "已验收已通过";
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
	/**重新赋值不会出问题*/
	HouseMessage message;
	/**
	 * 点击楼栋信息筛选使用
	 * @param buildName 楼栋号
	 * @param Housename 房间号
	 * @return 一个房间
	 */
	public HouseMessage getHouseByBuildNameAndHouseName(final String buildName,final String Housename){
//		if(BaseNet.isTest)  为什么会出错？  角标从一开始
//		return getHomeList(0).get(0);
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
