package com.sinooceanland.roomhelper.control.taskdata;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import android.content.Context;
import android.content.Intent;
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

	//第一次最少请求条数
	private static final int count = 20;
	//请求多加的bean 个数 看第一次请求个数而定
	private  int add = 0;

	private static Map<String, BigJsonManager> mapBigJson = new HashMap<String, BigJsonManager>();

	/**
	 * 最后推出调用 保存json
	 */
	// public static void saveModifyBigJson(){
	// for (Map.Entry<String, BigJsonManager> entry : mapBigJson.entrySet()) {
	// BigJsonManager value = entry.getValue();
	// entry.getValue().resetJson(value.beanToJson(value.getTaskDetailBean()));;
	// }
	// }

	/**
	 * 移除除这个房间以外的所有json
	 * @param homMessage
	 */
	public static void releaseOtherBigjson(HouseMessage homMessage) {
		String clickKey = null;
		BigJsonManager clickBigJsonManager = null;
		for (String key : mapBigJson.keySet()) {
			if (mapBigJson.get(key).getTaskList().contains(homMessage)) {
				clickKey = key;
				clickBigJsonManager = mapBigJson.get(key);
				break;
			}
		}
		if (clickKey != null && clickBigJsonManager != null) {
			mapBigJson.clear();
			mapBigJson.put(clickKey, clickBigJsonManager);
		}
	}

	/**
	 * 通过 key 找 BigJsonManager 如果已经存在就不会再次寻找
	 *
	 * @param key
	 * @return
	 * @throws IOException
	 */
	public static BigJsonManager getBigJson(String key) {
		BigJsonManager bigJsonManager = mapBigJson.get(key);
		if (bigJsonManager == null) {
			bigJsonManager = BaseJsonManager.findManagerByKey(context, key,
					BigJsonManager.class);
			mapBigJson.put(key, bigJsonManager);
		}
		return bigJsonManager;
	}

	/**
	 * 通过 key 找 BigJsonManager 如果已经存在就不会再次寻找 寻找到不向集合中添加
	 *
	 * @param key
	 *            这是key找到大json唯一标示
	 * @param isAddBigJson
	 *            是否添加到map集合 true 如果map中不存在进行添加，false 如过不存在不添加
	 * @return
	 */
	public static BigJsonManager getBigJsonNoAddMap(String key) {
		BigJsonManager bigJsonManager = mapBigJson.get(key);
		if (bigJsonManager == null) {
			bigJsonManager = BaseJsonManager.findManagerByKey(context, key,
					BigJsonManager.class);
		}
		return bigJsonManager;
	}

	public TaskDetailBean getTaskDetail() {
		if (BaseNet.isTest)
			return TestNet.getTaskDetail();
		return null;
	}

	private TaskMessage taskMessage;
	private static Context context;

	private static TaskMyssageData myssageData = new TaskMyssageData();

	/**
	 * 这是点击任务进入房间列表的类
	 *
	 * @param context
	 *            zz @param taskMessage
	 */
	// public TaskMyssageData(Context context, TaskMessage taskMessage) {
	// // 存储当前任务信息
	// SpUtil.putString(SpKey.CURRENTTASKMESSAGE, taskMessage.TaskCode);
	// this.taskMessage = taskMessage;
	// this.context = context;
	// mapBigJson = new HashMap<String, BigJsonManager>();
	// }
	private TaskMyssageData() {
	}

	/**
	 * 最后推出调用 保存json
	 */
	public static void saveModifyBigJson() {
		new Thread() {
			@Override
			public void run() {
				for (Map.Entry<String, BigJsonManager> entry : mapBigJson
						.entrySet()) {
					BigJsonManager value = entry.getValue();
					entry.getValue().resetJson(
							value.beanToJson(value.getTaskDetailBean()));
				}
			}
		}.start();

	}

	public static void saveTaskMessage(Context context, TaskMessage taskMessage) {
		if (myssageData == null) {
			myssageData = new TaskMyssageData();
		}
		SpUtil.putString(SpKey.CURRENTTASKMESSAGE, taskMessage.TaskCode);
		mapBigJson = new HashMap<String, BigJsonManager>();
		myssageData.taskMessage = taskMessage;
		myssageData.context = context;
	}

	public void clearJson(){
		for (String key : mapBigJson.keySet()) {
		 mapBigJson.get(key).resetJson("");

		}
	}


	public static TaskMyssageData getInstance() {
		if(myssageData==null||myssageData.taskMessage==null){
			myssageData = new TaskMyssageData();
			myssageData.taskMessage = BaseNet.getGson().fromJson(SpKey.CURRENTTASKMYSSAGEDATA, TaskMessage.class);
		}else{
			SpUtil.putString(SpKey.CURRENTTASKMYSSAGEDATA, BaseNet.getGson().toJson(myssageData.taskMessage));
		}
		return myssageData;
	}

	public String getTaskName() {
		return taskMessage.TaskName;
	}

	/** 以方法名定义成员计数 是第几页 从1开始 */
	private int getHomeListCount;
	private List<HouseMessage> messages;
	
	// 
	private List<HouseMessage> currentMessages;
	private boolean havaDate;
	public List<HouseMessage> getHomeList(final int index) {
		messages = new ArrayList<HouseMessage>();
		//当是第一页 add++ 表示要凑够20页
		if(index<=1){
			havaDate = true;
			add=-1;
			while (messages.size()<count) {
				add++;
				if(!havaDate)break;
				messages.addAll(getHomeCurrentList(index+add));
			}
		}else{
			messages = getHomeCurrentList(index+add);
		}
		return messages;
	}

	/**
	 * 获得房间列表
	 *
	 * @param index
	 *            请求页数 从一开始
	 * @return
	 */
	int bigJsonCount = 0;
	public List<HouseMessage> getHomeCurrentList(final int index) {
		getHomeListCount = 0;
		currentMessages = new ArrayList<HouseMessage>();
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				getHomeListCount++;
				if (index == getHomeListCount) {
					BigJsonManager bigJsonManager = getBigJson(key);
					currentMessages = bigJsonManager.getTaskList();
					return true;
				}
				return false;
			}
		};
		if(currentMessages.size()<=0)havaDate = false;
		return currentMessages;
	}
	
	public List<HouseMessage> getHomeList(final int index, final String Statue){
		messages = new ArrayList<HouseMessage>();
		//当是第一页 add++ 表示要凑够20页   如果第一页都没数据了那就不用考虑后边的了
		if(index<=1){
			havaDate = true;
			add=-1;
			while (messages.size()<count) {
				add++;
				if(!havaDate)break;
				messages.addAll(getCurrentHomeList(index+add,Statue));
			}
		}else{
			messages = getCurrentHomeList(index+add,Statue);
		}
		return messages;
	}
	
	

	/**
	 * 获得房间列表
	 *
	 * @param index
	 *            请求页数 从一开始
	 * @param CheckStauts
	 *            房屋验收状态
	 * @return
	 */
	public List<HouseMessage> getCurrentHomeList(final int index, final String Statue) {
		getHomeListCount = 0;
		final List<HouseMessage> messages = new ArrayList<HouseMessage>();
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				getHomeListCount++;
				if (index == getHomeListCount) {
					BigJsonManager bigJsonManager = getBigJson(key);
					List<HouseMessage> taskList = bigJsonManager.getTaskList();
					// 全部
					if ("-1".equals(Statue)) {
						messages.addAll(taskList);
						return true;
					}
					for (int i = 0; i < taskList.size(); i++) {
						HouseMessage houseMessage = taskList.get(i);
						// 0 未验收 1 已验收未通过 2 已验收已通过
						String checkStauts = houseMessage.CheckStauts;
						if (Statue.equals(checkStauts)) {
							messages.add(houseMessage);
						}
					}
					// messages = bigJsonManager.getTaskList();
					// TODO:当时怎么写了这句话？？
					// messages.addAll(bigJsonManager.getTaskList());
					return true;
				}
				return false;
			}
		};
		if(messages.size()<=0)havaDate = false;
		return messages;
	}

	// 已经完成的房间数
	int haveFinishouse = 0;

	int houseCount = 0;
	/**
	 * 得到楼栋信息的双级列表 第一层楼栋信息 第二层房间信息
	 *
	 * @return
	 */
	public ArrayList<ChooseHouseBean> getBuildingInformation() {
		/** 这是得到楼栋信息的方法 */
		String str = SpUtilCurrentTaskInfo.getString(
				SpKey.getBuildInfoKey(taskMessage.TaskCode), null);
		// 判断是否已经有此任务对应的楼栋列表信息 如果有就直接用
		if (!TextUtils.isEmpty(str)) {
			ChooseHouseBeanList chooseHouseBeanList = BaseNet.getGson()
					.fromJson(str, ChooseHouseBeanList.class);
			return chooseHouseBeanList.date;
		}

		// 这里是进行本地循环查询的方法 循环遍历向集合中添加元素
		final Map<String, ArrayList<String>> map = new TreeMap<String, ArrayList<String>>();
		houseCount= 0;
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				addChooseHouse(map, key);
				return false;
			}
		};
		//存储房间总数
		SpUtilCurrentTaskInfo.putInt(SpKey.getTaskHouseCount(), houseCount);
		// 将map对象转化为ChooseHouseBean集合
		ArrayList<ChooseHouseBean> arrayList = new ArrayList<ChooseHouseBean>();

		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			// it.next()得到的是key，tm.get(key)得到obj
			String key = it.next();
			ArrayList<String> value = map.get(key);
			ChooseHouseBean bean = new ChooseHouseBean();
			bean.build = key;
			// bean.houseCode = new ArrayList<String>(value);
			for (int i = 0; i < value.size(); i++) {
				String strValue = value.get(i);
				String[] split = strValue.split("fengexian");
				if (bean.houseCode == null || bean.houseCode.size() == 0) {
					bean.houseCode = new ArrayList<String>();
					bean.OnlyHouseCode = new ArrayList<String>();
				}
				bean.houseCode.add(split[0]);
				bean.OnlyHouseCode.add(split[1]);
			}
			bean.buildSize = bean.houseCode.size();
			arrayList.add(bean);
		}

		// 进行列表的存放
		ChooseHouseBeanList beanList = new ChooseHouseBeanList();
		beanList.date = arrayList;
		String json = BaseNet.getGson().toJson(beanList);
		SpUtilCurrentTaskInfo.putString(
				SpKey.getBuildInfoKey(taskMessage.TaskCode), json);
//		SpUtilCurrentTaskInfo.putInt(SpKey.getTaskHouseFinalCount(),
//				haveFinishouse);
//		if(haveFinishouse == houseCount){
//			SpUtil.putBoolean( TaskMyssageData.getInstance().getTaskMessage().TaskCode+SpKey.TASKSTATUE, true);
//		}
		return arrayList;
	}

	private void addChooseHouse(Map<String, ArrayList<String>> map, String key) {
		BigJsonManager bigJsonManager = getBigJsonNoAddMap(key);
		bigJsonManager.getTaskDetailBean().UserId = SpKey.getUerId();

		List<HouseMessage> taskList = bigJsonManager.getTaskList();
		houseCount+=taskList.size();
		for (int i = 0; i < taskList.size(); i++) {
			HouseMessage houseMessage = taskList.get(i);
			houseMessage.localIsFinish = false;
//			if (!"0".equals(houseMessage.CheckStauts)) {
//				haveFinishouse++;
//			}
			if (TextUtils.isEmpty(houseMessage.PreBuildingName)
					|| TextUtils.isEmpty(houseMessage.PreHouseName)) {
				break;
			}
			String actBuildingName = houseMessage.PreBuildingName;
			String actHouseName = houseMessage.PreUnitName + "-"
					+ houseMessage.PreHouseName + "fengexian"
					+ houseMessage.HouseCode;

			ArrayList<String> treeSet = map.get(actBuildingName);
			if (treeSet == null) {
				treeSet = new ArrayList<String>();
				map.put(actBuildingName, treeSet);
			}
			treeSet.add(actHouseName);
		}
		bigJsonManager.resetJson(BaseNet.getGson().toJson(
				bigJsonManager.getTaskDetailBean()));
	}

	public ArrayList<StatusBean> getStatus() {
		ArrayList<StatusBean> arrayList = new ArrayList<StatusBean>();
		for (int i = 0; i < 4; i++) {
			StatusBean statusBean = new StatusBean();
			arrayList.add(statusBean);
			statusBean.date = "已验收";
			statusBean.id = String.valueOf(i - 1);
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
	 *
	 * @param messages
	 *            要进行筛选的楼房列表
	 * @param Statue
	 *            楼房状态 0 未通过 1 通过
	 * @return 符合信息的楼房列表
	 */
	public List<HouseMessage> getListByStatue(List<HouseMessage> messages,
											  String Statue) {
		List<HouseMessage> houseMessagesStatue = new ArrayList<HouseMessage>();
		for (int i = 0; i < messages.size(); i++) {
			HouseMessage houseMessage = messages.get(i);
			String checkStauts = houseMessage.CheckStauts;
			if ("0".equals(checkStauts)) {
				houseMessagesStatue.add(houseMessage);
			} else if ("1".equals(checkStauts)) {
				houseMessagesStatue.add(houseMessage);
			}
		}
		return houseMessagesStatue;
	}

	/** 重新赋值不会出问题 */
	HouseMessage message;

	/**
	 * 点击楼栋信息筛选使用
	 *
	 * @param buildName
	 *            楼栋号
	 * @param Housename
	 *            房间号
	 * @return 一个房间
	 */
	public HouseMessage getHouseByBuildNameAndHouseName(
			final ChooseHouseBean bean, final int position) {
		// if(BaseNet.isTest) 为什么会出错？ 角标从一开始
		// return getHomeList(0).get(0);
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				BigJsonManager jsonManager = getBigJson(key);
				List<HouseMessage> taskList = jsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					// if(buildName.equals(
					// houseMessage.PreBuildingName)&&Housename.equals(houseMessage.PreHouseName)){
					if (bean.OnlyHouseCode.get(position).equals(
							houseMessage.HouseCode)) {
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
	 *
	 * @param Housename
	 * @return
	 */
	public List<HouseMessage> getHouseByHouseName(final String Housename) {
		final List<HouseMessage> messages = new ArrayList<HouseMessage>();
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				BigJsonManager jsonManager = getBigJson(key);
				List<HouseMessage> taskList = jsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					if (houseMessage.PreHouseFullName.contains(Housename)) {
						messages.add(houseMessage);
					}
				}
				return false;
			}
		};
		return messages;
	}

	/**
	 * @return 判断当前的任务是否已经完成 对每个房间进行循环遍历
	 */
	boolean isFinish;

	public boolean currentTaskIsFinish() {
		isFinish = true;
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				BigJsonManager bigJsonManager = getBigJsonNoAddMap(key);
				List<HouseMessage> taskList = bigJsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					if (!"2".equals(houseMessage.CheckStauts)) {
						isFinish = false;
						return true;
					}
				}
				return false;
			}
		};
		if (isFinish) {
			SpUtil.putBoolean(taskMessage.TaskCode + SpKey.TASKSTATUE, true);
		}
		return isFinish;
	}

	public TaskMessage getTaskMessage() {
		return taskMessage;
	}
}
