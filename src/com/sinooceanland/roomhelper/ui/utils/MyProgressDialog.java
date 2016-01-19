package com.sinooceanland.roomhelper.ui.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Jackson on 2015/12/28.
 * Version : 1
 * Details :
 */
public class MyProgressDialog {


    private ProgressDialog m_pDialog;
    private boolean isShowing =false;

    public  MyProgressDialog showDialog(Context context){

//创建ProgressDialog对象
        m_pDialog = new ProgressDialog(context);

// 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // 设置ProgressDialog 提示信息
       // m_pDialog.setMessage("请稍等。。。");

        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(true);
        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(true);
        m_pDialog.show();
        isShowing = true;
        return this;
    }

    public  MyProgressDialog  showDialogHorizontal(Context context,int max){

//创建ProgressDialog对象
        m_pDialog = new ProgressDialog(context);

// 设置进度条风格，风格为圆形，旋转的
        m_pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        // 设置ProgressDialog 提示信息
        // m_pDialog.setMessage("请稍等。。。");
        m_pDialog.setMax(max);
        // 设置ProgressDialog 的进度条是否不明确
        m_pDialog.setIndeterminate(true);
        // 设置ProgressDialog 是否可以按退回按键取消
        m_pDialog.setCancelable(false);
        m_pDialog.show();
        return this;
    }

    public void setProgress(int value){
        m_pDialog.setProgress(value);
    }

    public void dismissDialog(){
        m_pDialog.dismiss();
        isShowing = false;
    }

    public boolean isShowing(){
        return isShowing;
    }
}
