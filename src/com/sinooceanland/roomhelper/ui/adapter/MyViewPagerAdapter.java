package com.sinooceanland.roomhelper.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.utils.BitmapUtils;
import com.sinooceanland.roomhelper.control.taskdata.HouseMessageData.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/21.
 * Version : 1
 * Details :
 */
public class MyViewPagerAdapter extends PagerAdapter { //显示的数据

    private List<PictureInfo> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;

    public MyViewPagerAdapter(List<PictureInfo> datas, Context context) {
        super();
        this.datas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mViewCache = new LinkedList<View>();
    }

    public void setDatas(List<PictureInfo> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public List<PictureInfo> getDatas(){
        return datas;
    }

    @Override
    public int getCount() {
        Log.e("test", "getCount ");
        if(datas==null) return 0;
        return this.datas.size();
    }

    @Override
    public int getItemPosition(Object object) {
        Log.e("test", "getItemPosition ");
        return super.getItemPosition(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("test", "instantiateItem " + position);
        ViewHolder viewHolder = null;
        View convertView = null;
        if (mViewCache.size() == 0) {
            convertView = this.mLayoutInflater.inflate(R.layout.item_room_laout, null, false);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            viewHolder = new ViewHolder();
            viewHolder.tv = tv;
            viewHolder.iv = iv;
            convertView.setTag(viewHolder);
        } else {
            convertView = mViewCache.removeFirst();
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PictureInfo bean =   datas.get(position);
        ProbleamInfo problem = bean.getProblem();
        if(problem==null ||problem.equals("")){
            viewHolder.tv.setVisibility(View.GONE);
        }else {
            viewHolder.tv.setText(problem.ProblemDescriptionName);
            viewHolder.tv.setVisibility(View.VISIBLE);
        }
        viewHolder.iv.setImageBitmap(BitmapUtils.createBitmap(bean.getSmallPictureUri()));

       // container.addView(convertView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(convertView,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        convertView.setId(position);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("test", "destroyItem " + position);
        View contentView = (View) object;
        container.removeView(contentView);
        this.mViewCache.add(contentView);
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        Log.e("test", "isViewFromObject ");
        return view == o;
    }

    public final class ViewHolder {
        public TextView tv;
        public ImageView iv;
    }

    public void removeView(int position){

    }
}
