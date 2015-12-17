package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;
import com.sinooceanland.roomhelper.ui.testdata.TaskData;
import com.sinooceanland.roomhelper.ui.testdata.TestTaskBean;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class TaskActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private TextView tv_load;
    private TextView tv_unload;
    private ListView lv_content;
    private List<TestTaskBean> mList;
    private CommonAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的任务");
        initData();
        initView();
        initListener();

    }

    private void initData() {
        mList = TaskData.getList(10);
    }

    private void initListener() {
        tv_load.setOnClickListener(this);
        tv_unload.setOnClickListener(this);
        lv_content.setOnItemClickListener(this);
    }

    private void initView() {
        tv_load = (TextView) findViewById(R.id.tv_load);
        tv_unload = (TextView) findViewById(R.id.tv_unload);
        lv_content = (ListView) findViewById(R.id.lv_content);
        mAdapter = new CommonAdapter<TestTaskBean>(this,mList, R.layout.item_task) {
            @Override
            protected void getView(ViewHolder holder, TestTaskBean bean, int position) {
                holder.setText(R.id.tv_name,bean.name);
                holder.setText(R.id.tv_state,bean.state);
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
                setLoad(true);
                break;
            case R.id.tv_unload:
                if(isUnLoadSelect)return;
                setState(false, true);
                setLoad(false);
                break;

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
        Log.e("test","test");
        if(load){
            //TODO 这里写点击加载按钮的处理事件
            mAdapter.setData(TaskData.getList(20));
            mAdapter.notifyDataSetInvalidated();
        }else {
            //TODO 未下载事件
            mAdapter.setData(TaskData.getList(30));
            mAdapter.notifyDataSetInvalidated();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(isLoadSelect){
            //TODO 这里处理跳转项目操作
            startActivity(new Intent(this,ChooseBuildingActivity.class));
            finish();
        }
        if(isUnLoadSelect){
            mList.get(position);
            //TODO 这里处理下载操作
        }
    }
}
