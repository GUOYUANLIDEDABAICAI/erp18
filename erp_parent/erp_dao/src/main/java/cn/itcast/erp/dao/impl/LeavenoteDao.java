package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.ILeavenoteDao;
import cn.itcast.erp.entity.Leavenote;
/**
 * 请假单数据访问类
 * @author Administrator
 *
 */
public class LeavenoteDao extends BaseDao<Leavenote> implements ILeavenoteDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Leavenote leavenote1,Leavenote leavenote2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Leavenote.class);
		if(leavenote1!=null){
			if(null != leavenote1.getReason() && leavenote1.getReason().trim().length()>0){
				dc.add(Restrictions.like("reason", leavenote1.getReason(), MatchMode.ANYWHERE));
			}

		}
		return dc;
	}

}
