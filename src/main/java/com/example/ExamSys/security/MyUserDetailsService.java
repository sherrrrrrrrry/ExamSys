package com.example.ExamSys.security;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.User;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	private final Logger log = LoggerFactory.getLogger(MyUserDetailsService.class);
	
	private UserRepository userRepository;
	
	public MyUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String login){
		// TODO Auto-generated method stub
		log.debug("Authenticating {}", login);
		User user = userRepository.findByLogin(login);
		if(user == null) {
			throw new UsernameNotFoundException("user + " + login + "not found.");
		}
//		List<SimpleGrantedAuthority> authorities = (List<SimpleGrantedAuthority>) user.getAuthorities();
////		List<GrantedAuthority> authorities = 
////				authorityList.stream().map(e -> new SimpleGrantedAuthority(e.getName())).collect(Collectors.toList());
//		UserDetails userDetails = new org.springframework.security.core.userdetails.User(
//                user.getLogin(),user.getPassword(),authorities);
//		System.out.println("login:" + user.getLogin() + "  password:" + user.getPassword());
//		for(GrantedAuthority aut: user.getAuthorities())
//			System.out.println(aut.getAuthority() + "1111111111111111111");
		
		return user;
	}
}
