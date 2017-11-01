package cn.itcast.erp.biz.impl;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.core.io.ClassPathResource;

import cn.itcast.erp.biz.IOrdersBiz;
import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orderdetail;
import cn.itcast.erp.entity.Orders;
import cn.itcast.erp.exception.ErpException;
import net.sf.jxls.transformer.XLSTransformer;
/**
 * 订单业务逻辑类
 * @author Administrator
 *
 */
public class OrdersBiz extends BaseBiz<Orders> implements IOrdersBiz {

	private IOrdersDao ordersDao;
	private IEmpDao empDao;
	private ISupplierDao supplierDao;
	
	public void setOrdersDao(IOrdersDao ordersDao) {
		this.ordersDao = ordersDao;
		super.setBaseDao(this.ordersDao);
	}
	
	@Override
	public void add(Orders orders) {
		// Orders的操作
		orders.setCreatetime(new Date());
		// 订单类型为采购订单1, 整合销售订单，前端会传订单类型
		//orders.setType(Orders.TYPE_IN);
		//订单的状态为未审核
		orders.setState(Orders.STATE_CREATE);
		// 计算合计金额
		double totalMoney = 0;
		// 循环明细
		for(Orderdetail od : orders.getOrderdetails()){
			//累计金额
			totalMoney += od.getMoney();
			//明细的状态为未入库
			od.setState(Orderdetail.STATE_NOT_IN);
			//设置与订单的关系
			od.setOrders(orders);
		}
		orders.setTotalmoney(totalMoney);
		super.add(orders);
	}
	
	@Override
	public List<Orders> getListByPage(Orders t1, Orders t2, Object param, int firstResult, int maxResults) {
		List<Orders> list = super.getListByPage(t1, t2, param, firstResult, maxResults);
		//补上人员的名称
		for(Orders o : list){
			//根据员工编号，从缓存中取出名称
			o.setCreaterName(empDao.getObjectName(o.getCreater()));
			o.setCheckerName(empDao.getObjectName(o.getChecker()));
			o.setStarterName(empDao.getObjectName(o.getStarter()));
			o.setEnderName(empDao.getObjectName(o.getEnder()));
			
			o.setSupplierName(supplierDao.getObjectName(o.getSupplieruuid()));
		}
		return list;
	}
	
	/**
	 * 根据编号获取员工名称
	 * @param uuid 员工编号
	 * @param empNameMap 名称的缓存
	 * @return
	 */
	/*private String getName(Long uuid, Map<Long, String> nameMap){
		if(null == uuid){
			return null;
		}
		//根据员工编号，从缓存中取出名称
		String name = nameMap.get(uuid);
		//如果缓存中不存在名称
		if(null == nameMap.get(uuid)){
			//查询出对应的名称
			name = empDao.get(uuid).getName();
			//放入缓存中
			nameMap.put(uuid, name);
		}
		return name;
	}*/
	
	/*private String getSupplierName(Long uuid, Map<Long, String> supplierNameMap){
		if(null == uuid){
			return null;
		}
		//根据员工编号，从缓存中取出名称
		String name = supplierNameMap.get(uuid);
		//如果缓存中不存在名称
		if(null == supplierNameMap.get(uuid)){
			//查询出对应的名称
			name = supplierDao.get(uuid).getName();
			//放入缓存中
			supplierNameMap.put(uuid, name);
		}
		return name;
	}*/

	public void setEmpDao(IEmpDao empDao) {
		this.empDao = empDao;
	}

	public void setSupplierDao(ISupplierDao supplierDao) {
		this.supplierDao = supplierDao;
	}

	@Override
	public void doCheck(Long uuid, Long empuuid) {
		// 获取订单，进入持久化状态
		Orders orders = ordersDao.get(uuid);
		// 不是未审核的订单，不能审核
		if(!Orders.STATE_CREATE.equals(orders.getState())){
			throw new ErpException("该订单已审核过了");
		}
		// 审核时间
		orders.setChecktime(new Date());
		// 审核人
		orders.setChecker(empuuid);
		// 订单的状态改为 已审核
		orders.setState(Orders.STATE_CHECK);
		// 更新订单
		// ordersDao.update(orders);
	}
	
	@Override
	public void doStart(Long uuid, Long empuuid) {
		// 获取订单，进入持久化状态
		Orders orders = ordersDao.get(uuid);
		// 不是未确认的订单，不能确认
		if(!Orders.STATE_CHECK.equals(orders.getState())){
			throw new ErpException("该订单已确认过了");
		}
		// 确认时间
		orders.setStarttime(new Date());
		// 确认人
		orders.setStarter(empuuid);
		// 订单的状态改为 已确认
		orders.setState(Orders.STATE_START);
		// 更新订单
		// ordersDao.update(orders);
	}

	@Override
	public void exportById(Long uuid, OutputStream os) throws Exception {
		// 查出订单信息
		Orders orders = ordersDao.get(uuid);
		List<Orderdetail> orderdetails = orders.getOrderdetails();
		// 创建工作簿
		Workbook wb = null;
		try {
			wb = new HSSFWorkbook();//97-03 excel
			String title = "采 购 单";
			if(Orders.TYPE_OUT.equals(orders.getType())){
				title = "销 售 单";
			}
			// 创建工作表
			Sheet sheet = wb.createSheet(title);
			// 创建行
			Row row = sheet.createRow(0);//索引从0开始
			row.setHeight((short)1000);
			
			//创建样式
			// 内容的字体
			Font font_content = wb.createFont();
			font_content.setFontName("宋体");
			font_content.setFontHeightInPoints((short)12);
			// 标题字体
			Font font_title = wb.createFont();
			font_title.setFontName("黑体");
			font_title.setBold(true);//粗体
			font_title.setFontHeightInPoints((short)18);
			// 内容样式
			CellStyle style_content = wb.createCellStyle();
			// 标题样式
			CellStyle style_title = wb.createCellStyle();
			// 日期样式
			CellStyle style_date = wb.createCellStyle();
			
			style_content.setAlignment(CellStyle.ALIGN_CENTER);//水平居中
			style_content.setVerticalAlignment(CellStyle.VERTICAL_CENTER);//垂直居中
			// 复制内容样式，只需要水平与垂直居中
			style_title.cloneStyleFrom(style_content);
			// 设置标题的字体
			style_title.setFont(font_title);;
			
			// 设置内容样式的字体
			style_content.setFont(font_content);
			style_content.setBorderTop(CellStyle.BORDER_THIN);//上边框
			style_content.setBorderBottom(CellStyle.BORDER_THIN);//下边框
			style_content.setBorderLeft(CellStyle.BORDER_THIN);//左边框
			style_content.setBorderRight(CellStyle.BORDER_THIN);//右边框
			
			style_date.cloneStyleFrom(style_content);
			
			// 日期格式化器
			DataFormat dateFormat = wb.createDataFormat();
			style_date.setDataFormat(dateFormat.getFormat("yyyy-MM-dd HH:mm"));
			
			int rowCnt = 9 + orderdetails.size();
			// 创建行与列
			for(int i = 2; i <= rowCnt; i++){
				row = sheet.createRow(i);
				for(int j = 0; j < 4; j++){
					row.createCell(j).setCellStyle(style_content);;
				}
				row.setHeight((short)500);//行高
			}
			// 设置日期格式
			sheet.getRow(3).getCell(1).setCellStyle(style_date);
			sheet.getRow(4).getCell(1).setCellStyle(style_date);
			sheet.getRow(5).getCell(1).setCellStyle(style_date);
			sheet.getRow(6).getCell(1).setCellStyle(style_date);
			
			//合并单元格
			sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));//标题
			sheet.addMergedRegion(new CellRangeAddress(2,2,1,3));//供应商名称
			sheet.addMergedRegion(new CellRangeAddress(7,7,0,3));//明细
			
			//设值
			Cell cell = sheet.getRow(0).createCell(0);// 标题单元格
			cell.setCellStyle(style_title);
			cell.setCellValue(title);
			sheet.getRow(2).getCell(0).setCellValue("供应商");
			sheet.getRow(2).getCell(1).setCellValue(supplierDao.get(orders.getSupplieruuid()).getName());
			sheet.getRow(3).getCell(0).setCellValue("下单日期");
			sheet.getRow(3).getCell(1).setCellValue(orders.getCreatetime());
			sheet.getRow(4).getCell(0).setCellValue("审核日期");
			sheet.getRow(5).getCell(0).setCellValue("采购日期");
			sheet.getRow(6).getCell(0).setCellValue("入库日期");
			setDate(sheet.getRow(4).getCell(1),orders.getChecktime());
			setDate(sheet.getRow(5).getCell(1),orders.getStarttime());
			setDate(sheet.getRow(6).getCell(1),orders.getEndtime());
			
			sheet.getRow(3).getCell(2).setCellValue("经办人");
			sheet.getRow(4).getCell(2).setCellValue("经办人");
			sheet.getRow(5).getCell(2).setCellValue("经办人");
			sheet.getRow(6).getCell(2).setCellValue("经办人");
			sheet.getRow(3).getCell(3).setCellValue(empDao.getObjectName(orders.getCreater()));
			sheet.getRow(4).getCell(3).setCellValue(empDao.getObjectName(orders.getChecker()));
			sheet.getRow(5).getCell(3).setCellValue(empDao.getObjectName(orders.getStarter()));
			sheet.getRow(6).getCell(3).setCellValue(empDao.getObjectName(orders.getEnder()));
			
			sheet.getRow(7).getCell(0).setCellValue("明细");
			sheet.getRow(8).getCell(0).setCellValue("商品名称");
			sheet.getRow(8).getCell(1).setCellValue("数量");
			sheet.getRow(8).getCell(2).setCellValue("价格");
			sheet.getRow(8).getCell(3).setCellValue("金额");
			
			//设置列宽
			for(int i = 0; i < 4; i++){
				sheet.setColumnWidth(i, 5000);
			}
			
			//设置明细的值
			int i = 9;
			for(Orderdetail od : orderdetails){
				row = sheet.getRow(i);
				row.getCell(0).setCellValue(od.getGoodsname());
				row.getCell(1).setCellValue(od.getNum());
				row.getCell(2).setCellValue(od.getPrice());
				row.getCell(3).setCellValue(od.getMoney());
				i++;
			}
			
			//合计
			sheet.getRow(i).getCell(0).setCellValue("合计");
			sheet.getRow(i).getCell(3).setCellValue(orders.getTotalmoney());
			
			// 保存输出流中
			wb.write(os);
		} finally {
			if(null != wb){
				// 关闭工作簿
				try {
					wb.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setDate(Cell cell, Date date){
		if(null != date){
			cell.setCellValue(date);
		}
	}

	@Override
	public void ex(Long uuid, OutputStream os) throws Exception {
		// 查出订单信息
		Orders orders = ordersDao.get(uuid);
		// 创建工作簿
		Workbook wb = new HSSFWorkbook(new ClassPathResource("excelTemplate/export_orders.xls").getInputStream());//97-03 excel
		orders.setCreaterName(empDao.getObjectName(orders.getCreater()));
		orders.setCheckerName(empDao.getObjectName(orders.getChecker()));
		orders.setStarterName(empDao.getObjectName(orders.getStarter()));
		orders.setEnderName(empDao.getObjectName(orders.getEnder()));
		
		//数据模型
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("o", orders);
		//填充器
		XLSTransformer xls = new XLSTransformer();
		//数据模型中的数据填充到excel文件中
		xls.transformWorkbook(wb, model);
		wb.write(os);
		wb.close();
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(new ClassPathResource("excelTemplate/export_orders.xls").getInputStream().toString());
	}
	
}
