package com.example.ExamSys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
	
	@RequestMapping(value={"/login.go"})
	public String login() {
		return "login.html";
	}
	
	@RequestMapping(value="/admin/qw", method = RequestMethod.GET)
	@ResponseBody
	public String ok() {
		return "qw";
	}
}
