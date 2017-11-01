package cn.itcast.erp.biz.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.itcast.erp.biz.IReportBiz;
import cn.itcast.erp.dao.IReportDao;

public class ReportBiz implements IReportBiz {
	
	private IReportDao reportDao;

	@Override
	public List<Map<String,Object>> orderReport(Date startDate, Date endDate) {
		
		return reportDao.orderReport(startDate, endDate);
	}

	public void setReportDao(IReportDao reportDao) {
		this.reportDao = reportDao;
	}
	
	@Override
	public String tr(int year){
		if(year == 0){
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		
		//查询得到月份的数据，可能月份不完整，部分月份没有数据
		List<Map<String, Object>> monthsDataDB = reportDao.trendReport(year);
		String result = "[{\"name\":\"1月\",\"y\":0},{\"name\":\"2月\",\"y\":0},{\"name\":\"3月\",\"y\":0},{\"name\":\"4月\",\"y\":0},{\"name\":\"5月\",\"y\":0},{\"name\":\"6月\",\"y\":0},{\"name\":\"7月\",\"y\":0},{\"name\":\"8月\",\"y\":0},{\"name\":\"9月\",\"y\":0},{\"name\":\"10月\",\"y\":0},{\"name\":\"11月\",\"y\":0},{\"name\":\"12月\",\"y\":0}]";
		for(Map<String, Object> monthData : monthsDataDB){
			String key = monthData.get("name").toString().replace("月","");
			String replaceKey = "{\"name\":\"" + key + "月\",\"y\":0}";
			String replacement = "{\"name\":\"" + key + "月\",\"y\":" + monthData.get("y") + "}";
			result = result.replace(replaceKey, replacement);
		}
		return result;
	}

	@Override
	public List<Map<String, Object>> trendReport(int year) {
		if(year == 0){
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		
		//查询得到月份的数据，可能月份不完整，部分月份没有数据
		List<Map<String, Object>> monthsDataDB = reportDao.trendReport(year);
		//构建map存入数据中存在的月份数据, key = 月份， value = 月份的数据
		Map<String,Map<String,Object>> mapMonthData = new HashMap<String,Map<String,Object>>();
		for(Map<String, Object> monthDB : monthsDataDB){
			String key = (String)monthDB.get("name");//x月
			mapMonthData.put(key, monthDB);
		}
		
		//要返回的数据，带上12个月的数据，如果数据库中不存在这个月份的，则它的值为0
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		//int j = 0;
		//月份的数据 {name:'x月',y:xx}
		Map<String,Object> monthData = null;
		for(int i = 1; i <= 12; i++){
			//数据库中存在的月份
			monthData = mapMonthData.get(i + "月");
			/*
			if((i + "月").equals(monthsDataDB.get(j).get("name"))){
				monthData = monthsDataDB.get(j);
				j++;
			}*/
			
			/*for(Map<String, Object> monthDB : monthsDataDB){
				String month = (String)monthDB.get("name");
				//数据库这个月份有数据
				if(month.equals(i + "月")){
					monthData = monthDB;
					break;
				}
			}*/
			if(null == monthData){
				//构建月份数据
				monthData = new HashMap<String,Object>();
				monthData.put("name", i + "月");
				monthData.put("y", 0);
			}
			result.add(monthData);
		}
		
		//先构建12个月份的数据, 存到map里，key=月份
		
		return result;
	}

	@Override
	public List<Map<String, Object>> trendCancelReport(int year) {
		if(year == 0){
			year = Calendar.getInstance().get(Calendar.YEAR);
		}
		List<Map<String,Object>> monthsDataDB = reportDao.trendCancelReport(year);
		Map<String,Map<String,Object>> mapMonthData = new HashMap<String,Map<String,Object>>();
		for(Map<String, Object> monthDB : monthsDataDB){
			String key = (String)monthDB.get("name");//x月
			mapMonthData.put(key, monthDB);
		}
		//要返回的数据，带上12个月的数据，如果数据库中不存在这个月份的，则它的值为0
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String,Object> monthData = null;
		for(int i = 1; i <= 12; i++){
			monthData = mapMonthData.get(i + "月");
			if(null == monthData){
				//构建月份数据
				monthData = new HashMap<String,Object>();
				monthData.put("name", i + "月");
				monthData.put("y", 0);
			}
			result.add(monthData);
		}
		return result;
	}

}
