package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.QuestionChoice;

@Repository
public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Long>{

    @Modifying
    @Query("delete from QuestionChoice q where q.id=?1")
    void deleteById(Long id);

    @Query("select q from QuestionChoice q where q.id = ?1")
    QuestionChoice findByIndex(Long id);
}
