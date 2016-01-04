package com.sinooceanland.roomhelper.ui.weiget.expandlistview;

import android.content.Context;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.control.taskdata.StatusBean;
import com.sinooceanland.roomhelper.ui.common.CommonAdapter;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;

import java.util.List;

/**
 * Created by Jackson on 2015/12/30.
 * Version : 1
 * Details :
 */
public class ExpandRightListAdapter extends CommonAdapter<Object> {

    public ExpandRightListAdapter(Context context, List<Object> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    protected void getView(ViewHolder holder, Object bean, int position) {
        if(bean instanceof StatusBean){
            StatusBean statu = (StatusBean)bean;
            holder.setText(R.id.tv,statu.date);
            return;
        }else {
            holder.setText(R.id.tv,String.valueOf(bean));
        }
    }
}
