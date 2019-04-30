package com.example.ExamSys.service;

import java.io.File;
import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {
	
	@Value("${spring.mail.username}")
	private String from;
	
	@Autowired
	private JavaMailSender mailSender;

	private final Logger log = LoggerFactory.getLogger(MailService.class);
	
	/**
	 * 发送简单的内容邮件
	 * @param to：邮箱地址
	 * @param title：邮件标题
	 * @param content：文字内容
	 */
	public void sendSimpleMail(String to, String title, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(from);
		message.setTo(to);
		message.setSubject(title);
		message.setText(content);
		mailSender.send(message);
		log.info("{} 邮件发送成功", to);
	}
	
	/**
	 * 发送带附件的邮件
	 * @param to：邮箱地址
	 * @param title：邮件标题
	 * @param content：文字内容
	 * @param fileList：文件列表
	 */
	public void sendAttachmentsMail(String to, String title, String content, List<File> fileList) {
		MimeMessage message = mailSender.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(title);
			helper.setText(content);
			String fileName = null;
			for(File file : fileList) {
				fileName = MimeUtility.encodeText(file.getName(), "GB2312", "B");
				helper.addAttachment(fileName, file);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		mailSender.send(message);
		log.info("{} 邮件发送成功", to);
	}
}
