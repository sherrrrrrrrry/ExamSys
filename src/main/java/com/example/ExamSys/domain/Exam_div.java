package com.example.ExamSys.domain;

import javax.persistence.*;

@Entity
@Table(name = "exam_div")
public class Exam_div {
    //试卷成绩，记录每题的分数
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Exam_sum exam_sum;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Exam_sum getExam_sum() { return exam_sum; }

    public void setExam_sum(Exam_sum exam_sum) { this.exam_sum = exam_sum; }




}
