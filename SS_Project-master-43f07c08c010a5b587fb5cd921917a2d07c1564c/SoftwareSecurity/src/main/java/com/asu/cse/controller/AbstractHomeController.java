package com.asu.cse.controller;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.asu.cse.dao.SSFundTransactionDao;
import com.asu.cse.dao.SSProfileTransactionDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserRole;
import com.asu.cse.service.AccountService;
import com.asu.cse.service.SSFundTransactionService;
import com.asu.cse.service.SSPKIService;
import com.asu.cse.service.SSProfileTransactionService;
import com.asu.cse.service.SSTransactionTypeService;
import com.asu.cse.service.SSUserManagementService;
import com.asu.cse.service.SendEmailService;
import com.asu.cse.service.UserService;
import com.asu.cse.SSConstants;
/**
 * Put all DAO and common implementations here.
 */
public class AbstractHomeController {
	@Autowired
	protected UserDao userDao;
	@Autowired
	protected UserRoleDao userRoleDao;
	@Autowired
	protected UserService userService;
	@Autowired
	protected SendEmailService sendEmail;
	@Autowired
	protected AccountService accountService;
	@Autowired
	protected SSProfileTransactionService ssProfileTransactionService;
	@Autowired
	protected SSTransactionTypeService ssTransactionTypeService;
	@Autowired
	protected SSProfileTransactionDao ssProfileTransactionDao;
	@Autowired
	protected SSFundTransactionDao ssFundTransactionDao;
	@Autowired
	protected SSFundTransactionService ssFundTransactionService;
	@Autowired
	protected SSUserManagementService ssUserManagementService;
	@Autowired
	protected SSPKIService sSPKIService;
	
	SSUser getUserModel() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			// From username, get the user model to retive user role.
			return userDao.getUserByUserName(auth.getName());
		}
		return null;
	}

	Boolean resetPassword() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			SSUser user = userDao.getUserByUserName(auth.getName());
			if (userDao.resetpassword(auth.getName())){
				return true;
			}
		}
		return false;
	}
	
	String getUserRole() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			SSUser user = userDao.getUserByUserName(auth.getName());
			SSUserRole role = userRoleDao.getRole(user.getUserid());
			//TODO: Validation
			return role.getRole();
		}
		// Avoid null val exception
		return "NOROLE";
	}

	String getUserRoleName() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			SSUser user = userDao.getUserByUserName(auth.getName());
			SSUserRole role = userRoleDao.getRole(user.getUserid());
			//TODO: Validation
			if (role.getRole().equalsIgnoreCase(SSConstants.EXTERNAL_CUSTOMER_ROLE)) {
				return "Customer";
			} else if (role.getRole().equalsIgnoreCase(SSConstants.EXTERNAL_MERCHANT_ROLE)) {
				return "Merchant";
			} else if (role.getRole().equalsIgnoreCase(SSConstants.INTERNAL_ADMIN_ROLE)) {
				return "Admin";
			} else if (role.getRole().equalsIgnoreCase(SSConstants.INTERNAL_MANAGER_ROLE)) {
				return "Manager";
			} else if (role.getRole().equalsIgnoreCase(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE)) {
				return "Employee";
			}
		}
		return null;
	}

	String getUUIDSHA(String username) {
		SSUser user = userDao.getUserByUserName(username);
		byte[] dg1 = null;
		long millis = 0;
		String text = null;
		
		if (user == null)
			return null;
		try {
			MessageDigest sha256digest = MessageDigest.getInstance("SHA-256");
			Random randomGenerator = new Random();
			
			millis = System.currentTimeMillis() % 1000;
			text = Long.toString(millis) + Integer.toString(randomGenerator.nextInt(1000000000)) + UUID.randomUUID().toString();
			String salt = user.getPassword() + text;
			sha256digest.update(salt.getBytes("UTF-8"));
			dg1 = sha256digest.digest();
			userDao.setSecrect(username, String.format("%064x", new java.math.BigInteger(1, dg1)));
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return text;
	}
}
