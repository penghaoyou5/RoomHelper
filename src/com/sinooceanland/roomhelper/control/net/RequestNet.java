package com.sinooceanland.roomhelper.control.net;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.BuildingList;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.bean.TaskListBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.constant.NetUrl;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.taskdata.TaskList;
import com.sinooceanland.roomhelper.control.util.GetAssertUtil;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.TasMessagetUtil;
import com.sinooceanland.roomhelper.dao.BigJsonManager;
import com.sinooceanland.roomhelper.dao.base.BaseJsonManager;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.ui.weiget.tree.TreeDataBean;

/**
 * @author peng 主要进行网络请求与文件下载的类
 */
/**
 * @author peng
 * 
 */
public class RequestNet extends BaseNet {

	private Context context;
	private static final int ProCount = 5;

	public RequestNet(Context context) {
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
					Log.e("gggg", message);
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
		requestParams.add("userid", SpKey.getUerId());
		baseRequest(requestParams, NetUrl.TASK_LIST,
				new BaseCallBack<TaskListBean>() {

					@Override
					public void messageResponse(RequestType requestType,
							TaskListBean bean, String message) {
						if (requestType == RequestType.messagetrue) {
							// 使用用户id存储任务列表
							SpUtil.putString(SpKey.getUerId(), message);
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
	int responceCount;

	/**
	 * 是否已经进行过回调
	 */
	private boolean haveCallBack = false;

	/**
	 * 获取模板明细 并进行存根据任务信息进行任务的下载
	 * 
	 * @param taskMessage
	 *            任务消息le
	 * @param callBack
	 *            请求回掉 若全部成功则成功 有一个失败则进行失败回掉
	 */
	public void downTask(final Context context, final TaskMessage taskMessage,
			final BaseCallBack<String> callBack,
			final ImageCallBack imageCallBack) {
		SpUtil.putString(SpKey.CURRENTTASKMESSAGE, taskMessage.TaskCode);
		haveCallBack = false;
		// 这是请求正在请求中的次数
		requestCount = 0;
		responceCount = 0;
		for (int i = 0; i < taskMessage.BuildingList.size(); i++) {
			final BuildingList buildingList = taskMessage.BuildingList.get(i);
			for (int j = 0; j < buildingList.UnitCode.size(); j++) {
				requestCount++;
				System.out.println("requestCount" + requestCount);
				// if(requestCount>=1)return;
				final String UnitCode = buildingList.UnitCode.get(j);
				getTaskDetail(taskMessage.TaskCode, buildingList.BuildingCode,
						UnitCode, new BaseCallBack<String>() {
							@Override
							public void messageResponse(
									RequestType requestType, String bean,
									String message) {
								System.out.println("bean" + bean);
								if (requestType == RequestType.messagetrue) {
									responceCount++;
									String key = taskMessage.TaskCode + "+"
											+ buildingList.BuildingCode + "+"
											+ UnitCode;
									BigJsonManager bigJsonManager = new BigJsonManager(
											context, key, bean);

									// TODO:只让利强写一个只根据键存值的方法
									List<HouseMessage> taskList = bigJsonManager
											.getTaskList();
									BigJsonManager findManagerByKey = BaseJsonManager
											.findManagerByKey(context, key,
													BigJsonManager.class);
									List<HouseMessage> taskList2 = findManagerByKey
											.getTaskList();
									System.out.println("完成测试");

									if (requestCount == responceCount) {
										//数据json下载已完成
										SpUtil.putBoolean(taskMessage.TaskCode,
												true);
										// callBack.messageResponse(requestType,
										// "下载成功", "下载成功");
										System.gc();
										// TODO:开始进行图片下载 这是以后做的
										downLoadImage(taskMessage,
												imageCallBack, callBack);
									}
								} else {
									//控制只有一次失败的回掉
									if (!haveCallBack) {
										callBack.messageResponse(requestType,
												bean, message);
										haveCallBack = true;
									}
								}
							}

						});
			}
		}
	}

	private int problemCount = 0;

	public void initprojectProblemByNe() {
		problemCount++;
		getStringRequest(NetUrl.PROJECT_PROBLEM, new BaseCallBack<String>() {
			@Override
			public void messageResponse(RequestType requestType, String bean,
					String message) {
				if (requestType == RequestType.messagetrue) {
					SpUtil.putString(SpKey.PROJECTPROBLEM, bean);
				} else if (problemCount <= ProCount) {
					initprojectProblemByNe();
					return;
				}
			}
		});
	}

	public void getprojectProblemByNet(final BaseCallBack<TreeDataBean> callBack) {
		getprojectProblemByNet(callBack, true);
	}

	/**
	 * 进行网络请求 获取工程问题
	 * 
	 * @param callBack
	 *            请求回掉
	 * @param useCache
	 *            true 使用缓存 false 不管怎样都要进行网络请求
	 */
	public void getprojectProblemByNet(
			final BaseCallBack<TreeDataBean> callBack, boolean useCache) {
		if (useCache) {
			String str = SpUtil.getString(SpKey.PROJECTPROBLEM, "");
			if (!TextUtils.isEmpty(str)) {
				// 如果缓存已经存在就不进行网络请求
				TreeDataBean problemBean = getGson().fromJson(str,
						TreeDataBean.class);
				callBack.messageResponse(RequestType.messagetrue, problemBean,
						null);
				return;
			}
		}
		problemCount++;
		getStringRequest(NetUrl.PROJECT_PROBLEM, new BaseCallBack<String>() {

			@Override
			public void messageResponse(RequestType requestType, String bean,
					String message) {
				TreeDataBean problemBean = null;
				if (requestType == RequestType.messagetrue) {
					SpUtil.putString(SpKey.PROJECTPROBLEM, bean);
				} else if (problemCount <= ProCount) {
					getprojectProblemByNet(callBack, false);
					return;
					// bean = SpUtil.getString(SpKey.PROJECTPROBLEM, "");
				}

				if (!TextUtils.isEmpty(bean)) {
					problemBean = getGson().fromJson(bean, TreeDataBean.class);
					callBack.messageResponse(RequestType.messagetrue,
							problemBean, message);
				} else {
					callBack.messageResponse(RequestType.messagefalse,
							problemBean, message);
				}

			}
		});
	}

	int imagecurrent;
	int imagecount;

	/**
	 * 是否有图片进行下载
	 */
	public boolean haveMessage = false;

	/**
	 * 进行图片的下载
	 * 
	 * @param taskMessage
	 * @param imageCallBack
	 * @param callBack
	 */
	public void downLoadImage(final TaskMessage taskMessage,
			final ImageCallBack imageCallBack,
			final BaseCallBack<String> callBack) {
		haveMessage = false;
		new TasMessagetUtil(taskMessage) {
			@Override
			public boolean forKey(String key) {
				BigJsonManager bigJsonManager = BaseJsonManager
						.findManagerByKey(context, key, BigJsonManager.class);
				List<HouseMessage> taskList = bigJsonManager.getTaskList();
				for (int i = 0; i < taskList.size(); i++) {
					HouseMessage houseMessage = taskList.get(i);
					List<LastCheckProblemList> lastCheckProblemList = houseMessage
							.getLastCheckProblemList();
					for (int j = 0; j < lastCheckProblemList.size(); j++) {
						if (!haveMessage) {
							haveMessage = true;
							callBack.messageResponse(
									RequestType.haveImageSuccess, "下载成功",
									"下载成功");
						}
						List<String> attachmentIDS = lastCheckProblemList
								.get(j).AttachmentIDS;
						for (int k = 0; k < attachmentIDS.size(); k++) {
							imagecount++;
							SpUtil.putInt(taskMessage.TaskCode+"imagecount", imagecount);
							downOneImage(attachmentIDS.get(k),
									new BaseCallBack<String>() {

										@Override
										public void messageResponse(
												RequestType requestType,
												String bean, String message) {
											if (requestType == RequestType.messagetrue) {
												imagecurrent++;
												SpUtil.putInt(taskMessage.TaskCode+"imagecurrent", imagecurrent);
												//对当前任务的图片个数进行存储
												imageCallBack.imageResponse(
														RequestType.loading,
														imagecount,
														imagecurrent);
												//如果当前图片下载图片数等于全部图片数  下载完成
												if (imagecurrent == imagecount) {
													imageCallBack
															.imageResponse(
																	RequestType.messagetrue,
																	imagecount,
																	imagecurrent);
												}
											}else{
												imageCallBack.imageResponse(requestType, imagecount, imagecurrent);
											}
										}
									});

						}

					}
				}
				return false;
			}
		};
		callBack.messageResponse(RequestType.messagetrue, "下载成功", "下载成功");
	}

	/**
	 * 获得图片文件
	 */
	public void downOneImage(String id, BaseCallBack<String> callBack) {
		baseDownImage(id, NetUrl.PICTURE_DOWN, callBack);
	}
}
