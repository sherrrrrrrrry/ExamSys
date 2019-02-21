package com.example.ExamSys;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.ExamSys.dao.AuthorityRepository;
import com.example.ExamSys.dao.QuestionBankRepository;
import com.example.ExamSys.dao.QuestionChoiceRepository;
import com.example.ExamSys.dao.UserRepository;
import com.example.ExamSys.domain.Authority;
import com.example.ExamSys.domain.Choice;
import com.example.ExamSys.domain.QuestionChoice;
import com.example.ExamSys.domain.User;

@Component
public class ApplicationRunnerInit implements ApplicationRunner{

	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private QuestionChoiceRepository questionChoiceRepository;
	
	@Autowired
	private QuestionBankRepository questionBankRepository;
	
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
		
		try {
			/*
			 * 选项保存
			 */
			QuestionChoice qc1 = new QuestionChoice();
			qc1.setContent("123");
			qc1.setAnswer("123");
			qc1.setType("123");
			
			Choice c1 = new Choice();
			c1.setContent("123");
			c1.setImageUrl("123");

			qc1.addChoice(c1);
			questionChoiceRepository.save(qc1);
			System.out.println("Choice save ok");
		} catch(Exception e) {
			
		}
		try {
//			Student s1 = new Student();
//			User u1 = userRepository.findOneById(1L).get();
//			s1.setUser(u1);
//			s1.setGender(Gender.BOY);
//			s1.setName("小猪佩奇");
//			studetRepository.save(s1);
//			Student s2 = studentRepository.findOneById(3L);
//			System.out.println(s2.getGender().getAlias());
		} catch(Exception e) {
			e.printStackTrace();;
		}
		try {
			/*
			 * 成绩保存
			 */
//			Student s1 = studentRepository.findOneById(3L);
//			QuestionBank qb1 = questionBankRepository.findOneById(1L);
//			Transcript ts = new Transcript();
//			ts.setStudent(s1);
//			ts.setQuestionBank(qb1);
//			transcriptRepository.save(ts);
//			Transcript transcript = transcriptRepository.findOneById(1L);
//			QuestionBank qb = transcript.getQuestionBank();
//			System.out.println(qb.getId());
//			System.out.println(qb.getName());
			
		} catch(Exception e) {
			e.printStackTrace();;
		}
		
	}
}
