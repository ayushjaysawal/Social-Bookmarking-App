package com.semantics.entities;

import com.semantics.constant.Gender;
import com.semantics.constant.UserType;

public class User {
	private long id;
	private String email;
	private String password;
	private String firstname;
	private String lastname;
	private Gender gender;
	private UserType userType;
	private String kid_friendly;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", gender=" + gender + ", userType=" + userType +", kid_friendly"+kid_friendly+ "]";
	}

	public String getKid_friendly() {
		return kid_friendly;
	}

	public void setKid_friendly(String kid_friendly) {
		this.kid_friendly = kid_friendly;
	}

}
