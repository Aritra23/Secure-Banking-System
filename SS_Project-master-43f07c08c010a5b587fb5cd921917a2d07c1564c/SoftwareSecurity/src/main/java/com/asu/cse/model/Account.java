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
//import javax.persistence.Temporal;
//import javax.persistence.TemporalType;

@Entity
@Table(name = "ss_account")
public class Account implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "account_id", nullable = false)
    private Integer accountid;
	@Column(name = "user_id", nullable = false)
	private Integer userid;
	@Column(name = "account_type", nullable = false)
	private String accounttype;
	@Column(name = "balance", nullable = false)
	private Float balance;
	@Column(name = "pending_balance", nullable = false)
	private Float pendingbalance;
	@Column(name = "tranaction_limit", nullable = false)
	private Integer tranlimit;
	@Column(name = "is_active", nullable = false)
	private byte isactive;
	public Integer getAccountid() {
		return accountid;
	}
	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public Float getBalance() {
		return balance;
	}
	public void setBalance(Float balance) {
		this.balance = balance;
	}
	public Float getPendingbalance() {
		return pendingbalance;
	}
	public void setPendingbalance(Float pendingbalance) {
		this.pendingbalance = pendingbalance;
	}
	public Integer getTranlimit() {
		return tranlimit;
	}
	public void setTranlimit(Integer tranlimit) {
		this.tranlimit = tranlimit;
	}
	public byte getIsactive() {
		return isactive;
	}
	public void setIsactive(byte isactive) {
		this.isactive = isactive;
	}
}
