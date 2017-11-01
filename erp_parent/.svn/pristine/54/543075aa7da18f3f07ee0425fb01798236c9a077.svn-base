package cn.itcast.erp.action;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.biz.ISupplierBiz;
import cn.itcast.erp.entity.Supplier;
import cn.itcast.erp.util.WebUtil;

/**
 * 供应商Action 
 * @author Administrator
 *
 */
public class SupplierAction extends BaseAction<Supplier> {

	private ISupplierBiz supplierBiz;
	private String q;//jeasyui combogrid 设置了mode:remote时发送的参数名称
	private File file;//属性的名称必须跟form表单type=file这个标签里的 name值一致
	private String fileFileName;//上传的文件名称
	private String fileContentType;//文件类型

	public void setSupplierBiz(ISupplierBiz supplierBiz) {
		this.supplierBiz = supplierBiz;
		super.setBaseBiz(this.supplierBiz);
	}
	
	@Override
	public void list() {
		if(null == getT1()){
			//构建查询条件
			setT1(new Supplier());
		}
		getT1().setName(q);
		super.list();
	}
	
	/**
	 * 导出数据
	 */
	public void export(){
		//得到查询条件
		Supplier t1 = getT1();
		String filename = "";
		if(Supplier.TYPE_SUPPLIER.equals(t1.getType())){
			filename = "供应商.xls";
		}
		if(Supplier.TYPE_CUSTOMER.equals(t1.getType())){
			filename = "客户.xls";
		}
		
		HttpServletResponse res = ServletActionContext.getResponse();
		//设置响应头，告诉浏览器，下载文件
		try {
			res.setHeader("Content-Disposition","attachement;filename=" + new String(filename.getBytes(),"ISO-8859-1"));
			supplierBiz.export(res.getOutputStream(), t1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 导入数据
	 */
	public void doImport(){
		if(!"application/vnd.ms-excel".equals(fileContentType)){
			if(!fileFileName.endsWith(".xls")){
				WebUtil.ajaxReturn(false, "文件格式不正确");
				return;
			}
		}
		try {
			supplierBiz.doImport(new FileInputStream(file));
			WebUtil.ajaxReturn(true, "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.ajaxReturn(true, "导入失败");
		}
	}

	public void setQ(String q) {
		this.q = q;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	
	public static void main(String[] args) throws Exception{
		String tt = "中国人";
		System.out.println(new String(tt.getBytes("iso-8859-1")));
	}

}
