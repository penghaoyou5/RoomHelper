package com.sinooceanland.roomhelper.control.taskdata;

import com.sinooceanland.roomhelper.control.base.BaseNet;
import com.sinooceanland.roomhelper.control.constant.SpKey;
import com.sinooceanland.roomhelper.control.util.SpUtil;
import com.sinooceanland.roomhelper.dao.module.ProjectProblemBean;

public class ProjectProblemDate {

	private static ProjectProblemBean problemBean;

	/**
	 * @return 工程问题 从内存获取工程问题
	 */
	public ProjectProblemBean getProjectProblem() {
		if (problemBean == null) {
			String string = SpUtil.getString(SpKey.PROJECTPROBLEM, null);
			problemBean = BaseNet.getGson().fromJson(string,
					ProjectProblemBean.class);
		}
		return problemBean;
	}
}
