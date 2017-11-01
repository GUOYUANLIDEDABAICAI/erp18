package cn.itcast.erp.biz.impl;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Supplier;
/**
 * 供应商业务逻辑类
 * @author Administrator
 *
 */
public class SupplierBiz extends BaseBiz<Supplier> implements ISupplierBiz {

	private ISupplierDao supplierDao;
	
	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
		super.setBaseDao(this.supplierDao);
	}

	@Override
	public void export(OutputStream os, Supplier t1) throws Exception {
		List<Supplier> list = getList(t1, null, null);
		// 创建工作簿
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook();//97-03 excel
			// 创建工作表
			String sheetName = Supplier.TYPE_SUPPLIER_NAME;
			if(Supplier.TYPE_CUSTOMER.equals(t1.getType())){
				sheetName = Supplier.TYPE_CUSTOMER_NAME;
			}
			Sheet sheet = wb.createSheet(sheetName);
			// 创建行, 放表头
			Row row = sheet.createRow(0);//索引从0开始
			String[] headerNames = {"名称","地址","联系人","电话","Email"};
			int[] columnsWidth = {4000,8000,2000,3000,8000};
			Cell cell = null;
			int i = 0;
			for(; i < headerNames.length; i++){
				//单元格必须先创建后使用
				cell = row.createCell(i);
				cell.setCellValue(headerNames[i]);
				sheet.setColumnWidth(i, columnsWidth[i]);
			}
			
			//内容
			i = 1;
			for(Supplier s : list){
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(s.getName());
				row.createCell(1).setCellValue(s.getAddress());
				row.createCell(2).setCellValue(s.getContact());
				row.createCell(3).setCellValue(s.getTele());
				row.createCell(4).setCellValue(s.getEmail());
				i++;
			}
			wb.write(os);
		} finally {
			if(null != wb){
				wb.close();
			}
		}
	}

	@Override
	public void doImport(InputStream is) throws Exception {
		// 获取excel
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);//获取第一张工作表
			String sheetName = sheet.getSheetName();//工作表名称，用来区分类型
			int lastRowNum = sheet.getLastRowNum();// 得到最后一行
			String type = "";//类型
			if(Supplier.TYPE_SUPPLIER_NAME.equals(sheetName)){
				type = Supplier.TYPE_SUPPLIER;
			}
			if(Supplier.TYPE_CUSTOMER_NAME.equals(sheetName)){
				type = Supplier.TYPE_CUSTOMER;
			}
			Row row = null;
			Supplier supplier = null;
			String name = null;
			for(int i = 1; i <= lastRowNum; i++){
				row = sheet.getRow(i);
				//构建查询条件
				supplier = new Supplier();
				name = row.getCell(0).getStringCellValue();
				//根据名称精确查询
				supplier.setName(name);
				List<Supplier> list = supplierDao.getList(null, supplier, null);
				if(list.size() > 0){
					//存在记录
					supplier = list.get(0);//进入持久化状态
				}
				supplier.setAddress(row.getCell(1).getStringCellValue());
				supplier.setContact(row.getCell(2).getStringCellValue());
				supplier.setTele(row.getCell(3).getStringCellValue());
				supplier.setEmail(row.getCell(4).getStringCellValue());
				if(list.size() == 0){
					//设置类型
					supplier.setType(type);
					//不存在记录
					supplierDao.add(supplier);
				}
			}
		} finally {
			if(null != wb){
				wb.close();
			}
		}
	}
	
}
