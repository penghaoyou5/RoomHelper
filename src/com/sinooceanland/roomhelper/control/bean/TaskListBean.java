package com.sinooceanland.roomhelper.control.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

import com.sinooceanland.roomhelper.dao.base.BaseBean;

/**
 * @author peng
 * 任务列表
 */
public class TaskListBean extends BaseBean implements Parcelable{

	public String message;
	public int code;
	public List<TaskMessage> list;
	public TaskListBean(){}


	protected TaskListBean(Parcel in) {
		message = in.readString();
		code = in.readInt();
		list = in.createTypedArrayList(TaskMessage.CREATOR);
	}

	public static final Creator<TaskListBean> CREATOR = new Creator<TaskListBean>() {
		@Override
		public TaskListBean createFromParcel(Parcel in) {
			return new TaskListBean(in);
		}

		@Override
		public TaskListBean[] newArray(int size) {
			return new TaskListBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(message);
		dest.writeInt(code);
		dest.writeTypedList(list);
	}
}
