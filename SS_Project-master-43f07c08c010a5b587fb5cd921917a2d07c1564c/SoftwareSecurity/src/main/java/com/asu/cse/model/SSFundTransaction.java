package com.asu.cse.model;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ss_fund_transaction")

public class SSFundTransaction implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "fund_transaction_id", nullable = false)
    private Integer fundTransactionId;
	@Column(name = "user_id", nullable = false)
	private Integer userId;
	@Column(name = "transaction_type", nullable = false)
	private String transactionType;
	@Column(name = "source_account", nullable = false)
	private Integer sourceAccount;
	@Column(name = "destination_account", nullable = false)
	private Integer destinationAccount;
	@Column(name = "amount", nullable = false)
	private Float amount;
	@Column(name = "authorization_role", nullable = false)
	private String authorizationRole;
	@Column(name = "status", nullable = false)
	private String status;
	@Column(name = "comment", nullable = false)
	private String comment;
	@Column(name = "time_stamp", nullable = false)
	private Timestamp timeStamp;

	public Integer getFundTransactionId() {
		return fundTransactionId;
	}
	public void setFundTransactionId(Integer fundTransactionId) {
		this.fundTransactionId = fundTransactionId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public Integer getSourceAccount() {
		return sourceAccount;
	}
	public void setSourceAccount(Integer sourceAccount) {
		this.sourceAccount = sourceAccount;
	}
	public Integer getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(Integer destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getAuthorizationRole() {
		return authorizationRole;
	}
	public void setAuthorizationRole(String authorizationRole) {
		this.authorizationRole = authorizationRole;
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
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
}
