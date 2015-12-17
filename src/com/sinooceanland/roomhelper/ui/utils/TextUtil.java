package com.sinooceanland.roomhelper.ui.utils;

import android.widget.EditText;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class TextUtil {
    public static String getString(EditText et){
        return et.getText().toString();
    }

    public static String connectString(String ...strArr){
        StringBuilder builder = new StringBuilder();
        for (String str :strArr) {
            builder.append(str);
        }
       return builder.toString();
    }
}
