package com.sinooceanland.roomhelper.ui.testdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class TaskData {
    public static List<TestTaskBean> getList(int size){
        ArrayList<TestTaskBean> arr = new ArrayList<TestTaskBean>();
        for(int i=0;i<size;i++){
            TestTaskBean bean = new TestTaskBean();
            bean.name="name"+i;
            bean.state="state"+i;
            if(i%2==0) bean.isLoad =true;
            arr.add(bean);
        }
        return arr;
    }
}
