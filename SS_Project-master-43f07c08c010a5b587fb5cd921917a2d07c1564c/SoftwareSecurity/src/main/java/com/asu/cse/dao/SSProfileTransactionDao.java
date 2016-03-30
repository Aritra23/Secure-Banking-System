package com.asu.cse.dao;
import java.util.List;

import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;

public interface SSProfileTransactionDao {
	
      public Integer addProfileTransaction(SSProfileTransaction profileTransaction);
      public void updateProfileTransaction(Integer profileTransactionId , boolean approved);
      public void deleteProfileTransaction(SSProfileTransaction trans);
      public List<SSProfileTransaction> getProfileTransactionStatus(String status);
      public List<SSProfileTransaction> getAllProfileTransactions(); 
      public List<SSProfileTransaction> getAllExternalSignupByStatus(String status);
      public List<SSProfileTransaction> getAllInternalSignupByStatus(String status);
      public List<SSProfileTransaction> getProfileTransactionByStatus(SSUser user,String role,String status);
      public SSProfileTransaction getProfileTransactionById(Integer transactionId);
      public SSProfileTransaction getProfileTransactionByEmail(String transactionId);
	  public SSProfileTransaction userExists(String userId);
	  public SSProfileTransaction emailExists(String email);
	  public SSProfileTransaction ssnExists(String ssn);
	  public Integer updateProfileTransaction(SSProfileTransaction trans);
	  public List<SSProfileTransaction> getAllExternalEditRequestsByStatus(String status);
	  public SSProfileTransaction getProfileTransactionByUserId(Integer userId);
	  public List<SSProfileTransaction> getAllInternalEditRequestsByStatus(String status);
	 
}
