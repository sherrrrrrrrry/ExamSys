package com.example.ExamSys.controller;

import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.User;
import com.example.ExamSys.service.UserService;

@Controller
public class AccountController {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	/*
	 * 注册
	 */
	@RequestMapping("/register")
	public ResponseEntity registerAccount(@Valid @RequestBody User user) {
		
		HttpHeaders textPlainHeaders = new HttpHeaders();
		textPlainHeaders.setContentType(MediaType.TEXT_PLAIN);
		
		return  userRepository.findOneByLogin(user.getLogin())
				.map(u -> new ResponseEntity<>("login already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
				.orElseGet(() -> userRepository.findOneByPhoneNumber(user.getPhoneNumber())
						.map(u -> new ResponseEntity<>("phone number already in use", textPlainHeaders, HttpStatus.BAD_REQUEST))
						.orElseGet(() -> {
							User us = userService
									.createUser(user.getLogin(), 
											user.getPassword() == null ? String.valueOf(new Random().nextInt(1000000)) : user.getPassword(), 
											user.getPhoneNumber());
							return new ResponseEntity<>(HttpStatus.CREATED);
						})
				);
	}
}
