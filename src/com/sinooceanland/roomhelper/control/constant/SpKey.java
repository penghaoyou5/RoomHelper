package com.sinooceanland.roomhelper.control.constant;

import java.io.File;

import android.os.Environment;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.ui.utils.SDUtils;
import com.sinooceanland.roomhelper.ui.utils.SpUtils;

public class SpKey {
	
	
	/**当前登陆用户名*/
	public static final String USERINAME = "username";
	/**根据用户名得到用户id*/
	public static final String getUerId(){
		return SpUtil.getString(SpUtil.getString(USERINAME, ""), "");
	}
	/**根据用户id任务列表 */
	public static final String getTaskList(){
		return   SpUtil.getString(getUerId(), "") ;
	}
	/**工程问题*/
	public static final String PROJECTPROBLEM = "rojectProblem";

	/**工程问题*/
	public static final String TASKSTATUE = "taskStatue";

	/**当前进入的某个工程  这是用当前的任务名taskcode 进行区分的*/
	public static final String CURRENTTASKMESSAGE = "currentTaskMessage";
	public static final String getCurrentTaskMessage(){
		return SpUtil.getString(CURRENTTASKMESSAGE, "");
	}

	/**
	 * @return  当前任务已经完成的房间数
	 * 可能回传回来已经有已完成房间
	 */
	public static final String getTaskHouseFinalCount(){
		return SpUtil.getString(CURRENTTASKMESSAGE, "")+"houseFinalCount";
	}

	/**
	 * @return  当前任务的房间总数
	 */
	public static final String getTaskHouseCount(){
		return SpUtil.getString(CURRENTTASKMESSAGE, "")+"houseCount";
	}

	/**当前进入的某个工程*/
	public static final String BUILDINFO = "BuildInfo";
	public static final String getBuildInfoKey(String taskCode){
		return taskCode + BUILDINFO;
	}

	/**
	 * 大图片文件夹路径
	 * @param load
	 * @return
	 */
	private static final String getPictureAddress(String load){
		StringBuilder sb = new StringBuilder();
		if(SDUtils.isSDCardEnable()){
			sb.append(SDUtils.getSDCardPath());
			sb.append("RoomHelper");
			sb.append(File.separator);
			sb.append(getUerId());
			sb.append(File.separator);
			sb.append(getCurrentTaskMessage());
			sb.append(File.separator);
			sb.append(load);
			sb.append(File.separator);
			return sb.toString();
		}
		return null;
	}

	/**
	 * @return 未压缩图片路径
	 */
	public static final String getBigPictureAddress(){
		String fileName = getPictureAddress("big");
		File file = new File(fileName);
		if(!file.exists()){
			boolean mkdirs = file.mkdirs();
		}
		return file.getAbsolutePath()+File.separator;
	}
	/**
	 * @return 已压缩图片路径
	 */
	public static final String getSmallPictureAddress(){
		String fileName = getPictureAddress("small");
		File file = new File(fileName);
		if(!file.exists()){
			boolean mkdirs = file.mkdirs();
		}
		return file.getAbsolutePath()+File.separator;
	}

	/**
	 * @return 验收有问题路径
	 */
	public static final String getProblemPictureAddress(){
		String fileName = getPictureAddress("problem");
		if(BaseNet.isTest){
			fileName = getPictureAddress("small");
		}
		File file = new File(fileName);
		if(!file.exists()){
			boolean mkdirs = file.mkdirs();
		}
//		return file.getAbsolutePath()+File.separator;
		return fileName;
	}

	
	
	
	
	
	
	/**
	 * 获得当前的TaskMyssageData的taskMessage  这是保证上传任务界面不崩溃
	 */
	public static final String CURRENTTASKMYSSAGEDATA = "currentTaskMyssageData";
	
	
	
	/**
	 * 当前已完成处于上传状态的任务集合
	 */
	public static final String CURRENTDOUPTASKList = "currentDoUPTaskMap";
	
	/**
	 * 货物当前任务正在上传的时间
	 */
	public static final String ENDMESSAGEDOUPING = "endmessageDoUPIng";
	
	//获取上传任务的事件
	public static final void putEndMessageUpTime(String taskCode){
		SpUtil.putLong(taskCode+ENDMESSAGEDOUPING, System.currentTimeMillis());
	}
	
	/**
	 * 判断某个任务是否正在上传
	 * @param taskCode
	 * @return
	 */
	public static final boolean getEndMessageUping(String taskCode){
		float tim = SpUtil.getLong(taskCode+ENDMESSAGEDOUPING, 0);
		float cha =  System.currentTimeMillis() - tim;
		//大于一定时间间隔 没有上传   调用方法进行上传
		if(tim!=0&&cha<30000){
			return true;
		}
		return false;
	}
	
	
	
	
	/**
	 * 获得当前的upnet的taskMessage  这是保证上传任务界面不崩溃
	 */
	public static final String UPNETMYSSAGEDATA = "currentupentData";
	
	
	
//	private static final String TASKDITAIL = "TaskDetail";
//	public static final String getTaskDetail(){
//		return null;
//	}
}
