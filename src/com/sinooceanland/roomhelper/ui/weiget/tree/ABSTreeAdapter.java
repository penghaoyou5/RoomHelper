package com.sinooceanland.roomhelper.ui.weiget.tree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.sinooceanland.roomhelper.ui.common.ViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/14.
 * Version : 1
 * Details :
 */
public abstract class ABSTreeAdapter extends BaseAdapter {
    protected Context mContext;
    /**
     * 存储所有可见的Node
     */
    protected List<TreeNode> mNodes;
    protected LayoutInflater mInflater;
    /**
     * 存储所有的Node
     */
    protected List<TreeNode> mAllNodes;

    /**
     * 点击的回调接口
     */
    private OnTreeNodeClickListener onTreeNodeClickListener;
    private OnTreeNodeLongClickListener onTreeNodeLongClickListener;

    public interface OnTreeNodeClickListener {
        void onClick(TreeNode node, int position);
    }

    public void setOnTreeNodeClickListener(
            OnTreeNodeClickListener onTreeNodeClickListener) {
        this.onTreeNodeClickListener = onTreeNodeClickListener;
    }

    public interface OnTreeNodeLongClickListener {
        void onLongClick(TreeNode node, int position);
    }

    public void setOnTreeNodeLongClickListener(OnTreeNodeLongClickListener onTreeNodeLongClickListener) {
        this.onTreeNodeLongClickListener = onTreeNodeLongClickListener;
    }

    protected List<TreeDataBean> mAllDatas;
    private ListView mTree;
    private boolean mHasheadview;

    /**
     * @param context
     * @param datas
     */
    public ABSTreeAdapter(ListView tree, Context context, List<TreeDataBean> datas
            , boolean hasHeadView) {
        mTree = tree;
        mAllDatas = new ArrayList<TreeDataBean>();
        mAllDatas.addAll(datas);
        mContext = context;
        mHasheadview = hasHeadView;
        mInflater = LayoutInflater.from(context);
        generaTreeData();
    }

    public void clear() {
        mAllDatas.clear();
        if (mAllNodes != null)
            mAllNodes.clear();
        if (mNodes != null)
            mNodes.clear();
        notifyDataSetChanged();
    }

    public void generaTreeData() {
        try {
            /**
             * 对所有的Node进行排序
             */
            mAllNodes = TreeHelper.getSortedNodes(mAllDatas);
            /**
             * 过滤出可见的Node
             */
            mNodes = TreeHelper.filterVisibleNode(mAllNodes);

            /**
             * 设置节点点击时，可以展开以及关闭；并且将ItemClick事件继续往外公布
             */
            mTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    if (mHasheadview) {
                        expandOrCollapse(position - 1);
                    } else {
                        expandOrCollapse(position);
                    }
                    if (onTreeNodeClickListener != null) {
                        if (mHasheadview) {
                            onTreeNodeClickListener.onClick(mNodes.get(position - 1), position - 1);
                        } else {
                            onTreeNodeClickListener.onClick(mNodes.get(position), position);
                        }
                    }
                }

            });
            /**
             * 设置item长按
             */
            mTree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    if (onTreeNodeLongClickListener != null) {
                        if (mHasheadview) {
                            onTreeNodeLongClickListener.onLongClick(mNodes.get(position - 1), position - 1);
                        } else {
                            onTreeNodeLongClickListener.onLongClick(mNodes.get(position), position);
                        }

                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 相应ListView的点击事件 展开或关闭某节点
     *
     * @param position
     */
    public void expandOrCollapse(int position) {
        TreeNode n = mNodes.get(position);

        if (n != null)// 排除传入参数错误异常
        {
            if (!n.isLeaf()) {
                n.setExpand(!n.isExpand);
                mNodes = TreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();// 刷新视图
            }
        }
    }

    @Override
    public int getCount() {
        return mNodes.size();
    }

    @Override
    public TreeNode getItem(int position) {
        return mNodes.get(position);
    }

    public List<TreeNode> getNodes() {
        return mAllNodes;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getHolder(mContext, convertView, parent, getLayoutId(position, getItem(position)),mInflater);
        getView(holder, getItem(position),position);
        return holder.getConvertView();
    }

    protected abstract void getView(ViewHolder holder, TreeNode bean,int position);

    protected abstract int getLayoutId(int position, TreeNode bean);

    @Override
    public int getViewTypeCount() {
        return getMyViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, getItem(position));
    }

    public abstract int getMyViewTypeCount();

    protected abstract int getItemViewType(int position, TreeNode bean);

}
