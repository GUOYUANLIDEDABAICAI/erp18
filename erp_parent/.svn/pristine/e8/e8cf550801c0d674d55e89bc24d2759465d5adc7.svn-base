package cn.itcast.erp.dao.impl;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import cn.itcast.erp.dao.IJobDao;
import cn.itcast.erp.entity.Job;
/**
 * 职位数据访问类
 * @author Administrator
 *
 */
public class JobDao extends BaseDao<Job> implements IJobDao {

	/**
	 * 构建查询条件
	 * @param dep1
	 * @param dep2
	 * @param param
	 * @return
	 */
	public DetachedCriteria getDetachedCriteria(Job job1,Job job2,Object param){
		DetachedCriteria dc=DetachedCriteria.forClass(Job.class);
		if(job1!=null){
			if(null != job1.getName() && job1.getName().trim().length()>0){
				dc.add(Restrictions.like("name", job1.getName(), MatchMode.ANYWHERE));
			}
			if(null != job1.getSuffix() && job1.getSuffix().trim().length()>0){
				dc.add(Restrictions.like("suffix", job1.getSuffix(), MatchMode.ANYWHERE));
			}
			if(null != job1.getRemark() && job1.getRemark().trim().length()>0){
				dc.add(Restrictions.like("remark", job1.getRemark(), MatchMode.ANYWHERE));
			}
			
			// 根据empuuid查询岗位
			if (job1.getEmpuuid() != null) {
				dc.add(Restrictions.eq("empuuid", job1.getEmpuuid()));
			}

		}
		return dc;
	}

}
