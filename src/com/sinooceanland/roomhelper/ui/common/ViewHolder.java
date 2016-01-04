package com.sinooceanland.roomhelper.ui.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class ViewHolder {
    private final SparseArray<View> viewSparseArray;
    private final View mConvertView;
    public ViewHolder(Context context, ViewGroup parent, int layoutId,LayoutInflater inflater) {
        viewSparseArray = new SparseArray<View>();
        mConvertView = inflater.inflate(layoutId,null);
        mConvertView.setTag(this);
    }

    public static ViewHolder getHolder(Context ctx, View convertView, ViewGroup parent, int layoutId,LayoutInflater inflater) {
        if (convertView == null) {
            return new ViewHolder(ctx,parent,layoutId,inflater);
        }
        return (ViewHolder) convertView.getTag();
    }

    public <T extends View> T getView(int id) {
        View view = viewSparseArray.get(id);
        if(view == null){
            view = mConvertView.findViewById(id);
            viewSparseArray.put(id,view);
        }
        return (T) view;
    }
    public View getConvertView(){
        return mConvertView;
    }

    public ViewHolder setText(int id,String text){
        TextView tv = getView(id);
        tv.setText(text);
        return this;
    }

    public ViewHolder setImage(int ViewId,int drawableId){
        ImageView iv = (ImageView)getView(ViewId);
        iv.setImageResource(drawableId);
        return this;
    }
    public ViewHolder setImage(int ViewId,Bitmap bitmap){
        ImageView iv = (ImageView)getView(ViewId);
        iv.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setBackgroundResource(int ViewId,int resid){
        getView(ViewId).setBackgroundResource(resid);
        return this;
    }


}
