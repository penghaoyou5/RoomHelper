package com.sinooceanland.roomhelper.ui.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
<<<<<<< HEAD
=======
import android.widget.Button;
>>>>>>> bd7db19437929ed8e3a979a4de8392288bb9ee94
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.dao.module.ProblemPicture;
import com.sinooceanland.roomhelper.ui.adapter.CheckViewPagerAdapter;

/**
 * Created by Jackson on 2016/1/3.
 * Version : 1
 * Details :
 */
public class CheckAcceptContentActivity extends BaseActivity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager vp_content;
    private ArrayList<ProblemPicture> mProblemList;
    private CheckViewPagerAdapter mAdapter;
    private int mCurrentPage=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initListener() {
        findViewById(R.id.btn_yes).setOnClickListener(this);
        findViewById(R.id.btn_no).setOnClickListener(this);
    }

    private void initData() {
        int position = getIntent().getIntExtra("position", 0);
        HouseMessageData instance = HouseMessageData.getInstance();
        LastCheckProblemList lastCheckProblem = instance.getProblemList().get(position);
        mProblemList = lastCheckProblem.getPicture();
        mAdapter = new CheckViewPagerAdapter(mProblemList, this);
<<<<<<< HEAD
=======
        TextView tv_msg = (TextView) findViewById(R.id.tv_msg);
        tv_msg.setText(lastCheckProblem.EnginTypeFullName);
>>>>>>> bd7db19437929ed8e3a979a4de8392288bb9ee94
    }

    private void initView() {
        vp_content = (ViewPager)findViewById(R.id.vp_content);
        vp_content.setAdapter(mAdapter);
        vp_content.setOnPageChangeListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_check_accept_content;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_yes:
                CheckViewPagerAdapter.ViewHolder holder = (CheckViewPagerAdapter.ViewHolder) vp_content.findViewById(mCurrentPage).getTag();
               if(holder.tv_state.getText().toString().equals("YES"))return;
                boolean sure2 = mAdapter.getDatas().get(mCurrentPage).setPictureIsSure(0);
                holder.tv_state.setText("YES");
                holder.tv_state.setVisibility(View.VISIBLE);
                if(sure2){
                    save();
                }
                break;
            case R.id.btn_no:
                CheckViewPagerAdapter.ViewHolder holder2 = (CheckViewPagerAdapter.ViewHolder) vp_content.findViewById(mCurrentPage).getTag();
                if(holder2.tv_state.getText().toString().equals("NO"))return;
                boolean sure = mAdapter.getDatas().get(mCurrentPage).setPictureIsSure(1);
                holder2.tv_state.setText("NO");
                holder2.tv_state.setVisibility(View.VISIBLE);
                if(sure){
                    save();
                }
                break;
        }
    }

    public void save(){
        //long start = SystemClock.currentThreadTimeMillis();
        TaskMyssageData.saveModifyBigJson();
        //long end = SystemClock.currentThreadTimeMillis();
       // Log.e("test",String.valueOf((end-start)));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
