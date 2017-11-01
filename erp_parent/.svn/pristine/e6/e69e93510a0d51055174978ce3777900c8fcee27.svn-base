package cn.itcast.erp.biz;
import java.util.List;

import cn.itcast.erp.entity.Role;
import cn.itcast.erp.entity.Tree;
/**
 * 角色业务逻辑层接口
 * @author Administrator
 *
 */
public interface IRoleBiz extends IBaseBiz<Role>{


	/**
	 * 获取角色权限
	 * @param uuid
	 * @return
	 */
	List<Tree> readRoleMenus(Long uuid);
	
	/**
	 * 更新角色权限
	 * @param uuid
	 * @param ids 菜单编号，多个以逗号分割
	 */
	void updateRoleMenus(Long uuid, String ids);
}

