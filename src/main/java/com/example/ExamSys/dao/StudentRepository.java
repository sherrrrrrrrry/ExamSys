package com.example.ExamSys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.User;
import com.example.ExamSys.domain.enumeration.Gender;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>{
	
	@Modifying
	@Transactional
	@Query("delete from Student s where s.user.id=(select u.id from User u where u.login in (:logins))")
	void deleteByLogins(@Param("logins") List<String> logins);
	
	@Modifying
	@Transactional
	@EntityGraph(attributePaths = {"user"})
	@Query("delete from Student s where s.user.login = ?1")
	void deleteByLogin(String login);
	
	@EntityGraph(attributePaths = {"user"})
	@Query("select s from Student s")
	List<Student> findAllLazy();
	
	@Query("select s.user.id from Student s where s.id = ?1")
	Long findUserIdById(Long id);
	
	@Query("select s.user.id from Student s where s.user = (select u from User u where u.login = ?1)")
	Long findUserIdByLogin(String login);

	@Query("select s.user from Student s where s.user = (select u from User u where u.login = ?1)")
	User findUserByLogin(String login);
	
	@Query("select s.user from Student s where s.id = ?1")
	User findUserById(Long id);
	
	Student findOneById(Long id);

    @Query("select s from Student s where s.user = ?1")
    Student findStuByUser(User user);
	
	@Query("select s.photoUrl from Student s where s.user.login=?1")
	String findPhotoUrlByLogin(String login);
	
	@Query("select s from Student s where s.user = (select u from User u where u.login = ?1)")
	Student findOneByLogin(String login);

	@Query("select s.level from Student s where s.user = (select u from User u where u.login = ?1)")
	int getLevel(String login);

	@Query("select s.id from Student s where s.user = (select u from User u where u.login = ?1)")
	Long getIDbyUsername(String username);

	@Modifying
	@Transactional
	@EntityGraph(attributePaths = {"user"})
	@Query("update Student s set s.photoUrl=?1 where s.user = (select u from User u where u.login = ?2)")
	void updatePhotoUrlByLogin(String photoUrl, String login);
	
	@Modifying
	@Transactional
	@Query("update Student s set s.photoUrl=?1 where s.id=?2")
	void updatePhotoUrlById(String photoUrl, Long id);
	
	@Modifying
	@Transactional
	@Query("update Student s set s.name=?1, s.gender=?2, s.age=?3, s.school=?4, s.schoolProvince=?5, s.schoolCity=?6, s.schoolRegion=?7, s.trainingName=?8, "
			+ "s.motto=?9 where s.user = (select u from User u where u.login = ?10)")
	void updateInfoByLogin(String name, Gender gender, int age, String school, String schoolProvince, String schoolCity, String schoolRegion
			, String trainingName, String motto, String login);
}
