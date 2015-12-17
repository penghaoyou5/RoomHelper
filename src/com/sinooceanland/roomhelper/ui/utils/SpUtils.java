package com.sinooceanland.roomhelper.ui.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class SpUtils {
    private static final String fileName ="uiConfig";
    public static SharedPreferences.Editor putString(Context context,String key,String value){
       return getEditor(context).putString(key, value);
    }

    public static SharedPreferences getSp(Context context){
       return context.getSharedPreferences(fileName,Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getEditor(Context context){
       return getSp(context).edit();
    }

    public static String getString(Context context,String key){
       return getSp(context).getString(key,"");
    }
}
