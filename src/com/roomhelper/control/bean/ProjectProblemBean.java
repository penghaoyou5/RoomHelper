package com.roomhelper.control.bean;

import java.util.List;

import com.roomhelper.control.base.BaseBean;
import com.roomhelper.control.base.ProjectProblemBaseBean;

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
