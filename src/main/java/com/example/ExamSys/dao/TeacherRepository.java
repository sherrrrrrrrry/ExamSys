package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{

	@Query("select s.photoUrl from Student s where s.user.login=?1")
	String findPhotoUrlByLogin(String login);
	
	@Modifying
	@Transactional
	@EntityGraph(attributePaths = {"user"})
	@Query("update Student s set s.photoUrl=?1 where s.user = (select u from User u where u.login = ?2)")
	void updatePhotoUrlByLogin(String photoUrl, String login);
}
