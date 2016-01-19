package com.sinooceanland.roomhelper.ui.activity;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.net.UpNet;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Jackson on 2015/12/18.
 * Version : 1
 * Details :
 */
public class UploadCompleteActivity extends BaseActivity implements View.OnClickListener {

    private View mLoadingView;
    private AnimationDrawable loadingDrawable;
    private Button mBtn_complete;
    private TextView mTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        taskMyssageData = TaskMyssageData.getInstance();
        setTitle(taskMyssageData.getTaskName());
        initView();
        upLoad();
    }
    private boolean isFirst = true;
    TaskMyssageData taskMyssageData;
    private void upLoad() {
        new UpNet().upTaskMessage(this,
                taskMyssageData.getTaskMessage(), new BaseNet.JsonCallBack() {
                    @Override
                    public void jsonResponse(BaseNet.RequestType requestType, int count, int current) {
                        if(requestType == BaseNet.RequestType.messagetrue){
                            setCompleteState();
                        }
                    }
                }, new BaseNet.ImageCallBack() {
                    @Override
                    public void imageResponse(BaseNet.RequestType requestType, int count, int current) {
                        if(isFirst){
                            isFirst = false;
                        }
                    }
                });
        showLoading();
    }

    private void initView() {
        mBtn_complete = (Button) findViewById(R.id.btn_complete);
        mBtn_complete.setOnClickListener(this);
        mTv = (TextView) findViewById(R.id.tv);
        mLoadingView = findViewById(R.id.v);
        loadingDrawable = (AnimationDrawable) mLoadingView.getBackground();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_upload_complete;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_complete:
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    private void showLoading(){
       // mLoadingView.setVisibility(View.VISIBLE);
        loadingDrawable.start();
        mTv.setText("请稍后...");
    }

    private void dissmissLoading(){
        //mLoadingView.setVisibility(View.GONE);
        loadingDrawable.stop();
        mTv.setText("上传完成");
    }

    private void setCompleteState(){
        mBtn_complete.setEnabled(true);
        mBtn_complete.setText("上传完成");
        dissmissLoading();
    }
}