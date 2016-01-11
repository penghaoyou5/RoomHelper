package com.sinooceanland.roomhelper.control.taskdata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.MD5Util;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.dao.module.HouseMessage;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.LastCheckProblemList;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList.EnginTypeList;
import com.sinooceanland.roomhelper.dao.module.HouseMessage.SpaceLayoutList.EnginTypeList.ProblemDescriptionList;

public class HouseMessageData {

	// 这是保存homMessage 数据 为了保持始终一个引用初始化后能拿到所以静态
	private static HouseMessage homMessage;
//	private String bigPickturUrl;
//	private String smallPickturUrl;

	private static HouseMessageData data;
	
//	private HouseMessageData(HouseMessage homMessage) {
//		HouseMessageData.homMessage = homMessage;
//		bigPickturUrl = SpKey.getBigPictureAddress();
//		smallPickturUrl = SpKey.getSmallPictureAddress();
//	}
	
	private HouseMessageData(){}

	/**
	 * 记录当前进入的房间
	 * 
	 * @param homMessage
	 * @return
	 */
	public static HouseMessageData setHouseMessage(HouseMessage homMessage) {
		if(data==null){
			data = new HouseMessageData();
		}
		HouseMessageData.homMessage = homMessage;
//		data.bigPickturUrl = SpKey.getBigPictureAddress();
//		data.smallPickturUrl = SpKey.getSmallPictureAddress();
		return data;
	}
	
	public static HouseMessageData getInstance(){
		return data;
	}

	/**
	 * 得到HouseMessage 对象
	 * 
	 * @return
	 */
	public HouseMessage getHouseMessage() {
		return homMessage;
	}

	/**
	 * 点击布局名称调用 空间布局列表 里面的EnginTypeList集合代表了图片数据
	 * 
	 * @return
	 */
	public SpaceLayoutListHelper getSpaceLayoutList() {
//		for (int i = 0; i < homMessage.SpaceLayoutList.size(); i++) {
//			// 这是布局一级
//			SpaceLayoutList spaceLayoutList = homMessage.SpaceLayoutList.get(i);
//			// 这是布局中的照片集合
//			List<EnginTypeList> enginTypeList = spaceLayoutList.EnginTypeList;
//
//		}

		return new SpaceLayoutListHelper();
	}

	/**
	 * @author peng 由于所要数据与接口数据不统一所以只能封装类处理
	 * 这是空间布局的辅助类
	 */
	public class SpaceLayoutListHelper {
		List<EnginTypeList> enginTypeLists;

		/**
		 * @return 布局空间列表
		 */
		public List<SpaceLayoutList> getSpaceLayoutList() {
			return homMessage.SpaceLayoutList;
		}
		
		/**
		 * 这是进行图片保存的类
		 */
		public void savePickInfo(){
			
		}

		
		/**
		 * 增加图片信息 或者修改图片信息
		 * @param layoutPotion 是第几个布局？
		 * @param imageName 图片名
		 * @param sure 是否确认
		 * @param pinfo 问题
		 * @return 
		 */
		public PictureInfo addPictureInfoOrModify(int layoutPotion,String imageName,boolean sure,ProbleamInfo proinfo){
			//得到布局
			SpaceLayoutList spaceLayoutList = homMessage.SpaceLayoutList.get(layoutPotion);
			List<String> attachmentIDS = spaceLayoutList.AttachmentIDS;
			if(attachmentIDS==null){
				attachmentIDS=new ArrayList<String>();
			}
			if(!attachmentIDS.contains(imageName)){
				attachmentIDS.add(imageName);
			}
			
			PictureInfo pictureInfo = new PictureInfo();
			pictureInfo.pictureUri = imageName;
			pictureInfo.isSure = sure;
			pictureInfo.problem = proinfo;
			String json = BaseNet.getGson().toJson(pictureInfo);
			SpUtilCurrentTaskInfo.putString(imageName, json);
			return pictureInfo;
		}
		
		public PictureInfo newInstancePictureInfo(int layoutPotion,String imageName){
			//得到布局
			SpaceLayoutList spaceLayoutList = homMessage.SpaceLayoutList.get(layoutPotion);
			if(spaceLayoutList.AttachmentIDS==null){
				spaceLayoutList.AttachmentIDS=new ArrayList<String>();
			}
			if(!spaceLayoutList.AttachmentIDS.contains(imageName)){
				spaceLayoutList.AttachmentIDS.add(imageName);
			}
			PictureInfo pictureInfo = new PictureInfo();
			pictureInfo.pictureUri = imageName;
			String json = BaseNet.getGson().toJson(pictureInfo);
			SpUtilCurrentTaskInfo.putString(imageName, json);
			return pictureInfo;
		}
		
		//这是进行图片删除的方法
		public void deletePicture(int layoutPotion,PictureInfo pictureInfo){
			SpaceLayoutList spaceLayoutList = homMessage.SpaceLayoutList.get(layoutPotion);
			List<String> attachmentIDS = spaceLayoutList.AttachmentIDS;
			if(attachmentIDS!=null&&attachmentIDS.contains(pictureInfo.pictureUri)){
				attachmentIDS.remove(pictureInfo.pictureUri);
			}
			new File(pictureInfo.getBigPictureUri()).deleteOnExit();
			new File(pictureInfo.getSmallPictureUri()).deleteOnExit();
		}
		
		/**
		 * 通过图片名得到图片信息
		 * @param pictureName
		 * @return
		 */
		public PictureInfo getPictureInfo(String pictureName){
			String inf = SpUtilCurrentTaskInfo.getString(pictureName, "");
			PictureInfo info = BaseNet.getGson().fromJson(inf, PictureInfo.class);
			return info;
		}
		/**
		 * 根据空间布局返回图片列表信息
		 * 
		 * @param i
		 * @return
		 */
		public List<PictureInfo> getPotion(int i) {
			List<PictureInfo> infos = new ArrayList<HouseMessageData.PictureInfo>();

			// 这是返回布局一级
			SpaceLayoutList spaceLayoutList = homMessage.SpaceLayoutList.get(i);

			// 每个房间都有一个问题列表但是这里太过了
			List<EnginTypeList> enginTypeLists = spaceLayoutList
					.getEnginTypeList();
			// 要根据布局一级返回图片信息
			List<String> attachmentIDS = spaceLayoutList.AttachmentIDS;
			if(attachmentIDS==null)
				return infos;//如果图片为空返回null
			// 循环便利图片获得所有图片信息进行处理返回
			for (int j = 0; j < attachmentIDS.size(); j++) {
				String uri = attachmentIDS.get(j);
				//根据照片名获得一切信息  如果已经经过了照片存储那么信息一定不为空
				String inf = SpUtilCurrentTaskInfo.getString(uri, "");
				PictureInfo info = BaseNet.getGson().fromJson(inf, PictureInfo.class);
				infos.add(info);
//				// 这是单张照片路径
//				PictureInfo info = new PictureInfo();
//				info.bigPictureUri = bigPickturUrl + uri;
//				info.smallPictureUri = smallPickturUrl + uri;
				

//				// 因为一个照片对应一个问题 所以只能，一个问题列表中只有一个照片
//				if (enginTypeLists != null && enginTypeLists.size() > 0) {
//					EnginTypeList enginTypeList2 = enginTypeLists.get(0);
//					for (int k = 0; k < enginTypeLists.size(); k++) {
//						if (info.equals(enginTypeList2.getAttachmentIDS()
//								.get(0))) {
//							//如果这张照片是有问题的照片那么就添加问题
//							info.problem = enginTypeList2.ProblemDescriptionList.get(0).ProblemDescriptionName;
//							info.isProblem = false;
//						}
//
//					}
//				}
			}
			return infos;
		}
	}

	public class PictureInfo {
		private String pictureUri;
		public void setPictureUri(String pictureUri){
			this.pictureUri = pictureUri;
		}
		
		public String getBigPictureUri() {
			return SpKey.getBigPictureAddress()+pictureUri;
		}
		
		public String getSmallPictureUri() {
			return SpKey.getSmallPictureAddress()+pictureUri;
		}
		
		public boolean isSure() {
			return isSure;
		}

		public void setSure(boolean isSure) {
			this.isSure = isSure;
			String json = BaseNet.getGson().toJson(this);
			SpUtilCurrentTaskInfo.putString(pictureUri, json);
		}

		public ProbleamInfo getProblem() {
			return problem;
		}

		public void setProblem(ProbleamInfo problem) {
			this.problem = problem;
			String json = BaseNet.getGson().toJson(this);
			SpUtilCurrentTaskInfo.putString(pictureUri, json);
		}

		public String getPictureUri() {
			return pictureUri;
		}



		private boolean isSure = false;
		private ProbleamInfo problem;
		public void  setPictureProbleam(String EnginTypeCode,String ProblemDescriptionCode,String ProblemDescriptionName){
			if(problem==null)
			problem = new ProbleamInfo();
			problem.EnginTypeCode = EnginTypeCode;
			problem.ProblemDescriptionCode = ProblemDescriptionCode;
			problem.ProblemDescriptionName = ProblemDescriptionName;
		}
//		public String problem;
	}
	public static class ProbleamInfo{
		
		/**
		 * 第一层问题的编码 
		 */
		public String EnginTypeCode;
		/**
		 * 第三层问题编码
		 */
		public String ProblemDescriptionName;
		/**
		 * 第三层问题描述
		 */
		public String ProblemDescriptionCode;
	}
	
//	//TODO:请求结果
//	public List<HouseMessage> getHomeList(int page,String checkedStatue){
//		return null;
//	}
//	
//	public void initSpaceLayoutIsFinish(){
//		
//	}
//	
//	public void setCurrentHomeFinish(){
//		
//	}
	

	/**
	 * 进行照片的保存
	 * 
	 * @return
	 */
	public String saveAttachmentIDS(SpaceLayoutList layoutList, File file) {

		return null;
	}
	private static String preUrl;
	public static String getPictrueName(String layoutCode,boolean isPreUrl ){
		if(isPreUrl){
			return preUrl;
		}
		String pictureUri;
		if(homMessage==null||homMessage.PreHouseFullName==null){
			pictureUri =System.currentTimeMillis()+ layoutCode;
		}else{
			pictureUri = System.currentTimeMillis() +SpKey.getCurrentTaskMessage()+  homMessage.PreHouseFullName;
		}

		preUrl = MD5Util.GetMD5Code(pictureUri);
		return preUrl;
	}

	public List<LastCheckProblemList> getProblemList(){
		 return homMessage.LastCheckProblemList;
	}
	
	/**
	 * 当点击拍照完成时调用的方法
	 * @return
	 */
	public String setCheckStautsSure(){
		homMessage.CheckStauts  = "2";
		//进行已完成房间数增加
		int finishCount = SpUtilCurrentTaskInfo.getInt(SpKey.getTaskHouseFinalCount(),0)+1;
		SpUtilCurrentTaskInfo.putInt(SpKey.getTaskHouseFinalCount(),finishCount);
		
		int houseCount = SpUtilCurrentTaskInfo.getInt(SpKey.getTaskHouseCount(), 0);
		//房间全部已完成  则工程全部已完成
		if(finishCount>=houseCount){
			SpUtil.putBoolean( TaskMyssageData.getInstance().getTaskMessage().TaskCode+SpKey.TASKSTATUE, true);
			return "2";
		}
		
		//进行数据转换
		savePicture();
		System.out.println(homMessage);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				//当完成时遍历保存有问题的照片
			}
		}).start();
		
		return "2";
	}
//========================================================================================	
	/**
	 * 根据照片进行循环遍历存储有问题的照片
	 * 要对每个
	 */
	public void savePicture(){
		//得到空间布局的辅助类
		SpaceLayoutListHelper layoutListHelper = getSpaceLayoutList();
		List<SpaceLayoutList> spaceLayoutList = layoutListHelper.getSpaceLayoutList();
		int size = spaceLayoutList.size();
		for (int i = 0; i < size; i++) {
			SpaceLayoutList spaceLayoutList2 = spaceLayoutList.get(i);
			
			//得到问题布局
			List<EnginTypeList> enginTypeList = spaceLayoutList.get(i).EnginTypeList;
			//进行问题布局初始化
			if(enginTypeList==null){
				enginTypeList = new ArrayList<HouseMessage.SpaceLayoutList.EnginTypeList>();
			}			
			
			//得到对应id下的图片对象 
			List<PictureInfo> potion = layoutListHelper.getPotion(i);
			for (int j = 0; j < potion.size(); j++) {
				PictureInfo pictureInfo = potion.get(j);
				ProbleamInfo problem = pictureInfo.problem;
				//若有问题进行问题的添加
				if(problem!=null){					
					//对列表进行问题添加的方法
					addProbleamInfoToEnginTypeList(enginTypeList,problem,spaceLayoutList2,pictureInfo.getPictureUri());
				}
			}
			
		}
		
	}
	
	public void addProbleamInfoToEnginTypeList(List<EnginTypeList> enginTypeList, ProbleamInfo problem, SpaceLayoutList spaceLayoutList, String pictureNme){
		//列表中是否已存在第一层的问题 存在就直接
		EnginTypeList typeList = null;
		for (int i = 0; i < enginTypeList.size(); i++) {
			EnginTypeList typeListItem = enginTypeList.get(i);
			if(typeListItem.EnginTypeCode.equals(problem.EnginTypeCode)){
				typeList = typeListItem;
				break;
			}
		}
		
		//不存在直接添加 第一层问题编码
		if(typeList==null){
			typeList = spaceLayoutList.new EnginTypeList();
			typeList.EnginTypeCode = problem.EnginTypeCode;
			enginTypeList.add(typeList);
		}
		
		
		//进行第三层问题编码添加
		List<ProblemDescriptionList> problemDescriptionList = typeList.ProblemDescriptionList;
		if(problemDescriptionList==null){
			 problemDescriptionList = typeList.ProblemDescriptionList = new ArrayList<HouseMessage.SpaceLayoutList.EnginTypeList.ProblemDescriptionList>();
		}
		
		
		ProblemDescriptionList descriptionList = null;
		for (int i = 0; i < problemDescriptionList.size(); i++) {
			ProblemDescriptionList problemDescriptionListItem = problemDescriptionList.get(i);
			if(problemDescriptionListItem.equals(problem.ProblemDescriptionCode)){
				descriptionList = problemDescriptionListItem;
				break;
			}
		}
		
		//如果第三层没有这个问题进行添加
		if(descriptionList==null){
			descriptionList = typeList.new ProblemDescriptionList();
			descriptionList.ProblemDescriptionCode = problem.ProblemDescriptionCode;
			descriptionList.ProblemDescriptionName = problem.ProblemDescriptionName;
			problemDescriptionList.add(descriptionList);
		}
		//进行图片的添加
		List<String> attachmentIDS = typeList.AttachmentIDS;
		if(attachmentIDS==null){
			attachmentIDS = typeList.AttachmentIDS = new ArrayList<String>();
		}
		
		if(!attachmentIDS.contains(pictureNme))
		attachmentIDS.add(pictureNme);
	}
}
