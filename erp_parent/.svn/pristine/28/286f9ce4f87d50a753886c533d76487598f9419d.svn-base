package cn.itcast.erp.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 报表接口
 *
 */
public interface IReportDao {

	/**
	 * 销售统计报表
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Map<String,Object>> orderReport(Date startDate, Date endDate);
	
	/**
	 * 销售趋势图
	 * @param year
	 * @return
	 */
	List<Map<String,Object>> trendReport(int year);
	
	/**
	 * 销售退货趋势图
	 */
	List<Map<String,Object>> trendCancelReport(int year);
}
