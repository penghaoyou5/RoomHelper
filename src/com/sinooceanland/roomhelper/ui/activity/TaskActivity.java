package com.sinooceanland.roomhelper.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.TaskListBean;
import com.sinooceanland.roomhelper.control.bean.TaskMessage;
import com.sinooceanland.roomhelper.control.net.RequestNet;
import com.sinooceanland.roomhelper.control.taskdata.TaskList;
import com.sinooceanland.roomhelper.ui.testdata.TaskData;
import com.sinooceanland.roomhelper.ui.utils.MyProgressDialog;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener,BaseNet.BaseCallBack<TaskList> {

    private TextView tv_load;
    private TextView tv_unload;
    private ListView lv_content;
    private List<TaskMessage> mLoadList;
    private List<TaskMessage> mUnLoadList;
    private CommonAdapter mAdapter;
    private Dialog alertDialog;
    private MyProgressDialog myProgressDialog;
    private boolean isLoad = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的任务");
        initData();
        initView();
        initListener();
    }

    private void initData() {
        TaskList taskList = new TaskList();
       mLoadList =  taskList.getAlreadLoad();
      //  mLoadList = TaskData.getList(20);
    }

    private void initListener() {
        tv_load.setOnClickListener(this);
        tv_unload.setOnClickListener(this);
        lv_content.setOnItemClickListener(this);
        setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskActivity.this,MainActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        myProgressDialog = new MyProgressDialog();
        tv_load = (TextView) findViewById(R.id.tv_load);
        tv_unload = (TextView) findViewById(R.id.tv_unload);
        lv_content = (ListView) findViewById(R.id.lv_content);
        mAdapter = new CommonAdapter<TaskMessage>(this, mLoadList, R.layout.item_task) {
            @Override
            protected void getView(ViewHolder holder, TaskMessage bean, int position) {
                holder.setText(R.id.tv_name,bean.TaskName);
                String wanchenState = bean.isFinish?"已完成":"未完成";
                holder.setText(R.id.tv_state,wanchenState);

                //上传状态 这里需要赋值
                //bean.isLoading
            }
        };
        lv_content.setAdapter(mAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_task;
    }


    private boolean isLoadSelect = true;
    private boolean isUnLoadSelect = false;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_load:
                if(isLoadSelect)return;
                setState(true,false);
                getLoadList();
                setLoad(true);
                break;
            case R.id.tv_unload:
                if(isUnLoadSelect)return;
                setState(false, true);
                setLoad(false);
                netGetUnLoadList();
                break;

        }
    }

    private void getLoadList() {

    }

    private void netGetUnLoadList() {
        if(!isLoad){
            myProgressDialog.showDialog(this);
            new RequestNet(this).taskList(this);
        }
    }

    /**
     * 点击时，更改按钮等各种状态
     * @param loadIsSelect
     * @param unloadIsSelect
     */
    public void setState(boolean loadIsSelect,boolean unloadIsSelect){
        if(loadIsSelect){
            isLoadSelect=true;
            isUnLoadSelect = false;
            tv_load.setTextColor(getColor(R.color.colorBlue));
            tv_unload.setTextColor(getColor(R.color.textColor));
        }
        if(unloadIsSelect){
            isLoadSelect=false;
            isUnLoadSelect=true;
            tv_load.setTextColor(getColor(R.color.textColor));
            tv_unload.setTextColor(getColor(R.color.colorBlue));
        }

    }

    /**
     * 按钮的处理事件
     * @param load
     */
    public void setLoad(boolean load) {
        if(load){
            //TODO 这里写点击加载按钮的处理事件
            mAdapter.setData(mLoadList);
            mAdapter.notifyDataSetInvalidated();
        }else {
            //TODO 在这里请求网络获取未下载集合
            if(isLoad){
                mAdapter.setData(mUnLoadList);
                mAdapter.notifyDataSetInvalidated();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isLoadSelect){
            TaskMessage bean = mLoadList.get(position);
            Bundle bundle = new Bundle();
            bundle.putParcelable("bean",bean);
            if(bean.isFinish){
                //TODO 这里将上传的bean传过去
                Intent data = new Intent(this, UploadActivity.class);

                data.putExtra("bean",bundle);
                startActivity(data);
            }else {
                Intent data = new Intent(this, ChooseBuildingActivity.class);
                data.putExtra("bean",bundle);
                startActivity(data);
            }
        }
        if(isUnLoadSelect){
            //TODO 这里处理下载操作
            showAlertDialog(position);
        }
    }

    private void showAlertDialog(final int position){
        if(alertDialog==null){
            alertDialog = new AlertDialog.Builder(this).
                    setTitle("是否下载").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("test", "" + position);
                            TaskMessage taskMessage = mUnLoadList.get(position);
                            downNet(taskMessage);
                            dialog.dismiss();
                        }
                    }).
                    setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create();
        }
        alertDialog.show();
    }

    private void downNet(TaskMessage bean){
        myProgressDialog.showDialog(TaskActivity.this);
        new RequestNet(TaskActivity.this).downTask(
                TaskActivity.this,
                bean,
                new DownLoadStringCallBack(),
                new DownLoadPicCallBack());
    }

    private class DownLoadStringCallBack implements BaseNet.BaseCallBack<String>{

        @Override
        public void messageResponse(BaseNet.RequestType requestType, String bean, String message) {
            myProgressDialog.dismissDialog();
        }
    }

    private class DownLoadPicCallBack implements BaseNet.ImageCallBack{
        private boolean isFirst = true;
        @Override
        public void imageResponse(BaseNet.RequestType requestType, int count, int current) {
            if(isFirst){
                isFirst = false;
                myProgressDialog.showDialogHorizontal(TaskActivity.this,count);
            }
            myProgressDialog.setProgress(current);
            if(count == current){
                myProgressDialog.dismissDialog();
            }
        }
    }

    @Override
    public void messageResponse(BaseNet.RequestType requestType, TaskList bean, String message) {
        myProgressDialog.dismissDialog();
        if(requestType== BaseNet.RequestType.messagetrue){
            isLoad=true;
            mUnLoadList = bean.getUnLoad();
            mAdapter.setData(mUnLoadList);
            mAdapter.notifyDataSetChanged();
        }
    }
}
