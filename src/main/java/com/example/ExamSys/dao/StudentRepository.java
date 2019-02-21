package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.User;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query("select s.user.id from Student s where s.id = ?1")
	Long findUserIdById(Long id);
	
	@Query("select s.user from Student s where s.id = ?1")
	User findUserById(Long id);
	
	Student findOneById(Long id);
}
