package com.sinooceanland.roomhelper.control.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Jackson on 2015/12/30.
 * Version : 1
 * Details :
 */
public class BuildingList implements Parcelable {

    public String BuildingCode;
    public List<String> UnitCode;

    public BuildingList(){}

    protected BuildingList(Parcel in) {
        BuildingCode = in.readString();
        UnitCode = in.createStringArrayList();
    }



//    public static final Creator<BuildingList> CREATOR = new Creator<BuildingList>() {
//        @Override
//        public BuildingList createFromParcel(Parcel in) {
//            return new BuildingList(in);
//        }
//
//        @Override
//        public BuildingList[] newArray(int size) {
//            return new BuildingList[size];
//        }
//    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(BuildingCode);
        dest.writeStringList(UnitCode);
    }
}
