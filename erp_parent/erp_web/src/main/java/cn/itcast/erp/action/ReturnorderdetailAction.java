package cn.itcast.erp.action;
import cn.itcast.erp.biz.IReturnorderdetailBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Returnorderdetail;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.WebUtil;

/**
 * 退货订单明细Action 
 * @author Administrator
 *
 */
public class ReturnorderdetailAction extends BaseAction<Returnorderdetail> {
	
	private Long storeuuid;//仓库编号
	
	public void setStoreuuid(Long storeuuid) {
		this.storeuuid = storeuuid;
	}

	private IReturnorderdetailBiz returnorderdetailBiz;

	public void setReturnorderdetailBiz(IReturnorderdetailBiz returnorderdetailBiz) {
		this.returnorderdetailBiz = returnorderdetailBiz;
		super.setBaseBiz(this.returnorderdetailBiz);
	}
	
	/**
	 * 出库
	 */
	public void doOutStore(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		try {
			returnorderdetailBiz.doOutStore(getId(), storeuuid, loginUser.getUuid());
			WebUtil.ajaxReturn(true, "出库成功");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "出库失败");
			e.printStackTrace();
		}
	}
	
	/**
	 * 入库
	 */
	public void doInStore(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		try {
			returnorderdetailBiz.doInStore(getId(), storeuuid, loginUser.getUuid());
			WebUtil.ajaxReturn(true, "入库成功");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "入库失败");
			e.printStackTrace();
		}
	}

}
