package com.sinooceanland.roomhelper.ui.activity;

import com.sinooceanland.roomhelper.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Jackson on 2015/12/15.
 * Version : 1
 * Details :
 */
public abstract class BaseActivity extends FragmentActivity {
    private boolean mIsHasTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        isHasTitle();
        initLeft();
    }

    private void isHasTitle() {
        View v = findViewById(R.id.tv_title_ZXCVBNM);
        mIsHasTitle = v != null;
    }

    private void initLeft() {
        if (!mIsHasTitle) return;
        findViewById(R.id.ib_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setLeftVisbil(int Visibility) {
        if (!mIsHasTitle) return;
        findViewById(R.id.ib_left).setVisibility(Visibility);
    }

    protected abstract int getContentView();

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    public void setTitle(CharSequence title) {
        if (!mIsHasTitle) return;
        TextView titleTv = (TextView) findViewById(R.id.tv_title_ZXCVBNM);
        titleTv.setText(title);
    }

    public void setRightOnClickListener(View.OnClickListener listener) {
        if (!mIsHasTitle) return;
        View view = findViewById(R.id.ib_right);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(listener);
    }

    public void setLeftOnClickListener(View.OnClickListener listener) {
        if (!mIsHasTitle) return;
        View view = findViewById(R.id.ib_left);
        view.setVisibility(View.VISIBLE);
        view.setOnClickListener(listener);
    }

    protected int getColor(int colorId){
        return getResources().getColor(colorId);
    }

    public void showToast(String toast){
        Toast.makeText(this,toast,Toast.LENGTH_SHORT).show();
    }


    public void showDialog(boolean cancelable){
    }

    public void dismissDialog(){
    }

    public boolean isDialogShowing(){
        return false;
    }
}
