package com.roomhelper.control.net;

import com.loopj.android.http.RequestParams;
import com.roomhelper.control.base.BaseNet;
import com.roomhelper.control.base.Constants;
import com.roomhelper.control.bean.LoginBean;
import com.roomhelper.control.bean.ProjectProblemBean;
import com.roomhelper.control.bean.TaskDetailBean;
import com.roomhelper.control.bean.TaskListBean;

/**
 * @author peng
 *	��Ҫ���������������ļ����ص���
 */
public class RequestNet extends BaseNet {
	
	/**
	 * ���е�½�ķŸ�
	 * @param username �û���
	 * @param password ����
	 * @param callBack ����ص�
	 */
	public void login(String username,String password,BaseCallBack<LoginBean> callBack){
		RequestParams requestParams = new RequestParams();
		requestParams.add("username", username);
		requestParams.add("username", password);
		baseRequest(requestParams, Constants.LOGIN, callBack, LoginBean.class);
	}
	
	/**
	 * ��ȡ�����б�
	 * @param userid �û�ID
	 * @param callBack ����ص�
	 */
	public void taskList(String userid,BaseCallBack<TaskListBean> callBack){
		RequestParams requestParams = new RequestParams();
		requestParams.add("userid", userid);
		baseRequest(requestParams, Constants.TASK_LIST, callBack, TaskListBean.class);
	}
	
	/**
	 * ��ȡģ����ϸ
	 * @param PreCheckArrangeDateCode ���շ�Χ����
	 * @param callBack ����ص�
	 */
	public void taskDetail(String PreCheckArrangeDateCode,BaseCallBack<TaskDetailBean> callBack){
		RequestParams requestParams = new RequestParams();
		requestParams.add("PreCheckArrangeDateCode", PreCheckArrangeDateCode);
		baseRequest(requestParams, Constants.TASK_DETAIL, callBack, TaskDetailBean.class);
	}
	
	/**
	 * ��ȡ��������
	 * @param callBack ����ص�
	 */
	public void projectProblem(BaseCallBack<ProjectProblemBean> callBack){
		baseRequest(new RequestParams(), Constants.PROJECT_PROBLEM, callBack, ProjectProblemBean.class);
	}
	
	/**
	 * ���ͼƬ�ļ�
	 */
	public void getImage(){
		
	}
}
