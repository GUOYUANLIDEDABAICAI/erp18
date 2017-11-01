package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IJobBiz;
import cn.itcast.erp.dao.IJobDao;
import cn.itcast.erp.entity.Job;
/**
 * 职位业务逻辑类
 * @author Administrator
 *
 */
public class JobBiz extends BaseBiz<Job> implements IJobBiz {

	private IJobDao jobDao;
	
	public void setJobDao(IJobDao jobDao) {
		this.jobDao = jobDao;
		super.setBaseDao(this.jobDao);
	}
	
}
