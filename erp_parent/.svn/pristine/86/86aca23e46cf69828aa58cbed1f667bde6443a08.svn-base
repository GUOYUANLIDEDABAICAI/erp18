package cn.itcast.erp.biz;
import java.io.InputStream;
import java.io.OutputStream;

import cn.itcast.erp.entity.Goods;
import cn.itcast.erp.entity.Supplier;
/**
 * 商品业务逻辑层接口
 * @author Administrator
 *
 */
public interface IGoodsBiz extends IBaseBiz<Goods>{

	/**
	 * 商品导出
	 */
	void export(OutputStream os, Goods t1) throws Exception;
	
	/**
	 * 商品导入
	 */
	void doImport(InputStream is) throws Exception;
}

