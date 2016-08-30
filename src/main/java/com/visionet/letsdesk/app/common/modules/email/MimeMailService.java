package com.visionet.letsdesk.app.common.modules.email;

import com.visionet.letsdesk.app.common.mail.MailBean;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import com.visionet.letsdesk.app.common.modules.mapper.JsonMapper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * MIME邮件服务类.
 * 
 * 演示由Freemarker引擎生成的的html格式邮件, 并带有附件.
 * 
 * @author xt
 */
public class MimeMailService {

	private static final String DEFAULT_ENCODING = "utf-8";
	
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();

	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;

	private Template template;

	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendNotificationMail(MailBean mailBean) {

		try {
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);

			helper.setTo(mailBean.getToAddresses().toArray(new String[0]));
			helper.setFrom(mailBean.getSender());
			helper.setSubject(mailBean.getSubject());

//			String content = generateContent(mailBean.getSenderName(),mailBean.getContentHtml());
			String content = mailBean.getContentHtml();
			System.out.println(content);
			helper.setText(content, true);

			Map<String, File> attachments = mailBean.getAttachments();
            for (Map.Entry<String,File> entry: attachments.entrySet()) {
                helper.addAttachment(entry.getKey(), entry.getValue());
            }
			mailSender.send(msg);
			
			logger.info("mail sent to:"+mapper.toJson(mailBean.getToAddresses()));
		} catch (MessagingException e) {
			logger.error("generate mail error!", e);
		} catch (Exception e) {
			logger.error("send mail error!", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent(String userName,String contentHtml) throws MessagingException {

		try {
			Map context = Collections.singletonMap("userName", userName);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			logger.error("generate mail content fail, FreeMarker not exist!", e);
			throw new MessagingException("FreeMarker not exist!", e);
		} catch (TemplateException e) {
			logger.error("generate mail content fail, FreeMarker fail!", e);
			throw new MessagingException("FreeMarker fail!", e);
		}
	}

	/**
	 * 获取classpath中的附件.
	 */
	private File generateAttachment(String attachmentPath) throws MessagingException {
		try {
			Resource resource = new ClassPathResource(attachmentPath);
			return resource.getFile();
		} catch (IOException e) {
			logger.error("generate mail error,attachment not exist!", e);
			throw new MessagingException("attachment not exist!", e);
		}
	}

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		// 根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", DEFAULT_ENCODING);
	}
	

}
