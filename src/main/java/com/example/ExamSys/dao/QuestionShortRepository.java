package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionShortRepository extends JpaRepository<QuestionShort,Integer> {
    @Modifying
    @Query("delete from QuestionShort q where q.id=?1")
    void deleteById(Long id);

    @Query("select q from QuestionShort q where q.id = ?1")
    QuestionShort findByIndex(Long id);
}
