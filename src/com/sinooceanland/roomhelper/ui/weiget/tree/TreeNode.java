package com.sinooceanland.roomhelper.ui.weiget.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/14.
 * Version : 1
 * Details :
 */
public class TreeNode {
    public int level;
    public boolean isExpand =false;
    public String pId;
    public String id;
    public String name;
    private List<TreeNode> childrens;
    public TreeNode parentNode;
    public boolean isQuestion;
    public List<TreeNode> getChildrens(){
        if(childrens==null){
            childrens = new ArrayList<TreeNode>();
        }
        return childrens;
    }

    public boolean isRoot(){
        return level == 0;
    }

    /**
     * 设置展开
     *
     * @param isExpand
     */
    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
        if (!isExpand) {
            for (TreeNode node : getChildrens()) {
                node.setExpand(isExpand);
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)                                      //先检查是否其自反性，后比较other是否为空。这样效率高
            return true;
        if (other == null)
            return false;
        if (!(other instanceof TreeNode))
            return false;

        final TreeNode node = (TreeNode) other;

        if (!getId().equals(((TreeNode) other).getId()))
            return false;
//        if (!getName().equals(((Node) other).getName()))
//            return false;
        return true;
    }

    public String getId() {
        return id;
    }

    public boolean isParentExpand(){
        return parentNode.isExpand;
    }

    /**
     * 是否是叶子界点
     *
     * @return
     */
    public boolean isLeaf() {
        if(childrens==null){
            return false;
        }
        return childrens.size() == 0;
    }
}
