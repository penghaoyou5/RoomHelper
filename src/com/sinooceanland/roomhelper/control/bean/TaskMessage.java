package com.sinooceanland.roomhelper.control.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.sinooceanland.roomhelper.dao.base.BaseBean;

import java.util.List;

/**
 * Created by Jackson on 2015/12/30.
 * Version : 1
 * Details :
 */
public  class TaskMessage extends BaseBean implements Parcelable {

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
     * 0等待上传 1.正在上传
     */
    public boolean isLoading;

    public boolean isFinish;
    public List<BuildingList> buildingList;
    public TaskMessage(){}

    protected TaskMessage(Parcel in) {
        TaskCode = in.readString();
        TaskName = in.readString();
        isLoading = in.readByte() != 0;
        isFinish = in.readByte() != 0;
    }

    public static final Creator<TaskMessage> CREATOR = new Creator<TaskMessage>() {
        @Override
        public TaskMessage createFromParcel(Parcel in) {
            return new TaskMessage(in);
        }

        @Override
        public TaskMessage[] newArray(int size) {
            return new TaskMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(TaskCode);
        dest.writeString(TaskName);
        dest.writeByte((byte) (isLoading ? 1 : 0));
        dest.writeByte((byte) (isFinish ? 1 : 0));
    }
}
