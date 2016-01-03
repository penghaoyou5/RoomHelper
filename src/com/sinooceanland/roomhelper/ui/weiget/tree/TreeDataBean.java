package com.sinooceanland.roomhelper.ui.weiget.tree;

import java.util.List;

/**
 * Created by Jackson on 2015/12/14.
 * Version : 1
 * Details :
 */
public class TreeDataBean {
    public List<TreeDataBean> list;
    public String SuccessMsg;
    public String ErrorMsg;
    public String EnginTypeCode;
    public String EnginTypeName;
    public String EnginTypeFullName;

    public List<TreeDataBean> Children;
    

    public List<String> AttachmentIDS;
    public String CheckRemark;
    public List<ProblemDescription> AllProblemDescriptionList;
    public List<ProblemDescription> ProblemDescriptionList;

    public static class ProblemDescription {
        public String ProblemDescriptionCode;
        public String ProblemDescriptionName;
    }
}
