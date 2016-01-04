package com.sinooceanland.roomhelper.ui.weiget.expandlistview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.bean.ChooseHouseBean;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class ExpandListAdapter extends CommonAdapter<ChooseHouseBean> {

    private int checkPosition = -1;
    private View preView;

    public ExpandListAdapter(Context context, List<ChooseHouseBean> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    protected void getView(ViewHolder holder, ChooseHouseBean bean,int position) {
        holder.setText(R.id.tv, String.valueOf(bean.build));
        holder.setText(R.id.tv_right,String.valueOf(bean.buildSize));
        if(checkPosition == position){
            holder.getConvertView().setBackgroundResource(R.drawable.outline_press);
        }else {
            holder.getConvertView().setBackgroundResource(R.drawable.outline_normal);
        }
    }

    public void setCheckPosition(int position,View convertView){
        this.checkPosition = position;
        if(preView!=null){
            preView.setBackgroundResource(R.drawable.outline_normal);
        }
        preView = convertView;
        preView.setBackgroundResource(R.drawable.outline_press);
    }


}
