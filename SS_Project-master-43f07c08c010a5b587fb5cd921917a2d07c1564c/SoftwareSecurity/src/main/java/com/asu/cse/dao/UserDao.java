package com.asu.cse.dao;

import java.util.List;

import com.asu.cse.model.SSUser;

public interface UserDao {
	 public SSUser addUser(SSUser user);
	 public void deleteUser(Integer userId);
	 public SSUser getUser(Integer userId);
	 public List<SSUser> getUsersByUserNameSearch(String userNameSearch);
	 public boolean userExists(String userName);
	 public List <SSUser> getAllUsers();
	 public List <String> getUserNamesByRole(String role);
	 public SSUser getUserByUserName(String userName);
	 public boolean ssnExists(String ssn);
	 public boolean emailExists(String email);
	 public void setSecrect(String username, String hash);
	 public SSUser updateUser(SSUser user);
	 public SSUser getUserByEmail(String email);
	 public List<SSUser> getAllUsersByUserids(List<Integer> UserIDs);
	 public void setEnabledBitAfterDeleteProfileSubmission(Integer userId, Integer enabled);
	 public boolean resetpassword(String userName);
	 public List<SSUser> getUsersByRole(String role);
}
