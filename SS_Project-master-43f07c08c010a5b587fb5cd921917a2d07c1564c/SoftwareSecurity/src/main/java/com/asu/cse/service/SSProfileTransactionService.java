package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;

public interface SSProfileTransactionService {

	public Integer addProfileTransaction(SSProfileTransaction profileTransaction);

	public Integer updateProfileTransaction(SSProfileTransaction profileTransaction);

	public Integer newDeleteProfileTransaction(SSUser user, String role);

	public List<SSProfileTransaction> getProfileTransactionStatus(String status);

	public List<SSProfileTransaction> getAllProfileTransactions();

	// For User is for persons who is assigned to approve this request
	public List<SSProfileTransaction> getPendingProfileTransactionForUser(SSUser user);

	public List<SSProfileTransaction> getPendingSignupTransactionForUser(SSUser user);

	public SSProfileTransaction getProfileByEmail(String email);

    public Integer createNewEditProfileTransaction(SSUser userNew, SSUser user,String role);

	public List<SSProfileTransaction> getPendingEditProfileTransactionForUser(SSUser user);

	public SSProfileTransaction getProfileTransactionbyId(Integer transid);

	public Boolean editProfileMail(String email,String approver,Boolean approved);

	public Integer editProfileTransactionCheck(Integer UserId);
	
	public List<SSProfileTransaction> getDeleteProfileTrnRequests(SSUser user);

	Boolean deleteProfileMail(String email, String approver, Boolean approved);
	
	public Integer addInternalUser(SSProfileTransaction transaction);


}
