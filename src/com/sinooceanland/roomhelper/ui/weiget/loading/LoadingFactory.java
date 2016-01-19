package com.sinooceanland.roomhelper.ui.weiget.loading;

import android.content.Context;

/**
 * Created by Jackson on 2016/1/14.
 * Version : 1
 * Details :
 */
public class LoadingFactory {
    public static InterLoading getLoading(Context context){
        return new CyclLoading(context);
    }
}
