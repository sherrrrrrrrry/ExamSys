package com.example.ExamSys;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.HtmlEmail;

public class myTest {
	public void test() {
		try {
			HtmlEmail email = new HtmlEmail();
			email.setHostName("smtp.163.com");
			email.setCharset("utf-8");
			email.addTo("554220959@qq.com");
			email.setFrom("13260808093@163.com", "g");
			email.setAuthentication("13260808093@163.com", "mingxi960220");
			email.setSubject("测试");
			email.setMsg("1234");
			email.send();
		} catch(Exception e) {
			System.out.println("buggggggggggggggggggg");
		}
	}
	
	public static void main(String[] args) {
		Map<String, Object> p = new HashMap<String, Object>();
		Object x = p.get("123").toString();
		System.out.println(x);
	}
}
