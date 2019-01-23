package com.example.ExamSys.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

//@Component
public class MyAuthenticationProvider implements AuthenticationProvider{
	
//	@Autowired
	private MyUserDetailsService userService;

//	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// TODO Auto-generated method stub
		String login = authentication.getName();
		String password = (String)authentication.getCredentials();
		UserDetails user = userService.loadUserByUsername(login);
		if(user == null) {
			throw new BadCredentialsException("user + " + login + "not found.");
		}
		
		return null;
	}

//	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
