package com.asu.cse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asu.cse.dao.SSFundTransactionDao;
import com.asu.cse.dao.SSTransactionTypeDao;
import com.asu.cse.dao.SSUserManagementDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.model.SSUserManagement;

public class SSUserManagementServiceImpl implements SSUserManagementService {

	@Autowired
	SSUserManagementDao ssUserManagementDao;
	
	@Autowired
	UserDao userDao;

	public SSUserManagementDao getSsUserManagementDao() {
		return ssUserManagementDao;
	}

	public void setSsUserManagementDao(SSUserManagementDao ssUserManagementDao) {
		this.ssUserManagementDao = ssUserManagementDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<Integer> getApproverId(Integer userId) {
		return ssUserManagementDao.getApproverId(userId);
	}

	@Override
	public List<Integer> getApproveeId(Integer userId) {
		return ssUserManagementDao.getApproveeId(userId);
	}
	@Override
	public void addUserManagementList(List<String> approverList, Integer approveeUserId) {
		for(String approver:approverList)
		{
			SSUserManagement ssUserManagement = new SSUserManagement();
			Integer approverUserId = userDao.getUserByUserName(approver).getUserid();
			ssUserManagement.setUserId(approveeUserId);
			ssUserManagement.setApproverId(approverUserId);
			ssUserManagementDao.addUserManagement(ssUserManagement);
			
		}
	}
}
