package com.pony.core.service.impl;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.Assert;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.google.common.collect.Maps;
import com.pony.core.Constants;
import com.pony.core.Setting;
import com.pony.core.SettingUtils;
import com.pony.core.SystemException;
import com.pony.core.service.IMailService;
import com.pony.core.util.SpringUtils;
import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service("mailService")
public class MailServiceImpl implements IMailService {
	
	private static Logger logger = Logger.getLogger(MailServiceImpl.class);

	@Autowired FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired JavaMailSenderImpl mailSender;
	@Autowired TaskExecutor taskExecutor;

	public void sendMail(
			String smtpFrom, //邮件
			String smtpHost, //发送者邮件服务器
			Integer smtpPort, //发送者邮件端口
			String smtpUsername, //发送者用户名
			String smtpPassword, //发送者密码
			String toMail, //接收邮件者
			String subject, //主题
			String templatePath, //内容模板
			Map<String,Object> model, //内容模板数据
			boolean async){ //是否异步发送
	
		Assert.hasText(smtpFrom);
		Assert.hasText(smtpHost);
		Assert.notNull(smtpPort);
		Assert.hasText(smtpUsername);
		Assert.hasText(smtpPassword);
		Assert.hasText(toMail);
		Assert.hasText(subject);
		Assert.hasText(templatePath);
		
		try{
			Setting setting = SettingUtils.get();
			
			Configuration configuration = freeMarkerConfigurer.getConfiguration();
			Template template = configuration.getTemplate(templatePath);
			
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
			
			mailSender.setHost(smtpHost);
			mailSender.setPort(smtpPort);
			mailSender.setUsername(smtpUsername);
			mailSender.setPassword(smtpPassword);
			
			MimeMessage mimeMessage =  mailSender.createMimeMessage();
			
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
			
			helper.setFrom(MimeUtility.encodeWord(setting.getSiteName())+" <"+smtpFrom+">");
			helper.setTo(toMail);
			helper.setSubject(subject);
			helper.setText(content);
			
			if(async){
				taskExecutor.execute(new SendMailTask(mailSender,mimeMessage));
			}else{
				mailSender.send(mimeMessage);
			}
			
		}catch (Exception e) {
			logger.error("MailServiceImpl.sendMail Error:",e);
			throw new SystemException("MailServiceImpl.sendMail Error:",e);
		}
		
	}
	
	private class SendMailTask implements Runnable{
		
		private JavaMailSender mailSender;
		private MimeMessage mimeMessage;
		
		public SendMailTask(JavaMailSender mailSender, MimeMessage mimeMessage){
			this.mailSender = mailSender;
			this.mimeMessage = mimeMessage;
		}
		
		public void run() {
			if(null != mailSender && null != mimeMessage){
				mailSender.send(mimeMessage);
			}
		}
		
	}

	public void sendMail(
			String toMail, 
			String subject, 
			String templatePath,
			Map<String, Object> model,
			boolean async) {
		Setting setting = SettingUtils.get();
		sendMail(setting.getSmtpFromMail(),
				setting.getSmtpHost(),
				Integer.valueOf(setting.getSmtpPort()),
				setting.getSmtpUsername(),
				setting.getSmtpPassword(),
				toMail,
				subject,
				templatePath,
				model,
				async);
	}

	public void sendMail(String toMail, String subject, String templatePath,
			Map<String, Object> model) {
		Setting setting = SettingUtils.get();
		sendMail(setting.getSmtpFromMail(),
				setting.getSmtpHost(),
				Integer.valueOf(setting.getSmtpPort()),
				setting.getSmtpUsername(),
				setting.getSmtpPassword(),
				toMail,
				subject,
				templatePath,
				model,
				true);
	}

	public void sendMail(String toMail, String subject, String templatePath) {
		Setting setting = SettingUtils.get();
		sendMail(setting.getSmtpFromMail(),
				setting.getSmtpHost(),
				Integer.valueOf(setting.getSmtpPort()),
				setting.getSmtpUsername(),
				setting.getSmtpPassword(),
				toMail,
				subject,
				templatePath,
				null,
				true);
	}

	public void sendTestMail(String smtpFrom, String smtpHost,
			Integer smtpPort, String smtpUsername, String smtpPassword,
			String toMail) {
		Setting setting = SettingUtils.get();
		String subject = SpringUtils.getMessage("admin.setting.mail.subject", setting.getSiteName());
		String templatePath = Constants.TPLDIR_SYS_DEFINED_MAIL + "/test.ftl";
		Map<String,Object> model = Maps.newHashMap();
		model.put("setting", setting);
		sendMail(smtpFrom,smtpHost,smtpPort,smtpUsername,smtpPassword,toMail,subject,templatePath,model,false);
	}
}
