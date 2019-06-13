package com.example.ExamSys.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.ExamSys.domain.QuestionAnswer;
import com.example.ExamSys.domain.Student;
import com.example.ExamSys.domain.Teacher;

@Repository
public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer,Long> {

    @Query("select q from QuestionAnswer q where q.questionBank.id=?1 and number = ?2 and q.student.id=?3")
    QuestionAnswer findByIDandNumber(Long id, int number, Long studentId);
    
    @Query("select q from QuestionAnswer q where q.questionBank.id=?1 and q.student=?2 and q.number=?3")
    QuestionAnswer findByQuestionBankAndStudentAndNumber(Long id, Student student, int number);
    
    @EntityGraph(attributePaths = {"questionBank","student","teacher"})
    @Query("select q from QuestionAnswer q where q.isMarked=true and q.student = (select s from Student s where s.user.login = ?1) and q.teacher is not NULL group by q.questionBank")
    List<QuestionAnswer> findAllMarkedByLogin(String login);
    
//    @Query("select q from QuestionAnswer q where q.isMarked=true and q.number=5 order by score desc limit ?1,6")
//    List<QuestionAnswer> findAllMarkedShowByOrederNumber(Long startNumber);
    
    @Query(value="select q from QuestionAnswer q where q.isMarked=true and q.questiontype=2 and q.createdDate > ?1 order by q.score desc")
    Page<QuestionAnswer> findByNumberContaining(Pageable pageable, @Param("date") Date date);
    
    @Query(value="select q from QuestionAnswer q where q.createdDate > ?1")
    List<QuestionAnswer> findByNumberContaining(Date date);
    
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
    @Query("update QuestionAnswer q set q.isMarked=?1, q.teacher=?2 where q.id=?3")
    void updateisModifiedAndTeacher(boolean isMarked, Teacher teacher, Long qaId);
    
    @Modifying
    @Transactional
    @Query("update QuestionAnswer q set q.isMarked=?1, q.teacher=?2, q.score=?3 where q.id=?4")
    void updateMarkedAndTeacherAndScore(boolean isMarked, Teacher teacher, Integer score, Long id);
    
    @Modifying
    @Transactional
    @Query("delete from QuestionAnswer q where q.student=(select s from Student s where s.user.id=(select u.id from User u where u.login in (:logins)))")
    void deleteByLogins(@Param("logins") List<String> logins);
    
}
