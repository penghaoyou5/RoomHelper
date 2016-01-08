package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData;
import com.sinooceanland.roomhelper.control.taskdata.StatusBean;
import com.sinooceanland.roomhelper.control.taskdata.TaskMyssageData;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;
import com.sinooceanland.roomhelper.ui.utils.MyProgressDialog;
import com.sinooceanland.roomhelper.ui.weiget.expandlistview.ExpandListControler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/17.
 * Version : 1
 * Details :这里写传进来的数据
 */
public class ChooseBuildingActivity extends BaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AbsListView.OnScrollListener {

    private TextView mTv_load;
    private TextView mTv_msg;
    private TextView mTv_choose_state;
    private EditText mEt_serch;
    private RelativeLayout mRl_content;
    private ListView mLv_room_content;
    private CommonAdapter<HouseMessage> mAdapter;//这个是用来显示具体房间的adapter
    private View mDoubleListView;
    private ExpandListControler mRightControler;
    private ExpandListControler mLeftControler;
    private List<HouseMessage> mList;
    private TaskMyssageData taskMyssageData;
    private ArrayList<ChooseHouseBean> mBuilds;
    private ArrayList<StatusBean> mStatus;
    private MyProgressDialog mDialog;


    private boolean mIsSearch =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
        initListener();
    }

    private void initData() {
         taskMyssageData = TaskMyssageData.getInstance();

        setTitle(taskMyssageData.getTaskName());

        mList = taskMyssageData.getHomeList(1);
        mBuilds = taskMyssageData.getBuildingInformation();
        mStatus = taskMyssageData.getStatus();
    }

    private void setTextViewState(TextView tv, String state) {
        //TODO 这里修改判断状态
        switch (Integer.parseInt(state)) {
            case 0:
                tv.setText("未验收");
                tv.setBackgroundResource(R.drawable.btn_gray2);
                break;
            case 1:
                tv.setText("已验收未通过");
                tv.setBackgroundResource(R.drawable.btn_red);
                break;
            case 2:
                tv.setText("验收已通过");
                tv.setBackgroundResource(R.drawable.btn_green);
                break;
        }
    }

    private void initListener() {
        mLv_room_content.setOnScrollListener(this);
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
                    mIsSearch = true;
                    String msg = s.toString();
                    mDialog.showDialog(ChooseBuildingActivity.this);
                    List<HouseMessage> houseByHouseName = taskMyssageData.getHouseByHouseName(msg);
                    mDialog.dismissDialog();
                    if (houseByHouseName == null || houseByHouseName.size() == 0) {
                        mTv_msg.setVisibility(View.VISIBLE);
                        mAdapter.setData(null);
                        mAdapter.notifyDataSetChanged();

                    } else {
                        mTv_msg.setVisibility(View.GONE);
                        mAdapter.setData(houseByHouseName);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                if (start == 0 && before == 1 && count == 0) {
                    mTv_msg.setVisibility(View.GONE);
                    mAdapter.setData(mList);
                    mAdapter.notifyDataSetChanged();
                    mIsSearch = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initView() {
        mAdapter = new CommonAdapter<HouseMessage>(this, mList, R.layout.item_choose_building) {
            @Override
            protected void getView(ViewHolder holder, HouseMessage bean, int position) {
                holder.setText(R.id.tv_room, bean.ActHouseName);
                TextView tv_state = holder.<TextView>getView(R.id.tv_state);
                setTextViewState(tv_state, bean.CheckStauts);
            }
        };
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

        mDialog = new MyProgressDialog();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_choose:
                mRightControler.dissmissDoubleList();
                if (mLeftControler.isGone()) {
                    mLeftControler.showListView(
                            ExpandListControler.Position.left,
                            mBuilds,//TODO 这里写传进来的数据
                            !mLeftControler.hasData(ExpandListControler.Position.left));
                } else {
                    mLeftControler.dissmissDoubleList();
                }

                break;
            case R.id.rl_state:
                mLeftControler.dissmissDoubleList();
                if (mRightControler.isGone()) {
                    mRightControler.showListView(
                            ExpandListControler.Position.right
                            , mStatus//TODO 这里写传进来的数据
                            , !mRightControler.hasData(ExpandListControler.Position.right));
                } else {
                    mRightControler.dissmissDoubleList();
                }

                break;
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_building;
    }


    private int tempBuild = -1;

    private void initLeftView() {
        mLeftControler = new ExpandListControler(this);
        mDoubleListView = mLeftControler.getDoubleListView(
                new ExpandListControler.MyOnItemClickListener<Object>() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, Object bean) {
                        //TODO 这里写点击后出现的数据，用来展示二级菜单
                        ChooseHouseBean chooseHouseBean = mBuilds.get(position);
                        tempBuild = chooseHouseBean.build;
                        mLeftControler.showListView(ExpandListControler.Position.right, chooseHouseBean.houseCode, true);
                    }
                },
                new ExpandListControler.MyOnItemClickListener<Integer>() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, Integer bean) {
                        mLeftControler.dissmissDoubleList();
                        //TODO 这里写点击704房间的搜索
                        HouseMessage houseMessage = taskMyssageData.getHouseByBuildNameAndHouseName(
                                String.valueOf(tempBuild),
                                String.valueOf(bean));
                        ArrayList<HouseMessage> arr = new ArrayList<HouseMessage>();
                        arr.add(houseMessage);
                        mAdapter.setData(arr);
                    }
                });
        mRl_content.addView(mDoubleListView);
    }

    private String checkStatu = "-1";
    private void initRightView() {
        mRightControler = new ExpandListControler(this);
        mDoubleListView = mRightControler.getDoubleListView(
                new ExpandListControler.MyOnItemClickListener<Object>() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, Object bean) {
                        //TODO 这里什么都不写
                    }
                },
                new ExpandListControler.MyOnItemClickListener<StatusBean>() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, StatusBean bean) {
                        mRightControler.dissmissDoubleList();
                        checkStatu = bean.id;
                        mLoadTime = 1;
                        mList = taskMyssageData.getHomeList(mLoadTime, checkStatu);
                        mAdapter.setData(mList);
                        mAdapter.notifyDataSetChanged();
                    }
                });
        mRl_content.addView(mDoubleListView);
    }

    private final int REQUEST_CODE_OK = 2;

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (Integer.parseInt(mAdapter.getData().get(position).CheckStauts)) {
            case 0://未验收
                HouseMessage houseMessage = mAdapter.getData().get(position);//TODO 这里拿到点击的bean 然后跳转测量房间
                HouseMessageData.setHouseMessage(houseMessage);
                startActivityForResult(new Intent(this, StartActivity.class), REQUEST_CODE_OK);
                break;
            case 1:
                HouseMessage houseMessage2 = mAdapter.getData().get(position);//TODO 这里拿到点击的bean 然后跳转测量房间
                HouseMessageData.setHouseMessage(houseMessage2);
                // startActivityForResult(new Intent(this, StartActivity.class), REQUEST_CODE_OK);
                startActivity(new Intent(this, CheckAcceptActivity.class));
                break;
            case 2:
                showToast("验收已通过");
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_OK == requestCode && resultCode == RESULT_OK) {
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private int mLoadTime = 1;

    //--------滑动监听
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if(mIsSearch)return;
        if (scrollState == SCROLL_STATE_IDLE) {//滑动停止了
            if (view.getLastVisiblePosition() == view.getCount() - 1) {//滑动到底部了
                mLoadTime++;
                List<HouseMessage> statueList = taskMyssageData.getListByStatue(mList, checkStatu);
                mList.addAll(mList.size() - 1, statueList);
                mAdapter.setData(mList);
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
    //--------滑动监听

}
