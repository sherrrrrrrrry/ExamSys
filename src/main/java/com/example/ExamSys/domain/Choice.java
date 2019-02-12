package com.example.ExamSys.domain;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "choice")
public class Choice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * optional = false 表示QuestionChoice不可为空
	 */
	@ManyToOne(optional=false, fetch = FetchType.LAZY)
	private QuestionChoice questionChoice;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "image_url")
	private String imageUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public QuestionChoice getQuestionChoice() {
		return questionChoice;
	}

	public void setQuestionChoice(QuestionChoice questionChoice) {
		this.questionChoice = questionChoice;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
}
