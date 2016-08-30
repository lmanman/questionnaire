package com.visionet.letsdesk.app.common.modules.email;

import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;

/**
 * 纯文本邮件服务类.
 * 
 * @author xt
 */
public class SimpleMailService {
	private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);

	private JavaMailSender mailSender;
	private String textTemplate;

	/**
	 * 发送纯文本的用户修改通知邮件.
	 */
	public void sendNotificationMail(String userName) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("springside3.demo@gmail.com");
		msg.setTo("springside3.demo@gmail.com");
		msg.setSubject(MessageSourceHelper.GetMessages("app.common.utilities.email.SimpleMailService.modify.notice"));

		// 将用户名与当期日期格式化到邮件内容的字符串模板
		String content = String.format(textTemplate, userName, new Date());
		msg.setText(content);

		try {
			mailSender.send(msg);
			if (logger.isInfoEnabled()) {
				logger.info("mail text sent to{}", StringUtils.join(msg.getTo(), ","));
			}
		} catch (Exception e) {
			logger.error("send mail error!", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 邮件内容的字符串模板.
	 */
	public void setTextTemplate(String textTemplate) {
		this.textTemplate = textTemplate;
	}
}
