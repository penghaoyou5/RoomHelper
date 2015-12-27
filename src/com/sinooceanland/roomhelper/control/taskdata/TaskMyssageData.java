package com.sinooceanland.roomhelper.control.taskdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import android.content.Context;
import android.text.TextUtils;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.TaskListBean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.TasMessagetUtil;
import com.sinooceanland.roomhelper.dao.BigJsonManager;
import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;

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
	 */
	public static BigJsonManager getBigJson(String key){
		BigJsonManager bigJsonManager = mapBigJson.get(key);
		if(bigJsonManager==null){
			bigJsonManager = ((BigJsonManager) BaseJsonManager
					.findManagerByKey(context, key,BigJsonManager.class));
			mapBigJson.put(key, bigJsonManager);
		}
		return bigJsonManager;
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
	public BuildInfo getBuildingInformation() {
		String string = SpUtil.getString(SpKey.getBuildInfoKey(taskMessage.TaskCode), null);
		//判断是否已经有此任务对应的楼栋列表信息
		if(!TextUtils.isEmpty(string)){
			return BaseNet.getGson().fromJson(string, BuildInfo.class);
		}
		
		final Map<Integer, TreeSet<Integer>> map = new TreeMap<Integer, TreeSet<Integer>>();
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				addBuildInfo(map, key);
				return false;
			}
		};
		return new BuildInfo(map);
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
		/**
		 * 楼栋编号 
		 */
		public List<Integer> builds;

		/**
		 * 楼栋对应房间集合
		 */
		public Map<Integer, List<Integer>> houres;
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
