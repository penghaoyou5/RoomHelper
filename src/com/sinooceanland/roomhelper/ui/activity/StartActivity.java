package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;

/**
 * Created by Jackson on 2015/12/17.
 * Version : 1
 * Details :
 */
public class StartActivity extends BaseActivity implements View.OnClickListener {

    private HouseMessageData houseMessageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的任务");
        initData();
    }

    private void initView(HouseMessage houseMessage) {
        TextView tv_project = (TextView) findViewById(R.id.tv_project);
        TextView tv_pre_room = (TextView) findViewById(R.id.tv_pre_room);
        TextView tv_true_room = (TextView) findViewById(R.id.tv_true_room);
        TextView tv_state = (TextView) findViewById(R.id.tv_state);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_count = (TextView) findViewById(R.id.tv_count);
        tv_project.setText(houseMessage.PreHouseFullName);
        tv_pre_room.setText(houseMessage.ActHouseName);
        tv_true_room.setText(houseMessage.PreHouseName);
        tv_state.setText(houseMessage.PropertTypeName);
        tv_name.setText(houseMessage.OwnerNames);
        tv_count.setText(houseMessage.CheckRound);

        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }

    private void initData() {
        houseMessageData = HouseMessageData.getInstance();
        HouseMessage houseMessage = houseMessageData.getHouseMessage();
        initView(houseMessage);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(this,TakePhotoActivity.class));
                setResult(RESULT_OK);
                finish();
                break;
        }
    }
}