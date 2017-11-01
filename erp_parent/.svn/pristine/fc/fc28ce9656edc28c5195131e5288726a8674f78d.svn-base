package cn.itcast.erp.action;
import cn.itcast.erp.biz.IMenuBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Menu;
import cn.itcast.erp.util.WebUtil;

/**
 * 菜单Action 
 * @author Administrator
 *
 */
public class MenuAction extends BaseAction<Menu> {

	private IMenuBiz menuBiz;

	public void setMenuBiz(IMenuBiz menuBiz) {
		this.menuBiz = menuBiz;
		super.setBaseBiz(this.menuBiz);
	}
	
	/**
	 * 显示菜单
	 */
	public void getMenuTree(){
		//得到主菜单,此时它进入持久化状态
		//Menu menu = menuBiz.get("0");
		/*//得一级菜单, select * from menu where pid=0
		List<Menu> level1Menus = menu.getMenus();
		//
		for(Menu m1 : level1Menus){
			//二级菜单: select * from menu where pid=100,200
			List<Menu> level2Menus = m1.getMenus();
			
		}*/
		//WebUtil.write(menu);//fastJson=>转成json字符串, 就会调用getMenus()
		Emp loginUser = WebUtil.getLoginUser();
		if(null != loginUser){
			/*List<Menu> menus = menuBiz.getMenusByEmpuuid(loginUser.getUuid());
			WebUtil.write(menus);*/
			Menu menu = menuBiz.readMenusByEmpuuid(loginUser.getUuid());
			WebUtil.write(menu);
		}
	}

}
