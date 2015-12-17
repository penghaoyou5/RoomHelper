package com.sinooceanland.roomhelper.ui.weiget.expandlistview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;

import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class ExpandListAdapter extends CommonAdapter<String> {

    private int checkPosition = -1;
    private View preView;

    public ExpandListAdapter(Context context, List<String> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    protected void getView(ViewHolder holder, String bean,int position) {
        holder.setText(R.id.tv, bean);
        if(checkPosition == position){
            holder.getConvertView().setBackgroundDrawable(getDrawable(R.drawable.outline_press));
        }else {
            holder.getConvertView().setBackgroundDrawable(getDrawable(R.drawable.outline_normal));
        }
    }

    private Drawable getDrawable(int id) {
        return mContext.getResources().getDrawable(id);
    }

    public void setCheckPosition(int position,View convertView){
        this.checkPosition = position;
        if(preView!=null){
            preView.setBackgroundDrawable(getDrawable(R.drawable.outline_normal));
        }
        preView = convertView;
        preView.setBackgroundDrawable(getDrawable(R.drawable.outline_press));
    }


}
