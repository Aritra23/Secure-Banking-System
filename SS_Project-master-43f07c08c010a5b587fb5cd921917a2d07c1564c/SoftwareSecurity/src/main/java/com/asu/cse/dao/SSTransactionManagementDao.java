package com.asu.cse.dao;
import java.util.List;

import com.asu.cse.model.SSTransactionManagement;

public interface SSTransactionManagementDao {
	
	public List<SSTransactionManagement> getTransactionIdByTransactionGroup(String transactionGroup);
	public List<SSTransactionManagement> getAllTransactionId();

}
