package com.sinooceanland.roomhelper.control.net;

import com.loopj.android.http.RequestParams;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.bean.ProjectProblemBean;
import com.sinooceanland.roomhelper.control.bean.TaskDetailBean;
import com.sinooceanland.roomhelper.control.bean.TaskListBean;
import com.sinooceanland.roomhelper.control.constant.NetUrl;

/**
 * @author peng
 *	主要进行网络请求与文件下载的类
 */
public class RequestNet extends BaseNet {
	
	/**
	 * 进行登陆的放嘎
	 * @param username 用户名
	 * @param password 密码
	 * @param callBack 请求回掉
	 */
	public void login(String username,String password,BaseCallBack<LoginBean> callBack){
		RequestParams requestParams = new RequestParams();
		requestParams.add("username", username);
		requestParams.add("username", password);
		baseRequest(requestParams, NetUrl.LOGIN, callBack, LoginBean.class);
	}
	
	/**
	 * 获取任务列表
	 * @param userid 用户ID
	 * @param callBack 请求回掉
	 */
	public void taskList(String userid,BaseCallBack<TaskListBean> callBack){
		RequestParams requestParams = new RequestParams();
		requestParams.add("userid", userid);
		baseRequest(requestParams, NetUrl.TASK_LIST, callBack, TaskListBean.class);
	}
	
	/**
	 * 获取模板明细
	 * @param PreCheckArrangeDateCode 验收范围编码
	 * @param callBack 请求回掉
	 */
	public void taskDetail(String PreCheckArrangeDateCode,BaseCallBack<TaskDetailBean> callBack){
		RequestParams requestParams = new RequestParams();
		requestParams.add("PreCheckArrangeDateCode", PreCheckArrangeDateCode);
		baseRequest(requestParams, NetUrl.TASK_DETAIL, callBack, TaskDetailBean.class);
	}
	
	/**
	 * 获取工程问题
	 * @param callBack 请求回掉
	 */
	public void projectProblem(BaseCallBack<ProjectProblemBean> callBack){
		baseRequest(new RequestParams(), NetUrl.PROJECT_PROBLEM, callBack, ProjectProblemBean.class);
	}
	
	/**
	 * 获得图片文件
	 */
	public void getImage(){
		
	}
}
