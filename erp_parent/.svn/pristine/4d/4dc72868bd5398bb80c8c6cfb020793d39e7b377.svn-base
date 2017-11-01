package cn.itcast.erp.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 请假单实体类
 * @author Administrator *
 */
public class Leavenote {
	/**
	 * 请假单状态：未审批
	 */
	public static final Long STATE_CREATE = 0L;
	/**
	 * 请假单状态：已审批
	 */
	public static final Long STATE_CHECK = 1L;
	
	private Long uuid;//编号
	private java.util.Date starttime;//开始时间
	private java.util.Date endtime;//结束时间
	private String reason;//请假原因
	private Long state;//状态
	
	private List<Emp_leavenote> emp_leavenotes = new ArrayList<Emp_leavenote>();

	public Long getUuid() {		
		return uuid;
	}
	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}
	public java.util.Date getStarttime() {		
		return starttime;
	}
	public void setStarttime(java.util.Date starttime) {
		this.starttime = starttime;
	}
	public java.util.Date getEndtime() {		
		return endtime;
	}
	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}
	public String getReason() {		
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Long getState() {		
		return state;
	}
	public void setState(Long state) {
		this.state = state;
	}
	public List<Emp_leavenote> getEmp_leavenotes() {
		return emp_leavenotes;
	}
	public void setEmp_leavenotes(List<Emp_leavenote> emp_leavenotes) {
		this.emp_leavenotes = emp_leavenotes;
	}
	
	

}
