package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Inventory;
/**
 * 盘盈盘亏业务逻辑层接口
 * @author Administrator
 *
 */
public interface IInventoryBiz extends IBaseBiz<Inventory>{

	/**
	 * 审核
	 * @param uuid
	 * @param empuuid
	 */
	void doCheck(Long uuid, Long empuuid,String remark);
}

