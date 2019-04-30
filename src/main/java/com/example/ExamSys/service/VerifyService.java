package com.example.ExamSys.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
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
	
	@Autowired
	private PhoneService phoneService;
	
	/**
	 * 获取并发送邮箱验证码
	 * 参数: email
	 * 返回值: 6位随机数
	 */
	public String getAndSendVerify(String email) {
		
		String randomNum = String.valueOf(new Random().nextInt(899999) + 100000);
		String message = "您的注册验证码为:" + randomNum;
		mailService.sendSimpleMail(email, "教育", message);
		log.info("{} 验证码发送成功" + email);
		return randomNum;
	}
	
	
	public HashMap<Boolean, String> getAndSendVerifyMessage(String phoneNumber) {
		
		String randomNum = String.valueOf(new Random().nextInt(899999) + 100000);
		
		HashMap<Boolean, String> data = phoneService.sendSimpleMessage(phoneNumber, randomNum);
		
		if(data.get(true) != null) {
			HashMap<Boolean, String> success = new HashMap<Boolean, String>();
			success.put(true, randomNum);
			return success;
		} else {
			return data;
		}
	}
	
	/**
	 * 获取现在的时间
	 * @return
	 */
	public String getCurrentTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		return sf.format(c.getTime());
	}
	
	/**
	 * 获取五分钟后的时间
	 * @return
	 */
	public String getFiveMinuteTime() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MINUTE, 5);
		return sf.format(c.getTime());
	}
}

