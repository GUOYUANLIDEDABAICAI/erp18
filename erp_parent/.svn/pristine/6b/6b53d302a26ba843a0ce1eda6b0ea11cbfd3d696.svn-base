package cn.itcast.erp.action;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.WebUtil;
import cn.redsun.bos.ws.Waybilldetail;
import cn.redsun.bos.ws.impl.IWaybillWs;

/**
 * 订单Action 
 * @author Administrator
 *
 */
public class OrdersAction extends BaseAction<Orders> {

	private IOrdersBiz ordersBiz;
	private IWaybillWs waybillWs;
	private Long waybillsn;//运单号

	public void setOrdersBiz(IOrdersBiz ordersBiz) {
		this.ordersBiz = ordersBiz;
		super.setBaseBiz(this.ordersBiz);
	}
	
	private String jsonString;//订单明细数组，json字符串格式

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	/**
	 * 添加订单
	 */
	public void add(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		//获取传过来的订单，里面只有供应商编号
		try {
			Orders orders = getT();
			//设置下单个
			orders.setCreater(loginUser.getUuid());
			//把json格式的字符的明细，转成java的集合
			List<Orderdetail> orderdetails = JSON.parseArray(jsonString, Orderdetail.class);
			//设置订单的明细
			orders.setOrderdetails(orderdetails);
			//调用业务保存订单
			ordersBiz.add(orders);
			WebUtil.ajaxReturn(true, "添加订单成功");
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "添加订单失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 审核
	 */
	public void doCheck(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		try {
			ordersBiz.doCheck(getId(), loginUser.getUuid());
			WebUtil.ajaxReturn(true, "审核成功");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "审核失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 确认
	 */
	public void doStart(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		try {
			ordersBiz.doStart(getId(), loginUser.getUuid());
			WebUtil.ajaxReturn(true, "确认成功");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "确认失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 列出由我发起的订单
	 */
	public void myListByPage(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null != loginUser){
			if(null == getT1()){
				//构建条件
				setT1(new Orders());
			}
			//订单发起人
			getT1().setCreater(loginUser.getUuid());
			super.listByPage();
		}
	}
	
	/**
	 * 导出订单
	 */
	public void exportById(){
		HttpServletResponse res = ServletActionContext.getResponse();
		//设置响应头，告诉浏览器，下载文件
		try {
			res.setHeader("Content-Disposition","attachement;filename=orders_" + getId() + ".xls");
			ordersBiz.ex(getId(),res.getOutputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 查询运单详情
	 */
	public void waybilldetailList(){
		List<Waybilldetail> list = waybillWs.waybilldetailList(waybillsn);
		WebUtil.write(list);
	}

	public void setWaybillWs(IWaybillWs waybillWs) {
		this.waybillWs = waybillWs;
	}

	public void setWaybillsn(Long waybillsn) {
		this.waybillsn = waybillsn;
	}
}
