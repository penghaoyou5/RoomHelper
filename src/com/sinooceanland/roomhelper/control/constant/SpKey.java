package com.sinooceanland.roomhelper.control.constant;

import java.io.File;

import android.os.Environment;

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
		return getUerId();
	}
	/**工程问题*/
	public static final String PROJECTPROBLEM = "rojectProblem";
	
	/**工程问题*/
	public static final String TASKSTATUE = "taskStatue";
	
	/**当前进入的某个工程*/
	public static final String CURRENTTASKMESSAGE = "currentTaskMessage";
	public static final String getCurrentTaskMessage(){
		return SpUtil.getString(CURRENTTASKMESSAGE, "");
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
		if(SDUtils.isSDCardEnable()){
			return SDUtils.getSDCardPath()+getUerId()+File.separator+getCurrentTaskMessage()+File.separator+load+File.separator;
		}
		return null;
	}
	
	/**
	 * @return 未压缩图片路径
	 */
	public static final String getBigPictureAddress(){
		return getPictureAddress("big");
	}
	/**
	 * @return 已压缩图片路径
	 */
	public static final String getSmallPictureAddress(){
		return getPictureAddress("smal");
	}
	
	/**
	 * @return 验收有问题路径
	 */
	public static final String getProblemPictureAddress(){
		return getPictureAddress("problem");
	}
//	private static final String TASKDITAIL = "TaskDetail";
//	public static final String getTaskDetail(){
//		return null;
//	}
}
