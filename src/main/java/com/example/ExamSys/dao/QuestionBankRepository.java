package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionBank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.ListIterator;

public interface QuestionBankRepository extends JpaRepository<QuestionBank, Integer> {

    @Query("select q from QuestionBank q where q.name = ?1")
    QuestionBank findByName(String name);

    @Query("select name from QuestionBank")
    List<String> getBankNames();
}
