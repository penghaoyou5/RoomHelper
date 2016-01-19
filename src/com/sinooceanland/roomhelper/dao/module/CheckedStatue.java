package com.sinooceanland.roomhelper.dao.module;

/**
 * Created by Jackson on 2016/1/19.
 */
public enum CheckedStatue {
    YES("0"),NO("1");
    String state;
    CheckedStatue(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }
}
