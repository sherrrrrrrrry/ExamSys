package com.example.ExamSys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.domain.enumeration.Gender;

@Entity
@Table(name = "teacher_info")
public class Teacher implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(unique = true)
	private User user;
	
	@Pattern(regexp = Constants.NAME_REGEX)
	@Size(max = 50)
	@Column(name = "name", length = 50)
	private String name;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "gender")
	private Gender gender;
	
	@Column(name = "age")
	private int age;
	
	@Size(max = 256)
	@Column(name = "photo", length = 256)
	private String photoUrl;
	
	@Column(name = "school")
	private String school;
	
	@Column(name = "school_province")
	private String schoolProvince;
	
	@Column(name = "school_city")
	private String schoolCity;
	
	@Column(name = "school_region")
	private String schoolRegion;
	
	@Column(name = "training_name")
	private String trainingName;
	
	@Column(name = "motto")
	private String motto;

	@OneToMany(mappedBy = "teacher", orphanRemoval = true)
	private Set<QuestionAnswer> questionAnswers = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSchoolProvince() {
		return schoolProvince;
	}

	public void setSchoolProvince(String schoolProvince) {
		this.schoolProvince = schoolProvince;
	}

	public String getSchoolCity() {
		return schoolCity;
	}

	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}

	public String getSchoolRegion() {
		return schoolRegion;
	}

	public void setSchoolRegion(String schoolRegion) {
		this.schoolRegion = schoolRegion;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}
	
	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}
	
	
}
