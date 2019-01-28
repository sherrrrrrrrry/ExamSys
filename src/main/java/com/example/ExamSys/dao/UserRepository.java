package com.example.ExamSys.dao;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findOneByLogin(String login);
	
	Optional<User> findOneByEmail(String email);
	
	Set<User> findAllByEnabledFalse();
}
