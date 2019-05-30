package com.example.ExamSys.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "excellent_production")
public class ExcellentProduction implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	@Column(name="question_answer_id")
	private Long questionAnswerId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQuestionAnswerId() {
		return questionAnswerId;
	}

	public void setQuestionAnswerId(Long questionAnswerId) {
		this.questionAnswerId = questionAnswerId;
	}	
	
}
