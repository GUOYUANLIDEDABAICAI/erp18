package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;

import cn.itcast.erp.biz.IEmpBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
import cn.itcast.erp.exception.ErpException;
/**
 * 员工业务逻辑类
 * @author Administrator
 *
 */
public class EmpBiz extends BaseBiz<Emp> implements IEmpBiz {

	private IEmpDao empDao;
	private IRoleDao roleDao;
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
		super.setBaseDao(this.empDao);
	}
	
	@Override
	public void add(Emp emp) {
		//source: 要加密的内容   用户的密码
		//salt: 盐, 扰乱码           登陆名
		//hashIterattion: 散列次数  2
		//Md5Hash md5 = new Md5Hash(emp.getPwd(),emp.getUsername(),2);
		emp.setPwd(encrypt(emp.getUsername(),emp.getUsername()));
		super.add(emp);
	}

	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		//加密密码
		pwd = encrypt(pwd, username);
		System.out.println(pwd);
		return empDao.findByUsernameAndPwd(username, pwd);
	}
	
	/**
	 * 加密
	 * @param source
	 * @param salt
	 * @return
	 */
	private String encrypt(String source, String salt){
		Md5Hash md5 = new Md5Hash(source,salt,2);
		return md5.toString();
	}

	@Override
	public void updatePwd(String oldPwd, String newPwd, Long uuid) {
		//校验原密码
		Emp emp = empDao.get(uuid);
		//加密原密码
		oldPwd = encrypt(oldPwd, emp.getUsername());
		if(!oldPwd.equals(emp.getPwd())){
			throw new ErpException("原密码不正确");
		}
		//加密新密码
		newPwd = encrypt(newPwd,emp.getUsername());
		//更新新密码
		empDao.updatePwd(newPwd, uuid);
		
	}

	@Override
	public void updatePwd_reset(String newPwd, Long uuid) {
		//获取员工信息
		Emp emp = empDao.get(uuid);
		//加密新密码
		newPwd = encrypt(newPwd, emp.getUsername());
		//更新密码
		empDao.updatePwd(newPwd, uuid);
	}

	@Override
	public List<Tree> readEmpRoles(Long uuid) {
		//获取所有的角色列表
		List<Role> roleList = roleDao.getList(null,null,null);
		// 获取用户的信息, 进入 持久化状态
		Emp emp = empDao.get(uuid);
		// 用户下的角色
		List<Role> empRoles = emp.getRoles();
		
		// 转成tree后的结果
		List<Tree> result = new ArrayList<Tree>();
		// 把角色列表转成树的数据
		for(Role role : roleList){
			Tree t = createTree(role);
			if(empRoles.contains(role)){
				//用户拥有的角色中包含这个角色，就让它选中
				t.setChecked(true);
			}
			result.add(t);
		}
		return result;
	}

	@Override
	public void updateEmpRoles(Long uuid, String ids) {
		// 获取用户的信息, 进入 持久化状态
		Emp emp = empDao.get(uuid);
		// 清除原有的关系
		emp.setRoles(new ArrayList<Role>());
		
		// 设置新的角色关系
		String[] roleIds = ids.split(",");
		for(String roleuuid : roleIds){
			Role role = roleDao.get(Long.valueOf(roleuuid));
			//加上新角色关系
			emp.getRoles().add(role);
		}
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	/**
	 * 把menu转成树的节点
	 * @param menu
	 * @return
	 */
	private Tree createTree(Role role){
		Tree tree = new Tree();
		tree.setId(role.getUuid() + "");
		tree.setText(role.getName());
		tree.setChildren(new ArrayList<Tree>());
		return tree;
	}
	
}
