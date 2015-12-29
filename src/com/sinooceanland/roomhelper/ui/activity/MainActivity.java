package com.sinooceanland.roomhelper.ui.activity;

import junit.framework.Test;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.net.TestClassttt;
import com.sinooceanland.roomhelper.control.net.testTTT;
import com.sinooceanland.roomhelper.ui.utils.SpUtils;
import com.sinooceanland.roomhelper.ui.utils.TextUtil;


public class MainActivity extends BaseActivity {

    private Button btn_login;
    private EditText et_account;
    private EditText et_password;
    private final String ACCOUNT = "account";
    private final String PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("验房小助手");
        setLeftVisbil(View.GONE);
        initView();
        initListener();
        initData();
        testNet();
    }

    private void testNet() {
		try {
			new testTTT().testLogin();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_account = (EditText) findViewById(R.id.et_account);
        et_password = (EditText) findViewById(R.id.et_password);
    }

    private void initData() {
        String account = SpUtils.getString(this, ACCOUNT);
        //String password = SpUtils.getString(this, PASSWORD);
        et_account.setText(account);
    }

    private void initListener() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this, TaskActivity.class));
                String account = TextUtil.getString(et_account);
                String password = TextUtil.getString(et_password);
                SpUtils.putString(MainActivity.this, ACCOUNT, account).commit();
                finish();
            }
        });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

}