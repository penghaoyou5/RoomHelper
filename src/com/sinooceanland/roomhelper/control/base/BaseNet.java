package com.sinooceanland.roomhelper.control.base;

import java.io.File;
import java.lang.reflect.Modifier;

import org.apache.http.Header;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.sinooceanland.roomhelper.control.constant.NetUrl;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.FileUtils;
import com.sinooceanland.roomhelper.control.util.GetAssertUtil;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.dao.base.BaseBean;

/**
 * @author peng 进行网络请求的基类
 */
public class BaseNet {

	/**
	 * 回返3种类型 那这里就用枚举吧
	 * 
	 * @author Administrator 空的返回类型 无参回调返回
	 */
	public static enum RequestType {
		/**
		 * 连接失败
		 */
		connectFailure,
		/**
		 * 消息成功返回
		 */
		messagetrue,
		/**
		 * 消息请求失败
		 */
		messagefalse,
		
		/**
		 * 照片正在下载中
		 */
		loading,
		
		
		/**
		 * 有图片的上传成功
		 */
		haveImageSuccess
	}

	/**
	 * 基本接口
	 * 
	 * @author Administrator
	 * 
	 * @param <T>
	 *            Bean
	 */
	public static interface BaseCallBack<T> {

		/**
		 * 进行网络请求的回掉
		 * 
		 * @param requestType
		 *            请求成功
		 * @param bean
		 *            请求成功的bean
		 * @param message
		 *            请求的消息返回值消息体
		 */
		void messageResponse(RequestType requestType, T bean, String message);
	}

	// 这是进行图片下载的回调
	public static interface ImageCallBack {
		void imageResponse(RequestType requestType, int count, int current);
	}
	
	// 这是进行图片下载的回调
		public static interface JsonCallBack {
			void jsonResponse(RequestType requestType, int count, int current);
		}

	private static AsyncHttpClient httpClient;

	/**
	 * 获得单例的AsyncHttpClient 客户端
	 * 
	 * @return AsyncHttpClient
	 */
	public static AsyncHttpClient getAsyncHttpClient() {
		if (httpClient == null) {
			httpClient = new AsyncHttpClient();
			httpClient.setTimeout(20000);
			httpClient.setMaxConnections(1000);
		}
		return httpClient;
	}

	private static Gson gson;

	/**
	 * 获取单例Gson对象
	 * 
	 * @return Gson 说明http://blog.csdn.net/jxxfzgy/article/details/43746317
	 */
	public static Gson getGson() {
		// 通过指定声明的权限来过滤，这里过滤掉声明为 protcted 的变量
		gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED)
				.create();
		return gson;
	}

	Class cla;
	public static boolean dateProblem;
	public static boolean isTest = false;
	public <T extends BaseBean> void baseRequest(RequestParams requestParams,
			final String url, final BaseCallBack<T> callback, final Class<T> beanClass) {
		cla = beanClass;
		if(isTest){
			String str = "";
			if(url.equals(NetUrl.LOGIN)){
				str = "{\"SuccessMsg\":true,\"ErrorMsg\":\"\",\"userID\":\"47b70051-b1eb-820e-4614-18f2ebddd17a\"}";
			}else if(url.equals(NetUrl.TASK_LIST)){
				//获得任务列表数据
				str = GetAssertUtil.readAssertResource(SpUtil.mContext, "taskList.txt");
			}
			T bean = (T) getGson().fromJson(str,cla);
			callback.messageResponse(RequestType.messagetrue, bean, str);
			return;
		}
		
		getAsyncHttpClient().post(url, requestParams,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String req = new String(responseBody);
						T bean = (T) getGson().fromJson(req, cla);
						if (bean!=null&&"true".equals(bean.SuccessMsg)) {
							callback.messageResponse(RequestType.messagetrue,
									bean, req);
						} else {
							callback.messageResponse(RequestType.messagefalse,
									bean, req);
						}
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						callback.messageResponse(RequestType.connectFailure,
								null, null);
					}
				});
	}

	public void baseStringRequest(RequestParams requestParams, String url,
			final BaseCallBack<String> callback) {
		if(isTest){
			String str = "";
			//任务列表
			if(url.equals(NetUrl.LOGIN)){
				str = "{\"SuccessMsg\":true,\"ErrorMsg\":null,\"UserId\":\"47b70051-b1eb-820e-4614-18f2ebddd17a\"}";
			//问题列表
			}else if(url.equals( NetUrl.PROJECT_PROBLEM)){
				str = "{\"message\":\"\",\"code\":0,\"list\":[{\"TaskCode\":\"041cb848-523c-4cc7-8e59-07f3645212e6\",\"buildingList\":[{\"BuildingCode\":\"A152D0E9-1270-43A0-97A8-539F0DC24178\",\"UnitCode\":[\"31C24C65-0226-4D40-9239-191A63106E13\",\"31C24C65-0226-4D40-9239-191A63106E16\"]},{\"BuildingCode\":\"A152D0E9-1270-43A0-97A8-539F0DC24179\",\"UnitCode\":[\"31C24C65-0226-4D40-9239-191A63106E14\",\"31C24C65-0226-4D40-9239-191A63106E15\"]}],\"TaskName\":\"肖大爷测试专用\"},{\"TaskCode\":\"041cb848-523c-4cc7-8e59-07f3645212e6\",\"TaskName\":\"肖大爷测试专用\"}]}";
			//任务详情 没有上次验收问题
			}else if(url.equals(NetUrl.TASK_DETAIL)){
				str = GetAssertUtil.readAssertResource(SpUtil.mContext, "taskdetail_haveduogefangjiatxt");
			}
			callback.messageResponse(RequestType.messagetrue, str, str);
			return;
		}
		

		getAsyncHttpClient().post(url, requestParams,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
							byte[] responseBody) {
						String json = new String(responseBody);
						// 暂时没有区分消息的成功与失败
						callback.messageResponse(RequestType.messagetrue, json,
								json);
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
							byte[] responseBody, Throwable error) {
						callback.messageResponse(RequestType.connectFailure,
								null,null);
					}
				});
	}

	public void baseDownImage(final String id, String url,
			final BaseCallBack<String> callback) {
		File file = new File(SpKey.getProblemPictureAddress(), id);
		if(file.exists()){
			//如果图片已经存在就进行成功回调
			callback.messageResponse(RequestType.messagetrue,
					null, null);
			return;
		}
		
		RequestParams requestParams = new RequestParams();
//		requestParams.add("id", id);
		url = url+id;
		getAsyncHttpClient().get(url, requestParams,
				new AsyncHttpResponseHandler() {

					@Override
					public void onSuccess(int statusCode, Header[] headers,
										  byte[] responseBody) {
						if (statusCode == 200) {
							BitmapFactory factory = new BitmapFactory();
							Bitmap bitmap = factory.decodeByteArray(responseBody, 0, responseBody.length);
							//保存图片
							File file = FileUtils.getOrNewImageFile(SpKey.getProblemPictureAddress(), id);
//							FileUtils.write2file(file, responseBody);
							try {
								FileUtils.saveFile(bitmap,file);
							}catch (Exception e){

							}


						}
						callback.messageResponse(RequestType.messagetrue,
								null, new String(responseBody));
					}

					@Override
					public void onFailure(int statusCode, Header[] headers,
										  byte[] responseBody, Throwable error) {
						callback.messageResponse(RequestType.connectFailure,
								null, null);
					}
				});

	}
	
	
	
	
	
	//==================================增加get请求	直接根据请求地址进行回调
	public void getStringRequest(String url,
			final BaseCallBack<String> callback){
		getAsyncHttpClient().get(url,new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				String json = new String(arg2);
				callback.messageResponse(RequestType.messagetrue, json,
						json);
			}
			
			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2, Throwable arg3) {
				callback.messageResponse(RequestType.connectFailure,
						null, null);
			}
		});
		
	}
	
	
	
	
}
