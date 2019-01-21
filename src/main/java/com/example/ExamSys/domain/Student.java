package com.example.ExamSys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.domain.enumeration.Gender;

@Entity
@Table(name = "student_info")
public class Student implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
	@JoinColumn(unique = true)
	private User user;
	
	@NotNull
	@Pattern(regexp = Constants.NAME_REGEX)
	@Size(max = 50)
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	
	@NotNull
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
	
	@Pattern(regexp = Constants.PHONE_REGEX)
	@Size(min = 5, max = 50)
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Email
	@Size(min = 5,max = 100)
	@Column(length = 100)
	private String email;
	
	@Column(name = "motto")
	private String motto;


	@OneToMany(mappedBy = "student", orphanRemoval = true)
	private Set<Production> productions = new HashSet<>();

	@OneToMany(mappedBy = "student", orphanRemoval = true)
	private Set<Exam_sum> exam_sums = new HashSet<>();

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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public Set<Production> getProductions() {
		return productions;
	}

	public void setProductions(Set<Production> productions) {
		this.productions = productions;
	}

	public Set<Exam_sum> getExam_sums() {return exam_sums;}

	public void setExam_sums(Set<Exam_sum> exam_sums) {this.exam_sums = exam_sums;}
}
