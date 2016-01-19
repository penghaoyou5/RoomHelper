package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.net.UpNet;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;

/**
 * Created by Jackson on 2015/12/18.
 * Version : 1
 * Details :
 */
public class UploadActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }
    TaskMyssageData taskMyssageData;
    private void initData() {
        taskMyssageData = TaskMyssageData.getInstance();
        setTitle(taskMyssageData.getTaskName());
       TextView tv_content = (TextView) findViewById(R.id.tv_content);
        tv_content.setText(taskMyssageData.getTaskName());
    }

    private void initView() {
        Button btn_upload = (Button) findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(this);
/*        setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UploadActivity.this,TaskActivity.class));
                finish();
            }
        });*/
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_upload;
    }

    private static final int REQUEST_CODE = 1000;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_upload:
                //TODO 加参数过去
                Intent data = new Intent(this, UploadCompleteActivity.class);
                startActivityForResult(data, REQUEST_CODE);
                finish();
                break;
        }
    }

/*    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, TaskActivity.class));
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                if(resultCode == RESULT_OK){
                    setResult(RESULT_OK);
                    finish();
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}
