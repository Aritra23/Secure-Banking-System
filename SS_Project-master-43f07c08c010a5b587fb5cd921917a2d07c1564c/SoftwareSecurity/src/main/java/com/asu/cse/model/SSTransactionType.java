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
@Table(name="ss_transaction_type")
public class SSTransactionType implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "transaction_type", nullable = false)
    private String TransactionType;
	@Column(name = "authorization_role", nullable = false)
    private String authorizationRole;
	@Column(name = "time_stamp", nullable = false)
    private Timestamp timestamp;
	public String getTransactionType() {
		return TransactionType;
	}
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}
	public String getAuthorizationRole() {
		return authorizationRole;
	}
	public void setAuthorizationRole(String authorizationRole) {
		this.authorizationRole = authorizationRole;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
}
