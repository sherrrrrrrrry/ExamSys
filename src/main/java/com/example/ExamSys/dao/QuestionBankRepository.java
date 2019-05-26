package com.example.ExamSys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import com.example.ExamSys.domain.QuestionBank;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionBankRepository extends JpaRepository<QuestionBank, Long> {

    @Query("select q from QuestionBank q where q.name = ?1")
    QuestionBank findByName(String name);

    @Query("select q.level from QuestionBank q where q.name = ?1")
    int getLevelByname(String name);

    @Query("select name from QuestionBank")
    List<String> getBankNames();

    @Query("select q from QuestionBank q")
    List<QuestionBank> getQuestionBanklist();

    QuestionBank findOneById(Long id);

    @Query("select q.name from QuestionBank q where q.level = ?1")
    List<String> getBankNamesbyLevel(int level);

    @Query("select q.id from QuestionBank q where q.level = ?1")
    List<Long> getBankIdsbyLevel(int level);

    @Query("select q.name from QuestionBank q where q.id = ?1")
    String getNameByID(Long id);
}
