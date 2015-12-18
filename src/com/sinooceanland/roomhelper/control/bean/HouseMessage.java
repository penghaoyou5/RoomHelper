package com.sinooceanland.roomhelper.control.bean;
import java.util.List;

import com.sinooceanland.roomhelper.control.base.BaseBean;
import com.sinooceanland.roomhelper.control.base.HouseMessageBaseBean;

/**
 * @author peng
 * 房屋信息
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
			//TODO:原public List<AttachmentIDS> AttachmentIDS;
			public List<String> AttachmentIDS;
			
			//int
			/**
			 * 要修改
			 * 上次验收问题是否通过 0 未通过 1 通过
			 */
			public String CheckStauts;

		}
		/**
		 * @author peng
		 * 布局类 例如 厨房这一级
		 */
		public class SpaceLayoutList extends BaseBean {
			public String SpaceLayoutFullName;
			public int SpaceLayoutCode;
			public String SpaceLayoutName;
			//TODO:原public List<AttachmentIDS> AttachmentIDS;
			/**
			 * 要修改
			 * 要是修改的字段 看看接口文档，json格式可能不太一样
			 * 表示布局空间内所有图片
			 */
			public List<String> AttachmentIDS;

			public List<EnginTypeList> EnginTypeList;

			/**
			 * @author peng
			 * 这里面所有的都要能修改  这里表示问题集合 （问题的描述，及有问题图片）
			 */
			public class EnginTypeList extends BaseBean {

				public String EnginTypeFullName;
				public String CheckRemark;
				public String EnginTypeName;
				public String EnginTypeCode;
				//TODO:原public List<AttachmentIDS> AttachmentIDS;
				public List<String> AttachmentIDS;

				public List<ProblemDescriptionList> ProblemDescriptionList;

				public class ProblemDescriptionList extends BaseBean {

					public String ProblemDescriptionName;
					public String ProblemDescriptionCode;

				}
			}
		}
	}