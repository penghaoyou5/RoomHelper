package com.sinooceanland.roomhelper.control.bean;

import java.util.List;

import com.sinooceanland.roomhelper.control.base.BaseBean;
import com.sinooceanland.roomhelper.control.base.ProjectProblemBaseBean;

/**
 * @author peng
 * 问题类型选择界面
 */
public class ProjectProblemBean extends BaseBean {
	public String SuccessMsg;
	public String ErrorMsg;
	public List<OneFloor> list;

	public class OneFloor extends ProjectProblemBaseBean {

		public List<TwoFloor> Children;

		public class TwoFloor extends ProjectProblemBaseBean {

			public List<ProblemDescriptionList> ProblemDescriptionList;

			public class ProblemDescriptionList extends BaseBean {
				public String ProblemDescriptionName;
				public String ProblemDescriptionCode;
			}

			public List<Children> Children;

			public class Children {
			}

		}

	}
}
