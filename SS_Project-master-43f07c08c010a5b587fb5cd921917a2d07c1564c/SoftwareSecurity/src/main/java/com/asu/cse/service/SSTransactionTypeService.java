package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.SSTransactionType;

public interface SSTransactionTypeService {

	public List<SSTransactionType> getAllTransactionByTransactionType(String transactionType);
	public String getAuthorizationRoleForType (String transactionType);
}
