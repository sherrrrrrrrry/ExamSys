package com.example.ExamSys.domain;


import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "question_answer")
public class QuestionAnswer implements Serializable {
    /**
     * 存答案,选择判断 存成一个text，用分号分割。每个简答和展示题 分别存成一条记录。
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "transcript_id")
    private Transcript transcript;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Student student;

    @JsonBackReference
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private QuestionBank questionBank;

    //题号,选择判断集合的题号为0，简答和展示题的题号为真实题号
    private int number;

    @Lob
    @Basic(fetch=FetchType.LAZY)
    @Column(columnDefinition="TEXT",nullable=true)
    private String answer;

    private String questiontype;//题型，分别为0，1，2。 0为选择判断，1为简答，2为展示题

    @Value("${some.key:false}")
    private boolean isMarked;

    @JsonBackReference
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Teacher teacher;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Transcript getTranscript() { return transcript; }

    public void setTranscript(Transcript transcript) { this.transcript = transcript; }

    public int getNumber() { return number; }

    public void setNumber(int number) { this.number = number; }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }

    public QuestionBank getQuestionBank() { return questionBank; }

    public void setQuestionBank(QuestionBank questionBank) { this.questionBank = questionBank; }

    public String getAnswer() { return answer; }

    public void setAnswer(String answer) { this.answer = answer; }

    public String getQuestiontype() { return questiontype; }

    public void setQuestiontype(String questiontype) { this.questiontype = questiontype; }

    public boolean isMarked() { return isMarked; }

    public void setMarked(boolean marked) { isMarked = marked; }

    public Teacher getTeacher() { return teacher; }

    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
}
