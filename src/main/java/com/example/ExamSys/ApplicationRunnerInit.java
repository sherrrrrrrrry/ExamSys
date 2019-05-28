package com.example.ExamSys;


import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ExamSys.dao.AuthorityRepository;
import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.Authority;
import com.example.ExamSys.domain.User;

@Component
public class ApplicationRunnerInit implements ApplicationRunner{
	
	private final Logger log = LoggerFactory.getLogger(ApplicationRunnerInit.class);

	@Autowired
	AuthorityRepository authorityRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// TODO Auto-generated method stub
		try {
			Authority authority1 = new Authority("ROLE_ADMIN");
			Authority authority2 = new Authority("ROLE_STUDENT");
			Authority authority3 = new Authority("ROLE_TEACHER");
			Authority authority4 = new Authority("ROLE_ANONYMOUS");
			authorityRepository.save(authority1);
			authorityRepository.save(authority2);
			authorityRepository.save(authority3);
			authorityRepository.save(authority4);
	
			log.info("Authority save ok");
		} catch(Exception e) {

		}
		
		try {
			User user1 = new User();
			Set<Authority> au1 = new HashSet<Authority>();
			Authority a1 = new Authority();
			a1.setName("ROLE_ADMIN");
			au1.add(a1);
			user1.setAuthorities(au1);
			user1.setLogin("admin");
			BCryptPasswordEncoder encoder1 = new BCryptPasswordEncoder();
			String encodePassword1 = encoder1.encode("e,.1x,.2a,.3m,.4");
			user1.setPassword(encodePassword1);
			user1.setEnabled(true);
			userRepository.save(user1);
			
			log.info("Admin save ok");
		} catch(Exception e) {
			
		}
	}

}