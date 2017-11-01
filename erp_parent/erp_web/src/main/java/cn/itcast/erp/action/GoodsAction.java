package cn.itcast.erp.action;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.itcast.erp.biz.IGoodsBiz;
import cn.itcast.erp.entity.Goods;
import cn.itcast.erp.util.WebUtil;

/**
 * 商品Action 
 * @author Administrator
 *
 */
public class GoodsAction extends BaseAction<Goods> {

	private IGoodsBiz goodsBiz;
	private File file;//属性的名称必须跟form表单type=file这个标签里的 name值一致
	private String fileFileName;//上传的文件名称
	private String fileContentType;//文件类型

	public void setFile(File file) {
		this.file = file;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public void setGoodsBiz(IGoodsBiz goodsBiz) {
		this.goodsBiz = goodsBiz;
		super.setBaseBiz(this.goodsBiz);
	}
	/**
	 * 导出商品
	 */
	public void export(){
		Goods t1 = getT1();
		HttpServletResponse res = ServletActionContext.getResponse();
		String filename="商品.xls";
		try{
			res.setHeader("Content-Disposition","attachement;filename=" + new String(filename.getBytes(),"ISO-8859-1"));
			goodsBiz.export(res.getOutputStream(), t1);
		}catch(Exception e){
		
			e.printStackTrace();
		}
	}
	/**
	 * 导入商品
	 */
	public void doImport(){
		if(!"application/vnd.ms-excel".equals(fileContentType)){
			if(!fileFileName.endsWith(".xls")){
				WebUtil.ajaxReturn(false, "文件格式不正确");
				return;
			}
		}
		try {
			goodsBiz.doImport(new FileInputStream(file));
			WebUtil.ajaxReturn(true, "导入成功");
		} catch (Exception e) {
			e.printStackTrace();
			WebUtil.ajaxReturn(true, "导入失败");
		}
	}

}
