package com.sinooceanland.roomhelper.dao;

import java.lang.reflect.Method;
import java.util.List;

import android.content.Context;

import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList.EnginTypeList;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;
import com.sinooceanland.roomhelper.dao.utils.RoomHelperDaoUtil;

/**
 * 该类是主要操作大Json的管理类，继承自BaseJsonManager
 * 主要作用是负责管理项目中的大Json数据，封装基本的增删改查
 * @author Administrator
 */
public class BigJsonManager extends BaseJsonManager {
	public static final String SPACE_LAYOUT = "SpaceLayoutList";
	public static final String LAST_CHECK_PROBLEM = "LastCheckProblemList";
	public static final String ENGIN_TYPE = "EnginTypeList";
	public static final String PROBLEM_DESCRIPTION = "ProblemDescriptionList";
	protected String JSONKEY = "Big-Json";
	private TaskDetailBean taskDetailBean;
	public BigJsonManager(Context context, String json){
		super(context, json);
		taskDetailBean = jsonToBean(obtainJson(), TaskDetailBean.class);
	}
	
	public BigJsonManager(Context context, String key, String json){
		super(context,key,json);
		taskDetailBean = jsonToBean(obtainJson(), TaskDetailBean.class);
	}
	
	//将解析的大json的Bean返回
	public TaskDetailBean getTaskDetailBean(){
		return this.taskDetailBean;
	}
	
	//获取任务列表集合
	public List<HouseMessage> getTaskList(){
		List<HouseMessage> list = null;
		if(taskDetailBean!=null){
			list = taskDetailBean.getList();
		}
		return list;
	}
	
	/**
	 * 通过界面position获取指定的目标集合
	 * @param clazz 目标集合中对象的class字节码（比如：想获取SpaceLayoutList集合，传递SpaceLayoutList.class即可）
	 * @param indexs 可变参数，指目标集合的上级对象所在它的集合中位置position
	 * @return
	 * 
	 * 补充：该方法为统一管理方法，调用该方法可获取到大Json中的任何一个集合
	 */
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
	
	/**
	 * 专门获取AttachmentIDS得方法，使用方法和getGoalList()类似
	 * @param clazz
	 * @param indexs
	 * @return
	 */
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
	
	/**
	 * 通过字段的值来点位对象在集合中的位置，即通过某个字段值找到对应的对象
	 * @param list 要查找的集合
	 * @param variableName 提供的字段的名字
	 * @param value 提供的字段的值
	 * @return
	 */
	public <T> T findDataFromList(List<T> list, String variableName, String value){
		T t = null;
		String methodName = RoomHelperDaoUtil.fromatGetMethodName(variableName);
		try{
			Method method = t.getClass().getMethod(methodName);
			for(T temp : list){
				String str = (String)method.invoke(temp);
				if(value.equals(str)){
					t = temp;
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return t;
	}
	
	/**
	 * 该方法为统一管理方法，调用该方法可修改任意一个对象的任意一个字段值
	 * @param variableName 要修改的变量的名称
	 * @param value 要修改的值
	 * @param position 要修改的对象在集合中的位置
	 * @param clazz 要修改的对象的class字节码
	 * @param indexs 可变参数，界面上对象对应的位置
	 */
	public <T> void modifyData(String variableName, String value, int position, Class<T> clazz, int ...indexs){
		List<T> goalList = getGoalList(clazz, indexs);
		if(goalList!=null && goalList.size()>position){
			String methodName = RoomHelperDaoUtil.fromatSetMethodName(variableName);
			T t = goalList.get(position);
			try {
				Method method = clazz.getMethod(methodName, String.class);
				method.invoke(t, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 修改对象的字段的值，调用该方法可修改任意对象的任意字段值
	 * @param m 要修改的对象
	 * @param variableName 要修改的变量的名称
	 * @param n 要修改的值
	 */
	public <M,N> void modifyData(M m, String variableName, N n){
		String methodName = RoomHelperDaoUtil.fromatSetMethodName(variableName);
		try{
			Method method = m.getClass().getMethod(methodName, n.getClass());
			method.invoke(m, n);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
