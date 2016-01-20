package com.sinooceanland.roomhelper.control.bean.fuzhu;

import android.os.Parcel;
import android.os.Parcelable;

import com.sinooceanland.roomhelper.dao.base.BaseBean;

import java.util.List;

/**
 * Created by Jackson on 2015/12/30.
 * Version : 1
 * Details :
 */
public  class TaskMessageFuZhu extends BaseBean{

    /**
     * 任务编码
     */
    public String TaskCode;
    /**
     * 任务描述
     * 任务是否已经完成 true已完成 false 未完成
     */
    public String TaskName;
    
    /**
     * 时间
     */
    public String CreateTime;

    /**
     * 0等待上传 1.正在上传
     */
    public boolean isLoading;

    public boolean isFinish;
    public List<BuildingListFuZhu> BuildingList;

}
