package cn.itcast.erp.biz.impl;
import java.util.ArrayList;
import java.util.List;

import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.dao.IMenuDao;
import cn.itcast.erp.entity.Menu;
/**
 * 菜单业务逻辑类
 * @author Administrator
 *
 */
public class MenuBiz extends BaseBiz<Menu> implements IMenuBiz {

	private IMenuDao menuDao;
	
	public void setMenuDao(IMenuDao menuDao) {
		this.menuDao = menuDao;
		super.setBaseDao(this.menuDao);
	}

	@Override
	public List<Menu> getMenusByEmpuuid(Long empuuid) {
		// TODO Auto-generated method stub
		return menuDao.getMenusByEmpuuid(empuuid);
	}

	@Override
	public Menu readMenusByEmpuuid(Long empuuid) {
		// 获取所有的菜单
		Menu root = menuDao.get("0");
		// 用户所拥有的菜单权限
		List<Menu> empMenus = getMenusByEmpuuid(empuuid);
		
		Menu _root = cloneMenu(root);//复制后得到的主菜单
		//循环复制菜单
		for(Menu m1 : root.getMenus()){
			//一级菜单
			Menu _m1 = cloneMenu(m1);
			for(Menu m2 : m1.getMenus()){
				//二级菜单的复制
				Menu _m2 = cloneMenu(m2);
				//如果用户下的权限菜单包含这个菜单，则复制并加入到复制的一级菜单低下
				if(empMenus.contains(m2)){
					_m1.getMenus().add(_m2);
				}
			}
			if(_m1.getMenus().size() > 0 ){
				//复制后的一级菜单下有二级菜单
				_root.getMenus().add(_m1);
			}
		}
		
		return _root;
	}
	
	private Menu cloneMenu(Menu src){
		Menu m = new Menu();
		m.setIcon(src.getIcon());
		m.setMenuid(src.getMenuid());
		m.setMenuname(src.getMenuname());
		m.setUrl(src.getUrl());
		m.setMenus(new ArrayList<Menu>());
		return m;
	}
}
