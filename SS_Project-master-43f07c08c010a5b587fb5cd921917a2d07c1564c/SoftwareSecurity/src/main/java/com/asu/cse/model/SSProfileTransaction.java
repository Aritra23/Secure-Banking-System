package com.asu.cse.model;

import java.io.Serializable;
//import java.security.Timestamp;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ss_profile_transaction")
public class SSProfileTransaction implements Serializable{
	private static final long serialVersionUID = -7520338084244844389L;
	@Id
	@GeneratedValue
	@Column(name = "profile_transaction_id", nullable = false)
    private Integer profileTransactionId;
	@Column(name = "transaction_type", nullable = false)
	private String transactionType;
	@Column(name = "user_id", nullable = false)
    private Integer userId;
	@Column(name = "user_name", nullable = false)
    private String userName;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "address", nullable = false)
    private String address;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "email", nullable = false)
    private String email;
	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;
	@Column(name = "ssn", nullable = false)
    private String social;
	@Column(name = "state", nullable = false)
	private String state;
	@Column(name = "country", nullable = false)
	private String country;
	@Column(name = "authorization_role", nullable = false)
	private String authorizationUserRole;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "comment", nullable = false)
	private String comment;
	@Column(name = "time_stamp", nullable = false)
	private Timestamp timestamp;
	@Column(name="savings_enabled",nullable = false)
	private byte savingsEnabled;
	@Column(name="firstname" , nullable = false)
	private String firstname;
	@Column(name="lastname", nullable = false)
	private String lastname;
	@Column(name="zip_code",nullable = false)
 	private String zipcode;
	
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public byte getSavingsEnabled() {
		return savingsEnabled;
	}
	public void setSavingsEnabled(byte savingsEnabled) {
		this.savingsEnabled = savingsEnabled;
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
	public Integer getProfileTransactionId() {
		return profileTransactionId;
	}
	public void setProfileTransactionId(Integer profileTransactionId) {
		this.profileTransactionId = profileTransactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSocial() {
		return social;
	}
	public void setSocial(String social) {
		this.social = social;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAuthorizationUserRole() {
		return authorizationUserRole;
	}
	public void setAuthorizationUserRole(String authorizationUserRole) {
		this.authorizationUserRole = authorizationUserRole;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}