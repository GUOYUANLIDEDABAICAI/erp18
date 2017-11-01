package cn.itcast.erp.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import cn.itcast.erp.dao.IReportDao;
@SuppressWarnings("unchecked")
public class ReportDao extends HibernateDaoSupport implements IReportDao {
	
	@Override
	public List<Map<String,Object>> orderReport(Date startDate, Date endDate) {
		String hql = "select new Map(gt.name as name,sum(od.money) as y) from Goodstype gt, Orderdetail od, Orders o, Goods g "
				+ "where "
				+ "g.goodstype=gt and "
				+ "g.uuid = od.goodsuuid and "
				+ "od.orders = o and o.type='2' ";
		List<Date> params = new ArrayList<Date>();
		if(null != startDate){
			hql += " and o.createtime>=?";
			params.add(startDate);
		}
		if(null != endDate){
			hql += " and o.createtime<=?";
			params.add(endDate);
		}
		hql += "group by gt.name";
		return (List<Map<String,Object>>)this.getHibernateTemplate().find(hql, params.toArray());
	}

	@Override
	public List<Map<String, Object>> trendReport(int year) {
		String hql ="select new Map(month(o.createtime) || '月' as name,sum(od.money) as y) from Orders o, Orderdetail od "
				+ "where od.orders=o and o.type='2' and year(o.createtime)=? "
				+ "group by month(o.createtime)";
		return (List<Map<String, Object>>)this.getHibernateTemplate().find(hql, year);
	}

	@Override
	public List<Map<String, Object>> trendCancelReport(int year) {
		String hql="select new Map(month(ro.createtime) || '月' as name,sum(rod.money) as y) from Returnorders ro,Returnorderdetail rod "
				+ "where rod.returnorders=ro and ro.type='2' and year(ro.createtime)=? "
				+ "group by month(ro.createtime)" ;
		return (List<Map<String, Object>>) this.getHibernateTemplate().find(hql, year);
	}

}
