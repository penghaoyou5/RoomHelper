package com.sinooceanland.roomhelper.ui.weiget.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/14.
 * Version : 1
 * Details :
 */
public class TreeHelper {

    /**
     * 传入我们的普通bean，转化为我们排序后的Node
     */
    public static List<TreeNode> getSortedNodes(List<TreeDataBean> datas) {
        List<TreeNode> rootSmarts = new ArrayList<TreeNode>();
        for (TreeDataBean node : datas) {
            TreeNode rootSmart = formatNode(node, 0, "-1", null);

            //将二级bean变换后挂到一级bean上
            List<TreeDataBean> rootChildrens = node.Children;
            List secondSmarts = rootSmart.getChildrens();
            for (TreeDataBean node1 : rootChildrens) {
                TreeNode secondSmart = formatNode(node1, 1, rootSmart.id, rootSmart);

                //将problem挂到二级bean上
                List<TreeDataBean.ProblemDescription> problems = node1.ProblemDescriptionList;
                List footSmarts = secondSmart.getChildrens();
                for (TreeDataBean.ProblemDescription problem : problems) {
                    TreeNode footSmart = formatProblem2Node(problem, secondSmart.id, secondSmart);
                    footSmarts.add(footSmart);
                }

                secondSmarts.add(secondSmart);
            }
            rootSmarts.add(rootSmart);
        }
        return rootSmarts;
    }

    private static TreeNode formatNode(TreeDataBean node,int level,String pId,TreeNode parentNode) {
        TreeNode smart = new TreeNode();
        smart.level = level;
        smart.isExpand = false;
        smart.pId = pId;
        smart.id = node.EnginTypeCode;
        smart.name = node.EnginTypeName;
        smart.parentNode = parentNode;
        smart.isQuestion = false;
        return smart;
    }

    private static TreeNode formatProblem2Node(TreeDataBean.ProblemDescription problem,String pId,TreeNode parentNode) {
        TreeNode smart = new TreeNode();
        smart.level = 2;
        smart.isExpand = false;
        smart.pId = pId;
        smart.id = problem.ProblemDescriptionCode;
        smart.name = problem.ProblemDescriptionName;
        smart.parentNode = parentNode;
        smart.isQuestion = true;
        return smart;
    }

    private static List<TreeNode> getRootNodes(List<TreeNode> nodes) {
        List<TreeNode> root = new ArrayList<TreeNode>();
        for (TreeNode node : nodes) {
            if (node.isRoot())
                root.add(node);
        }
        return root;
    }


    /**
     * 过滤出所有可见的Node
     * @param nodes
     * @return
     */
    public static List<TreeNode> filterVisibleNode(List<TreeNode> nodes) {
        List<TreeNode> result = new ArrayList<TreeNode>();
        for (TreeNode node : nodes) {
            // 如果为跟节点，或者上层目录为展开状态
            if (node.isRoot() ) {
                showExpand(node,result);
            }
        }
        return result;
    }

    public static void showExpand(TreeNode node, List<TreeNode> result){
        result.add(node);
        if(node.isExpand){
            List<TreeNode> childrens = node.getChildrens();
            for (TreeNode node1:childrens){
                showExpand(node1,result);
            }
        }
    }

}
