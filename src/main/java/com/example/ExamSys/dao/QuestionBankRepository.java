package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, Integer> {

}
