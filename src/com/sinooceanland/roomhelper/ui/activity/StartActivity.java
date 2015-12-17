package com.sinooceanland.roomhelper.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;

/**
 * Created by Jackson on 2015/12/17.
 * Version : 1
 * Details :
 */
public class StartActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("我的任务");
        initData();
    }

    private void initData() {
        TextView tv_project = (TextView) findViewById(R.id.tv_project);
        TextView tv_pre_room = (TextView) findViewById(R.id.tv_pre_room);
        TextView tv_true_room = (TextView) findViewById(R.id.tv_true_room);
        TextView tv_state = (TextView) findViewById(R.id.tv_state);
        TextView tv_name = (TextView) findViewById(R.id.tv_name);
        TextView tv_count = (TextView) findViewById(R.id.tv_count);
        tv_project.setText("啊啊啊啊啊啊啊啊啊啊啊");
        tv_pre_room.setText("");
        tv_true_room.setText("");
        tv_state.setText("");
        tv_name.setText("");
        tv_count.setText("");

        Button btn_start = (Button) findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_start;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:

                break;
        }
    }
}