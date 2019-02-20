package com.example.ExamSys.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

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

    @JsonBackReference
    @ManyToMany(mappedBy = "choiceQuestions", fetch = FetchType.LAZY)
    private Set<QuestionBank> questionBankSet = new HashSet<>();

    @Column(name = "content")
	private String content;
	
	@Column(name = "answer")
	private String answer;
	
	@Column(name = "type")
	private String type;

	@Column(name = "choicetype")
    private String choicetype;

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

    public String getChoicetype() {
        return choicetype;
    }

    public void setChoicetype(String choicetype) {
        this.choicetype = choicetype;
    }

    public void addChoice(Choice choice) {
		choice.setQuestionChoice(this);
		this.choices.add(choice);
	}

    public Set<QuestionBank> getQuestionBankSet() {
        return questionBankSet;
    }

    public void setQuestionBankSet(Set<QuestionBank> questionBankSet) {
        this.questionBankSet = questionBankSet;
    }
}
