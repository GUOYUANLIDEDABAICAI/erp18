package cn.itcast.erp.job;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import cn.itcast.erp.biz.IStoredetailBiz;
import cn.itcast.erp.entity.Storealert;
import cn.itcast.erp.util.MailUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class MailJob {

	private MailUtil mailUtil;
	
	private IStoredetailBiz storedetailBiz;
	
	private String to;
	private String subject;
	private String text;
	private Configuration freeMarker;
	
	public void doJob(){
		// 获取预警的列表
		List<Storealert> list = storedetailBiz.getStorealertList();
		if(list.size() > 0){
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String _subject = subject.replace("[time]", sdf.format(Calendar.getInstance().getTime()));
				Template template = freeMarker.getTemplate("email.html");
				Map<String,Object> model = new HashMap<String,Object>();
				model.put("storealertList", list);
				
				String _text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
				//String _text = text.replace("[count]", list.size() + "");
				//_text = _text.replace("蓝云ERP旗舰版3.1系统", "<a href='http://localhost:8080/erp/index.html'>蓝云ERP旗舰版3.1系统</a>");
				mailUtil.sendMail(to, _subject, _text);
				System.out.println("发送成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setMailUtil(MailUtil mailUtil) {
		this.mailUtil = mailUtil;
	}

	public void setStoredetailBiz(IStoredetailBiz storedetailBiz) {
		this.storedetailBiz = storedetailBiz;
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

	public void setFreeMarker(Configuration freeMarker) {
		this.freeMarker = freeMarker;
	}
}
