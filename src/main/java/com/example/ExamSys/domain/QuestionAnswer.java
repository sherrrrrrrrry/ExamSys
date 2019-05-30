package com.example.ExamSys.domain;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "question_answer")
@EntityListeners(AuditingEntityListener.class)
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
    
    @Column(name = "score")
    private Integer score;
    
    @CreatedDate
    @Column(name = "creatate")
    private Date createdDate;
    
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public void setScore(Integer score) {
		this.score = score;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
    
    
}
