package com.sinooceanland.roomhelper.dao.module;

import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;

public class ProblemPicture {
	private LastCheckProblemList lastCheckProblemList;

	public ProblemPicture(String pictureName,String problemDescroption, LastCheckProblemList lastCheckProblemList){
		this.lastCheckProblemList = lastCheckProblemList;
		this.problemDescroption = problemDescroption;
		this.pictureName =  pictureName;
	}
	private String pictureName;
	private String pictureUri;
	/**
	 * 是否已经确认
	 */
	private int pictureIsSure;
	private String problemDescroption;
	
	public String getPictureUri() {
		return SpKey.getProblemPictureAddress()+pictureName;
	}
	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}
	public String isPictureIsSure() {
		int int1 = SpUtilCurrentTaskInfo.getInt(pictureName, -1);
		switch (int1) {
		case 0:
		return "YES";	
		case 1:
		return "NO";
		}
		return null;
	}
	/**
	 * @param pictureIsSure 0 yes 1 no
	 * @return
	 */
	public boolean setPictureIsSure(int pictureIsSure) {
		SpUtilCurrentTaskInfo.putInt(pictureName, pictureIsSure);
		this.pictureIsSure = pictureIsSure;
		return lastCheckProblemList.setCheckStauts();
	}
	public String getProblemDescroption() {
		return problemDescroption;
	}
}
