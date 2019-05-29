package com.example.ExamSys.dao;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.QuestionAnswer;
import com.example.ExamSys.domain.Teacher;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer,Long> {

    @Query("select q from QuestionAnswer q where q.questionBank.id=?1 and number = ?2")
    QuestionAnswer findByIDandNumber(Long id, int number);
    
    @EntityGraph(attributePaths = {"questionBank","student","teacher"})
    @Query("select q from QuestionAnswer q where q.isMarked=true and q.student = (select s from Student s where s.user.login = ?1) group by q.questionBank")
    List<QuestionAnswer> findAllMarkedByLogin(String login);

    @Modifying
    @Transactional
    @Query("update QuestionAnswer q set q.isMarked=?1 where q.id=?2")
    void updateisModified(boolean isMarked, Long id);

    @Query("select q from QuestionAnswer q where q.questionBank.id=?1 and q.student.id = ?2")
    Set<QuestionAnswer> findByBankandStu(Long bankid, Long stuid);

    @Query("select q from QuestionAnswer q where q.isMarked=0")
    List<QuestionAnswer> getTobeMarked();//返回所有需要阅卷的

    @Query("select q from QuestionAnswer q where q.isMarked=0 and q.questionBank.id=?1 and q.student.id = ?2")
    List<QuestionAnswer> getTobeMarkedByBankandStu(Long bankid, Long stuid);//返回所有需要阅卷的
    //"select s.level from Student s where s.user = (select u from User u where u.login = ?1)"

    @Modifying
    @Transactional
    @EntityGraph(attributePaths = {"user","student"})
    @Query("update QuestionAnswer q set q.isMarked=?1 where q.student.user = (select u from User u where u.login = ?3) and q.questionBank.id=?2")
    void updateisMarked(boolean isMarked, Long bankid, Long stuid);
    
    @Modifying
    @Transactional
    @Query("update QuestionAnswer q set q.isMarked=?1, q.teacher=?2, q.score=?3 where q.id=?4")
    void updateMarkedAndTeacherAndScore(boolean isMarked, Teacher teacher, Integer score, Long id);

}
