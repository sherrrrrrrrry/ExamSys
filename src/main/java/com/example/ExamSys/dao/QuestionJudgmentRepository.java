package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionJudgment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionJudgmentRepository extends JpaRepository<QuestionJudgment,Integer> {
}
