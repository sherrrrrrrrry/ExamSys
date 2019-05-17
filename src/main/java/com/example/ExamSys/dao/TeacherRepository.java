package com.example.ExamSys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.Teacher;
import com.example.ExamSys.domain.enumeration.Gender;

public interface TeacherRepository extends JpaRepository<Teacher, Long>{
	
	@EntityGraph(attributePaths = {"user"})
	@Query("select t from Teacher t where t.user.enabled=true")
	List<Teacher> findAllLazy();

	@Query("select s.photoUrl from Teacher s where s.user.login=?1")
	String findPhotoUrlByLogin(String login);
	
	@Query("select s from Teacher s where s.user = (select u from User u where u.login = ?1)")
	Teacher findOneByLogin(String login);
	
	@Modifying
	@Transactional
	@EntityGraph(attributePaths = {"user"})
	@Query("update Teacher s set s.photoUrl=?1 where s.user = (select u from User u where u.login = ?2)")
	void updatePhotoUrlByLogin(String photoUrl, String login);
	
	@Modifying
	@Transactional
	@Query("update Teacher s set s.name=?1, s.gender=?2, s.age=?3, s.school=?4, s.schoolProvince=?5, s.schoolCity=?6, s.schoolRegion=?7, s.trainingName=?8, "
			+ "s.motto=?9 where s.user = (select u from User u where u.login = ?10)")
	void updateInfoByLogin(String name, Gender gender, int age, String school, String schoolProvince, String schoolCity, String schoolRegion
			, String trainingName, String motto, String login);
}
