package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionBank;
import com.example.ExamSys.domain.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.Transcript;

import java.util.Optional;

@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long>{
	
	@EntityGraph(attributePaths = {"questionBank"})
	Transcript findOneById(Long id);

	@Query("select t from Transcript t where t.questionBank = (select q from QuestionBank q where q.name = ?1) and t.student =(select s from Student s where s.name = ?2) and t.type = ?3")
	Optional<Transcript> findOne(String bankname, String stuname, String type);
}
