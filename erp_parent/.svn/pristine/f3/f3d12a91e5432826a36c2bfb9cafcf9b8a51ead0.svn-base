package cn.itcast.erp.action;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.util.WebUtil;

/**
 * 登陆用户的action
 *
 */
public class LoginAction {

	private String username;
	private String pwd;
	private IEmpBiz empBiz;

	public void setEmpBiz(IEmpBiz empBiz) {
		this.empBiz = empBiz;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	/**
	 * 登陆功能
	 */
	public void checkUser(){
		try{
			Emp emp = empBiz.findByUsernameAndPwd(username, pwd);
			if(null == emp){
				WebUtil.ajaxReturn(false, "用户名或密码不正确");
			}else{
				//把登陆用户放入到session中
				ServletActionContext.getContext().getSession().put("loginUser", emp);
				WebUtil.ajaxReturn(true, "");
			}
		} catch(Exception ex){
			WebUtil.ajaxReturn(false, "登陆失败");
		}
	}
	
	/**
	 * 显示登陆用户名
	 */
	public void showName(){
		//把登陆用户放入到session中
		Emp loginUser = WebUtil.getLoginUser();
		if(null != loginUser){
			//登陆过了
			WebUtil.ajaxReturn(true, loginUser.getName());
		}else{
			WebUtil.ajaxReturn(false, "你还没有登陆");
		}
	}
	
	/**
	 * 退出登陆
	 */
	public void logOut(){
		ServletActionContext.getContext().getSession().remove("loginUser");
	}
}
