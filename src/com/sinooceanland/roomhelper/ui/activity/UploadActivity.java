package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.net.UpNet;

/**
 * Created by Jackson on 2015/12/18.
 * Version : 1
 * Details :
 */
public class UploadActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("这里写项目名称");
        initView();

    }

    private void initView() {
        Button btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_upload;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload:
                //TODO 加参数过去
              // new UpNet().upTaskMessage();
                startActivity(new Intent(this,UploadCompleteActivity.class));
                break;
        }
    }
}
