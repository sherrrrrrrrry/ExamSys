package com.example.ExamSys.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VerifyService {
	
	private final Logger log = LoggerFactory.getLogger(VerifyService.class);
	
	@Autowired
	private MailService mailService;
	
	public String getAndSendVerify(String email) {
		
		String randomNum = String.valueOf(new Random().nextInt(899999) + 100000);
		String message = "您的注册验证码为:" + randomNum;
		mailService.sendSimpleMail(email, "教育", message);
		log.info("{} 验证码发送成功" + email);
		return randomNum;
	}
	
	public String getCurrentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		return sf.format(c.getTime());
	}
	
	public String getFiveMinuteTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 5);
		return sf.format(c.getTime());
	}
}
