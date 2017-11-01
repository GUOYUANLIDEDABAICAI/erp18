package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Tree;
/**
 * 员工业务逻辑层接口
 * @author Administrator
 *
 */
public interface IEmpBiz extends IBaseBiz<Emp>{

	/**
	 * 登陆查询
	 * @param username
	 * @param pwd
	 * @return
	 */
	Emp findByUsernameAndPwd(String username, String pwd);
	
	/**
	 * 更新密码
	 * @param oldPwd 原密码
	 * @param newPwd 新密码
	 * @param uuid 用户编号
	 */
	void updatePwd(String oldPwd, String newPwd, Long uuid);
	
	/**
	 * 重置密码
	 * @param newPwd
	 * @param uuid
	 */
	void updatePwd_reset(String newPwd, Long uuid);
	
	/**
	 * 获取用户权限
	 * @param uuid
	 * @return
	 */
	List<Tree> readEmpRoles(Long uuid);
	
	/**
	 * 更新用户权限
	 * @param uuid
	 * @param ids 角色编号，多个以逗号分割
	 */
	void updateEmpRoles(Long uuid, String ids);
}

