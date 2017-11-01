package cn.itcast.erp.biz.impl;
import java.util.Date;
import java.util.List;

import cn.itcast.erp.biz.IInventoryBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IInventoryDao;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Inventory;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;
/**
 * 盘盈盘亏业务逻辑类
 * @author Administrator
 *
 */
public class InventoryBiz extends BaseBiz<Inventory> implements IInventoryBiz {

	private IInventoryDao inventoryDao;
	private IEmpDao empDao;
	private IGoodsDao goodsDao;
	private IStoreDao storeDao;
	private IStoredetailDao storedetailDao;
	Storedetail storedetail = new Storedetail();
	
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}
	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}
	public void setInventoryDao(IInventoryDao inventoryDao) {
		this.inventoryDao = inventoryDao;
		super.setBaseDao(this.inventoryDao);
	}

	@Override
	public void add(Inventory inventory) {
		
		System.out.println("99999");
		//登记时间
		inventory.setCreatetime(new Date());
		//状态为未审核
		inventory.setState(Inventory.STATE_CREATE);
		//获取库存数量
		storedetail.setGoodsuuid(inventory.getGoodsuuid());
		List<Storedetail> list = storedetailDao.getList(storedetail, null, null);
		Long storenum = list.get(0).getNum();
		
		//比较 盘点数量与库存数量 
		if(null == storenum || inventory.getNum() > storenum){
			inventory.setType(Inventory.TYPE_IN);  //盘点数量大于库存数量   自动更新为盘盈
		}else{
			inventory.setType(Inventory.TYPE_OUT);  //盘亏
		}
		super.add(inventory);
	}
	
	 @Override
	public List<Inventory> getListByPage(Inventory t1, Inventory t2, Object param, int firstResult, int maxResults) {
		 List<Inventory> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		 for(Inventory in : list){
			//根据员工编号，从缓存中取出名称
			in.setCreaterName(empDao.getObjectName(in.getCreater()));
			in.setCheckerName(empDao.getObjectName(in.getChecker()));
			in.setGoodsname(goodsDao.getObjectName(in.getGoodsuuid()));
			in.setStorename(storeDao.getObjectName(in.getStoreuuid()));
		}
		return list;
	}
	@Override
	public void doCheck(Long uuid, Long empuuid,String remark) {
		//获取盘盈盘亏信息
		Inventory inventory = inventoryDao.get(uuid);
		//不是未审核的,不能审核
		if(!Inventory.STATE_CREATE.equals(inventory.getState())){
			throw new ErpException("该条目已经审核过了");
		}
		//审核时间
		inventory.setChecktime(new Date());
		//审核人
		inventory.setChecker(empuuid);
		//备注
		inventory.setRemark(remark);
		//条目的状态改为 已审核
		inventory.setState(Inventory.STATE_CHECK);
		System.out.println("77777777777777777777" + inventory.getNum());
		//改变库存数量
		storedetail.setGoodsuuid(inventory.getGoodsuuid());
		List<Storedetail> list = storedetailDao.getList(storedetail, null, null);
		list.get(0).setNum(inventory.getNum());
	}
		
}
