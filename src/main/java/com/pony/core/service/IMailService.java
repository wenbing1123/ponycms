package com.pony.core.service;

import java.util.Map;

public interface IMailService {

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
			boolean async); //是否异步发送
	
	public void sendMail(
			String toMail, //接收邮件者
			String subject, //主题
			String templatePath, //内容模板
			Map<String,Object> model, //内容模板数据
			boolean async); //是否异步发送
	
	public void sendMail(
			String toMail, //接收邮件者
			String subject, //主题
			String templatePath, //内容模板
			Map<String,Object> model); //内容模板数据
	
	public void sendMail(
			String toMail, //接收邮件者
			String subject, //主题
			String templatePath); //内容模板
	
	public void sendTestMail(
			String smtpFrom, //邮件
			String smtpHost, //发送者邮件服务器
			Integer smtpPort, //发送者邮件端口
			String smtpUsername, //发送者用户名
			String smtpPassword, //发送者密码
			String toMail); //接收邮件者
	
}
