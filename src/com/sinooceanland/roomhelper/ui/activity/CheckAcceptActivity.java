package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.dao.module.ProblemPicture;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;
import com.sinooceanland.roomhelper.ui.utils.BitmapUtils;

import java.util.List;

/**
 * Created by Jackson on 2016/1/3.
 * Version : 1
 * Details :
 */
public class CheckAcceptActivity extends BaseActivity implements View.OnClickListener,AdapterView.OnItemClickListener{

    private Button btn_check;
    ListView lv_content;
    CommonAdapter mAdapter;
    private HouseMessageData mHouseMessageData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initview();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    private void initData() {
        mHouseMessageData = HouseMessageData.getInstance();
        List<LastCheckProblemList> problemList = mHouseMessageData.getProblemList();
        mAdapter = new CommonAdapter<LastCheckProblemList>(this,problemList,R.layout.item_check_accept){
            @Override
            protected void getView(ViewHolder holder, LastCheckProblemList bean, int position) {
                ProblemPicture problemPicture = bean.getPicture().get(0);
                String pictureUri = problemPicture.getPictureUri();
                holder.
                        setImage(R.id.iv, BitmapUtils.createBitmap(pictureUri)).
                        setText(R.id.tv_name, problemPicture.getProblemDescroption()).
                        setText(R.id.tv_state, bean.getCheckStauts());
                String state =bean.getCheckStauts();
                if(state!=null)
                holder.setText(R.id.tv_state,state);
            }
        };
    }

    private void initListener() {
        btn_check.setOnClickListener(this);
        lv_content.setOnItemClickListener(this);
    }

    private void initview() {
        lv_content = (ListView) findViewById(R.id.lv_content);
        lv_content.setAdapter(mAdapter);
        btn_check = (Button) findViewById(R.id.btn_check);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_check_accpet;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_check:
                startActivity(new Intent(this,TakePhotoActivity.class));
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent data = new Intent(this,CheckAcceptContentActivity.class);
        data.putExtra("position",position);
        startActivity(data);
    }
}
