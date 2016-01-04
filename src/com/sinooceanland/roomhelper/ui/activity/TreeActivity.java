package com.sinooceanland.roomhelper.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.net.RequestNet;
import com.sinooceanland.roomhelper.ui.testdata.TreeData;
import com.sinooceanland.roomhelper.ui.weiget.tree.ABSTreeAdapter;
import com.sinooceanland.roomhelper.ui.weiget.tree.ImplTreeAdapter;
import com.sinooceanland.roomhelper.ui.weiget.tree.TreeDataBean;
import com.sinooceanland.roomhelper.ui.weiget.tree.TreeNode;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class TreeActivity extends BaseActivity implements ABSTreeAdapter.OnTreeNodeClickListener{

    private ImplTreeAdapter treeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("选择问题");
        initData();
    }

    private void initData() {
        new RequestNet(this).getprojectProblemByNet(new BaseNet.BaseCallBack<TreeDataBean>() {
            @Override
            public void messageResponse(BaseNet.RequestType requestType, TreeDataBean bean, String message) {
                if(requestType== BaseNet.RequestType.messagetrue)
                setAdapter(bean);
                else showToast("网络连接超时");
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_tree_test;
    }


    private void setAdapter(TreeDataBean bean){
        ListView lv = (ListView) findViewById(R.id.lv);
        treeAdapter = new ImplTreeAdapter(lv, this, bean.list, false);
        lv.setAdapter(treeAdapter);
        treeAdapter.setOnTreeNodeClickListener(this);
    }

    @Override
    public void onClick(TreeNode node, int position) {
        if(node.isQuestion){
            Intent intent = new Intent();
            intent.putExtra("question3",node.name);
            intent.putExtra("questionCode3",node.id);
            intent.putExtra("question1",node.parentNode.parentNode.name);
            intent.putExtra("questionCode1",node.parentNode.parentNode.id);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}