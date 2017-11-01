package cn.itcast.erp.action;
import java.util.List;

import com.alibaba.fastjson.JSON;

import cn.itcast.erp.biz.IReturnordersBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Returnorderdetail;
import cn.itcast.erp.entity.Returnorders;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.WebUtil;

/**
 * 退货订单Action 
 * @author Administrator
 *
 */
public class ReturnordersAction extends BaseAction<Returnorders> {
	
	private Long ordersuuid;
	
	public void setOrdersuuid(Long ordersuuid) {
		this.ordersuuid = ordersuuid;
	}
	
	private String jsonString;
	
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	private IReturnordersBiz returnordersBiz;

	public void setReturnordersBiz(IReturnordersBiz returnordersBiz) {
		this.returnordersBiz = returnordersBiz;
		super.setBaseBiz(this.returnordersBiz);
	}
	
	public void myListByPage(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null != loginUser){
			if(null == getT1()){
				//构建条件
				setT1(new Returnorders());
			}
			//订单发起人
			getT1().setCreater(loginUser.getUuid());
			super.listByPage();
		}
	}
	
	public void add(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		//获取传过来的订单，里面只有供应商编号
		try {
			Returnorders orders = getT();
			//设置下单个
			orders.setCreater(loginUser.getUuid());
			//把json格式的字符的明细，转成java的集合
			List<Returnorderdetail> orderdetails = JSON.parseArray(jsonString, Returnorderdetail.class);
			//设置订单的明细
			orders.setReturnorderdetails(orderdetails);
			//调用业务保存订单
			returnordersBiz.add(orders);
			WebUtil.ajaxReturn(true, "添加退货单成功");
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "添加退货单失败");
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
			returnordersBiz.doCheck(getId(), loginUser.getUuid());
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
	 * 根据已有订单，生成退货单
	 */
	public void doReturn() {
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		try {
			returnordersBiz.doReturn(ordersuuid, loginUser.getUuid());
			WebUtil.ajaxReturn(true, "订单退货申请成功！");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "订单退货申请失败！");
			e.printStackTrace();
		}
	}

}
