package com.sinooceanland.roomhelper.control.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.NetUrl;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.control.test.testTTT;
import com.sinooceanland.roomhelper.control.util.EscapeUnescape;
import com.sinooceanland.roomhelper.control.util.FileUtils;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.control.util.TasMessagetUtil;
import com.sinooceanland.roomhelper.dao.BigJsonManager;
import com.sinooceanland.roomhelper.dao.base.BaseBean;
import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;
import com.sinooceanland.roomhelper.ui.camera.util.FileUtil;

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

	private ImageCallBack imageCallBack;
	/**
	 * 进行任务列表的上传的进度回掉
	 * @param taskMessage 任务信息类
	 * @param callBack
	 */
	public void upTaskMessage(final Context context, final TaskMessage taskMessage,
			final JsonCallBack callBack,final ImageCallBack imageCallBack){
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
				if(requestType == RequestType.messagetrue){
					requestCurrentProgress++;
					if(requestCount==requestCurrentProgress){
						callBack.jsonResponse(RequestType.messagetrue, requestCount, requestCurrentProgress);
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
	public void upImage(){			
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
					requestParams.put("file", fileImage);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
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
}
