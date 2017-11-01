package cn.itcast.erp.biz.impl;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IStoreDao;
import cn.itcast.erp.dao.IStoredetailDao;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.entity.Storedetail;
import cn.itcast.erp.exception.ErpException;
import cn.itcast.erp.util.MailUtil;
/**
 * 仓库库存业务逻辑类
 * @author Administrator
 *
 */
public class StoredetailBiz extends BaseBiz<Storedetail> implements IStoredetailBiz {

	private IStoredetailDao storedetailDao;
	private IStoreDao storeDao;
	private IGoodsDao goodsDao;
	private MailUtil mailUtil;
	private String to;
	private String subject;
	private String text;
	
	public void setStoredetailDao(IStoredetailDao storedetailDao) {
		this.storedetailDao = storedetailDao;
		super.setBaseDao(this.storedetailDao);
	}
	
	@Override
	public List<Storedetail> getListByPage(Storedetail t1, Storedetail t2, Object param, int firstResult,
			int maxResults) {
		// TODO Auto-generated method stub
		List<Storedetail> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		for(Storedetail sd : list){
			sd.setGoodsname(goodsDao.getObjectName(sd.getGoodsuuid()));
			sd.setStorename(storeDao.getObjectName(sd.getStoreuuid()));
		}
		return list;
	}

	public void setStoreDao(IStoreDao storeDao) {
		this.storeDao = storeDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}

	@Override
	public List<Storealert> getStorealertList() {
		return storedetailDao.getStorealertList();
	}

	@Override
	public void sendStorealertMail() throws Exception {
		// 获取预警的列表
		List<Storealert> list = storedetailDao.getStorealertList();
		if(list.size() > 0){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String _subject = subject.replace("[time]", sdf.format(Calendar.getInstance().getTime()));
			String _text = text.replace("[count]", list.size() + "");
			mailUtil.sendMail(to, _subject, _text);
		}else{
			throw new ErpException("没有商品库存预警");
		}
		
	}

	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
