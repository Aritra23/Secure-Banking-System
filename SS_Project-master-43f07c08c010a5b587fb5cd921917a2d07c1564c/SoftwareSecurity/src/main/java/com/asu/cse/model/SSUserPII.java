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
//import javax.persistence.Transient;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity
@Table(name="ss_user_pii")

public class SSUserPII implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "user_name", nullable = false)
	private String username;
	@Column(name = "pii", nullable = false)
	private String pii;
	@Column(name = "token", nullable = false)
	private String token;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPii() {
		return pii;
	}
	public void setPii(String pii) {
		this.pii = pii;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
