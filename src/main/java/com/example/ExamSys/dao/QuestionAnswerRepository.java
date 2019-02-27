package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer,Long> {

    @Query("select q from QuestionAnswer q where q.questionBank.id=?1 and number = ?2")
    QuestionAnswer findByIDandNumber(Long id, int number);
}
