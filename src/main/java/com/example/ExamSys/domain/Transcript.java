package com.example.ExamSys.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transcript")
public class Transcript implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Student student;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private QuestionBank questionBank;
	
	@Column(name = "total_points")
	private Integer totalPoints;
	
	//一张试卷中个人修养得分/一张试卷中个人修养总分*level
	@Column(name = "grxy_points")
	private Integer grxyPoints;
	
	@Column(name = "gzylcsnl_points")
	private Integer gzylcsnlPoints;
	
	@Column(name = "zwglnl_points")
	private Integer zwglnlPoints;
	
	@Column(name = "gtxtnl_points")
	private Integer gtxtnlPoints;
	
	@Column(name = "cxnl_points")
	private Integer cxnlPoints;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public QuestionBank getQuestionBank() {
		return questionBank;
	}

	public void setQuestionBank(QuestionBank questionBank) {
		this.questionBank = questionBank;
	}

	public Integer getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(Integer totalPoints) {
		this.totalPoints = totalPoints;
	}

	public Integer getGrxyPoints() {
		return grxyPoints;
	}

	public void setGrxyPoints(Integer grxyPoints) {
		this.grxyPoints = grxyPoints;
	}

	public Integer getGzylcsnlPoints() {
		return gzylcsnlPoints;
	}

	public void setGzylcsnlPoints(Integer gzylcsnlPoints) {
		this.gzylcsnlPoints = gzylcsnlPoints;
	}

	public Integer getZwglnlPoints() {
		return zwglnlPoints;
	}

	public void setZwglnlPoints(Integer zwglnlPoints) {
		this.zwglnlPoints = zwglnlPoints;
	}

	public Integer getGtxtnlPoints() {
		return gtxtnlPoints;
	}

	public void setGtxtnlPoints(Integer gtxtnlPoints) {
		this.gtxtnlPoints = gtxtnlPoints;
	}

	public Integer getCxnlPoints() {
		return cxnlPoints;
	}

	public void setCxnlPoints(Integer cxnlPoints) {
		this.cxnlPoints = cxnlPoints;
	}
	
	
}
