package com.example.ExamSys.dao;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.Transcript;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long>{
	
	@EntityGraph(attributePaths = {"questionBank"})
	Transcript findOneById(Long id);

	@Query("select t from Transcript t where t.questionBank = (select q from QuestionBank q where q.name = ?1) and t.student =(select s from Student s where s.name = ?2) and t.type = ?3")
	Optional<Transcript> findOne(String bankname, String stuname, String type);

	@Query("select t.questionBank from Transcript t where t.student.user.login = ?1")
	Set<QuestionBank> findQuestionBank(String username);
	
	@Query("select t from Transcript t where t.questionBank.id = ?1 and t.student.id = ?2")
	List<Transcript> findAllByQuestionBankIdAndStudentId(Long questionBankId, Long StudentId);
	
	@Query("select t from Transcript t where t.questionBank.id = ?1 and t.student = (select s from Student s where s.user.login=?2)")
	List<Transcript> findAllByQuestionBankIdAndStudentLogin(Long questionBankId, String login);
	
	@Modifying
	@Transactional
	@Query("delete from Transcript t where t.student=(select s from Student s where s.user.id=(select u.id from User u where u.login in (:logins)))")
	void deleteByLogins(@Param("logins") List<String> logins);
}
