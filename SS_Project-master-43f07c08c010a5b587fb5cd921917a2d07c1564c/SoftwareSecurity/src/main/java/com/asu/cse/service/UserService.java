package com.asu.cse.service;

import com.asu.cse.model.SSUser;

import java.util.List;

import com.asu.cse.model.SSProfileTransaction;

public interface UserService {
	 public void addUser(SSUser user);
	 public void deleteUser(Integer userId);
	 public SSUser getUser(Integer userId);
	 public SSUser addUserAfterSignUp(SSProfileTransaction ssProfileTransaction);
	 public void forgotPassword(String email);
	 public List<SSUser> getAllUsersByEditProfileRequests(List<SSProfileTransaction> ProfileTransaction);
	 public Integer editUserAfterApproval(Integer userId, SSProfileTransaction Transaction);
	 public boolean updatePassword(SSUser user, String newPassword);
	 public void emailStatement(Integer userId, String monthYear);
	 public void downloadLogs(SSUser user);
	 public List<SSUser> getUsersByRole(String role);
	 public List<SSUser> getAllUsersByUserName(String userNameSearch);
	 public void setEnabledBitAfterDeleteProfileSubmission (Integer userId, Integer enabled);
}
