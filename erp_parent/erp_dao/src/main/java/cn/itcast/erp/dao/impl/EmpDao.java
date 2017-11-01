package cn.itcast.erp.dao.impl;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.entity.Emp;
/**
 * 员工数据访问类
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class EmpDao extends BaseDao<Emp> implements IEmpDao {
	
	

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	@Override
	public DetachedCriteria getDetachedCriteria(Emp emp1,Emp emp2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Emp.class);
		if(emp1!=null){
			if(null != emp1.getUsername() && emp1.getUsername().trim().length()>0){
				dc.add(Restrictions.like("username", emp1.getUsername(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getName() && emp1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", emp1.getName(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getEmail() && emp1.getEmail().trim().length()>0){
				dc.add(Restrictions.like("email", emp1.getEmail(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getTele() && emp1.getTele().trim().length()>0){
				dc.add(Restrictions.like("tele", emp1.getTele(), MatchMode.ANYWHERE));
			}
			if(null != emp1.getAddress() && emp1.getAddress().trim().length()>0){
				dc.add(Restrictions.like("address", emp1.getAddress(), MatchMode.ANYWHERE));
			}
			//性别查询
			if(null != emp1.getGender()){
				dc.add(Restrictions.eq("gender", emp1.getGender()));
			}
			//部门查询
			if(null != emp1.getDep() && null != emp1.getDep().getUuid()){
				dc.add(Restrictions.eq("dep", emp1.getDep()));
			}
			//出生年月日, 开始日期
			if(null != emp1.getBirthday()){
				dc.add(Restrictions.ge("birthday", emp1.getBirthday()));
			}
		}
		if(null != emp2){
			//出生年月日, 结束日期
			if(null != emp2.getBirthday()){
				dc.add(Restrictions.le("birthday",emp2.getBirthday()));
			}
		}
		dc.addOrder(Order.asc("uuid"));
		//dc.addOrder(Order.asc("createtime"));
		return dc;
	}

	@Override
	public Emp findByUsernameAndPwd(String username, String pwd) {
		
		List<Emp> empList = (List<Emp>)this.getHibernateTemplate().find("from Emp where username=? and pwd=?", username,pwd);
		if(null != empList && empList.size() > 0){
			return empList.get(0);
		}
		return null;
	}

	@Override
	public void updatePwd(String newPwd, Long uuid) {
		String hql = "update Emp set pwd=? where uuid=?";
		this.getHibernateTemplate().bulkUpdate(hql, newPwd, uuid);
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
