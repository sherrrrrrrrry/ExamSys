package com.example.ExamSys.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.domain.enumeration.Gender;

@Entity
@Table(name = "student_info")
public class Student implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Cascade(CascadeType.ALL)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(unique = true)
	private User user;
	
	@Pattern(regexp = Constants.NAME_REGEX)
	@Size(max = 50)
	@Column(name = "name", length = 50)
	private String name;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	private Gender gender;
	
	@Column(name = "age", nullable=false)
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

	@Column(name = "level", nullable=false)
    private int level;
//
//	@OneToMany(mappedBy = "student", orphanRemoval = true)
//	private Set<Production> productions = new HashSet<>();

	@OneToMany(mappedBy = "student", orphanRemoval = true)
	private Set<Transcript> transcripts = new HashSet<>();

//    @OneToMany(mappedBy = "student", orphanRemoval = true)
//    private Set<ExamAnswer_choice> examAnswer_choices = new HashSet<>();
//
//    @OneToMany(mappedBy = "student", orphanRemoval = true)
//    private Set<ExamAnswer_text> examAnswer_texts = new HashSet<>();

	@OneToMany(mappedBy = "student", orphanRemoval = true)
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

//	public Set<Production> getProductions() {
//		return productions;
//	}
//
//	public void setProductions(Set<Production> productions) {
//		this.productions = productions;
//	}


    public int getLevel() { return level; }

    public void setLevel(int level) { this.level = level; }

    public Set<Transcript> getTranscripts() { return transcripts; }

    public void setTranscripts(Set<Transcript> transcripts) { this.transcripts = transcripts; }

    public Set<QuestionAnswer> getQuestionAnswers() { return questionAnswers; }

    public void setQuestionAnswers(Set<QuestionAnswer> questionAnswers) { this.questionAnswers = questionAnswers; }
}
