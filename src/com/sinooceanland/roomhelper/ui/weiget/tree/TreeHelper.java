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
    public static List<TreeNode> getSortedNodes(List<TreeDataBean> datas,List<TreeNode> nodeList,TreeNode parentNode) {
        if(nodeList==null){
            nodeList= new ArrayList<TreeNode>();
        }
        for (TreeDataBean bean : datas) {
            TreeNode node = formatNode(bean, parentNode);
            nodeList.add(node);
           if(bean.ProblemDescriptionList!=null &&bean.ProblemDescriptionList.size()!=0){
                List<TreeDataBean.ProblemDescription> list = bean.ProblemDescriptionList;
                for(TreeDataBean.ProblemDescription problem:list){
                    TreeNode problemNodenode = formatProblemNode(problem, node);
                    nodeList.add(problemNodenode);
                }
            }else {
               getSortedNodes(bean.Children,nodeList,node);
           }
        }
        return nodeList;
    }




    private static TreeNode formatNode(TreeDataBean bean,TreeNode parentNode) {
        TreeNode node = new TreeNode();
        node.isExpand = false;
        node.id = bean.EnginTypeCode;
        node.name = bean.EnginTypeName;
        node.parentNode = parentNode;
        node.isQuestion = false;
        if(parentNode == null){
            node.level = 0;
            node.pId = null;
        }else {
            node.level = parentNode.level+1;
            node.pId = parentNode.id;
            parentNode.getChildrens().add(node);
        }
        return node;
    }
    private static TreeNode formatProblemNode(TreeDataBean.ProblemDescription problem,TreeNode parentNode) {
        TreeNode node = new TreeNode();
        node.level = 2;//3包含3 为问题的布局
        node.isExpand = false;
        node.id = problem.ProblemDescriptionCode;
        node.name = problem.ProblemDescriptionName;
        node.isQuestion = true;
        if(parentNode!=null){
            node.pId = parentNode.id;
            node.parentNode = parentNode;
            parentNode.getChildrens().add(node);
        }
        return node;
    }



    /*
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

                if(problems==null ||problems.size()==0){
                    secondSmart.isQuestion = true;
                }
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
    }*/

 /*   private static TreeNode formatNode(TreeDataBean bean,int level,String pId,TreeNode parentNode) {
        TreeNode node = new TreeNode();
        node.level = level;
        node.isExpand = false;
        node.pId = pId;
        node.id = bean.EnginTypeCode;
        node.name = bean.EnginTypeName;
        node.parentNode = parentNode;
        node.isQuestion = false;
        return node;
    }*/

/*    private static TreeNode formatProblem2Node(TreeDataBean.ProblemDescription problem,String pId,TreeNode parentNode) {
        TreeNode smart = new TreeNode();
        smart.level = 2;
        smart.isExpand = false;
        smart.pId = pId;
        smart.id = problem.ProblemDescriptionCode;
        smart.name = problem.ProblemDescriptionName;
        smart.parentNode = parentNode;
        smart.isQuestion = true;
        return smart;
    }*/

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
