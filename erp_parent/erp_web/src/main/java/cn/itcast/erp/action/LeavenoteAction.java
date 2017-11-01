package cn.itcast.erp.action;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.biz.ILeavenoteBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Emp_leavenote;
import cn.itcast.erp.entity.Leavenote;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.WebUtil;

/**
 * 请假单Action 
 * @author Administrator
 *
 */
public class LeavenoteAction extends BaseAction<Leavenote> {

	private ILeavenoteBiz leavenoteBiz;
	private IEmpBiz empBiz;

	public void setLeavenoteBiz(ILeavenoteBiz leavenoteBiz) {
		this.leavenoteBiz = leavenoteBiz;
		super.setBaseBiz(this.leavenoteBiz);
	}
	
	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	
	/**
	 * 申请请假
	 */
	public void apply() {
		Emp loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			WebUtil.ajaxReturn(false, "你还没登录");
			return;
		}
		
		try {
			Leavenote ln = this.getT();
			leavenoteBiz.add(ln, loginUser.getUuid());
			WebUtil.ajaxReturn(true, "申请请假成功！");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "申请请假失败！");
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取当前用户申请的请假单
	 */
	public void myApplyLeavenotes() {
		Emp loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			WebUtil.ajaxReturn(false, "你还没登录");
			return;
		}
		
		Emp emp = empBiz.get(loginUser.getUuid());
		List<Leavenote> list = emp.getLeavenotes();
		
		List<Leavenote> _list = new ArrayList<Leavenote>();
		for (Leavenote ln : list) {
			List<Emp_leavenote> elList = ln.getEmp_leavenotes(); // 获得该请假单的明细（分别和谁发生何种关联）
			for (Emp_leavenote el : elList) {
				if (el.getEmpuuid()==loginUser.getUuid() && Emp_leavenote.TYPE_APPLY.equals(el.getType())) {
					_list.add(ln);
					break;
				}
			}
		}
		
		WebUtil.write(_list);
	}
	
	
	/**
	 * 获取当前用户未审批的请假单
	 */
	public void myUncheckLeavenotes() {
		Emp loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			WebUtil.ajaxReturn(false, "你还没登录");
			return;
		}
		
		Emp emp = empBiz.get(loginUser.getUuid());
		List<Leavenote> list = emp.getLeavenotes();
		
		List<Leavenote> _list = new ArrayList<Leavenote>();
		for (Leavenote ln : list) {
			List<Emp_leavenote> elList = ln.getEmp_leavenotes(); // 获得该请假单的明细（分别和谁发生何种关联）
			for (Emp_leavenote el : elList) {
				if (el.getEmpuuid()==loginUser.getUuid() && el.getType()==Emp_leavenote.TYPE_CHECK && el.getState()==Emp_leavenote.STATE_NOT_DONE) {
					_list.add(ln);
					break;
				}
			}
		}
		
		WebUtil.write(_list);
	}
	
	
	/**
	 * 获取当前用户已审批的请假单
	 */
	public void myCheckLeavenotes() {
		Emp loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			WebUtil.ajaxReturn(false, "你还没登录");
			return;
		}
		
		Emp emp = empBiz.get(loginUser.getUuid());
		List<Leavenote> list = emp.getLeavenotes();
		
		List<Leavenote> _list = new ArrayList<Leavenote>();
		for (Leavenote ln : list) {
			List<Emp_leavenote> elList = ln.getEmp_leavenotes(); // 获得该请假单的明细（分别和谁发生何种关联）
			for (Emp_leavenote el : elList) {
				if (el.getEmpuuid()==loginUser.getUuid() && el.getType()==Emp_leavenote.TYPE_CHECK && el.getState()==Emp_leavenote.STATE_DONE) {
					_list.add(ln);
					break;
				}
			}
		}
		
		WebUtil.write(_list);
	}
	
	
	public void doCheck() {
		Emp loginUser = WebUtil.getLoginUser();
		if (loginUser == null) {
			WebUtil.ajaxReturn(false, "你还没登录");
			return;
		}
		
		try {
			leavenoteBiz.doCheck(this.getId(), loginUser.getUuid());
			WebUtil.ajaxReturn(true, "审批成功！");
		} catch(ErpException e) {
			e.printStackTrace();
			WebUtil.ajaxReturn(false, e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			WebUtil.ajaxReturn(false, "审批失败！");
		}
	}
	

}
