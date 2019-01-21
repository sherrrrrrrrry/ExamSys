package com.example.ExamSys.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "exam_sum")
public class Exam_sum implements Serializable {
    //试卷成绩，记录总分和等级
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //不知道考试成绩等级和卷子的等级是不是一回事？
    @Column(name = "level")
    private int level;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestionBank questionBank;

    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public QuestionBank getQuestionBank() { return questionBank; }

    public void setQuestionBank(QuestionBank questionBank) { this.questionBank = questionBank; }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }
}