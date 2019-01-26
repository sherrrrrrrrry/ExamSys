package com.example.ExamSys.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.User;

@Service
@Transactional
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User createUser(String login, String password, String phoneNumber) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setPhoneNumber(phoneNumber);
		return user;
	}
}
