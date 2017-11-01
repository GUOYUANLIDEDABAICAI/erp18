package cn.itcast.erp.action;
import java.util.List;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
import cn.itcast.erp.util.WebUtil;

/**
 * 员工Action 
 * @author Administrator
 *
 */
public class EmpAction extends BaseAction<Emp> {

	private IEmpBiz empBiz;
	
	private String oldPwd;//旧密码
	private String newPwd;//新密码
	private String ids;//角色编号，多个以逗号分割

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
		super.setBaseBiz(this.empBiz);
	}
	
	/**
	 * 更新 密码
	 */
	public void updatePwd(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "你还没有登陆");
			return;
		}
		try {
			empBiz.updatePwd(oldPwd, newPwd, loginUser.getUuid());
			WebUtil.ajaxReturn(true, "更新密码成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebUtil.ajaxReturn(false, "更新密码失败");
		}
	}
	
	/**
	 * 重置密码
	 */
	public void updatePwd_reset(){
		try {
			empBiz.updatePwd_reset(newPwd, getId());
			WebUtil.ajaxReturn(true, "重置密码成功");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			WebUtil.ajaxReturn(false, "重置密码失败");
		}
	}
	
	/**
	 * 读取用户角色
	 */
	public void readEmpRoles(){
		List<Tree> tree = empBiz.readEmpRoles(getId());
		WebUtil.write(tree);
	}
	
	/**
	 * 更新用户角色
	 */
	public void updateEmpRoles(){
		try {
			empBiz.updateEmpRoles(getId(), ids);
			WebUtil.ajaxReturn(true, "更新成功");
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "更新失败");
			e.printStackTrace();
		}
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
