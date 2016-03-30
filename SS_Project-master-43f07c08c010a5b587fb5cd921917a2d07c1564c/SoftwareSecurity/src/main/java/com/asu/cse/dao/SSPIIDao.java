package com.asu.cse.dao;

import java.util.List;

import com.asu.cse.model.SSUser;
public interface SSPIIDao {
	
	public List<SSUser> getAllPIIInfo();
	public boolean ssnExists(String ssn);
	public boolean emailExists(String email);
	public boolean nameExists(String firstName);
	

}
