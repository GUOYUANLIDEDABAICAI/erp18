package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IRoleBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.dao.IRoleDao;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色业务逻辑类
 * @author Administrator
 *
 */
public class RoleBiz extends BaseBiz<Role> implements IRoleBiz {

	private IRoleDao roleDao;
	private IMenuDao menuDao;
	
	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
		super.setBaseDao(this.roleDao);
	}

	@Override
	public List<Tree> readRoleMenus(Long uuid) {
		// 取出所有的菜单
		Menu root = menuDao.get("0");
		//获取 角色信息 进入持久化状态
		Role role = roleDao.get(uuid);
		//角色所拥有的菜单
		List<Menu> roleMenus = role.getMenus();
		
		// 转成tree后的结果
		List<Tree> result = new ArrayList<Tree>();
		//取出所有的一级菜单
		for(Menu m1 : root.getMenus()){
			Tree t1 = createTree(m1);
			//二级菜单
			for(Menu m2 : m1.getMenus()){
				Tree t2 = createTree(m2);
				//角色下菜单集中，包含这个菜单，就应让它选中
				if(roleMenus.contains(m2)){
					t2.setChecked(true);
				}
				t1.getChildren().add(t2);
			}
			result.add(t1);
		}
		return result;
	}
	
	/**
	 * 把menu转成树的节点
	 * @param menu
	 * @return
	 */
	private Tree createTree(Menu menu){
		Tree tree = new Tree();
		tree.setId(menu.getMenuid());
		tree.setText(menu.getMenuname());
		tree.setChildren(new ArrayList<Tree>());
		return tree;
	}

	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	public void updateRoleMenus(Long uuid, String ids) {
		//获取 角色信息 进入持久化状态
		Role role = roleDao.get(uuid);
		//清除原的权限
		// delete from role_menu where roleuuid=uuid
		role.setMenus(new ArrayList<Menu>());
		
		String[] idArr = ids.split(",");
		for(String id : idArr){
			//添加角色下的权限
			Menu menu = menuDao.get(id);
			role.getMenus().add(menu);
		}
	}
	
}
