package com.example.ExamSys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * 暂时包含测试方法
 */
@Controller
@RequestMapping("/")
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="/login.go")
	public String login() {
		return "login.html";
	}
	
	@RequestMapping(value="/register.go")
	public String register() {
		return "register.html";
	}
}
