package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.Transcript;
@Repository
public interface TranscriptRepository extends JpaRepository<Transcript, Long>{
	
	@EntityGraph(attributePaths = {"questionBank"})
	Transcript findOneById(Long id);
}
