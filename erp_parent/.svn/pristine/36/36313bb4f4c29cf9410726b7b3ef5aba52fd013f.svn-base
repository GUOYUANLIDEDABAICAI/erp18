package cn.itcast.erp.biz.impl;
import java.util.List;

import cn.itcast.erp.biz.ILeavenoteBiz;
import cn.itcast.erp.dao.IJobDao;
import cn.itcast.erp.dao.ILeavenoteDao;
import cn.itcast.erp.entity.Emp_leavenote;
import cn.itcast.erp.entity.Job;
import cn.itcast.erp.entity.Leavenote;
import cn.itcast.erp.exception.ErpException;
/**
 * 请假单业务逻辑类
 * @author Administrator
 *
 */
public class LeavenoteBiz extends BaseBiz<Leavenote> implements ILeavenoteBiz {

	private ILeavenoteDao leavenoteDao;
	private IJobDao jobDao;
	
	public void setLeavenoteDao(ILeavenoteDao leavenoteDao) {
		this.leavenoteDao = leavenoteDao;
		super.setBaseDao(this.leavenoteDao);
	}
	
	public void setJobDao(IJobDao jobDao) {
		this.jobDao = jobDao;
	}
	
	@Override
	public void add(Leavenote ln, Long empuuid) {
		
		Job jobQueryParam = new Job();
		jobQueryParam.setEmpuuid(empuuid);
		List<Job> list = jobDao.getList(jobQueryParam, null, null);
		Job checkerJob = list.get(0);
		Long rank = checkerJob.getRank(); // 申请者职级
		
		if (rank == 1) {
			throw new ErpException("在公司你是老大，不上班不需要请假了！");
		}
		
		// 设置请假单状态为未审批
		ln.setState(Leavenote.STATE_CREATE);
		
		// 标记为本人的请假单
		Emp_leavenote el1 = new Emp_leavenote();
		el1.setEmpuuid(empuuid);
		el1.setType(Emp_leavenote.TYPE_APPLY);
		el1.setState(Emp_leavenote.STATE_NOT_DONE);
		el1.setLeavenote(ln);
		ln.getEmp_leavenotes().add(el1);
		
		Job jobQueryParam2 = new Job();
		jobQueryParam2.setEmpuuid(empuuid);
		List<Job> list2 = jobDao.getList(jobQueryParam2, null, null);
		Job myjob = list2.get(0);
		
		// 让直接上级审批
		Emp_leavenote el2 = new Emp_leavenote();
		el2.setEmpuuid(jobDao.get(myjob.getPid()).getEmpuuid());
		el2.setType(Emp_leavenote.TYPE_CHECK);
		el2.setState(Emp_leavenote.STATE_NOT_DONE);
		el2.setLeavenote(ln);
		ln.getEmp_leavenotes().add(el2);
		
		super.add(ln);
	}

	@Override
	public void doCheck(Long leavenoteuuid, Long empuuid) {
		Leavenote leavenote = leavenoteDao.get(leavenoteuuid);
		
		// 在假单的明细中，更新为该审批者已审批
		List<Emp_leavenote> elList = leavenote.getEmp_leavenotes();
		boolean flag = false; // 标记是否操作过明细
		for (Emp_leavenote el : elList) {
			if (el.getEmpuuid()==empuuid && Emp_leavenote.STATE_NOT_DONE.equals(el.getState()) && Emp_leavenote.TYPE_CHECK.equals(el.getType())) {
				if (Emp_leavenote.STATE_DONE.equals(el.getState())) {
					throw new ErpException("你已经审核过该请假单！");
				}
				el.setState(Emp_leavenote.STATE_DONE);
				flag = true;
				break;
			}
		}
		
		// 没有操作过明细，该用户没有该请假单的审批权限
		if (! flag) {			
			throw new ErpException("你没有审批该请假单的权限");
		}
		
		// 计算请假天数 
		long timeMills = leavenote.getEndtime().getTime() - leavenote.getStarttime().getTime();
		int days = (int)(timeMills / 1000 / 3600 / 24);
		
		// 获取审批者职级
		Job jobQueryParam = new Job();
		jobQueryParam.setEmpuuid(empuuid);
		List<Job> list = jobDao.getList(jobQueryParam, null, null);
		Job checkerJob = list.get(0);
		Long rank = checkerJob.getRank(); // 审批者职级
		
		// 如果审批者权限已足够
		if (rank <= 4 && days <= 1) {
			leavenote.setState(Leavenote.STATE_CHECK);
			return;
		}
		if (rank <= 3 && days > 1 && days <= 3) {
			leavenote.setState(Leavenote.STATE_CHECK);
			return;
		}
		if (rank <= 2 && days > 3 && days <= 7) {
			leavenote.setState(Leavenote.STATE_CHECK);
			return;
		}
		if (rank == 1) {
			leavenote.setState(Leavenote.STATE_CHECK);
			return;
		}
		
		// 如果审批者权限不足，继续让上级审批
		Job jobQueryParam2 = new Job();
		jobQueryParam2.setEmpuuid(empuuid);
		List<Job> list2 = jobDao.getList(jobQueryParam2, null, null);
		Job myjob = list2.get(0);

		Emp_leavenote el2 = new Emp_leavenote();
		el2.setEmpuuid(jobDao.get(myjob.getPid()).getEmpuuid());
		el2.setType(Emp_leavenote.TYPE_CHECK);
		el2.setState(Emp_leavenote.STATE_NOT_DONE);
		el2.setLeavenote(leavenote);
		leavenote.getEmp_leavenotes().add(el2);
		
		
	}
	
	
	
}
