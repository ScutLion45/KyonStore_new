package com.kyon.tools;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	public static void sendMail(String TO, String emailMsg) throws AddressException, MessagingException{
		//创建Properties对象，设置邮件服务器基本信息
		Properties props=new Properties();
		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    	props.setProperty("mail.smtp.socketFactory.port", "465");
		//设置传输协议SMTP
		props.setProperty("mail.transport.protocol", "SMPT");
		//设置SMTP服务器
		props.setProperty("mail.host", "smtp.qq.com");
		//设置smtp是否需要用户验证
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.ssl.enable","true");
		//启用调试
		props.setProperty("mail.debug", "true");
		//设置链接超时
		props.setProperty("mail.smtp.timeout", "30000");
		//设置端口
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		//创建验证器
		Authenticator auth=new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("354178145@qq.com","vgsiuzbqbddcbifc");
			}
		};
		//实例化邮件会话session
		Session session=Session.getInstance(props, auth);
		//创建message,即内容
		Message message=new MimeMessage(session);
		//发送者
    	message.setFrom(new InternetAddress("354178145@qq.com"));
    	//设置发送方式和接收者，接收者在调用sendMail时通过参数传递
    	message.setRecipient(RecipientType.TO, new InternetAddress(TO));
    	//邮件主题
    	message.setSubject("KyonStore订单通知");
    	message.setContent(emailMsg, "text/html;charset=utf-8");
    	//发送
    	Transport.send(message);
	}
}
