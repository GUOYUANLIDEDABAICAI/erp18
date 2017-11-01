package cn.itcast.erp.biz.impl;
import java.util.Date;
import java.util.List;

import cn.itcast.erp.biz.IOrderdetailBiz;
import cn.itcast.erp.dao.IOrderdetailDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.dao.IStoreoperDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.entity.Storeoper;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.exception.ErpException;
import cn.redsun.bos.ws.impl.IWaybillWs;
/**
 * 订单明细业务逻辑类
 * @author Administrator
 *
 */
public class OrderdetailBiz extends BaseBiz<Orderdetail> implements IOrderdetailBiz {

	private IOrderdetailDao orderdetailDao;
	private IStoredetailDao storedetailDao;
	private IStoreoperDao storeoperDao;
	private IWaybillWs waybillWs;
	private ISupplierDao supplierDao;
	
	public void setOrderdetailDao(IOrderdetailDao orderdetailDao) {
		this.orderdetailDao = orderdetailDao;
		super.setBaseDao(this.orderdetailDao);
	}

	@Override
	public void doInStore(Long uuid, Long storeuuid, Long empuuid) {
		// TODO Auto-generated method stub
		// ******** 入库的步骤： *****************
		// ******** 1. Orderdetail *****************
		// 查询明细, 明细进入持久化状态
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		// 状态的判断
		if(!Orderdetail.STATE_NOT_IN.equals(orderdetail.getState())){
			throw new ErpException("亲，该明细已经入库了");
		}
//		1.1 结束日期(系统时间)
		orderdetail.setEndtime(new Date());
//		  1.2 设置库管员(当前登陆用户)
		orderdetail.setEnder(empuuid);
//		  1.3 仓库编号(前端传过来的)
		orderdetail.setStoreuuid(storeuuid);
//		  1.4 明细的状态为已入库(1)
		orderdetail.setState(Orderdetail.STATE_IN);
		// ******** 2. 商品仓库库存(Storedetail) *****************
//		  2.2 查看是否存在库存信息, 查询的条件(仓库的编号，商品的编号)
		//  构建查询条件
		Storedetail sd = new Storedetail();
		//  构建查询条件  商品编号
		sd.setGoodsuuid(orderdetail.getGoodsuuid());
		//  构建查询条件 仓库编号
		sd.setStoreuuid(storeuuid);
		List<Storedetail> storedetails = storedetailDao.getList(sd, null, null);
		if(storedetails.size() > 0){
//		  2.3  如果存在, 数量累加
			sd = storedetails.get(0);
			sd.setNum(sd.getNum() + orderdetail.getNum());
		}else{
//		  2.4  不存在, 新增库存信息记录
			//设置数量
			sd.setNum(orderdetail.getNum());
			//新增库存信息记录
			storedetailDao.add(sd);
		}
		// ******** 3. 商品库存变更记录 *****************
//		  3.1 增加操作记录
		Storeoper log = new Storeoper();
//		      操作员: 当前登陆用户
		log.setEmpuuid(empuuid);
//			  操作日期：系统时间
		log.setOpertime(orderdetail.getEndtime());
//			  仓库的编号: 前端
		log.setStoreuuid(storeuuid);
//			  商品的编号: 前端(明细里有)
		log.setGoodsuuid(orderdetail.getGoodsuuid());
//			  数量： 前端(明细里有)
		log.setNum(orderdetail.getNum());
//			  操作类型：入库(1)
		log.setType(Storeoper.TYPE_IN);
		storeoperDao.add(log);
		// ******** 4. 订单表的操作(orders) *****************
//		  4.1 判断是否所有的明细都完成入库?
//		      计算未入库的明细的个数,查询条件(ordersuuid, state=0)
		//  构建查询条件
		Orderdetail queryParam = new Orderdetail();
		//获取所属订单，此时订单进入持久化状态
		Orders orders = orderdetail.getOrders();
		queryParam.setOrders(orders);
		queryParam.setState(Orderdetail.STATE_NOT_IN);
		long count = orderdetailDao.getCount(queryParam, null, null);
//		  4.2 都已完成入库，更新订单
		if(count == 0){
//			  订单的状态(3:已入库)
			orders.setState(Orders.STATE_END);
//			  入库的时间：系统时间
			orders.setEndtime(orderdetail.getEndtime());
//			  库管员: 当前登陆用户
			orders.setEnder(empuuid);
		}
	}
	
	@Override
	public void doOutStore(Long uuid, Long storeuuid, Long empuuid) {
		// TODO Auto-generated method stub
		// ******** 出库的步骤： *****************
		// ******** 1. Orderdetail *****************
		// 查询明细, 明细进出持久化状态
		Orderdetail orderdetail = orderdetailDao.get(uuid);
		// 状态的判断
		if(!Orderdetail.STATE_NOT_OUT.equals(orderdetail.getState())){
			throw new ErpException("亲，该明细已经出库了");
		}
//		1.1 结束日期(系统时间)
		orderdetail.setEndtime(new Date());
//		  1.2 设置库管员(当前登陆用户)
		orderdetail.setEnder(empuuid);
//		  1.3 仓库编号(前端传过来的)
		orderdetail.setStoreuuid(storeuuid);
//		  1.4 明细的状态为已出库(1)
		orderdetail.setState(Orderdetail.STATE_OUT);
		// ******** 2. 商品仓库库存(Storedetail) *****************
//		  2.2 查看是否存在库存信息, 查询的条件(仓库的编号，商品的编号)
		//  构建查询条件
		Storedetail sd = new Storedetail();
		//  构建查询条件  商品编号
		sd.setGoodsuuid(orderdetail.getGoodsuuid());
		//  构建查询条件 仓库编号
		sd.setStoreuuid(storeuuid);
		List<Storedetail> storedetails = storedetailDao.getList(sd, null, null);
		//库存数量
		long num = -1l;
		if(storedetails.size() > 0){
//		  2.3  如果存在, 数量减掉
			sd = storedetails.get(0);
			num = sd.getNum() - orderdetail.getNum();
			if(num < 0){
				throw new ErpException("库存不足!");
			}
			sd.setNum(num);
		}else{
			throw new ErpException("库存不足!");
		}
		
		// ******** 3. 商品库存变更记录 *****************
//		  3.1 增加操作记录
		Storeoper log = new Storeoper();
//		      操作员: 当前登陆用户
		log.setEmpuuid(empuuid);
//			  操作日期：系统时间
		log.setOpertime(orderdetail.getEndtime());
//			  仓库的编号: 前端
		log.setStoreuuid(storeuuid);
//			  商品的编号: 前端(明细里有)
		log.setGoodsuuid(orderdetail.getGoodsuuid());
//			  数量： 前端(明细里有)
		log.setNum(orderdetail.getNum());
//			  操作类型：出库(2)
		log.setType(Storeoper.TYPE_OUT);
		storeoperDao.add(log);
		// ******** 4. 订单表的操作(orders) *****************
//		  4.1 判断是否所有的明细都完成出库?
//		      计算未出库的明细的个数,查询条件(ordersuuid, state=0)
		//  构建查询条件
		Orderdetail queryParam = new Orderdetail();
		//获取所属订单，此时订单进出持久化状态
		Orders orders = orderdetail.getOrders();
		queryParam.setOrders(orders);
		queryParam.setState(Orderdetail.STATE_NOT_OUT);
		long count = orderdetailDao.getCount(queryParam, null, null);
//		  4.2 都已完成出库，更新订单
		if(count == 0){
//			  订单的状态(2:已出库)
			orders.setState(Orders.STATE_OUT);
//			  出库的时间：系统时间
			orders.setEndtime(orderdetail.getEndtime());
//			  库管员: 当前登陆用户
			orders.setEnder(empuuid);
			
/*			//通过客户编号获取客户信息
			Supplier customer = supplierDao.get(orders.getSupplieruuid());
			
			//预约下单
			// 用户编号: ERP相对于BOS系统的客户编号
			// 调用bos预约下单, 得到运单号
			Long sn = waybillWs.add(1l, customer.getAddress(), customer.getContact(), customer.getTele(), "--");
			// 设置订单的运单号
			orders.setWaybillsn(sn);*/
		}
	}

	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
	}

	public void setStoreoperDao(IStoreoperDao storeoperDao) {
		this.storeoperDao = storeoperDao;
	}

	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}
	
}
