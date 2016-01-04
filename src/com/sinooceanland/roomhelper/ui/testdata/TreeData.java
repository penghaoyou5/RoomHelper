package com.sinooceanland.roomhelper.ui.testdata;

import com.sinooceanland.roomhelper.ui.weiget.tree.TreeDataBean;
import com.sinooceanland.roomhelper.ui.weiget.tree.TreeDataBean.ProblemDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/14.
 * Version : 1
 * Details :
 */
public class TreeData {

    public List<TreeDataBean> getMyNode(int rootRange,int secondRange,int footRange){
        List<TreeDataBean> root = getNode(rootRange, "根节点");
        for(int i=0;i<rootRange;i++){
            List<TreeDataBean> second = getNode(secondRange, "子节点");
            for(int j=0;j<secondRange;j++){
                List<TreeDataBean.ProblemDescription> problem = getProblem(footRange, "问题");
                second.get(j).ProblemDescriptionList = problem;
            }
            root.get(i).Children = second;
        }
        return root;
    }

    private List<TreeDataBean.ProblemDescription> getProblem(int range,String name){
        ArrayList<TreeDataBean.ProblemDescription> result = new ArrayList<ProblemDescription>();
        for(int i=0;i<range;i++){
            TreeDataBean.ProblemDescription problem = new TreeDataBean.ProblemDescription();
            problem.ProblemDescriptionCode = name+" ID"+i;
            problem.ProblemDescriptionName = name+" name"+i;
            result.add(problem);
        }
        return result;
    }

    private List<TreeDataBean> getNode(int range,String name){
        ArrayList<TreeDataBean> result = new ArrayList<TreeDataBean>();
        for(int i=0;i<range;i++){
            TreeDataBean node = new TreeDataBean();
            node.EnginTypeCode = name+" id" + i;
            node.EnginTypeName = name+" name" + i;
            result.add(node);
        }
        return result;
    }
}
