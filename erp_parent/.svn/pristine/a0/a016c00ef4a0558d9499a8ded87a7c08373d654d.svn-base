package cn.itcast.erp.action;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.util.WebUtil;

public class ReportAction {

	private IReportBiz reportBiz;
	private Date startDate;//页面传过来的开始日期
	private Date endDate;//页面传过来的结束日期
	private int year;
	
	/**
	 * 销售统计
	 */
	public void orderReport(){
		List<Map<String,Object>> list = reportBiz.orderReport(startDate, endDate);
		WebUtil.write(list);
	}
	
	/**
	 * 销售趋势
	 */
	public void trendReport(){
		List<Map<String,Object>> list = reportBiz.trendReport(year);
		WebUtil.write(list);
	}
	/**
	 * 销售退货趋势
	 */
	public void trendCancelReport(){
		List<Map<String,Object>> list = reportBiz.trendCancelReport(year);
		WebUtil.write(list);
	}
	
	/**
	 * 销售趋势
	 */
	public void tr(){
		String list = reportBiz.tr(year);
		WebUtil.write(list);
	}
	
	public void setReportBiz(IReportBiz reportBiz) {
		this.reportBiz = reportBiz;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setYear(int year) {
		this.year = year;
	}
}
