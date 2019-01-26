package com.example.ExamSys.controller;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ExamSys.service.MailService;

@Controller
public class MailController {

	private final Logger log = LoggerFactory.getLogger(MailController.class);
	
	@Autowired
	private MailService mailService;
	
	@RequestMapping("getCheckCode")
	public ResponseEntity<String> getCheckCode(String email) {
		if(email == null || email.length()==0) {
			return ResponseEntity.badRequest().header("Email", "get email faild").body(null);
		}
		String checkCode = String.valueOf(new Random().nextInt(899999) + 100000);
		String message = "您的注册验证码为:" + checkCode;
		try {
			mailService.sendSimpleMail(email, "注册验证码", message);
		} catch(Exception e) {
			log.info(email + "邮件发送出错");
		}
		return ResponseEntity.ok().body(checkCode);
	}
}
