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

		}
		public class SpaceLayoutList extends BaseBean {
			public String SpaceLayoutFullName;
			public int SpaceLayoutCode;
			public String SpaceLayoutName;
			//TODO:原public List<AttachmentIDS> AttachmentIDS;
			public List<String> AttachmentIDS;

			public List<EnginTypeList> EnginTypeList;

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