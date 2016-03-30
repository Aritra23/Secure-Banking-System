package com.asu.cse.service;

import com.asu.cse.dao.SSTransactionTypeDao;
import com.asu.cse.model.SSTransactionType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class SSTransactionTypeServiceImpl implements SSTransactionTypeService{

	@Autowired
	SSTransactionTypeDao ssTransactionTypeDao;
	
	@Override
	public List<SSTransactionType> getAllTransactionByTransactionType(String transactionType) {
		// TODO Auto-generated method stub
		return null;
	}

	public SSTransactionTypeDao getSsTransactionTypeDao() {
		return ssTransactionTypeDao;
	}

	public void setSsTransactionTypeDao(SSTransactionTypeDao ssTransactionTypeDao) {
		this.ssTransactionTypeDao = ssTransactionTypeDao;
	}

	@Override
	public String getAuthorizationRoleForType(String transactionType) {
		
		return ssTransactionTypeDao.getAuthorizationRoleForType(transactionType);
	}

}
