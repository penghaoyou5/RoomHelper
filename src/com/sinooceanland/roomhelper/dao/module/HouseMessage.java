package com.sinooceanland.roomhelper.dao.module;

import java.util.ArrayList;
import java.util.List;

import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.SpUtilCurrentTaskInfo;
import com.sinooceanland.roomhelper.dao.base.BaseBean;
import com.sinooceanland.roomhelper.dao.base.HouseMessageBaseBean;

/**
 * @author peng 房屋信息
 */
public class HouseMessage extends HouseMessageBaseBean {
	public String PropertTypeName;
	public String ActBuildingName;
	public String SaleRecordCode;
	public List<LastCheckProblemList> LastCheckProblemList;
	public List<SpaceLayoutList> SpaceLayoutList;

	public class LastCheckProblemList extends HouseMessageBaseBean {
		public String SpaceLayoutName;
		public String SpaceLayoutFullName;
		public String TaskCode;
		public String PreCheckCode;
		public String ProPretTypeName;
		public String SpaceLayoutCode;
		// TODO:原public List<AttachmentIDS> AttachmentIDS;
		public List<String> AttachmentIDS;
		public ArrayList<ProblemPicture> getPicture(){
			ArrayList<ProblemPicture> arrayList = new ArrayList<ProblemPicture>();
			for (int i = 0; i < AttachmentIDS.size(); i++) {
				ProblemPicture problemPicture = new ProblemPicture(AttachmentIDS.get(i),SpaceLayoutName,this);
				arrayList.add(problemPicture);
			}
			return arrayList;
		}
		
		// int
		/**
		 * 要修改 上次验收问题是否通过 0 未通过 1 通过
		 */
//		public String CheckStauts;

		public String getSpaceLayoutName() {
			return SpaceLayoutName;
		}

		public void setSpaceLayoutName(String spaceLayoutName) {
			SpaceLayoutName = spaceLayoutName;
		}

		public String getSpaceLayoutFullName() {
			return SpaceLayoutFullName;
		}

		public void setSpaceLayoutFullName(String spaceLayoutFullName) {
			SpaceLayoutFullName = spaceLayoutFullName;
		}

		public String getTaskCode() {
			return TaskCode;
		}

		public void setTaskCode(String taskCode) {
			TaskCode = taskCode;
		}

		public String getPreCheckCode() {
			return PreCheckCode;
		}

		public void setPreCheckCode(String preCheckCode) {
			PreCheckCode = preCheckCode;
		}

		public String getProPretTypeName() {
			return ProPretTypeName;
		}

		public void setProPretTypeName(String proPretTypeName) {
			ProPretTypeName = proPretTypeName;
		}

		public String getSpaceLayoutCode() {
			return SpaceLayoutCode;
		}

		public void setSpaceLayoutCode(String spaceLayoutCode) {
			SpaceLayoutCode = spaceLayoutCode;
		}

		public List<String> getAttachmentIDS() {
			return AttachmentIDS;
		}

		public void setAttachmentIDS(List<String> attachmentIDS) {
			AttachmentIDS = attachmentIDS;
		}

		public String getCheckStauts() {
			if("1".equals(CheckStauts)){
				return "已通过";
			}
			return "未通过";
		}

		/**
		 * 进行验收状态的设置方法
		 * @return
		 */
		public boolean setCheckStauts() {
			for (int i = 0; i < AttachmentIDS.size(); i++) {
				String str = AttachmentIDS.get(i);
				int in = SpUtilCurrentTaskInfo.getInt(str, -1);
				if(in==-1){
					return false;
				}
			}
			CheckStauts = "1";
			return true;
		}
	}

	/**
	 * @author peng 布局类 例如 厨房这一级
	 */
	public static class SpaceLayoutList extends BaseBean {
		public String SpaceLayoutFullName;
		public String SpaceLayoutCode;
		public String SpaceLayoutName;
		// TODO:原public List<AttachmentIDS> AttachmentIDS;
		/**
		 * 要修改 要是修改的字段 看看接口文档，json格式可能不太一样 表示布局空间内所有图片
		 */
		public List<String> AttachmentIDS;

		public List<EnginTypeList> EnginTypeList;
		
		public String getSpaceLayoutFullName() {
			return SpaceLayoutFullName;
		}

		public void setSpaceLayoutFullName(String spaceLayoutFullName) {
			SpaceLayoutFullName = spaceLayoutFullName;
		}

		public String getSpaceLayoutCode() {
			return SpaceLayoutCode;
		}

		public void setSpaceLayoutCode(String spaceLayoutCode) {
			SpaceLayoutCode = spaceLayoutCode;
		}

		public String getSpaceLayoutName() {
			return SpaceLayoutName;
		}

		public void setSpaceLayoutName(String spaceLayoutName) {
			SpaceLayoutName = spaceLayoutName;
		}

		public List<String> getAttachmentIDS() {
			return AttachmentIDS;
		}

		public void setAttachmentIDS(List<String> attachmentIDS) {
			AttachmentIDS = attachmentIDS;
		}

		public List<EnginTypeList> getEnginTypeList() {
			return EnginTypeList;
		}

		public void setEnginTypeList(List<EnginTypeList> enginTypeList) {
			EnginTypeList = enginTypeList;
		}

		/**
		 * @author peng 这里面所有的都要能修改 这里表示问题集合 （问题的描述，及有问题图片）
		 */
		public class EnginTypeList extends BaseBean {

			public String EnginTypeFullName;
			public String CheckRemark;
			public String EnginTypeName;
			public String EnginTypeCode;
			// TODO:原public List<AttachmentIDS> AttachmentIDS;
			public List<String> AttachmentIDS;

			public List<ProblemDescriptionList> ProblemDescriptionList;

			public class ProblemDescriptionList extends BaseBean {

				public String ProblemDescriptionName;
				public String ProblemDescriptionCode;
				
				public String getProblemDescriptionName() {
					return ProblemDescriptionName;
				}
				public void setProblemDescriptionName(String problemDescriptionName) {
					ProblemDescriptionName = problemDescriptionName;
				}
				public String getProblemDescriptionCode() {
					return ProblemDescriptionCode;
				}
				public void setProblemDescriptionCode(String problemDescriptionCode) {
					ProblemDescriptionCode = problemDescriptionCode;
				}
			}

			public String getEnginTypeFullName() {
				return EnginTypeFullName;
			}

			public void setEnginTypeFullName(String enginTypeFullName) {
				EnginTypeFullName = enginTypeFullName;
			}

			public String getCheckRemark() {
				return CheckRemark;
			}

			public void setCheckRemark(String checkRemark) {
				CheckRemark = checkRemark;
			}

			public String getEnginTypeName() {
				return EnginTypeName;
			}

			public void setEnginTypeName(String enginTypeName) {
				EnginTypeName = enginTypeName;
			}

			public String getEnginTypeCode() {
				return EnginTypeCode;
			}

			public void setEnginTypeCode(String enginTypeCode) {
				EnginTypeCode = enginTypeCode;
			}

			public List<String> getAttachmentIDS() {
				return AttachmentIDS;
			}

			public void setAttachmentIDS(List<String> attachmentIDS) {
				AttachmentIDS = attachmentIDS;
			}

			public List<ProblemDescriptionList> getProblemDescriptionList() {
				return ProblemDescriptionList;
			}

			public void setProblemDescriptionList(
					List<ProblemDescriptionList> problemDescriptionList) {
				ProblemDescriptionList = problemDescriptionList;
			}
		}
	}

	public String getPropertTypeName() {
		return PropertTypeName;
	}

	public void setPropertTypeName(String propertTypeName) {
		PropertTypeName = propertTypeName;
	}

	public String getActBuildingName() {
		return ActBuildingName;
	}

	public void setActBuildingName(String actBuildingName) {
		ActBuildingName = actBuildingName;
	}

	public String getSaleRecordCode() {
		return SaleRecordCode;
	}

	public void setSaleRecordCode(String saleRecordCode) {
		SaleRecordCode = saleRecordCode;
	}

	public List<LastCheckProblemList> getLastCheckProblemList() {
		return LastCheckProblemList;
	}

	public void setLastCheckProblemList(
			List<LastCheckProblemList> lastCheckProblemList) {
		LastCheckProblemList = lastCheckProblemList;
	}

	public List<SpaceLayoutList> getSpaceLayoutList() {
		return SpaceLayoutList;
	}

	public void setSpaceLayoutList(List<SpaceLayoutList> spaceLayoutList) {
		SpaceLayoutList = spaceLayoutList;
	}
}