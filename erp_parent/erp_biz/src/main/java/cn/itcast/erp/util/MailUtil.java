package cn.itcast.erp.util;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailUtil {

	private JavaMailSender sender;
	
	private String from;

	public void setSender(JavaMailSender sender) {
		this.sender = sender;
	}
	
	public void sendMail(String to, String subject, String text) throws Exception{
		//创建邮件
		MimeMessage mail = sender.createMimeMessage();
		//包装到spring邮件工具类中
		MimeMessageHelper helper = new MimeMessageHelper(mail,"utf-8");
		//收件人
		helper.setTo(to);
		//发件人
		helper.setFrom(from);
		//邮件的标题
		helper.setSubject(subject);
		//邮件 的内容
		helper.setText(text,true);
		//发送邮件
		sender.send(mail);
	}

	public void setFrom(String from) {
		this.from = from;
	}
}
