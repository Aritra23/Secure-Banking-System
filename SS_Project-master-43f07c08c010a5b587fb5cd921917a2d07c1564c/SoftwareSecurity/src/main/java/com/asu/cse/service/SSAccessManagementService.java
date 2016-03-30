package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.SSAccessManagement;

public interface SSAccessManagementService {
	
	public List<SSAccessManagement> getAccessManagementId(Integer accessManagementId);
	public List<SSAccessManagement> getAccessManagementIdByUserRole(String userRole);
	public List<SSAccessManagement> getTransactionGroupByUserRole(String userRole);

}
