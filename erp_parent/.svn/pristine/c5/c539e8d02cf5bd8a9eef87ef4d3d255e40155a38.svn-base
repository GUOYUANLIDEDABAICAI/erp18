package cn.itcast.erp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;


public class MyDateConverter extends StrutsTypeConverter{
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");

	
	//收到页面的数据，就走这个方法去转化类型
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		
//		values 就是收到页面传过来的数据
		try {
			return format1.parse(values[0]);
		} catch (ParseException e) {
			e.printStackTrace();
			try {
				return format2.parse(values[0]);
			} catch (ParseException e1) {
				e1.printStackTrace();
				try {
					return format3.parse(values[0]);
				} catch (ParseException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		return null;
	}

	//给页面输出的时候，走这个方法
	@Override
	public String convertToString(Map context, Object o) {
		return format3.format(o);
	}

}