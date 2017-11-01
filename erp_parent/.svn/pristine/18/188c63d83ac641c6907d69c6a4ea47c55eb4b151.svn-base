package cn.itcast.erp.action;
import cn.itcast.erp.biz.IInventoryBiz;
import cn.itcast.erp.entity.Emp;
import cn.itcast.erp.entity.Inventory;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.WebUtil;

/**
 * 盘盈盘亏Action 
 * @author Administrator
 *
 */
public class InventoryAction extends BaseAction<Inventory> {

	private IInventoryBiz inventoryBiz;

	public void setInventoryBiz(IInventoryBiz inventoryBiz) {
		this.inventoryBiz = inventoryBiz;
		super.setBaseBiz(this.inventoryBiz);
	}
	
	@Override
	public void add() {
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		try {
			Inventory inventory = getT();
			//设置登记人,就是登陆用户
			inventory.setCreater(loginUser.getUuid());
			inventoryBiz.add(inventory);
			WebUtil.ajaxReturn(true, "登记成功");
		} catch (Exception e) {
			WebUtil.ajaxReturn(false, "登记失败");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void doCheck(){
		Emp loginUser = WebUtil.getLoginUser();
		if(null == loginUser){
			WebUtil.ajaxReturn(false, "您还没有登陆");
			return;
		}
		
		try {
			
			System.out.println("999999999999999999888888"+getT().getNum());
			inventoryBiz.doCheck(getId(), loginUser.getUuid(),getT().getRemark());
			WebUtil.ajaxReturn(true, "审核成功");
		} catch (ErpException e) {
			WebUtil.ajaxReturn(false, e.getMessage());
			e.printStackTrace();
		}catch (Exception e) {
			WebUtil.ajaxReturn(false, "审核失败");
			e.printStackTrace();
		}
	}
}
