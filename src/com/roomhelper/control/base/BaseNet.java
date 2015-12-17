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
	 * �ط�3������ ���������ö�ٰ�
	 * 
	 * @author Administrator �յķ������� �޲λص�����
	 */
	public static  enum RequestType {
		/**
		 * ����ʧ��
		 */
		connectFailure, 
		/**
		 *��Ϣ�ɹ����� 
		 */
		messagetrue, 
		/**
		 * ��Ϣ����ʧ��
		 */
		messagefalse
	}
	
	/**
	 * �����ӿ�
	 * 
	 * @author Administrator
	 * 
	 * @param <T> Bean
	 */
	public static interface BaseCallBack<T extends BaseBean> {
		
		/**
		 * ������������Ļص�
		 * @param requestType ����ɹ�
		 * @param bean ����ɹ���bean
		 * @param errorMessage �������Ϣ����ֵ
		 */
		void messageResponse(RequestType requestType,T bean,String message);
	}
	
	private static AsyncHttpClient httpClient;
	/**
	 * ��õ�����AsyncHttpClient �ͻ���
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
	 * ��ȡ����Gson����
	 * @return Gson
	 * ˵��http://blog.csdn.net/jxxfzgy/article/details/43746317
	 */
	public static Gson getGson(){
		//ͨ��ָ��������Ȩ�������ˣ�������˵�����Ϊ protcted �ı���
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
				
				//��ʱû��������Ϣ�ĳɹ���ʧ��
				callback.messageResponse( RequestType.messagetrue, bean, null);
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,byte[] responseBody, Throwable error) {
				callback.messageResponse(RequestType.connectFailure, null, new String(responseBody));
			}
		});
	}
}
