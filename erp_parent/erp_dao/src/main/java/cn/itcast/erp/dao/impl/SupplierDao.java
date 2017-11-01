package cn.itcast.erp.dao.impl;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Supplier;
/**
 * 供应商数据访问类
 * @author Administrator
 *
 */
public class SupplierDao extends BaseDao<Supplier> implements ISupplierDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Supplier supplier1,Supplier supplier2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Supplier.class);
		if(supplier1!=null){
			if(null != supplier1.getName() && supplier1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", supplier1.getName(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getAddress() && supplier1.getAddress().trim().length()>0){
				dc.add(Restrictions.like("address", supplier1.getAddress(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getContact() && supplier1.getContact().trim().length()>0){
				dc.add(Restrictions.like("contact", supplier1.getContact(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getTele() && supplier1.getTele().trim().length()>0){
				dc.add(Restrictions.like("tele", supplier1.getTele(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getEmail() && supplier1.getEmail().trim().length()>0){
				dc.add(Restrictions.like("email", supplier1.getEmail(), MatchMode.ANYWHERE));
			}
			if(null != supplier1.getType() && supplier1.getType().trim().length()>0){
				dc.add(Restrictions.eq("type", supplier1.getType()));
			}
		}
		if(null != supplier2){
			//精确匹配名称
			if(!StringUtils.isEmpty(supplier2.getName())){
				dc.add(Restrictions.eq("name", supplier2.getName()));
			}
		}
		return dc;
	}
	
	/*@Override
	public String getObjectName(Long uuid, Map<Long, String> nameMap) {
		if(null == uuid){
			return null;
		}
		//根据员工编号，从缓存中取出名称
		String name = nameMap.get(uuid);
		//如果缓存中不存在名称
		if(null == nameMap.get(uuid)){
			//查询出对应的名称
			name = get(uuid).getName();
			//放入缓存中
			nameMap.put(uuid, name);
		}
		return name;
	}*/

}
