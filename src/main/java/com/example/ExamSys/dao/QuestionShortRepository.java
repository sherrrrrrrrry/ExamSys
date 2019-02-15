package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionShort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionShortRepository extends JpaRepository<QuestionShort,Integer> {
}
