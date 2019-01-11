package com.example.ExamSys.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Email;

import com.example.ExamSys.config.Constants;
import com.example.ExamSys.domain.enumeration.Gender;

@Entity
@Table(name = "user")
public class User {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Pattern(regexp = Constants.LOGIN_REGEX)
	@Size(min = 1, max = 50)
	@Column(length = 50, unique = true, nullable = false)
	private String login;
	
	@NotNull
	@Size(min = 60, max = 60)
	@Column(name = "password_hash", length = 60, nullable = false)
	private String password;
	
	@Size(max = 256)
	@Column(name = "image_url", length = 256)
	private String imageUrl = "";
	
	
	@Pattern(regexp = Constants.PHONE_REGEX)
	@Size(min = 5, max = 50)
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@NotNull
	@Pattern(regexp = Constants.NAME_REGEX)
	@Size(max = 50)
	@Column(name = "name", length = 50, nullable = false)
	private String name;
	
	@Email
	@Size(min = 5,max = 100)
	@Column(length = 100)
	private String email;
	
	@Column(name = "is_buddy")
	private Boolean isBuddy = false;
	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Gender gender;
	
	@NotNull
	@Pattern(regexp = Constants.IDENTITY_REGEX)
	@Size(min = 15, max = 18)
	@Column(name = "identity_number", length = 18, nullable = false)
	private String identityNumber;
	
	@Size(max = 256)
	@Column(name = "identity_photo", length = 256)
	private String identityPhoto;
	
	@Column(name = "identity_province")
	private String identityProvince;
	
	@Column(name = "identity_city")
	private String identityCity;
	
	@Column(name = "identity_region")
	private String identityRegion;
	
	@Column
	private String school;
	
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private Set<Production> productions = new HashSet<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsBuddy() {
		return isBuddy;
	}

	public void setIsBuddy(Boolean isBuddy) {
		this.isBuddy = isBuddy;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getIdentityNumber() {
		return identityNumber;
	}

	public void setIdentityNumber(String identityNumber) {
		this.identityNumber = identityNumber;
	}

	public String getIdentityPhoto() {
		return identityPhoto;
	}

	public void setIdentityPhoto(String identityPhoto) {
		this.identityPhoto = identityPhoto;
	}

	public String getIdentityProvince() {
		return identityProvince;
	}

	public void setIdentityProvince(String identityProvince) {
		this.identityProvince = identityProvince;
	}

	public String getIdentityCity() {
		return identityCity;
	}

	public void setIdentityCity(String identityCity) {
		this.identityCity = identityCity;
	}

	public String getIdentityRegion() {
		return identityRegion;
	}

	public void setIdentityRegion(String identityRegion) {
		this.identityRegion = identityRegion;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public Set<Production> getProductions() {
		return productions;
	}

	public void setProductions(Set<Production> productions) {
		this.productions = productions;
	}
	
}
