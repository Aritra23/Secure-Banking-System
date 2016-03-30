package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.SSUserManagement;

public interface SSUserManagementService {
	public void addUserManagementList(List<String> approverList, Integer approveeUserId);
	public List<Integer> getApproverId(Integer userId);
	public List<Integer> getApproveeId(Integer userId);
}
