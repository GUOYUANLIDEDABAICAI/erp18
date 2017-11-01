package cn.itcast.erp.biz.impl;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.dao.IGoodsDao;
import cn.itcast.erp.dao.IGoodstypeDao;
import cn.itcast.erp.entity.Goods;
import cn.itcast.erp.entity.Goodstype;
import cn.itcast.erp.entity.Supplier;
/**
 * 商品业务逻辑类
 * @author Administrator
 *
 */
public class GoodsBiz extends BaseBiz<Goods> implements IGoodsBiz {

	private IGoodsDao goodsDao;
	private IGoodstypeDao goodstypeDao;

	public void setGoodstypeDao(IGoodstypeDao goodstypeDao) {
		this.goodstypeDao = goodstypeDao;
	}

	public void setGoodsDao(IGoodsDao goodsDao) {
		this.goodsDao = goodsDao;
		super.setBaseDao(this.goodsDao);
	}
	
	/**
	 * 商品导出
	 */
	@Override
	public void export(OutputStream os, Goods t1) throws Exception {
		List<Goods> list = getList(t1, null, null);
		// 创建工作簿
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook();//97-03 excel
			// 创建工作表
			String sheetName = Goods.SHEET_NAME;//表名
			Sheet sheet = wb.createSheet(sheetName);
			// 创建行, 放表头
			Row row = sheet.createRow(0);//索引从0开始
			String[] headerNames = {"名称","产地","厂家","计量单位","进货价格","销售价格","商品类型"};
			int[] columnsWidth = {2000,2000,8000,3000,3000,3000,3000,3000};
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
			for(Goods g : list){
				row = sheet.createRow(i);
				row.createCell(0).setCellValue(g.getName());
				row.createCell(1).setCellValue(g.getOrigin());
				row.createCell(2).setCellValue(g.getProducer());
				row.createCell(3).setCellValue(g.getUnit());
				row.createCell(4).setCellValue(g.getInprice());
				row.createCell(5).setCellValue(g.getOutprice());
				row.createCell(6).setCellValue(g.getGoodstype().getName());
				i++;
			}
			wb.write(os);
		} finally {
			if(null != wb){
				wb.close();
			}
		}
	}
	
	/**
	 * 商品导入
	 */
	@Override
	public void doImport(InputStream is) throws Exception {
		// 获取excel
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook(is);
			Sheet sheet = wb.getSheetAt(0);//获取第一张工作表
			int lastRowNum = sheet.getLastRowNum();// 得到最后一行

			Row row = null;
			Goods goods = null;
			String name = null;
			for(int i = 1; i <= lastRowNum; i++){
				row = sheet.getRow(i);
				//构建查询条件
				goods = new Goods();
				if(null == row){
						break;			
				}
				name = row.getCell(0).getStringCellValue();
				
				//根据名称精确查询
				goods.setName(name);
				List<Goods> list = goodsDao.getList(null, goods, null);
				if(list.size() > 0){
					//存在记录
					goods = list.get(0);//进入持久化状态
				}
				goods.setOrigin(row.getCell(1).getStringCellValue());
				goods.setProducer(row.getCell(2).getStringCellValue());
				goods.setUnit(row.getCell(3).getStringCellValue());
				goods.setInprice(Double.valueOf(row.getCell(4).getNumericCellValue()));
				goods.setOutprice(Double.valueOf(row.getCell(5).getNumericCellValue()));
				String goodsTypeName = row.getCell(6).getStringCellValue();
				
				Goodstype goodstype1 = new Goodstype();
				goodstype1.setName(goodsTypeName);
				Goodstype goodstype = goodstypeDao.getList(goodstype1, null, null).get(0);
				/*Long uuid = goodstype.getUuid();
				goods.setGoodstypeuuid(uuid);*/
				goods.setGoodstype(goodstype);
				if(list.size() == 0){
					//不存在记录
					goodsDao.add(goods);
				}
			}
		} finally {
			if(null != wb){
				wb.close();
			}
		}
	}
	
}
