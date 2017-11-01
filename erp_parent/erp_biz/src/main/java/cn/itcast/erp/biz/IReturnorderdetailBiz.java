package cn.itcast.erp.biz;
import cn.itcast.erp.entity.Returnorderdetail;
/**
 * 退货订单明细业务逻辑层接口
 * @author Administrator
 *
 */
public interface IReturnorderdetailBiz extends IBaseBiz<Returnorderdetail>{
	void doOutStore(Long uuid, Long storeuuid, Long empuuid);
	void doInStore(Long uuid, Long storeuuid, Long empuuid);
}

