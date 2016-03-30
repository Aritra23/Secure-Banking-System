package com.asu.cse.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ss_user_role")
public class SSUserRole implements Serializable{
	private static final long serialVersionUID = -7520338084244844389L;
	
	@Id
	@Column(name = "user_id", nullable = false)
    private Integer userId;
	@Column(name = "role", nullable = false)
	private String role;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
