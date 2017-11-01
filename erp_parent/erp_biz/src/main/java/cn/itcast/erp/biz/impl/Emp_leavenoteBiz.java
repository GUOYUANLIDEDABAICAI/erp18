package cn.itcast.erp.biz.impl;
import cn.itcast.erp.biz.IEmp_leavenoteBiz;
import cn.itcast.erp.dao.IEmp_leavenoteDao;
import cn.itcast.erp.entity.Emp_leavenote;
/**
 * 员工请假单业务逻辑类
 * @author Administrator
 *
 */
public class Emp_leavenoteBiz extends BaseBiz<Emp_leavenote> implements IEmp_leavenoteBiz {

	private IEmp_leavenoteDao emp_leavenoteDao;
	
	public void setEmp_leavenoteDao(IEmp_leavenoteDao emp_leavenoteDao) {
		this.emp_leavenoteDao = emp_leavenoteDao;
		super.setBaseDao(this.emp_leavenoteDao);
	}
	
}
