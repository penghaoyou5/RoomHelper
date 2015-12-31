package com.sinooceanland.roomhelper.control.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class ImageMessageBean implements Parcelable{
	public String bigPicureUrl;
	public String smallPictureUrl;
	public boolean statue;
	public String problem;

	protected ImageMessageBean(Parcel in) {
		bigPicureUrl = in.readString();
		smallPictureUrl = in.readString();
		statue = in.readByte() != 0;
		problem = in.readString();
	}

	public static final Creator<ImageMessageBean> CREATOR = new Creator<ImageMessageBean>() {
		@Override
		public ImageMessageBean createFromParcel(Parcel in) {
			return new ImageMessageBean(in);
		}

		@Override
		public ImageMessageBean[] newArray(int size) {
			return new ImageMessageBean[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(bigPicureUrl);
		dest.writeString(smallPictureUrl);
		dest.writeByte((byte) (statue ? 1 : 0));
		dest.writeString(problem);
	}
}
