package com.example.ExamSys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question_bank")
public class QuestionBank implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	//试卷难度
	@Column(name = "level")
	private int level;
	
	@OneToMany(mappedBy = "questionBank", orphanRemoval = true)
	private Set<QuestionChoice> choiceQuestions = new HashSet<>();

	@OneToMany(mappedBy = "questionBank", orphanRemoval = true)
	private Set<QuestionShort> shortQuestions = new HashSet<>();
	
	@OneToMany(mappedBy = "questionBank", orphanRemoval = true)
	private Set<QuestionShow> showQuestions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Set<QuestionChoice> getChoiceQuestions() {
		return choiceQuestions;
	}

	public void setChoiceQuestions(Set<QuestionChoice> choiceQuestions) {
		this.choiceQuestions = choiceQuestions;
	}

	public Set<QuestionShort> getShortQuestions() {
		return shortQuestions;
	}

	public void setShortQuestions(Set<QuestionShort> shortQuestions) {
		this.shortQuestions = shortQuestions;
	}

	public Set<QuestionShow> getShowQuestions() {
		return showQuestions;
	}

	public void setShowQuestions(Set<QuestionShow> showQuestions) {
		this.showQuestions = showQuestions;
	}
	
	
}
