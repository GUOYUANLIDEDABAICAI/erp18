package cn.itcast.erp.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.entity.Emp;

public class WebUtil {

	/**
	 * 返回前端操作结果
	 * @param success
	 * @param message
	 */
	public static void ajaxReturn(boolean success, String message){
		//返回前端的JSON数据
		Map<String, Object> rtn = new HashMap<String, Object>();
		rtn.put("success",success);
		rtn.put("message",message);
		write(JSON.toJSONString(rtn));
	}
	
	public static void write(Object obj){
		write(JSON.toJSONString(obj));
	}
	
	/**
	 * 输出字符串到前端
	 * @param jsonString
	 */
	public static void write(String jsonString){
		try {
			//响应对象
			HttpServletResponse response = ServletActionContext.getResponse();
			//设置编码
			response.setContentType("text/html;charset=utf-8"); 
			//输出给页面
			response.getWriter().write(jsonString);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取登陆用户
	 * @return
	 */
	public static Emp getLoginUser(){
		return (Emp)ServletActionContext.getContext().getSession().get("loginUser");
	}
}
