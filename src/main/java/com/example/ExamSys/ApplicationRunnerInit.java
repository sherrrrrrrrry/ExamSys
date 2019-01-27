package com.example.ExamSys;

import java.util.HashSet;
import java.util.Set;

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
		
		User user1 = new User();
		Set<Authority> au1 = new HashSet<Authority>();
		Authority a1 = new Authority();
		a1.setName("ROLE_STUDENT");
		au1.add(a1);
		user1.setAuthorities(au1);
		user1.setLogin("user1");
		BCryptPasswordEncoder encoder1 = new BCryptPasswordEncoder();
		String encodePassword1 = encoder1.encode("123456");
		user1.setPassword(encodePassword1);
		user1.setEmail("554220979@qq.com");
		user1.setEnabled(true);
		userRepository.save(user1);
		
		User user2 = new User();
		Set<Authority> au2 = new HashSet<Authority>();
		Authority a2 = new Authority();
		a2.setName("ROLE_ADMIN");
		au2.add(a2);
		user2.setAuthorities(au2);
		user2.setLogin("admin");
		BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();
		String encodePassword2 = encoder2.encode("admin");
		user2.setPassword(encodePassword2);
		user2.setEmail("554220969@qq.com");;
		user2.setEnabled(true);
		userRepository.save(user2);
		System.out.println("Authority save ok");
		} catch(Exception e) {
		}
	}
	
}
