package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;
import com.sinooceanland.roomhelper.ui.testdata.ExpandData;
import com.sinooceanland.roomhelper.ui.testdata.TaskData;
import com.sinooceanland.roomhelper.ui.testdata.TestTaskBean;
import com.sinooceanland.roomhelper.ui.weiget.expandlistview.ExpandListControler;

import java.util.List;

/**
 * Created by Jackson on 2015/12/17.
 * Version : 1
 * Details :
 */
public class ChooseBuildingActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    private TextView mTv_load;
    private TextView mTv_msg;
    private TextView mTv_choose_state;
    private EditText mEt_serch;
    private RelativeLayout mRl_content;
    private ListView mLv_room_content;
    private CommonAdapter<TestTaskBean> mAdapter;
    private View mDoubleListView;
    private ExpandListControler mRightControler;
    private ExpandListControler mLeftControler;
    private List<TestTaskBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("这里是选择项目的名字");
        initData();
        initView();
        initListener();
    }

    private void initData() {
        mList = TaskData.getList(20);
        mAdapter = new CommonAdapter<TestTaskBean>(this,mList,R.layout.item_choose_building) {
            @Override
            protected void getView(ViewHolder holder, TestTaskBean bean, int position) {
                holder.setText(R.id.tv_room,bean.name)
                .setText(R.id.tv_state,"未验收");
                holder.setBackgroundResource(R.id.tv_state, R.drawable.btn_blue);

            }
        };
    }

    private void initListener() {
        findViewById(R.id.rl_choose).setOnClickListener(this);
        findViewById(R.id.rl_state).setOnClickListener(this);
        mEt_serch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mLeftControler.dissmissDoubleList();
                mRightControler.dissmissDoubleList();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (start >= 2) {
                    //TODO 这里去查询输入数据
                    if (true) {
                        mTv_msg.setVisibility(View.VISIBLE);
                        mAdapter.setData(null);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                if (start == 0 && before == 1 && count == 0) {
                    mTv_msg.setVisibility(View.GONE);
                    mAdapter.setData(mList);
                }
                Log.e("test", "搜索房屋更新UI");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        mTv_load = (TextView) findViewById(R.id.tv_load);
        mTv_msg = (TextView) findViewById(R.id.tv_msg);
        mTv_choose_state = (TextView) findViewById(R.id.tv_choose_state);
        mEt_serch = (EditText) findViewById(R.id.et_serch);

        mRl_content = (RelativeLayout) findViewById(R.id.rl_content);
        mLv_room_content = (ListView) findViewById(R.id.lv_room_content);
        mLv_room_content.setAdapter(mAdapter);
        mLv_room_content.setOnItemClickListener(this);
        initLeftView();
        initRightView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_choose:
                mRightControler.dissmissDoubleList();
                if(mLeftControler.isGone()){
                    mLeftControler.showListView(
                            ExpandListControler.Position.left,
                            ExpandData.getData(30),
                            !mLeftControler.hasData(ExpandListControler.Position.left));
                }else {
                    mLeftControler.dissmissDoubleList();
                }

                break;
            case R.id.rl_state:
                mLeftControler.dissmissDoubleList();
                if(mRightControler.isGone()){
                    mRightControler.showListView(
                            ExpandListControler.Position.right
                            , ExpandData.getData(20)
                            ,!mRightControler.hasData(ExpandListControler.Position.right));
                }else {
                    mRightControler.dissmissDoubleList();
                }

                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_building;
    }


    private void initLeftView() {
        mLeftControler = new ExpandListControler(this);
        mDoubleListView = mLeftControler.getDoubleListView(
                new ExpandListControler.MyOnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mLeftControler.showListView(ExpandListControler.Position.right, ExpandData.getData(position), true);
                    }
                },
                new ExpandListControler.MyOnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mLeftControler.dissmissDoubleList();
                    }
                });
        mRl_content.addView(mDoubleListView);
    }

    private void initRightView() {
        mRightControler = new ExpandListControler(this);
        mDoubleListView = mRightControler.getDoubleListView(
                new ExpandListControler.MyOnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                },
                new ExpandListControler.MyOnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mRightControler.dissmissDoubleList();
                    }
                });
        mRl_content.addView(mDoubleListView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("test","onTouchEvent");
        mRightControler.dissmissDoubleList();
        mLeftControler.dissmissDoubleList();
        return super.onTouchEvent(event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mAdapter.getData().get(position);//TODO 这里拿到点击的bean 然后跳转
        startActivity(new Intent(this,StartActivity.class));
    }
}
