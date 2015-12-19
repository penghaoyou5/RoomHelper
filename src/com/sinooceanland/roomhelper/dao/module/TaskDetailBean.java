package com.sinooceanland.roomhelper.dao.module;
import java.util.List;

import com.sinooceanland.roomhelper.dao.base.BaseBean;
/**
 * @author peng
 * 任务详情
 */
public class TaskDetailBean extends BaseBean {

	public String SuccessMsg;
	public int ErrorMsg;
	public List<HouseMessage> list;
	public int downloadState = 0;
	public int finishState = 0;
	public int uploadState = 0;
}
