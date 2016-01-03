package com.sinooceanland.roomhelper.control.net;

import android.content.Context;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.base.BaseNet.BaseCallBack;
import com.sinooceanland.roomhelper.control.base.BaseNet.ImageCallBack;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;

/**
 * @author peng
 * 这里是进行文件上传的接口
 */
public class UpNet extends BaseNet{

	/**
	 * 进行任务列表的上传
	 * @param taskMessage 任务信息类
	 * @param callBack
	 */
	public void upTaskMessage(final Context context, final TaskMessage taskMessage,
			final BaseCallBack<String> callBack,final ImageCallBack imageCallBack){
		
	}
}
