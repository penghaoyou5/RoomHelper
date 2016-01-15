package com.sinooceanland.roomhelper;

import android.app.Application;

import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;

/**
 * Created by Jackson on 2015/12/28.
 * Version : 1
 * Details :
 */
public class MyApplication extends Application {

    public MyApplication(){
        SpUtil.init(this);
        SpUtilCurrentTaskInfo.init(this);

    }

    @Override
    public void onCreate() {
        super.onCreate();
       /* CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());*/
    }
}
