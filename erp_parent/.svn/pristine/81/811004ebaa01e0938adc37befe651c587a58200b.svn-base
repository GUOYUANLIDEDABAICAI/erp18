package cn.itcast.erp.biz.impl;
import java.util.Date;
import java.util.List;

import cn.itcast.erp.biz.IReturnordersBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.IReturnordersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Returnorderdetail;
import cn.itcast.erp.entity.Returnorders;
import cn.itcast.erp.exception.ErpException;
/**
 * 退货订单业务逻辑类
 * @author Administrator
 *
 */
public class ReturnordersBiz extends BaseBiz<Returnorders> implements IReturnordersBiz {

	private IReturnordersDao returnordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	private IOrdersDao ordersDao;
	
	public void setReturnordersDao(IReturnordersDao returnordersDao) {
		this.returnordersDao = returnordersDao;
		super.setBaseDao(this.returnordersDao);
	}
	
	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
	}
	
	@Override
	public List<Returnorders> getListByPage(Returnorders t1, Returnorders t2, Object param, int firstResult, int maxResults) {
		List<Returnorders> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		for(Returnorders o : list){
			o.setCreaterName(empDao.getObjectName(o.getCreater()));
			o.setCheckerName(empDao.getObjectName(o.getChecker()));
			o.setEnderName(empDao.getObjectName(o.getEnder()));			
			o.setSupplierName(supplierDao.getObjectName(o.getSupplieruuid()));
		}
		return list;
	}
	
	@Override
	public void add(Returnorders orders) {
		orders.setCreatetime(new Date());
		orders.setState(Returnorders.STATE_CREATE);
		double totalMoney = 0;
		for(Returnorderdetail od : orders.getReturnorderdetails()){
			totalMoney += od.getMoney();
			od.setState(Returnorderdetail.STATE_NOT_OUT);
			od.setReturnorders(orders);
		}
		orders.setTotalmoney(totalMoney);
		super.add(orders);
	}
	
	@Override
	public void doCheck(Long uuid, Long empuuid) {
		// 获取订单，进入持久化状态
		Returnorders orders = returnordersDao.get(uuid);
		// 不是未审核的订单，不能审核
		if(!Returnorders.STATE_CREATE.equals(orders.getState())){
			throw new ErpException("该订单已审核过了");
		}
		// 审核时间
		orders.setChecktime(new Date());
		// 审核人
		orders.setChecker(empuuid);
		// 订单的状态改为 已审核
		orders.setState(Returnorders.STATE_CHECK);
	}

	@Override
	public void doReturn(Long ordersuuid, Long empuuid) {
		// 获取原订单
		Orders orders = ordersDao.get(ordersuuid);
		
		Returnorders rtnOrders = new Returnorders();
		rtnOrders.setOrdersuuid(ordersuuid);
		rtnOrders.setCreater(empuuid);
		rtnOrders.setCreatetime(new Date());
		rtnOrders.setType(orders.getType());
		rtnOrders.setState(Returnorders.STATE_CREATE);
		rtnOrders.setSupplieruuid(orders.getSupplieruuid());
		
		double totalMoney = 0;		
		// 遍历原订单明细
		for(Orderdetail od : orders.getOrderdetails()){
			totalMoney += od.getMoney();
			
			// 根据原订单明细，生成退货单明细
			Returnorderdetail rod = new Returnorderdetail();
			rod.setGoodsname(od.getGoodsname());
			rod.setGoodsuuid(od.getGoodsuuid());
			rod.setMoney(od.getMoney());
			rod.setNum(od.getNum());
			rod.setPrice(od.getPrice());
			
			// 填充退货单明细剩余信息
			rod.setState(Returnorderdetail.STATE_NOT_OUT);
			rod.setReturnorders(rtnOrders);
			
			rtnOrders.getReturnorderdetails().add(rod);
		}
		rtnOrders.setTotalmoney(totalMoney);
		
		super.add(rtnOrders);
		
		// 将原订单的状态修改为 退货登记
		orders.setState(Orders.STATE_RETURN_CREATE);
	}
}
