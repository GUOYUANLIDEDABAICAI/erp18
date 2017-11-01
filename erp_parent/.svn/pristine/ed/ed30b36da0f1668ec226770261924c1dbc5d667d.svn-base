package cn.itcast.erp.entity;
/**
 * 员工请假单实体类
 * @author Administrator *
 */
public class Emp_leavenote {	
	
	/**
	 * 关联类型：申请
	 */
	public static Long TYPE_APPLY = 1L;
	/**
	 * 关联类型：审批
	 */
	public static Long TYPE_CHECK = 2L;
	
	/**
	 * 状态：未完成
	 */
	public static Long STATE_NOT_DONE = 0L;
	/**
	 * 状态：已完成
	 */
	public static Long STATE_DONE = 1L;
	
	private Long uuid;//编号
	private Long empuuid;//员工编号
	//private Long leavenoteuuid;//请假单编号
	private Long type;//类型
	private Long state;//状态

	private Leavenote leavenote;
	
	public Long getUuid() {		
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	
	public Long getEmpuuid() {
		return empuuid;
	}
	public void setEmpuuid(Long empuuid) {
		this.empuuid = empuuid;
	}
	public Long getType() {		
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public Long getState() {		
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public Leavenote getLeavenote() {
		return leavenote;
	}
	public void setLeavenote(Leavenote leavenote) {
		this.leavenote = leavenote;
	}
	
}
