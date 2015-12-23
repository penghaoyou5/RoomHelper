package com.sinooceanland.roomhelper.dao;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;

import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList.EnginTypeList;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList;
import com.sinooceanland.roomhelper.dao.utils.RoomHelperDaoUtil;

/**
 * 该类是主要操作大Json的管理类，继承自BaseJsonManager
 * 主要作用是负责管理项目中的大Json数据，封装基本的增删改查
 * @author Administrator
 */
public class BigJsonManager extends BaseJsonManager {
	protected String JSONKEY = "Big-Json";
	public TaskDetailBean taskDetailBean;
	public static final String SPACE_LAYOUT = "SpaceLayoutList";
	public static final String LAST_CHECK_PROBLEM = "LastCheckProblemList";
	public static final String ENGIN_TYPE = "EnginTypeList";
	public static final String PROBLEM_DESCRIPTION = "ProblemDescriptionList";
	public BigJsonManager(Context context, String json){
		super(context, json);
	}
	
	public BigJsonManager(Context context, String key, String json){
		super(context,key,json);
	}
	
	public TaskDetailBean getTaskDetailBean(){
		taskDetailBean = jsonToBean(mJson, TaskDetailBean.class);
		return taskDetailBean;
	}
	
	public List<HouseMessage> getTaskList(){
		List<HouseMessage> list = null;
		if(taskDetailBean!=null){
			list = taskDetailBean.getList();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getGoalList(Class<T> clazz, int ...indexs){
		List<HouseMessage> taskList = getTaskList();
		int length = indexs.length;
		String className = clazz.getName();
		if(taskList!=null && taskList.size()>0){
			if(SPACE_LAYOUT.equalsIgnoreCase(className) && length>0){
				return (List<T>)taskList.get(indexs[0]).getSpaceLayoutList();
			}else if(LAST_CHECK_PROBLEM.equalsIgnoreCase(className) && length>0){
				return (List<T>)taskList.get(indexs[0]).getLastCheckProblemList();
			}else if(ENGIN_TYPE.equalsIgnoreCase(className) && length>1){
				return (List<T>)taskList.get(indexs[0]).getSpaceLayoutList().get(indexs[1]).getEnginTypeList();
			}else if(PROBLEM_DESCRIPTION.equalsIgnoreCase(className) && length>2){
				return (List<T>)taskList.get(indexs[0]).getSpaceLayoutList().get(indexs[1]).getEnginTypeList().get(indexs[2]).getProblemDescriptionList();
			}
		}
		return null;
	}
	
	public <T> List<String> getAttachmentIDS(Class<T> clazz, int ...indexs){
		List<HouseMessage> taskList = getTaskList();
		int length = indexs.length;
		String className = clazz.getName();
		List<T> goalList = getGoalList(clazz,indexs);
		if(taskList!=null && taskList.size()>0){
			if(SPACE_LAYOUT.equalsIgnoreCase(className) && length>1){
				return ((SpaceLayoutList)goalList.get(indexs[1])).getAttachmentIDS();
			}else if(LAST_CHECK_PROBLEM.equalsIgnoreCase(className) && length>1){
				return ((LastCheckProblemList)goalList.get(indexs[1])).getAttachmentIDS();
			}else if(ENGIN_TYPE.equalsIgnoreCase(className) && length>2){
				return ((EnginTypeList)goalList.get(indexs[2])).getAttachmentIDS();
			}
		}
		return null;
	}
	
	public <T> void modifyData(String variableNamem, String value, int position, Class<T> clazz, int ...indexs){
		List<T> goalList = getGoalList(clazz, indexs);
		if(goalList!=null && goalList.size()>position){
			String methodName = RoomHelperDaoUtil.fromatMethodName(variableNamem);
			T t = goalList.get(position);
			try {
				Method method = clazz.getMethod(methodName, String.class);
				method.invoke(t, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
