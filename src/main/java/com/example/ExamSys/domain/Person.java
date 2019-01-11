package com.example.ExamSys.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = true, length = 20)
	private String name;
	
	@Column(name = "ages", nullable = true, length = 4)
	private int ages;
	
}
