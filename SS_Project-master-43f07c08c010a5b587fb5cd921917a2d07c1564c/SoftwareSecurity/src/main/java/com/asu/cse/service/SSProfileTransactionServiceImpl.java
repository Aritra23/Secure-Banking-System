package com.asu.cse.service;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asu.cse.SSConstants;
import com.asu.cse.dao.SSProfileTransactionDao;
import com.asu.cse.dao.SSTransactionTypeDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserRole;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan(basePackages = { "com.asu.*" })
@PropertySource("classpath:config.properties")
public class SSProfileTransactionServiceImpl implements SSProfileTransactionService, Serializable {

	private static final long serialVersionUID = -1323466999567887649L;
	@Autowired
	private UserRoleDao userRoleDao;
	@Autowired
	private SSProfileTransactionDao sSProfileTransactionDao;
	@Autowired
	private SSTransactionTypeDao ssTransactionTypeDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private Environment env;
	@Autowired
	private SendEmailService sendEmail;

	public UserRoleDao getUserRoleDao() {
		return userRoleDao;
	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public SSTransactionTypeDao getSsTransactionTypeDao() {
		return ssTransactionTypeDao;
	}

	public void setSsTransactionTypeDao(SSTransactionTypeDao ssTransactionTypeDao) {
		this.ssTransactionTypeDao = ssTransactionTypeDao;
	}

	public SSProfileTransactionDao getSsProfileTransactionDao() {
		return sSProfileTransactionDao;
	}

	public void setSsProfileTransactionDao(SSProfileTransactionDao ssProfileTransactionDao) {
		this.sSProfileTransactionDao = ssProfileTransactionDao;
	}

	@Override
	public Integer addProfileTransaction(SSProfileTransaction profileTransaction) {
		String transactionType = profileTransaction.getTransactionType();
		String userName = profileTransaction.getUserName();

		if (userDao.userExists(userName))
			return -2;
		String email = profileTransaction.getEmail();
		if (userDao.emailExists(email))
			return -3;
		String ssn = profileTransaction.getSocial();
		if (userDao.ssnExists(ssn))
			return -4;

		SSProfileTransaction old = null;
		profileTransaction.setUserId(0);
		old = sSProfileTransactionDao.ssnExists(ssn);

		profileTransaction.setCountry("USA");
		String authorizationUserRole = ssTransactionTypeDao.getAuthorizationRoleForType(transactionType);
		profileTransaction.setAuthorizationUserRole(authorizationUserRole);
		java.util.Date date = new java.util.Date();
		profileTransaction.setTimestamp(new Timestamp(date.getTime()));

		if (old != null) {
			sSProfileTransactionDao.deleteProfileTransaction(old);
		}
		old = sSProfileTransactionDao.emailExists(email);
		if (old != null) {
			sSProfileTransactionDao.deleteProfileTransaction(old);
		}
		old = sSProfileTransactionDao.userExists(userName);
		if (old != null) {
			sSProfileTransactionDao.deleteProfileTransaction(old);
		}
		return sSProfileTransactionDao.addProfileTransaction(profileTransaction);
	}

	@Override
	public Integer updateProfileTransaction(SSProfileTransaction profileTransaction) {
		return sSProfileTransactionDao.updateProfileTransaction(profileTransaction);
	}

	// Delete external user profile - request will be submitted by external user
	// add transaction with transaction type ="CUST_PROFILE_DELETE" or
	// "MER_PROFILE_DELETE".
	@Override
	public Integer newDeleteProfileTransaction(SSUser user, String role) {

		SSProfileTransaction profiletransaction = new SSProfileTransaction();
		profiletransaction.setFirstname(user.getFirstName());
		profiletransaction.setLastname(user.getLastName());
		profiletransaction.setAddress(user.getAddress());
		profiletransaction.setCity(user.getCity());
		profiletransaction.setState(user.getState());
		profiletransaction.setZipcode(user.getZipcode());
		profiletransaction.setCountry(user.getCountry());
		profiletransaction.setPhoneNumber(user.getPhoneNumber());
		profiletransaction.setUserName(user.getUserName());
		profiletransaction.setUserId(user.getUserid());
		profiletransaction.setPassword(user.getPassword());
		profiletransaction.setSocial(user.getSocial());
		profiletransaction.setEmail(user.getEmail());
		profiletransaction.setStatus("Pending");
		profiletransaction.setComment("NULL");
		if (role.equals("EXT_CUSTOMER")) {
			profiletransaction.setTransactionType(SSConstants.CUSTOMER_PROFILE_DELETE_TRN);
		} else if (role.equals("EXT_MERCHANT")) {
			profiletransaction.setTransactionType(SSConstants.MERCHANT_PROFLE_DELETE_TRN);
		} else {
			profiletransaction.setTransactionType("error");
		}
		String authorizationUserRole = ssTransactionTypeDao
				.getAuthorizationRoleForType(profiletransaction.getTransactionType());
		if (authorizationUserRole == null || authorizationUserRole.isEmpty()) {
			return -1;
		}
		profiletransaction.setAuthorizationUserRole(authorizationUserRole);
		java.util.Date date = new java.util.Date();
		profiletransaction.setTimestamp(new Timestamp(date.getTime()));
		SSProfileTransaction verifyTransaction = sSProfileTransactionDao
				.getProfileTransactionByUserId(profiletransaction.getUserId());
		if (verifyTransaction != null) {
			sSProfileTransactionDao.updateProfileTransaction(verifyTransaction.getProfileTransactionId(), false);
		}
		return sSProfileTransactionDao.addProfileTransaction(profiletransaction);
	}

	// Manager should be able to retrieve all pending user profile delete
	// requests
	// with status = pending and transaction type = CUST_PROFILE_DELETE or
	// MER_PROFILE_DELETE
	public List<SSProfileTransaction> getDeleteProfileTrnRequests(SSUser user) {
		List<SSProfileTransaction> deleteprofileTransactions = null;
		String role = userRoleDao.getRole(user.getUserid()).getRole();
		deleteprofileTransactions = sSProfileTransactionDao.getProfileTransactionStatus(SSConstants.PENDING);

		// .getProfileTransactionByStatus(user,role,SSConstants.PENDING);
		// for (SSProfileTransaction profTrn : deleteprofileTransactions) {
		// if(!profTrn.getTransactionType().equals("CUST_PROFILE_DELETE") ||
		// !profTrn.getTransactionType().equals("MER_PROFILE_DELETE")) {
		// deleteprofileTransactions.remove(profTrn);
		// }
		// }

		Iterator<SSProfileTransaction> iter = deleteprofileTransactions.iterator();
		while (iter.hasNext()) {
			SSProfileTransaction profTrn = iter.next();
			if (!profTrn.getTransactionType().equals("CUST_PROFILE_DELETE")
					&& !profTrn.getTransactionType().equals("MER_PROFILE_DELETE")) {
				iter.remove();
			}
		}

		return deleteprofileTransactions;
	}

	@Override
	public List<SSProfileTransaction> getProfileTransactionStatus(String status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SSProfileTransaction> getAllProfileTransactions() {

		List<SSProfileTransaction> listProfileTransaction = sSProfileTransactionDao.getAllProfileTransactions();
		return listProfileTransaction;
	}

	@Override
	public SSProfileTransaction getProfileTransactionbyId(Integer TransactionId) {
		return sSProfileTransactionDao.getProfileTransactionById(TransactionId);
	}

	@Override
	public List<SSProfileTransaction> getPendingSignupTransactionForUser(SSUser user) {
		// Will be used by manager, system admin to show pending signup request
		// for
		// external customer, internal employee respectively
		List<SSProfileTransaction> signupTransactions = null;
		String role = userRoleDao.getRole(user.getUserid()).getRole();
		if (role.equals(SSConstants.INTERNAL_MANAGER_ROLE)) {
			// For pending transaction
			// Getting value from property file
			String pendingState = env.getProperty("pendingState");
			signupTransactions = sSProfileTransactionDao.getAllExternalSignupByStatus(pendingState);
		} else if (role.equals(SSConstants.INTERNAL_ADMIN_ROLE)) {
			// For internal employees
			String approvedState = env.getProperty("approvedState");
			signupTransactions = sSProfileTransactionDao.getAllInternalSignupByStatus(approvedState);
		}
		return signupTransactions;

	}

	@Override
	public List<SSProfileTransaction> getPendingProfileTransactionForUser(SSUser user) {
		List<SSProfileTransaction> profileTransactions = null;
		String role = userRoleDao.getRole(user.getUserid()).getRole();
		profileTransactions = sSProfileTransactionDao.getProfileTransactionByStatus(user, role, SSConstants.PENDING);
		return profileTransactions;
	}

	@Override
	public Integer createNewEditProfileTransaction(SSUser user, SSUser userOld, String role) {
		SSProfileTransaction profileTransaction = new SSProfileTransaction();
		profileTransaction.setFirstname(user.getFirstName());
		profileTransaction.setLastname(user.getLastName());
		profileTransaction.setAddress(user.getAddress());
		profileTransaction.setCity(user.getCity());
		profileTransaction.setCountry(user.getCountry());
		profileTransaction.setState(user.getState());
		profileTransaction.setPhoneNumber(user.getPhoneNumber());
		profileTransaction.setZipcode(user.getZipcode());
		profileTransaction.setEmail(userOld.getEmail());
		profileTransaction.setUserName(userOld.getUserName());
		profileTransaction.setUserId(userOld.getUserid());
		profileTransaction.setPassword(userOld.getPassword());
		profileTransaction.setSocial(userOld.getSocial());
		profileTransaction.setStatus("Pending");
		profileTransaction.setComment("NULL");
		if (role.equals("EXT_CUSTOMER")) {
			profileTransaction.setTransactionType(SSConstants.CUSTOMER_UPDATE_TRANSACTION);
		} else if (role.equals("EXT_MERCHANT")) {
			profileTransaction.setTransactionType(SSConstants.MERCHANT_UPDATE);
		} else if (role.equals("INT_REMP") || role.equals("INT_AEMP") || role.equals("INT_MEMP")) {
			profileTransaction.setTransactionType(SSConstants.INTERNAL_EMPLOYEE_UPDATE);
		} else {
			profileTransaction.setTransactionType("error");
		}
		String authorizationUserRole = ssTransactionTypeDao
				.getAuthorizationRoleForType(profileTransaction.getTransactionType());
		if (authorizationUserRole == null || authorizationUserRole.isEmpty()) {
			return -1;
		}
		profileTransaction.setAuthorizationUserRole(authorizationUserRole);
		java.util.Date date = new java.util.Date();
		profileTransaction.setTimestamp(new Timestamp(date.getTime()));
		SSProfileTransaction verifyTransaction = sSProfileTransactionDao
				.getProfileTransactionByUserId(profileTransaction.getUserId());
		if (verifyTransaction != null) {
			sSProfileTransactionDao.updateProfileTransaction(verifyTransaction.getProfileTransactionId(), false);
		}
		return sSProfileTransactionDao.addProfileTransaction(profileTransaction);
	}

	@Override
	public Integer editProfileTransactionCheck(Integer UserId) {
		SSProfileTransaction verifyTransaction = sSProfileTransactionDao.getProfileTransactionByUserId(UserId);
		if (verifyTransaction != null) {
			return 1;
		}
		return 0;
	}

	@Override
	public Boolean editProfileMail(String email, String approver, Boolean approved) {
		String text;
		String subject;
		if (approved) {
			text = "Your edit profile request has been approved by: " + approver
					+ "   Thank you for choosing Pitchfork State Bank";
			subject = "[Pitchfork State Bank] Edit Profile Request Approved";
		} else {
			text = "Your edit profile request has been denied by: " + approver
					+ "   Please contact your local branch for further details";
			subject = "[Pitchfork State Bank] Edit Profile Request Denied";
		}
		sendEmail.sendEmailBasic(email, subject, text);
		return true;
	}

	@Override
	public Boolean deleteProfileMail(String email, String approver, Boolean approved) {
		String text;
		String subject;
		if (approved) {
			text = "Your delete profile request has been approved by: " + approver
					+ "   Thank you for choosing Pitchfork State Bank";
			subject = "[Pitchfork State Bank] Delete Profile Request Approved";
		} else {
			text = "Your delete profile request has been denied by: " + approver
					+ "   Please contact your local branch for further details";
			subject = "[Pitchfork State Bank] Delete Profile Request Denied";
		}
		sendEmail.sendEmailBasic(email, subject, text);
		return true;
	}

	@Override
	public List<SSProfileTransaction> getPendingEditProfileTransactionForUser(SSUser user) {
		// Will be used by manager, to show pending edit request for
		// external customer/merchant
		// Internal user functionality still needs to be added.
		List<SSProfileTransaction> editTransactions = null;
		String role = userRoleDao.getRole(user.getUserid()).getRole();
		if (role.equals(SSConstants.INTERNAL_MANAGER_ROLE)) {
			// For pending transaction
			// Getting value from property file
			String pendingState = env.getProperty("pendingState");
			editTransactions = sSProfileTransactionDao.getAllExternalEditRequestsByStatus(pendingState);
		} else if (role.equals(SSConstants.INTERNAL_ADMIN_ROLE)) {
			// For internal employees.
			String pendingState = env.getProperty("pendingState");
			editTransactions = sSProfileTransactionDao.getAllInternalEditRequestsByStatus(pendingState);
		} else {
			return null;
		}
		return editTransactions;
	}

	@Override
	public SSProfileTransaction getProfileByEmail(String email) {
		return sSProfileTransactionDao.getProfileTransactionByEmail(email);
	}

	@Override
	public Integer addInternalUser(SSProfileTransaction transaction) {
		String roleString = null;
		if (userDao.userExists(transaction.getUserName()))
			return -2;
		if (userDao.emailExists(transaction.getEmail()))
			return -3;
		if (userDao.ssnExists(transaction.getSocial()))
			return -4;
		SSUser user = new SSUser();
		user.setFirstName(transaction.getFirstname());
		user.setLastName(transaction.getLastname());
		user.setUserName(transaction.getUserName());
		user.setPassword(transaction.getPassword());
		user.setAddress(transaction.getAddress());
		user.setCity(transaction.getCity());
		user.setState(transaction.getState());
		user.setZipcode(transaction.getZipcode().toString());
		user.setCountry("US");
		user.setEnabled(1);
		user.setEmail(transaction.getEmail());
		user.setPhoneNumber(transaction.getPhoneNumber());
		user.setSocial(transaction.getSocial());
		user.setForgotPasswordEnabled((byte) 1);
		SSUser newUser = new SSUser();
		newUser = userDao.addUser(user);
		SSUserRole ssUserRole = new SSUserRole();
		if (transaction.getTransactionType().equals("regular_employee")) {
			ssUserRole.setRole(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE);
			roleString = "Regular employee";
		} else if (transaction.getTransactionType().equals("manager")) {
			ssUserRole.setRole(SSConstants.INTERNAL_MANAGER_ROLE);
			roleString = "System Manager";
		}
		String text1 = " You are now authroized as " + roleString + " Your temporary password is "
				+ transaction.getPassword() + ". Your username will be sent in other mail.";
		String text2 = " Your username is :" + transaction.getUserName()
				+ ". Your password will be sent in other mail.";
		String subject = "[Pitchfork State Bank] " + roleString + " Registeration ";
		ssUserRole.setUserId(newUser.getUserid());
		userRoleDao.addUserRole(ssUserRole);
		sendEmail.sendEmailBasic(transaction.getEmail(), subject, text1);
		sendEmail.sendEmailBasic(transaction.getEmail(), subject, text2);
		return 0;
	}
}
