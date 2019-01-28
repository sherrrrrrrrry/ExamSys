package com.example.ExamSys.controller;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.User;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "/getUnactivatedList", method = RequestMethod.POST)
	public ResponseEntity<Set<User>> getUnactivatedList(){
		
		log.info("正在获取未激活账户列表");
		Set<User> userSet = userRepository.findAllByEnabledFalse();
		log.info("获取未激活账户列表成功");
		
		return ResponseEntity.ok().body(userSet);
	}
	
	@DeleteMapping(value = "/activate")
	public ResponseEntity<Boolean> activate(@RequestBody List<Long> ids){
		
		log.info("正在激活所选账户");
		try {
			userRepository.setEnabledTrueByIdIn(ids);
		} catch(Exception e) {
			log.info("账户激活失败");
			return ResponseEntity.badRequest().body(false);
		}
		log.info("账户激活完毕");
		
		return ResponseEntity.ok().body(true);
	}
}
