package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByLogin(String login);
}
