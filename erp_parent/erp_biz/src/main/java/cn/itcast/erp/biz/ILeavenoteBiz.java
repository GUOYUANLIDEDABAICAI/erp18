package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Leavenote;
/**
 * 请假单业务逻辑层接口
 * @author Administrator
 *
 */
public interface ILeavenoteBiz extends IBaseBiz<Leavenote>{
	/**
	 * 申请请假
	 * @param leavenote 请假单
	 * @param empuuid 申请者的empuuid
	 */
	void add(Leavenote leavenote, Long empuuid);
	
	/**
	 * 审批请假
	 * @param leavenote 待审批的请假单uuid
	 * @param empuuid 审批者
	 */
	void doCheck(Long leavenoteuuid, Long empuuid);
}

