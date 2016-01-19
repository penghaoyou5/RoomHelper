package com.sinooceanland.roomhelper.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.KeyEvent;
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
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.ui.testdata.TaskData;
import com.sinooceanland.roomhelper.ui.utils.MyProgressDialog;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;
import com.sinooceanland.roomhelper.ui.utils.SpUtils;
import com.sinooceanland.roomhelper.ui.utils.TextUtil;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, BaseNet.BaseCallBack<TaskList> {

    private TextView tv_load;
    private TextView tv_unload;
    private ListView lv_content;
    private List<TaskMessage> mLoadList;
    private List<TaskMessage> mUnLoadList;
    private CommonAdapter mAdapter;
    private Dialog alertDialog;


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
        mLoadList = taskList.getAlreadLoad();
    }

    private void initListener() {
        tv_load.setOnClickListener(this);
        tv_unload.setOnClickListener(this);
        lv_content.setOnItemClickListener(this);
        setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaskActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    private void initView() {
        tv_load = (TextView) findViewById(R.id.tv_load);
        tv_unload = (TextView) findViewById(R.id.tv_unload);
        lv_content = (ListView) findViewById(R.id.lv_content);
        mAdapter = new CommonAdapter<TaskMessage>(this, mLoadList, R.layout.item_task) {
            @Override
            protected void getView(ViewHolder holder, TaskMessage bean, int position) {
                holder.setText(R.id.tv_name, bean.TaskName);
                String completeState = bean.isFinish ? "(已完成)" : "(未完成)";
                String loadState = isLoadSelect? "(已下载)":"(未下载)";
                String state  = TextUtil.connectString(bean.CreateTime," ",completeState, loadState);
                holder.setText(R.id.tv_state, state);

                //上传状态 这里需要赋值
                //bean.isLoading
            }
        };
        lv_content.setAdapter(mAdapter);
        myProgressDialog = new MyProgressDialog();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_task;
    }


    private boolean isLoadSelect = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_load:
                if (isLoadSelect) return;
                isLoadSelect = true;
                setState();
                getLoadList();
                setLoad(true);
                break;
            case R.id.tv_unload:
                if (!isLoadSelect) return;
                isLoadSelect = false;
                setState();
                showDialog(true);
                netGetUnLoadList();
                break;

        }
    }

    private void getLoadList() {
        TaskList taskList = new TaskList();
        mLoadList = taskList.getAlreadLoad();
    }

    private void netGetUnLoadList() {
        new RequestNet(this).taskList(this);
    }

    /**
     * 点击时，更改按钮等各种状态
     *
     */
    public void setState() {
        if (isLoadSelect) {
            tv_load.setTextColor(getColor(R.color.colorBlue));
            tv_unload.setTextColor(getColor(R.color.textColor));
        }else {
            tv_load.setTextColor(getColor(R.color.textColor));
            tv_unload.setTextColor(getColor(R.color.colorBlue));
        }
    }

    /**
     * 按钮的处理事件
     *
     * @param load
     */
    public void setLoad(boolean load) {
        if (load) {
            //TODO 这里写点击加载按钮的处理事件
            mAdapter.setData(mLoadList);
            mAdapter.notifyDataSetInvalidated();
        } else {
            //TODO 在这里请求网络获取未下载集合
            mAdapter.setData(mUnLoadList);
            mAdapter.notifyDataSetInvalidated();
        }
    }

    private int mClickPosition = -1;
    private final int REQUEST_UPLOAD =10000;
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isLoadSelect) {
            TaskMessage bean = mLoadList.get(position);
            if (bean.isFinish) {
                //TODO 这里将上传的bean传过去
                Intent data = new Intent(this, UploadActivity.class);
                TaskMyssageData.saveTaskMessage(this, bean);
                startActivityForResult(data, REQUEST_UPLOAD);
            } else {
                TaskMyssageData.saveTaskMessage(this, bean);
                Intent data = new Intent(this, ChooseBuildingActivity.class);
                startActivity(data);
                finish();
            }
        }
        if (!isLoadSelect) {//未完成状态下
            mClickPosition = position;
            showAlertDialog();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_UPLOAD:
                if(resultCode == RESULT_OK){
                    TaskList taskList = new TaskList();
                    mLoadList = taskList.getAlreadLoad();
                    mAdapter.setData(mLoadList);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    private void showAlertDialog() {
        if (alertDialog == null) {
            alertDialog = new AlertDialog.Builder(this).
                    setTitle("是否下载").
                    setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.e("test", "" + mClickPosition);
                            TaskMessage  tempDownLoadBean = mUnLoadList.get(mClickPosition);
                            showDialog(false);
                            downNet(tempDownLoadBean);
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

    private void downNet(TaskMessage bean) {

        new RequestNet(TaskActivity.this).downTask(
                TaskActivity.this,
                bean,
                new DownLoadStringCallBack(),
                new DownLoadPicCallBack());
    }

    private class DownLoadStringCallBack implements BaseNet.BaseCallBack<String> {

        @Override
        public void messageResponse(BaseNet.RequestType requestType, String bean, String message) {
            dismissDialog();
            if (requestType == BaseNet.RequestType.haveImageSuccess) {

            }
            if (requestType == BaseNet.RequestType.messagetrue) {
                mUnLoadList.remove(mClickPosition);
                mAdapter.setData(mUnLoadList);
                mAdapter.notifyDataSetChanged();
                showToast("下载成功");
            }
            if (requestType == BaseNet.RequestType.messagefalse) {
                showToast("下载失败");
            }
            if (requestType == BaseNet.RequestType.connectFailure) {
                showToast("网络异常");
            }

        }
    }
    private MyProgressDialog myProgressDialog;
    private class DownLoadPicCallBack implements BaseNet.ImageCallBack {
        private boolean isFirst = true;

        @Override
        public void imageResponse(BaseNet.RequestType requestType, int count, int current) {

            if (isFirst) {
                isFirst = false;
                myProgressDialog.showDialogHorizontal(TaskActivity.this, count);
            }

            if(requestType.loading == requestType){
                myProgressDialog.setProgress(current);
            }

            if (requestType.messagetrue == requestType) {
                myProgressDialog.dismissDialog();
                mUnLoadList.remove(mClickPosition);
                mAdapter.setData(mUnLoadList);
                mAdapter.notifyDataSetChanged();
            }

            if (requestType == BaseNet.RequestType.messagefalse) {
                showToast("下载失败");
            }
            if (requestType == BaseNet.RequestType.connectFailure) {
                showToast("网络异常");
            }
        }
    }

    //获取未下载列表的
    @Override
    public void messageResponse(BaseNet.RequestType requestType, TaskList bean, String message) {
        if(isLoadSelect)return;
        dismissDialog();
        if (requestType == BaseNet.RequestType.messagetrue) {
            mUnLoadList = bean.getUnLoad();
            mAdapter.setData(mUnLoadList);
            mAdapter.notifyDataSetChanged();
        }
        if(requestType == BaseNet.RequestType.connectFailure){
            showToast("网络异常");
            mAdapter.setData(null);
            mAdapter.notifyDataSetChanged();
        }
        if(requestType == BaseNet.RequestType.messagefalse){
            showToast("网络异常");
            mAdapter.setData(null);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
