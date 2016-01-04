package com.sinooceanland.roomhelper.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.testdata.RoomBean;
import com.sinooceanland.roomhelper.ui.utils.BitmapUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/19.
 * Version : 1
 * Details :
 */
public class PhotoPagerAdapter extends PagerAdapter { //

    private List<RoomBean> datas = null;
    private LinkedList<View> mViewCache = null;
    private Context mContext;
    private LayoutInflater mLayoutInflater = null;

    public PhotoPagerAdapter(List<RoomBean> datas, Context context) {
        super();
        this.datas = datas;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mViewCache = new LinkedList<View>();
    }

    public void setDatas(List<RoomBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() { //总数量
        return datas.size();
    }

    @Override //固定写法
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override //固定写法
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override //向viewpager中填充view的方法
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.item_room_laout, null);
        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        Bitmap bitmap = BitmapUtils.createBitmap(datas.get(position).imagePaths);
        iv.setImageBitmap(bitmap);
        container.addView(view);
        return view;
    }
}
