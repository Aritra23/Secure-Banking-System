package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.SSTransactionManagement;

public interface SSTransactionManagementService {
	
	public List<SSTransactionManagement> getTransactionIdByTransactionGroup(String transactionGroup);

}
