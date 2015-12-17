package com.sinooceanland.roomhelper.ui.common;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    public Context mContext;
    private List<T> mList;
    private int mLayoutId;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.mList = list;
        mLayoutId = layoutId;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (mList == null) return 0;
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(mContext, convertView, parent, mLayoutId,mInflater);
        getView(holder, getItem(position),position);
        Log.e("test", String.valueOf(position));
        return holder.getConvertView();
    }

    protected abstract void getView(ViewHolder holder, T bean,int position);

    public void setData(List list) {
        mList = list;
    }

    public boolean hasData() {
        return !(mList == null);
    }

    public List<T> getData(){
        return mList;
    }
}
