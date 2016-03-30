package com.asu.cse.dao;
import java.util.List;
import com.asu.cse.model.SSTransactionType;
public interface SSTransactionTypeDao {
	public List<SSTransactionType> getAllTransactions();
	public List<SSTransactionType> getAllTransactionByTransactionType(String transactionType);
	public List<SSTransactionType> getSignupTransactionForRole(String role);
	public String getAuthorizationRoleForType(String transactionType);
}
