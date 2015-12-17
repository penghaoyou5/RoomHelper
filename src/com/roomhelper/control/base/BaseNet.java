package com.roomhelper.control.base;

import java.lang.reflect.Modifier;

import org.apache.http.Header;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class BaseNet {
	
	/**
	 * 回返3种类型 那这里就用枚举吧
	 * 
	 * @author Administrator 空的返回类型 无参回调返回
	 */
	public static  enum RequestType {
		/**
		 * 连接失败
		 */
		connectFailure, 
		/**
		 *消息成功返回 
		 */
		messagetrue, 
		/**
		 * 消息请求失败
		 */
		messagefalse
	}
	
	/**
	 * 基本接口
	 * 
	 * @author Administrator
	 * 
	 * @param <T> Bean
	 */
	public static interface BaseCallBack<T extends BaseBean> {
		
		/**
		 * 进行网络请求的回掉
		 * @param requestType 请求成功
		 * @param bean 请求成功的bean
		 * @param errorMessage 请求的消息返回值
		 */
		void messageResponse(RequestType requestType,T bean,String message);
	}
	
	private static AsyncHttpClient httpClient;
	/**
	 * 获得单例的AsyncHttpClient 客户端
	 * @return AsyncHttpClient
	 */
	public static AsyncHttpClient getAsyncHttpClient(){
		if(httpClient==null){
			httpClient = new AsyncHttpClient();
		}
		return httpClient;
	}
	
	private static Gson gson;
	/**
	 * 获取单例Gson对象
	 * @return Gson
	 * 说明http://blog.csdn.net/jxxfzgy/article/details/43746317
	 */
	public static Gson getGson(){
		//通过指定声明的权限来过滤，这里过滤掉声明为 protcted 的变量
		gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create() ;
		return gson;
	}

	public <T extends BaseBean> void baseRequest(RequestParams requestParams, String url,final BaseCallBack<T> callback, final Class<T> beanClass) {
//		com.loopj.android.http.RequestParams requestParams = new com.loopj.android.http.RequestParams();

//		requestParams.add("description", description);
//		requestParams.add("expectedFixTime", expectedFixTime);
//		requestParams.put("serviceFiles", serviceFiles);
//
//		client.addHeader(
//				"Cookie",
//				SharedPreferencesUtil.getString(
//						BaseApplication.getBaseApplication(), "cookie"));
		
		getAsyncHttpClient().post(url, requestParams, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,byte[] responseBody) {
				T bean = getGson().fromJson(new String(responseBody), beanClass);
				
				//暂时没有区分消息的成功与失败
				callback.messageResponse( RequestType.messagetrue, bean, null);
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,byte[] responseBody, Throwable error) {
				callback.messageResponse(RequestType.connectFailure, null, new String(responseBody));
			}
		});
	}
}
