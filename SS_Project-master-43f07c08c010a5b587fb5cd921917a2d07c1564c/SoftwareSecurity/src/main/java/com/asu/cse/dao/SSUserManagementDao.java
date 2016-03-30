package com.asu.cse.dao;

import java.util.List;

import com.asu.cse.model.SSUserManagement;

public interface SSUserManagementDao {
	
	public List<Integer> getApproverId(Integer userId);
	public List<Integer> getApproveeId(Integer userId);
	public void addUserManagement(SSUserManagement ssUserManagement);
	public void deleteUserManagement(Integer userId);
}
