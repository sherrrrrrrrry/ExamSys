package com.example.ExamSys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "question_choice")
public class QuestionChoice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/*
	 * 如果orphanRemoval = true，那么这个操作会删除Choice对象，
	 * 如果为false，则会删除他们的关系，将choice对questionChoice的引用设置为null。
	 */
	@OneToMany(mappedBy = "questionChoice",cascade=CascadeType.ALL, orphanRemoval = true)
	private Set<Choice> choices = new HashSet<>();
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "answer")
	private String answer;
	
	@Column(name = "type")
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Choice> getChoices() {
		return choices;
	}

	public void setChoices(Set<Choice> choices) {
		this.choices = choices;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void addChoice(Choice choice) {
		choice.setQuestionChoice(this);
		this.choices.add(choice);
	}
}
