package com.asu.cse.model;

import java.io.Serializable;
//import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity
@Table(name="ss_user")
public class SSUser implements Serializable{
	private static final long serialVersionUID = -7520338084244844389L;
	@Id
	@GeneratedValue
	@Column(name = "user_id", nullable = false)
    private Integer userid;
	@Column(name = "first_name", nullable = false)
	private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "ssn")
	private String social;
	public String getSocial() {
		return social;
	}
	public void setSocial(String social) {
		this.social = social;
	}
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "state", nullable = false)
	private String state;
	@Column(name = "country", nullable = false)
	private String country;
	@Column(name = "zip_code", nullable = false)
	private String zipcode;
	@Column(name = "email", nullable = false)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "user_name", nullable = false)
	private String userName;
	@Column(name ="enabled",nullable = true)
	private Integer enabled;
	@Column(name ="Secret",nullable = true)
	private String secret;
	@Column(name = "forgot_password_enabled", nullable = false)
	private byte forgotPasswordEnabled;
	public String getSecret() {
		return secret;
	}
	public byte getForgotPasswordEnabled() {
		return forgotPasswordEnabled;
	}
	public void setForgotPasswordEnabled(byte forgotPasswordEnabled) {
		this.forgotPasswordEnabled = forgotPasswordEnabled;
	}
	public void setSecret(String secret) {
		this.secret = secret;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	//Added confirmPassword - not stored in db - Do not delete, needed for backend validation.
	@Transient
	private String confirmPassword;
	
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
}
