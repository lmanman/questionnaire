package com.visionet.letsdesk.app.common.mail;

import com.visionet.letsdesk.app.common.thread.RegistThread;
import com.visionet.letsdesk.app.common.thread.TheadConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;


public class MailSender extends MailService{
    private static Log log = LogFactory.getLog(MailSender.class);

	protected int port = 25;
	protected String protocol = "smtp";
	private static Logger logger = Logger.getLogger(MailSender.class);
	
	public MailSender(Properties prop){
		super(prop);
	}
	
	public class SendCallBack{
		public void callBack(){};
	}
	
	public  void sendAsyn(MailBean mailBean,SendCallBack sendCallBack){
		//放入线程池
		new RegistThread(new MailSenderThread(mailBean,this,sendCallBack), "run", null);
//		Thread thread = new Thread(new MailSenderThread(mailBean,this,sendCallBack));
//		thread.start();
	}
	
	public void send(MailBean mailBean) throws MessagingException{
		Session session = getSession();
		
		MimeMessage msg;
		try {
			msg = _convert(mailBean, session);
			
			if(isDebug){
				debug(mailBean,logger);
			}
			
			if(isDebug){
				oldTimestamp = System.currentTimeMillis();
			}
			
			_send(session,msg);
			
			if(isDebug){
				newTimestamp = System.currentTimeMillis();
				logger.debug("duration:" + (newTimestamp - oldTimestamp) + "ms");
			}
		} catch (IOException e) {
			log.error(e.toString(),e);
			logger.debug("send mail error!", e);
		}
		
	}
	
	private  void _send(Session session,MimeMessage msg) throws MessagingException{
		msg.saveChanges();
		Transport transport = session.getTransport(protocol);
		transport.connect(host,port,userName,userPwd);
		transport.sendMessage(msg,msg.getAllRecipients());
		transport.close();
	}
	
	@Override
	protected Session getSession(){
		Properties prop = new Properties();
		Session session = Session.getInstance(prop);
		session.setDebug(false);
		return session;
	}
	
	private  MimeMessage _convert(MailBean mailBean,Session session) throws MessagingException, IOException{
		MimeMessage msg = new MimeMessage(session);
		msg.setSubject(mailBean.getSubject());
		
		msg.setFrom(new InternetAddress(mailBean.getSender()));
		msg.setSender(new InternetAddress(mailBean.getSender()));
		
		if(null != mailBean.getSenderDate()){
			msg.setSentDate(mailBean.getSenderDate());
		}else{
			msg.setSentDate(new Date());
		}
		
		//处理邮件头信息
		if(null != mailBean.getHeaders()){
            for (Map.Entry<String,String> entry : mailBean.getHeaders().entrySet()) {
                msg.setHeader(entry.getKey(), entry.getValue());
            }
		}
		
		//处理接收人
		if(null != mailBean.getToAddresses()){
			InternetAddress[] to = new InternetAddress[mailBean.getToAddresses().size()];
			
			for (int i = 0; i < to.length; i++) {
				to[i] = new InternetAddress(mailBean.getToAddresses().get(i));
			}
			msg.setRecipients(RecipientType.TO, to);
		}
		//处理暗送人
		if(null != mailBean.getBccAddresses()){
			InternetAddress[] bcc = new InternetAddress[mailBean.getBccAddresses().size()];
			
			for (int i = 0; i < bcc.length; i++) {
				bcc[i] = new InternetAddress(mailBean.getBccAddresses().get(i));
			}
			msg.setRecipients(RecipientType.BCC	, bcc);
		}
		//处理抄送人
		if(null != mailBean.getCcAddresses()){
			InternetAddress[] cc = new InternetAddress[mailBean.getCcAddresses().size()];
			
			for (int i = 0; i < cc.length; i++) {
				cc[i] = new InternetAddress(mailBean.getCcAddresses().get(i));
			}
			msg.setRecipients(RecipientType.CC, cc);
		}
		//处理回复邮箱
		if(null != mailBean.getReplyToAddresses()){
			InternetAddress[] replyTo = new InternetAddress[mailBean.getReplyToAddresses().size()];
			
			for (int i = 0; i < replyTo.length; i++) {
				replyTo[i] = new InternetAddress(mailBean.getReplyToAddresses().get(i));
			}
			msg.setReplyTo(replyTo);
		}
		
		if(mailBean.isReceipt()){
			msg.addHeader("Disposition-Notification-To", "1");
		}
		
		_dealMsgContent(mailBean,msg);
		
		return msg;
	}
	
	private  void _dealMsgContent(MailBean mailBean,MimeMessage msg) throws MessagingException, IOException{
		boolean hasAttachments = mailBean.getAttachments() != null && mailBean.getAttachments().size() > 0;
		boolean hasInnerResources = mailBean.getInnerResources() != null && mailBean.getInnerResources().size() > 0;
		
		
		//处理邮件内容
		MimeMultipart contentMultipart = new MimeMultipart("alternative");
		
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(mailBean.getContent(), "text/html;charset=\"UTF-8\"");
		MimeBodyPart txtPart = new MimeBodyPart();
		txtPart.setText(mailBean.getContent());
		
		contentMultipart.addBodyPart(txtPart);
		contentMultipart.addBodyPart(htmlPart);
		
		
		if(hasAttachments || hasInnerResources){
			MimeMultipart rootMultipart = new MimeMultipart();
			rootMultipart.setSubType(hasAttachments ? "mixed" : "related");
			
			MimeBodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(contentMultipart);
			
			//处理附件
			if(hasAttachments){
				for (Map.Entry<String,File> entry : mailBean.getAttachments().entrySet()) {
					File attachment = entry.getValue();
					
					MimeBodyPart filePart = new MimeBodyPart();
					filePart.setDisposition(Part.ATTACHMENT);
					DataSource source = new FileDataSource(attachment);
					filePart.setDataHandler(new DataHandler(source));
					String fileName = MimeUtility.encodeText(entry.getKey(), "utf-8", null);
					filePart.setFileName(fileName);
					
					rootMultipart.addBodyPart(filePart);
				}
			}
			
			//处理内嵌资源
			if(hasInnerResources){
				MimeMultipart innerMultipart = new MimeMultipart("related");
				innerMultipart.addBodyPart(contentPart);
				
				for (Map.Entry<String,File> entry : mailBean.getInnerResources().entrySet()) {
					File innerFile = entry.getValue();
					
					MimeBodyPart innerFilePart = new MimeBodyPart();
					DataSource source = new FileDataSource(innerFile);
					innerFilePart.setDataHandler(new DataHandler(source));
					innerFilePart.setDisposition(Part.INLINE);
					String fileName = MimeUtility.encodeText(entry.getKey(), "utf-8", null);
					innerFilePart.setFileName(fileName);
					innerFilePart.setContentID(entry.getKey());
					
					innerMultipart.addBodyPart(innerFilePart);
				}
				
				if(hasAttachments){
					MimeBodyPart innerPart = new MimeBodyPart();
					innerPart.setContent(innerMultipart);
					rootMultipart.addBodyPart(innerPart);
				}else{
					rootMultipart = innerMultipart;
				}
			}else{
				rootMultipart.addBodyPart(contentPart);
			}
			
			msg.setContent(rootMultipart);
		}else{
			msg.setContent(contentMultipart);
		}
	}
	
	private class MailSenderThread{
		
		private MailBean mailBean;
		private MailSender mailSender;
		private SendCallBack sendCallBack;
		
		private Logger logger = Logger.getLogger(MailSenderThread.class);
		
		public MailSenderThread(MailBean mailBean,MailSender mailSender,SendCallBack sendCallBack){
			this.mailBean = mailBean;
			this.mailSender = mailSender;
			this.sendCallBack = sendCallBack;
		}
		
		
		public void run() {
			try {
				mailSender.send(mailBean);
				if(sendCallBack != null){
					sendCallBack.callBack();
				}
			} catch (MessagingException e) {
				logger.error("Thread send mail error!", e);
			}
		}
	}

	
}
