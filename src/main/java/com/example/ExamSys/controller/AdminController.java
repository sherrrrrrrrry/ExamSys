package com.example.ExamSys.controller;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.User;

@RestController
public class AdminController {
	private final Logger log = LoggerFactory.getLogger(AdminController.class);
	
	@Autowired
	UserRepository userRepository;

	@RequestMapping(value = "getUnactivatedList", method = RequestMethod.POST)
	public ResponseEntity<Set<User>> getUnactivatedList(){
		
		Set<User> userSet = userRepository.findAllByEnabledFalse();
		
		return ResponseEntity.ok().body(userSet);
	}
}
