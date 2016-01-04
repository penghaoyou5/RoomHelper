package com.sinooceanland.roomhelper.ui.weiget.tree;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.sinooceanland.roomhelper.R;
import com.sinooceanland.roomhelper.ui.common.ViewHolder;

import java.util.List;

/**
 * Created by Jackson on 2015/12/14.
 * Version : 1
 * Details :
 */
public class ImplTreeAdapter extends ABSTreeAdapter {
    /**
     * @param tree
     * @param context
     * @param datas
     * @param hasHeadView
     */
    public ImplTreeAdapter(ListView tree, Context context, List<TreeDataBean> datas, boolean hasHeadView) {
        super(tree, context, datas, hasHeadView);
    }

    @Override
    protected void getView(ViewHolder holder, TreeNode bean,int position) {
        holder.setText(R.id.tv, bean.name);
        switch (bean.level){
            case 0://root
                if(bean.isExpand){
                    setRoot(holder,0, R.color.colorBlue,R.color.colorWhite);
                }else {
                    setRoot(holder, R.drawable.nest, R.color.colorBlue,R.color.colorWhite);
                }
                break;
            case 1:
                if(bean.isExpand){
                    setSecond(holder, R.drawable.nest_up, R.color.colorWhite, R.color.colorBlue);
                }else {
                    setRoot(holder, R.drawable.nest, R.color.colorWhite,R.color.textColor2);
                }
                break;
            case 2://foot
               // holder.setText(R.id.tv,bean.name);
                break;
        }
    }

    private void setRoot(ViewHolder holder,int imageId,int backGroundColorId,int textColor){

        if(imageId==0){
            holder.getView(R.id.iv).setVisibility(View.INVISIBLE);
        }else {
            holder.setImage(R.id.iv, imageId);
            holder.getView(R.id.iv).setVisibility(View.VISIBLE);
        }
        TextView tv = holder.getView(R.id.tv);
        tv.setBackgroundColor(getColor(backGroundColorId));
        tv.setTextColor(getColor(textColor));
    }

    private void setSecond(ViewHolder holder,int imageId,int backGroundColorId,int textColor){
        holder.setImage(R.id.iv,imageId);
        TextView tv = holder.getView(R.id.tv);
        tv.setBackgroundColor(getColor(backGroundColorId));
        tv.setTextColor(getColor(textColor));
    }

    @Override
    protected int getLayoutId(int position, TreeNode bean) {
        int layout=0;
        switch (bean.level){
            case 0://root
                layout = R.layout.item_question_root;
                break;
            case 1:
                layout = R.layout.item_question_second;
                break;
            case 2://foot
                layout = R.layout.item_question_footer;
                break;
        }
        return layout;
    }

    @Override
    public int getMyViewTypeCount() {
        return 3;
    }

    @Override
    protected int getItemViewType(int position, TreeNode bean) {
        return bean.level;
    }

    public int getColor(int colorId){
       return mContext.getResources().getColor(colorId);
    }
}
