package com.sinooceanland.roomhelper.control.net;

import java.util.List;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.base.BaseNet.BaseCallBack;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.bean.TaskListBean;
import com.sinooceanland.roomhelper.control.bean.TaskListBean.TaskMessage;
import com.sinooceanland.roomhelper.control.bean.TaskListBean.TaskMessage.BuildingList;
import com.sinooceanland.roomhelper.control.constant.NetUrl;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.taskdata.TaskList;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.TasMessagetUtil;
import com.sinooceanland.roomhelper.dao.BigJsonManager;
import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.ProjectProblemBean;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.dao.module.TaskDetailBean;

/**
 * @author peng 主要进行网络请求与文件下载的类
 */
public class RequestNet extends BaseNet {
	
	

	private Context context;

	public RequestNet(Context context){
		this.context = context;
	}
	/**
	 * 进行登陆的方法
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param callBack
	 *            请求回掉
	 */
	public void login(final String username, String password,
			final BaseCallBack<LoginBean> callBack) {
		RequestParams requestParams = new RequestParams();
		requestParams.add("username", username);
		requestParams.add("password", password);
		baseRequest(requestParams, NetUrl.LOGIN, new BaseCallBack<LoginBean>() {

			@Override
			public void messageResponse(RequestType requestType,
					LoginBean bean, String message) {
				if (requestType == RequestType.messagetrue) {
					// 如果登陆成功保存现有的用户名
					SpUtil.putString(SpKey.USERINAME, username);
					// 根据用户名保存用户id
					SpUtil.putString(username, bean.userID);
				}
				callBack.messageResponse(requestType, bean, message);
			}
		}, LoginBean.class);
	}

	/**
	 * 获取任务列表 并进行存储
	 * 
	 * @param callBack
	 *            请求回掉 获得TaskList
	 */
	public void taskList(final BaseCallBack<TaskList> callBack) {
		
		RequestParams requestParams = new RequestParams();
		requestParams.add("userid", SpUtil.getString(SpKey.getUerId(), ""));
		baseRequest(requestParams, NetUrl.TASK_LIST,
				new BaseCallBack<TaskListBean>() {

					@Override
					public void messageResponse(RequestType requestType,
							TaskListBean bean, String message) {
						if (requestType == RequestType.messagetrue) {
							// 使用用户id存储任务列表
							SpUtil.putString(
									SpUtil.getString(SpKey.getUerId(), ""),
									message);
						}
						callBack.messageResponse(requestType, new TaskList(),
								message);
					}
				}, TaskListBean.class);
	}

	/**
	 * 获取模板明细
	 * 
	 * @param PreCheckArrangeDateCode
	 * @param BuildingCode
	 * @param UnitCode
	 * @param callBack
	 */
	public void getTaskDetail(String PreCheckArrangeDateCode,
			String BuildingCode, String UnitCode, BaseCallBack<String> callBack) {
		RequestParams requestParams = new RequestParams();
		requestParams.add("PreCheckArrangeDateCode", PreCheckArrangeDateCode);
		requestParams.add("BuildingCode", BuildingCode);
		requestParams.add("UnitCode", UnitCode);
		baseStringRequest(requestParams, NetUrl.TASK_DETAIL, callBack);
	}

	int requestCount;

	/**
	 * 获取模板明细 并进行存根据任务信息进行任务的下载
	 * 
	 * @param taskMessage
	 *            任务消息le
	 * @param callBack
	 *            请求回掉 若全部成功则成功 有一个失败则进行失败回掉
	 */
	int ii;
	public void downTask(final Context context, final TaskMessage taskMessage,
			final BaseCallBack<String> callBack,final ImageCallBack imageCallBack) {
		if(isTest){
			for (int i = 0; i < 100; i++) {
				requestCount++;
				getTaskDetail("", "",
						"", new BaseCallBack<String>() {
							@Override
							public void messageResponse(
									RequestType requestType, String bean,
									String message) {
								if (requestType == RequestType.messagetrue) {
									requestCount--;
									ii++;
									
									new BigJsonManager(context,
											"key"+ii+System.currentTimeMillis(), bean);
//									BaseNet.getGson().fromJson(bean, TestClassttt.class);
									if (requestCount <= 0) {
//										SpUtil.putBoolean(taskMessage.TaskCode, true);
										callBack.messageResponse(requestType,
												"下载成功", "下载成功");
										//开始进行图片下载
//										downLoadImage(taskMessage,imageCallBack);
									}
								} else {
									callBack.messageResponse(requestType, bean,
											message);
								}
							}

						});
				
				
			}
			
			return;
		}
		
		
		
		requestCount = 0;
		for (int i = 0; i < taskMessage.buildingList.size(); i++) {
			final BuildingList buildingList = taskMessage.buildingList.get(i);
			for (int j = 0; j < buildingList.UnitCode.size(); j++) {
				requestCount++;
				final String UnitCode = buildingList.UnitCode.get(j);
				getTaskDetail(taskMessage.TaskCode, buildingList.BuildingCode,
						UnitCode, new BaseCallBack<String>() {
							@Override
							public void messageResponse(
									RequestType requestType, String bean,
									String message) {
								if (requestType == RequestType.messagetrue) {
									requestCount--;
									new BigJsonManager(context,
											taskMessage.TaskCode + "+"
													+ buildingList.BuildingCode
													+ "+" + UnitCode, bean);
									if (requestCount <= 0) {
										SpUtil.putBoolean(taskMessage.TaskCode, true);
										callBack.messageResponse(requestType,
												"下载成功", "下载成功");
										//开始进行图片下载
//										downLoadImage(taskMessage,imageCallBack);
									}
								} else {
									callBack.messageResponse(requestType, bean,
											message);
								}
							}

						});
			}
		}
	}

	/**
	 * 进行网络请求 获取工程问题
	 * 
	 * @param callBack
	 *            请求回掉
	 */
	public void getprojectProblemByNet(final BaseCallBack<ProjectProblemBean> callBack) {
		baseStringRequest(new RequestParams(), NetUrl.PROJECT_PROBLEM,
				new BaseCallBack<String>() {

					@Override
					public void messageResponse(RequestType requestType,
							String bean, String message) {
						ProjectProblemBean problemBean = null;
						if (requestType == RequestType.messagetrue) {
							SpUtil.putString(SpKey.PROJECTPROBLEM, bean);
							problemBean = getGson().fromJson(bean, ProjectProblemBean.class);
						}
						callBack.messageResponse(requestType, problemBean, message);
					}
				});
	}
	int imagecurrent ;
	int imagecount ;
	/**
	 * 进行图片的下载
	 * @param taskMessage 
	 * @param imageCallBack
	 */
	public void  downLoadImage(TaskMessage taskMessage, final ImageCallBack imageCallBack){
		 new TasMessagetUtil(taskMessage) {
			
			@Override
			public boolean forKey(String key) {
				BigJsonManager bigJsonManager = BaseJsonManager.findManagerByKey(context, key,BigJsonManager.class);
				List<HouseMessage> taskList = bigJsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					List<LastCheckProblemList> lastCheckProblemList = houseMessage.getLastCheckProblemList();
					for (int j = 0; j < lastCheckProblemList.size(); j++) {
						List<String> attachmentIDS = lastCheckProblemList.get(j).AttachmentIDS;
						for (int k = 0; k < attachmentIDS.size(); k++) {
							imagecount++;
							downOneImage(attachmentIDS.get(k),new BaseCallBack<String>() {

								@Override
								public void messageResponse(
										RequestType requestType, String bean,
										String message) {
									if(requestType==RequestType.messagetrue){
										imagecurrent++;
										imageCallBack.imageResponse(RequestType.loading, imagecount, imagecurrent);
										if(imagecurrent<=0){
											imageCallBack.imageResponse(RequestType.messagetrue, imagecount, imagecurrent);
										}
									}
								}
							});
							
						}
						
					}
				}
				return false;
			}
		};
	}
	
	
	/**
	 * 获得图片文件
	 */
	public void downOneImage(String id,BaseCallBack<String> callBack) {
		baseDownImage(id, NetUrl.PICTURE_DOWN, callBack);
	}
}
