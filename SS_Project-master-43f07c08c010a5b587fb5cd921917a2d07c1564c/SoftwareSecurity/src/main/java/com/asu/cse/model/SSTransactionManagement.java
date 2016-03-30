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
@Table(name = "ss_transaction_management")

public class SSTransactionManagement implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue
	@Column(name = "transaction_id", nullable = false)
    private Integer transactionId;
	@Column(name = "transaction_group", nullable = false)
	private String transactionGroup;
	@Column(name = "time_stamp", nullable = false)
	private Timestamp timeStamp;
	public Integer getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}
	public String getTransactionGroup() {
		return transactionGroup;
	}
	public void setTransactionGroup(String transactionGroup) {
		this.transactionGroup = transactionGroup;
	}
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
