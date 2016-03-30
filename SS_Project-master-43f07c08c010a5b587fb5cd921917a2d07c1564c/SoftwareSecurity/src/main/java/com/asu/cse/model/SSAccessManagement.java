package com.asu.cse.model;

import java.io.Serializable;
import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
//import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.Table;
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity
@Table(name = "ss_access_management")


public class SSAccessManagement implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6368521623205226702L;
	@Id
	@GeneratedValue
	@Column(name = "access_management_id", nullable = false)
    private Integer accessManagementId;
	@Column(name = "transaction_group", nullable = false)
	private String transactionGroup;
	@Column(name = "user_role", nullable = false)
	private String userRole;
	@Column(name = "time_stamp", nullable = false)
	private Timestamp timeStamp;
	public Integer getAccessManagementId() {
		return accessManagementId;
	}
	public void setAccessManagementId(Integer accessManagementId) {
		this.accessManagementId = accessManagementId;
	}
	public String getTransactionGroup() {
		return transactionGroup;
	}
	public void setTransactionGroup(String transactionGroup) {
		this.transactionGroup = transactionGroup;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	

}
