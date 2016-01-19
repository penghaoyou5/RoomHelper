package com.sinooceanland.roomhelper.dao.module;

import java.util.ArrayList;

import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;

public class ProblemPicture {

	private final int YES = 0;
	private final int NO = 1;

	private LastCheckProblemList lastCheckProblemList;

	private ArrayList<ProblemPicture> problemPictures;
	
	public void setProblemPictures(ArrayList<ProblemPicture> problemPictures){
		this.problemPictures =problemPictures;
	}
	
	public ProblemPicture(String pictureName,String problemDescroption, LastCheckProblemList lastCheckProblemList){
		this.lastCheckProblemList = lastCheckProblemList;
		this.problemDescroption = problemDescroption;
		this.pictureName =  pictureName;
		int int1 = SpUtilCurrentTaskInfo.getInt(pictureName, -1);
		this.pictureIsSure = int1;
	}
	private String pictureName;
	private String pictureUri;
	/**
	 * 是否已经确认
	 */
	private int pictureIsSure= -1;
	private String problemDescroption;
	
	public String getPictureUri() {
		return SpKey.getProblemPictureAddress()+pictureName;
	}
	public void setPictureUri(String pictureUri) {
		this.pictureUri = pictureUri;
	}
	public String isPictureIsSure() {
		int int1 = SpUtilCurrentTaskInfo.getInt(pictureName, -1);
		this.pictureIsSure = int1;
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
		if(problemPictures!=null)
		for (int i = 0; i < problemPictures.size(); i++) {
			//如果有一张不确认 点击
			int pictureIsSureMy = problemPictures.get(i).pictureIsSure;
			if(!(pictureIsSureMy==YES)){
				lastCheckProblemList.setCheckStauts(CheckedStatue.YES);
				return false;
			}
		}
		return lastCheckProblemList.setCheckStauts(CheckedStatue.NO);
	}
	public String getProblemDescroption() {
		return problemDescroption;
	}
}
