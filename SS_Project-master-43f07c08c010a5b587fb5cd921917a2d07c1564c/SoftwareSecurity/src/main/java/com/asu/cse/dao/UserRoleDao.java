package com.asu.cse.dao;

import com.asu.cse.model.SSUserRole;

public interface UserRoleDao {
		public SSUserRole getRole(Integer userId);
		public void addUserRole(SSUserRole ssUserRole);
		public void deleteUserRole(Integer userId);
}
