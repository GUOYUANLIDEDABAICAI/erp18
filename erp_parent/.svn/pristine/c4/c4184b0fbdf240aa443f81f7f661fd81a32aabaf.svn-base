package cn.itcast.erp.biz;
import java.io.InputStream;
import java.io.OutputStream;

import cn.itcast.erp.entity.Supplier;
/**
 * 供应商业务逻辑层接口
 * @author Administrator
 *
 */
public interface ISupplierBiz extends IBaseBiz<Supplier>{
	
	/**
	 * 导出数据
	 * @param os
	 * @throws Exception
	 */
	void export(OutputStream os, Supplier t1) throws Exception;
	
	/**
	 * 导入数据
	 * @param is
	 * @throws Exception
	 */
	void doImport(InputStream is) throws Exception;

}

