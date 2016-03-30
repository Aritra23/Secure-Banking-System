package com.asu.cse.dao;

public interface SSOTPDao {
	Integer getPin(String email);
	void removePin(String email);
	boolean isValidPin(String email, Integer pin);
}
