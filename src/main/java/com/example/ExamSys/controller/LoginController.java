package com.example.ExamSys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * 暂时包含测试方法
 */
@Controller
public class LoginController {
	
	private final Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value={"/login.go"})
	public String login() {
		return "login.html";
	}
	
	@RequestMapping(value="/admin/qw", method = RequestMethod.GET)
	@ResponseBody
	public String ok() {
		log.error("wqeqwewqe");
		return "qw";
	}
}
