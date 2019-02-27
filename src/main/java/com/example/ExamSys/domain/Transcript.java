package com.example.ExamSys.domain;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "transcript")
public class Transcript implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@OneToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "examanswer_text")
//	private ExamAnswer_text examAnswer_text;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "examanswer_choice")
//    private ExamAnswer_choice examAnswer_choice;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private Student student;
	
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	private QuestionBank questionBank;

	//按照不同类别存储分数，一张试卷需维护N条记录，N为试卷包含的类别数。
	@Column(name = "score")
	private int score;

	@Column(name = "type")
	private String type;

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

    public int getScore() { return score; }

    public void setScore(int score) { this.score = score; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }
}
