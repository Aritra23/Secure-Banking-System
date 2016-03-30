package com.asu.cse.dao;
import java.util.*;

import com.asu.cse.model.SSAccessManagement;

public interface SSAccessManagementDao {
	
	public List<SSAccessManagement> getAccessManagementId(Integer accessManagementId);
	public List<SSAccessManagement> getAccessManagementIdByUserRole(String userRole);
	public List<SSAccessManagement> getTransactionGroupByUserRole(String userRole);
	public List<SSAccessManagement> getAllUserRoles();
	

}
