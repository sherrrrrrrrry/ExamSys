package com.example.ExamSys.dao;

import com.example.ExamSys.domain.QuestionShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionShowRepository extends JpaRepository<QuestionShow,Long> {
    @Modifying
    @Query("delete from QuestionShow q where q.id=?1")
    void deleteById(Long id);

    @Query("select q from QuestionShow q where q.id = ?1")
    QuestionShow findByIndex(Long id);
}
