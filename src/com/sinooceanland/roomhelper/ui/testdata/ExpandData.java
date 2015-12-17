package com.sinooceanland.roomhelper.ui.testdata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackson on 2015/12/16.
 * Version : 1
 * Details :
 */
public class ExpandData {
    public static List<String> getData(int size){
        List<String> list = new ArrayList<String>();
        for(int i=0;i<size;i++){
            list.add(i+"#");
        }
        return list;
    }
}
