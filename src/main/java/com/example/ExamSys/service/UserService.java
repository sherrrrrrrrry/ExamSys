package com.example.ExamSys.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.User;

import java.util.Optional;

@Service
@Transactional
public class UserService {
	
	private final Logger log = LoggerFactory.getLogger(UserService.class);
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public User createUserEmail(String login, String password, String email, Boolean enabled) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(getHashPassword(password));
		user.setEmail(email);
		user.setEnabled(enabled);
		return user;
	}
	
	public User createUserPhone(String login, String password, String phoneNumber, Boolean enabled) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(getHashPassword(password));
		user.setPhoneNumber(phoneNumber);
		user.setEnabled(enabled);
		return user;
	}
	
	private String getHashPassword(String password) {
		BCryptPasswordEncoder encoder1 = new BCryptPasswordEncoder();
		String hash = encoder1.encode(password);
		return hash;
	}
	public Optional<User> findOneByLogin(String login){
		return userRepository.findOneByLogin(login);
	}
}
