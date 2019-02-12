package com.example.ExamSys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ExamSys.domain.QuestionChoice;

@Repository
public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, String>{

}
