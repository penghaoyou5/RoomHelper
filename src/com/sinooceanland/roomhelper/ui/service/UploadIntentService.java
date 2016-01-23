package com.sinooceanland.roomhelper.ui.service;

import java.util.Timer;
import java.util.TimerTask;

import com.sinooceanland.roomhelper.control.net.UpNet;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

/**
 * Created by Jackson on 2015/12/17.
 * Version : 1
 * Details :
 */
@SuppressLint("NewApi") public class UploadIntentService  extends IntentService {
    public UploadIntentService() {
        super("UploadIntentService");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
    	//开始进行图片上传
        Timer timer = new Timer();  
        timer.schedule(new TimerTask(
        		) {
			@Override
			public void run() {
				if(SpUtil.mContext==null){
					SpUtil.init(getApplicationContext());
				}
				if(SpUtilCurrentTaskInfo.mContext==null){
					SpUtilCurrentTaskInfo.init(getApplicationContext());
				}
				UpNet.startUpMessage(getApplicationContext());
				Log.e("run", SystemClock.currentThreadTimeMillis() + "");
			}
		}, 1000, 20000); 
        // 经测试，IntentService里面是可以进行耗时的操作的
        //IntentService使用队列的方式将请求的Intent加入队列，然后开启一个worker thread(线程)来处理队列中的Intent
        //对于异步的startService请求，IntentService会处理完成一个之后再处理第二个
        Log.e("test", "onStart");
        SystemClock.sleep(20000);
        Log.e("tesat", SystemClock.currentThreadTimeMillis()+"");
    }
}

