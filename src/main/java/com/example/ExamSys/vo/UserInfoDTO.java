package com.example.ExamSys.vo;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.domain.enumeration.Gender;

public class UserInfoDTO {
	
	@NotNull
	@Pattern(regexp = Constants.NAME_REGEX)
	@Size(max = 50)
	private String realname;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	private int age;
	
	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	private String login;
	
	private String school;
	
	private String province;
	
	private String city;
	
	private String town;
	
	private String trainingAgency;
	
	private String motto;
	
	private MultipartFile personalpic;

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
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

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getTrainingAgency() {
		return trainingAgency;
	}

	public void setTrainingAgency(String trainingAgency) {
		this.trainingAgency = trainingAgency;
	}

	public String getMotto() {
		return motto;
	}

	public void setMotto(String motto) {
		this.motto = motto;
	}

	public MultipartFile getPersonalpic() {
		return personalpic;
	}

	public void setPersonalpic(MultipartFile personalpic) {
		this.personalpic = personalpic;
	}

}
