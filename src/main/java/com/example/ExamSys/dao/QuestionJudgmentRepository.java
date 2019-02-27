package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionJudgment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionJudgmentRepository extends JpaRepository<QuestionJudgment,Long> {
    @Modifying
    @Query("delete from QuestionJudgment q where q.id=?1")
    void deleteById(Long id);

    @Query("select q from QuestionJudgment q where q.id = ?1")
    QuestionJudgment findByIndex(Long id);
}
