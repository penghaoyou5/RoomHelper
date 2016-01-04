package com.sinooceanland.roomhelper.ui.weiget.expandlistview;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class ExpandListControler {
    private Context mContext;
    private ExpandListAdapter mLeftAdapter;
    private ExpandRightListAdapter mRightAdapter;
    private ListView mLv_left;
    private ListView mLv_right;
    private MyOnItemClickListener leftListener;
    private MyOnItemClickListener rightListener;
    private View doubleView;
    private boolean isDoubleCreate = false;

    public ExpandListControler(Context context) {
        this.mContext = context;
    }

    public View getDoubleListView(MyOnItemClickListener leftListener, MyOnItemClickListener rightListener) {
        if (!isDoubleCreate) {
            this.leftListener = leftListener;
            this.rightListener = rightListener;
            doubleView = View.inflate(mContext, R.layout.view_double_list, null);
            initDoubleView(doubleView);
            isDoubleCreate = true;
        }
        return doubleView;
    }

    private void initDoubleView(View doubleView) {
        mLv_left = (ListView) doubleView.findViewById(R.id.lv_left);
        mLv_right = (ListView) doubleView.findViewById(R.id.lv_right);
        mLeftAdapter = new ExpandListAdapter(mContext, null, R.layout.item_pop);
        mRightAdapter = new ExpandRightListAdapter(mContext, null, R.layout.item_pop2);
        mLv_left.setAdapter(mLeftAdapter);
        mLv_right.setAdapter(mRightAdapter);
        mLv_left.setOnItemClickListener(new DoubleOnItemClickListener(Position.left));
        mLv_right.setOnItemClickListener(new DoubleOnItemClickListener(Position.right));
        doubleView.setVisibility(View.GONE);
    }

    class DoubleOnItemClickListener implements AdapterView.OnItemClickListener {
        private Position position;

        public DoubleOnItemClickListener(Position position) {
            this.position = position;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (this.position) {
                case left:
                    mLeftAdapter.setCheckPosition(position,view);
                    leftListener.onItemClick(parent, view, position, id,0);
                    break;
                case right:
                    List<Object> data = mRightAdapter.getData();

                    rightListener.onItemClick(parent, view, position, id,data.get(position));
                    break;
            }
        }
    }

    public void showListView(Position position, List data, boolean isFlush) {
        switch (position) {
            case left:
                showListView(mLeftAdapter, data, isFlush);
                break;
            case right:
                showListView(mRightAdapter, data, isFlush);
                break;
        }
    }

    private void showListView(CommonAdapter adapter, List data, boolean isFlush) {
        if (isFlush) {
            adapter.setData(data);
            adapter.notifyDataSetInvalidated();
        }
        showDoubleList();
    }

    public void dissmissListView(Position position) {
        switch (position) {
            case left:
                mLv_left.setVisibility(View.GONE);
                break;
            case right:
                mLv_right.setVisibility(View.GONE);
                break;
        }
    }

    public enum Position {
        left, right
    }

    public interface MyOnItemClickListener<T> {
        void onItemClick(AdapterView<?> parent, View view, int position, long id,T bean);
    }

    public void dissmissDoubleList() {
        doubleView.setVisibility(View.GONE);
    }

    public boolean hasData(Position position) {
        CommonAdapter adapter = null;
        switch (position) {
            case left:
                adapter =  mLeftAdapter;
                break;
            case right:
                adapter =  mRightAdapter;
                break;
        }
        return adapter.hasData();
    }

    public void showDoubleList(){
        doubleView.setVisibility(View.VISIBLE);
    }

    public boolean isGone(){
       return View.GONE == doubleView.getVisibility();
    }
}
