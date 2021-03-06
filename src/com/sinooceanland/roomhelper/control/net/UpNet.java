package com.sinooceanland.roomhelper.control.net;


import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.RequestParams;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.base.BaseNet.RequestType;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.bean.fuzhu.TaskListBeanFuZhu;
import com.sinooceanland.roomhelper.control.bean.fuzhu.TaskMessageFuZhu;
import com.sinooceanland.roomhelper.control.constant.NetUrl;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.control.test.testTTT;
import com.sinooceanland.roomhelper.control.util.FileUtils;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.control.util.TasMessagetUtil;
import com.sinooceanland.roomhelper.dao.BigJsonManager;
import com.sinooceanland.roomhelper.dao.base.BaseBean;
import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;

/**
 * @author peng
 * 这里是进行文件上传的接口
 */
public class UpNet extends BaseNet{
	JsonCallBack callBack;

	//请求总条数 上传json
	int requestCount = 0;
	//当前进度 上传json
	int requestCurrentProgress;
	
	//请求总条数 上传image
	int imageCount = 0;
	//当前进度 上传image
	int imageProgress;

	private Context context;

	private TaskMessage taskMessage;

	private  ImageCallBack imageCallBack;
	/**
	 * 进行任务列表的上传的进度回掉
	 * @param taskMessage 任务信息类
	 * @param callBack
	 */
	public void upTaskMessage(final Context context, final TaskMessage taskMessage,
			final JsonCallBack callBack,final ImageCallBack imageCallBack){
		
		
		if(SpUtil.mContext==null){
			SpUtil.init(context);
		}
		if(SpUtilCurrentTaskInfo.mContext==null){
			SpUtilCurrentTaskInfo.init(context);
		}

		
		
		//进行上传任务的记录
		{
			if(taskMessage!=null){
//				String doList = SpUtil.getString(SpKey.CURRENTDOUPTASKList, "");
				SpUtil.putString(SpKey.UPNETMYSSAGEDATA, BaseNet.getGson().toJson(taskMessage));
			}
			
			//如果任务正在上传直接返回 
			if(SpKey.getEndMessageUping(taskMessage.TaskCode)){
				return;
			}
		}
		SpKey.putEndMessageUpTime(taskMessage.TaskCode);
//		{
//			//1.得到当前正在上传的图片文件夹
//			String doList = SpUtil.getString(SpKey.CURRENTDOUPTASKList, null);
//				//进行集合装换
//			Type type = new TypeToken<ArrayList<TaskMessage>>() {}.getType();  
//			ArrayList<TaskMessage> messages=getGson().fromJson(doList, type); 
//			if(messages == null){
//				messages = new ArrayList<TaskMessage>();
//			}
//			//集合中是否已经包含着这个文件
//			boolean alreadHave = false;
//			f:for (int i = 0; i < messages.size(); i++) {
//				if(messages.get(i).TaskCode.equals(taskMessage.TaskCode)){
//					alreadHave = true;
//					break f;
//				}
//			}
//			//没有包含储存
//			if(!alreadHave){
//				messages.add(taskMessage);
//				SpUtil.putString(SpKey.CURRENTDOUPTASKList, BaseNet.getGson().toJson(messages));
//			}
//		}
		
		
		
		this.context = context;
		this.taskMessage = taskMessage;
		this.imageCallBack = imageCallBack;
		requestCount=0;
		requestCurrentProgress = 0;
		this.callBack = callBack;
		upImage();
//		upLoadAllJson();
	}
	
	private void upLoadAllJson(){
		new TasMessagetUtil(taskMessage){
			@Override
			public boolean forKey(String key) {
				requestCount++;
				BigJsonManager bigJsonManager = BaseJsonManager.findManagerByKey(context, key, BigJsonManager.class);			
				//TODO:只让利强写一个只根据键存值的方法
				TaskDetailBean taskDetailBean = bigJsonManager.getTaskDetailBean();
				String json = getGson().toJson(taskDetailBean);
//				 json = EscapeUnescape.escape(json);
				 testTTT.saveFile(json);
				upLoadJson(context, json);
				return false;
			}
			
		};
		
	}
	
	
	
	
	
	
	
	
	
	private void upLoadJson(Context context,String json){
		//data
		RequestParams requestParams = new RequestParams();
		requestParams.add("data", json);
		baseRequest(requestParams, NetUrl.UP_PROTECT, new BaseCallBack<BaseBean>() {

			@Override
			public void messageResponse(RequestType requestType, BaseBean bean,
					String message) {
				SpKey.putEndMessageUpTime(taskMessage.TaskCode);
				if(requestType == RequestType.messagetrue){
					requestCurrentProgress++;
					if(requestCount==requestCurrentProgress){
						callBack.jsonResponse(RequestType.messagetrue, requestCount, requestCurrentProgress);
						{
							TaskListBeanFuZhu	beanFuZhu = BaseNet.getGson().fromJson(SpKey.getTaskList(), TaskListBeanFuZhu.class);
							m:if(beanFuZhu==null||beanFuZhu.list==null||beanFuZhu.list.size()<=0){
							}else{
								List<TaskMessageFuZhu> fuZhus = beanFuZhu.list;
								for (int u = 0; u < fuZhus.size(); u++) {
									TaskMessageFuZhu messageFuZhu = fuZhus.get(u);
									String taskCode = messageFuZhu.TaskCode;
									if(taskCode.endsWith(taskMessage.TaskCode)){
										fuZhus.remove(messageFuZhu);
										SpUtil.putString(SpKey.getUerId(), BaseNet.getGson().toJson(beanFuZhu));
									}
									break m;
								}
							}
							
						}
						SpUtilCurrentTaskInfo.clear();
						TaskMyssageData.getInstance().clearJson();
						SpUtil.putBoolean(taskMessage.TaskCode, false);
						SpUtil.putBoolean(taskMessage.TaskCode + SpKey.TASKSTATUE, false);
					}
					callBack.jsonResponse(RequestType.loading, requestCount, requestCurrentProgress);
				}else{
					callBack.jsonResponse(requestType, requestCount, requestCurrentProgress);
				}
			}
		}, BaseBean.class);
	}
	
	public int imageOldCount;
	private int imageAddCount;
	/**
	 * 进行本个任务图片的上传
	 */
	public void upImageOld(){			
		imageCount = 0;
		imageProgress = 0;
		//得到小图片的文件夹路径
		String smallPictureAddress = SpKey.getSmallPictureAddress();
		//得到当前所在文件夹
		final File file = new File(smallPictureAddress);
		File[] listFiles = null;
		if(file.isDirectory()){
			listFiles = file.listFiles();
		}
		if(listFiles!=null&&listFiles.length>0){
			imageCount = listFiles.length;
			imageOldCount = SpUtilCurrentTaskInfo.getInt("imageOldCount", 0);
			if(imageOldCount<imageCount)
			SpUtilCurrentTaskInfo.putInt("imageOldCount", imageCount);
			imageAddCount = SpUtilCurrentTaskInfo.getInt("imageOldCount", imageCount)-imageCount;			
			for (int i = 0; i < listFiles.length; i++) {
				//这里是单个图片的上传
				final File fileImage = listFiles[i];
				RequestParams requestParams = new RequestParams();
				try {
					requestParams.put("TaskCode", SpKey.getCurrentTaskMessage());
					requestParams.put("file", fileImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Log.e("requestimageNum", ""+listFiles.length+"===="+i);
				baseRequest(requestParams, NetUrl.PICTURE_UP, new BaseCallBack<BaseBean>() {
					@Override
					public void messageResponse(RequestType requestType,
							BaseBean bean, String message) {
						if(requestType==RequestType.messagetrue){
							imageProgress++;
							try {
								fileImage.deleteOnExit();
								FileUtils.deletefile(fileImage.getAbsolutePath());
							}catch (Exception e){
								
							}
							Log.e("imageNum",imageCount+"---"+imageProgress+"-------"+imageAddCount);
							if(imageCount == imageProgress){
								//上传完成 进行文件删除
								try {
									FileUtils.deletefile(SpKey.getBigPictureAddress());
									FileUtils.deletefile(SpKey.getProblemPictureAddress());
									FileUtils.deletefile(file.getAbsolutePath());
								} catch (Exception e) {
									e.printStackTrace();
								}
								imageCallBack.imageResponse(RequestType.messagetrue, imageCount+imageAddCount, imageAddCount+imageProgress);
								//图片上传完成进行json的上传
								upLoadAllJson();
							}
							imageCallBack.imageResponse(RequestType.loading, imageCount+imageAddCount, imageProgress+imageAddCount);
						}else{
							imageCallBack.imageResponse(requestType, imageCount+imageAddCount, imageProgress+imageAddCount);
						}
					}
				}, BaseBean.class);
			}
		}else{
			//图片上传完成进行json的上传
			//如果用户把  图片删了怎么办?????
			upLoadAllJson();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//========================================================================
	private int  responsedezongshu = 0;
	
	//是否已经请求失败了？
	private boolean haveRequestFalsean = false;
	
	/**
	 * 进行本个任务图片的上传
	 */
	public void upImage(){			
		imageCount = 0;
		imageProgress = 0;
		responsedezongshu = 0;
		haveRequestFalsean = false;
		//得到小图片的文件夹路径
		String smallPictureAddress = SpKey.getBigPictureAddress();
		//得到当前所在文件夹
		final File file = new File(smallPictureAddress);
		File[] listFiles = null;
		if(file.isDirectory()){
			listFiles = file.listFiles();
		}
		if(listFiles!=null&&listFiles.length>0){
			imageCount = listFiles.length;
			imageOldCount = SpUtilCurrentTaskInfo.getInt("imageOldCount", 0);
			if(imageOldCount<imageCount)
			SpUtilCurrentTaskInfo.putInt("imageOldCount", imageCount);
			imageAddCount = SpUtilCurrentTaskInfo.getInt("imageOldCount", imageCount)-imageCount;			
			final int requestzongshu = listFiles.length>5?5:listFiles.length;
			for (int i = 0; i < requestzongshu; i++) {
				//这里是单个图片的上传
				final File fileImage = listFiles[i];
				RequestParams requestParams = new RequestParams();
				try {
					requestParams.put("TaskCode", SpKey.getCurrentTaskMessage());
					requestParams.put("file", fileImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				Log.e("requestimageNum", ""+listFiles.length+"===="+i);
				SpKey.putEndMessageUpTime(taskMessage.TaskCode);
				baseRequest(requestParams, NetUrl.PICTURE_UP, new BaseCallBack<BaseBean>() {
					@Override
					public void messageResponse(RequestType requestType,
							BaseBean bean, String message) {
						
						SpKey.putEndMessageUpTime(taskMessage.TaskCode);
						if(requestType==RequestType.messagetrue){
							imageProgress++;
							try {
								FileUtils.deletefile(fileImage.getAbsolutePath());
								fileImage.deleteOnExit();
							}catch (Exception e){
								
							}
							Log.e("imageNum",imageCount+"---"+imageProgress+"-------"+imageAddCount);
							if(imageCount == imageProgress){
								//上传完成 进行文件删除
								try {
									FileUtils.deletefile(file.getAbsolutePath());
									FileUtils.deletefile(SpKey.getProblemPictureAddress());
									FileUtils.deletefile(SpKey.getSmallPictureAddress());
								} catch (Exception e) {
									e.printStackTrace();
								}
								imageCallBack.imageResponse(RequestType.messagetrue, imageCount+imageAddCount, imageAddCount+imageProgress);
								//图片上传完成进行json的上传
								upLoadAllJson();
								return;
							}
							imageCallBack.imageResponse(RequestType.loading, imageCount+imageAddCount, imageProgress+imageAddCount);
						}else{
							if(!haveRequestFalsean){
								imageCallBack.imageResponse(requestType, imageCount+imageAddCount, imageProgress+imageAddCount);
								upImage();
							}
							haveRequestFalsean = true;
							return;//请求失败或错误以后  不应该在继续进行请求
						}
						
						//不管断没断网  请求数据请求数据达到数目进行下一轮请求
						responsedezongshu++;
						if(responsedezongshu == requestzongshu){
							upImage();
						}
						
					}
				}, BaseBean.class);
			}
		}else{
			//图片上传完成进行json的上传
			//如果用户把  图片删了怎么办?????
			upLoadAllJson();
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 有serivce调用判断是否有正在上传中的任务 进行上传
	 * 目前限定为只有一个任务
	 */
	public static void startUpMessage(Context context){
		//1.得到当前正在上传的图片文件夹
//		SpUtil.init(context);
//		SpUtilCurrentTaskInfo.init(context);
		String doList = SpUtil.getString(SpKey.UPNETMYSSAGEDATA, "");
		
		if(TextUtils.isEmpty(doList)){
			return;
		}
//		if(TextUtils.isEmpty(doList)){
//			return;
//		}
//			进行集合装换
//		Type type = new TypeToken<ArrayList<TaskMessage>>() {}.getType();  
//		  
//		ArrayList<TaskMessage> messages=getGson().fromJson(doList, type); 
//		if(messages==null||messages.size()<=0){
//			return;
//		}						
		//2.取集合第一个数据进行非正在上传判断  		//第一正在上传状态  第二 没有正在上传
		TaskMessage taskMessage2 = getGson().fromJson(doList, TaskMessage.class);
		if(taskMessage2==null){
				return;
		}
		//如果任务正在上传直接返回 
		if(SpKey.getEndMessageUping(taskMessage2.TaskCode)){
			return;
		}
		
		//如果任务  已经完成 状态  返回  是任务未完成或者已经上传状态
		if(!SpUtil.getBoolean(taskMessage2.TaskCode, false)){
			return;
		}
		
		//3.进行上传
		new UpNet().upTaskMessage(context, taskMessage2, new JsonCallBack() {
			
			@Override
			public void jsonResponse(RequestType requestType, int count, int current) {
//				if(callBack!=null){
//					callBack.jsonResponse(requestType, count, current);
//				}
			}
		}, new ImageCallBack() {
			@Override
			public void imageResponse(RequestType requestType, int count, int current) {
//				if(imageCallBack!=null){
//					imageCallBack.imageResponse(requestType, count, current);
//				}
			}
		});
		
		
	}
	
	
	
	
	
	
}
