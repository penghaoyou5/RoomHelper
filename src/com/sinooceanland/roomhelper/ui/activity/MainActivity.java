package com.sinooceanland.roomhelper.ui.activity;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.bean.LoginBean;
import com.sinooceanland.roomhelper.control.net.RequestNet;
import com.sinooceanland.roomhelper.ui.utils.SpUtils;
import com.sinooceanland.roomhelper.ui.utils.TextUtil;
<<<<<<< HEAD
=======

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
>>>>>>> bd7db19437929ed8e3a979a4de8392288bb9ee94


public class MainActivity extends BaseActivity implements BaseNet.BaseCallBack<LoginBean> {

    private Button btn_login;
    private EditText et_account;
    private EditText et_password;
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";
    private RequestNet requestNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("验房小助手");
        setLeftVisbil(View.GONE);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void initData() {
        String account = SpUtils.getString(this, ACCOUNT);
        //String password = SpUtils.getString(this, PASSWORD);
//        et_account.setText(account);
        et_account.setText("v-xiaoliang");
        requestNet = new RequestNet(this);
    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = TextUtil.getString(et_account);
                String password = TextUtil.getString(et_password);
                showDialog(false);
                requestNet.login(account, password, MainActivity.this);
                btn_login.setEnabled(false);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void messageResponse(BaseNet.RequestType requestType, LoginBean bean, String message) {
        btn_login.setEnabled(true);
        dismissDialog();
        if(requestType== requestType.messagetrue){
            startActivity(new Intent(MainActivity.this, TaskActivity.class));
            SpUtils.putString(MainActivity.this, ACCOUNT, TextUtil.getString(et_account)).commit();
            requestNet.initprojectProblemByNe();
            finish();
        }
        if(requestType== requestType.connectFailure){
            showToast("网络连接失败");
        }
        if(requestType== requestType.messagefalse) {
            showToast("账号密码错误");
        }
    }
}