package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface QuestionShowRepository extends JpaRepository<QuestionShow,Integer> {
    @Modifying
    @Query("delete from QuestionShow q where q.id=?1")
    void deleteById(Long id);
}
