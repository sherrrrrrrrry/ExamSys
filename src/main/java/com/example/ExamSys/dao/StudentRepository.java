package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.User;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Query("select s.user.id from Student s where s.id = ?1")
	Long findUserIdById(Long id);
	
	@Query("select s.user from Student s where s.id = ?1")
	User findUserById(Long id);
	
	Student findOneById(Long id);

    @Query("select s from Student s where s.user = ?1")
    Student findStuByUser(User user);
	
	@Query("select s.photoUrl from Student s where s.user.login=?1")
	String findPhotoUrlByLogin(String login);
	
	@Modifying
	@Transactional
	@EntityGraph(attributePaths = {"user"})
	@Query("update Student s set s.photoUrl=?1 where s.user = (select u from User u where u.login = ?2)")
	void updatePhotoUrlByLogin(String photoUrl, String login);
	
	@Modifying
	@Transactional
	@Query("update Student s set s.photoUrl=?1 where s.id=?2")
	void updatePhotoUrlById(String photoUrl, Long id);
}
