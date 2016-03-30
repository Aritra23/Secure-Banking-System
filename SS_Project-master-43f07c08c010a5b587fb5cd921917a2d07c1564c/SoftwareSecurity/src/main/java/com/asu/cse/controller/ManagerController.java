package com.asu.cse.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asu.cse.SSConstants;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserRole;
import com.asu.cse.service.AccountService;
import com.asu.cse.service.SSUserManagementService;

/**
 * This will handle only Manager APIs.
 */
@SuppressWarnings("unused")
@Controller
public class ManagerController extends AbstractHomeController {

	// Will be the landing page for manager
	@RequestMapping(value = "/manager", method = RequestMethod.GET)
	public String managerPage(Model model) {
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("error", true);
		model.addAttribute("TypeOf", 1);
		model.addAttribute("activeMUserApp", "active");
		SSUser user = getUserModel();
		List<String> userlist = userDao.getUserNamesByRole(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE);
		if (userlist != null)
			model.addAttribute("validatorNames", userlist);
		List<SSProfileTransaction> transactionList = ssProfileTransactionService
				.getPendingSignupTransactionForUser(user);
		if (transactionList == null || transactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No pending transactions!");
		}
		model.addAttribute("transactionRecur", transactionList);
		model.addAttribute("activeManager", "active");
		return "managerPage";
	}

	@RequestMapping(value = "/manager", method = RequestMethod.POST)
	public String managerPagResponse(@RequestParam("tranId1") String transactionId,
			@RequestParam("Validator") List<String> transactionValidator,
			@RequestParam("BoolVal") Boolean ApprovalStatus, Model model) {
		model.addAttribute("role", "Manager");
		Boolean result = true;
		model.addAttribute("TypeOf", 1);
		String message = " ";
		result = false;
		if (ApprovalStatus) {

			if ((transactionValidator == null || transactionValidator.isEmpty())) {
				result = false;
				message = "Invalid Validator";
			} else {
				transactionValidator.remove("e$$oR");
			}

			if ((transactionValidator == null || transactionValidator.isEmpty())) {
				result = false;
				message = "Invalid Validator";
			} else {
				SSProfileTransaction ssProfileTransaction = ssProfileTransactionDao
						.getProfileTransactionById(Integer.parseInt(transactionId));
				if (ssProfileTransaction != null) {
					ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), true);
					SSUser user = userService.addUserAfterSignUp(ssProfileTransaction);
					ssUserManagementService.addUserManagementList(transactionValidator, user.getUserid());
					SSUserRole custrole = new SSUserRole();
					custrole.setUserId(user.getUserid());
					if (ssProfileTransaction.getTransactionType().equals(SSConstants.CUSTOMER_SIGNUP_TRANSACTION))
						custrole.setRole(SSConstants.EXTERNAL_CUSTOMER_ROLE);
					else
						custrole.setRole(SSConstants.EXTERNAL_MERCHANT_ROLE);

					userRoleDao.addUserRole(custrole);
					sSPKIService.generateClientCertificate(user);

					if (ssProfileTransaction.getSavingsEnabled() == 0) {
						// This will create checking account
						accountService.addAccountsAfterSignup(ssProfileTransaction);
					} else {
						// This will create savings account
						accountService.addAccountsAfterSignup(ssProfileTransaction);
						// This will create checking account
						ssProfileTransaction.setSavingsEnabled((byte) 0);
						accountService.addAccountsAfterSignup(ssProfileTransaction);
					}
					result = false;
					message = "Request Approved";
				} else {
					result = false;
					message = "Invalid Transactionid";
				}
			}
		} else {
			SSProfileTransaction ssProfileTransaction = ssProfileTransactionDao
					.getProfileTransactionById(Integer.parseInt(transactionId));
			if (ssProfileTransaction != null) {
				ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), false);
				String text = "Sorry, your signup request has been rejected by the manager.";
				sendEmail.sendEmailBasic(ssProfileTransaction.getEmail(), "[PitchFork State Bank]Signup request", text);
				result = false;
				message = "Request Denied";
			}
		}
		model.addAttribute("error", result);
		model.addAttribute("errorMessage", message);
		SSUser user1 = getUserModel();
		model.addAttribute("validatorNames", userDao.getUserNamesByRole(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE));
		List<SSProfileTransaction> transactionList = ssProfileTransactionService
				.getPendingSignupTransactionForUser(user1);
		if (transactionList == null || transactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No pending transactions!");
		}
		model.addAttribute("transactionRecur", transactionList);
		return "managerPage";
	}

	@RequestMapping(value = "/manager/UPrequest", method = RequestMethod.GET)
	public String managerUPresponse(Model model) {
		model.addAttribute("role", "Manager");
		model.addAttribute("PostRequest", "/cse/manager/UPrequest");
		model.addAttribute("error", true);
		model.addAttribute("TypeOf", 2);
		List<SSProfileTransaction> TransactionList = ssProfileTransactionService
				.getPendingEditProfileTransactionForUser(getUserModel());
		if (TransactionList == null || TransactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "managerPage";
		}

		List<SSUser> UserList = userService.getAllUsersByEditProfileRequests(TransactionList);
		if (UserList == null || TransactionList.size() != UserList.size()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "Internal Error: Contact Sys admin");
			model.addAttribute("recur", 0);
			return "managerPage";
		}
		model.addAttribute("transactionRecur", TransactionList);
		model.addAttribute("UserList", UserList);
		model.addAttribute("recur", UserList.size());
		return "managerPage";
	}

	@RequestMapping(value = "/manager/UPrequest", method = RequestMethod.POST)
	public String managerUPResponse(@RequestParam("tranId1") String transactionId,
			@RequestParam("BoolVal") Boolean ApprovalStatus, @RequestParam("userId1") Integer userId, Model model) {
		// TODO: Send approval email to customer.
		// TODO: Validation of input parameters.
		model.addAttribute("role", "Manager");
		model.addAttribute("PostRequest", "/cse/manager/UPrequest");
		model.addAttribute("error", true);
		model.addAttribute("TypeOf", 2);
		if (transactionId == null || userId == null || ApprovalStatus == null) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "Invalid Response");
		}
		if (ApprovalStatus) {
			ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), true);
			SSProfileTransaction approvedTransaction = ssProfileTransactionDao
					.getProfileTransactionById(Integer.parseInt(transactionId));
			userService.editUserAfterApproval(userId, approvedTransaction);
			ssProfileTransactionService.editProfileMail(approvedTransaction.getEmail(), "Manager", true);
		} else {
			ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), false);
			SSProfileTransaction approvedTransaction = ssProfileTransactionDao
					.getProfileTransactionById(Integer.parseInt(transactionId));
			ssProfileTransactionService.editProfileMail(approvedTransaction.getEmail(), "Manager", false);
		}
		List<SSProfileTransaction> TransactionList = ssProfileTransactionService
				.getPendingEditProfileTransactionForUser(getUserModel());
		if (TransactionList == null || TransactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "managerPage";
		}
		List<SSUser> UserList = userService.getAllUsersByEditProfileRequests(TransactionList);
		if (UserList == null || TransactionList.size() != UserList.size()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "Internal Error: Contact Sys admin");
			model.addAttribute("recur", 0);
			return "managerPage";
		}
		model.addAttribute("transactionRecur", TransactionList);
		model.addAttribute("UserList", UserList);
		model.addAttribute("recur", UserList.size());
		return "managerPage";

	}

	@RequestMapping(value = "/manager/CTrequest", method = RequestMethod.GET)
	public String managerCTResponse(Model model) {
		model.addAttribute("transactions",
				ssFundTransactionService.getPendingFundTransaction(SSConstants.INTERNAL_MANAGER_ROLE));
		model.addAttribute("role", "Manager");
		model.addAttribute("error", true);
		model.addAttribute("TypeOf", 3);

		return "managerPage";
	}

	@RequestMapping(value = "/manager/CTrequest", method = RequestMethod.POST)
	public String managerCTResponse(@RequestParam("tranId1") Integer transactionId,
			@RequestParam("BoolVal") Boolean ApprovalStatus, Model model) {
		// TODO: Backend validation and success check

		try {
		SSFundTransaction updatedTransaction = ssFundTransactionService.getFundTransaction(transactionId);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		SSUser user = userDao.getUserByUserName(auth.getName());
		
		if (updatedTransaction.getAuthorizationRole().toString().equals("INT_MEMP")) {
		float amount = updatedTransaction.getAmount();
		String transactionType = updatedTransaction.getTransactionType();

		Account sourceAccount = accountService.getAccountByAccountId(updatedTransaction.getSourceAccount());
		float sourceUpdatedBalance = sourceAccount.getBalance();

		if (transactionType.equals(SSConstants.MERCHANT_REQUEST_MONEY) && ApprovalStatus) {
			updatedTransaction.setAuthorizationRole(userRoleDao
					.getRole(accountService.getAccountByAccountId(updatedTransaction.getSourceAccount()).getUserid())
					.getRole());
			updatedTransaction.setComment("Approved by " + user.getFirstName());
		} else if (ApprovalStatus) {
			if (transactionType.equals(SSConstants.CUSTOMER_DEBIT_TRANSACTION)) {
				sourceUpdatedBalance -= amount;
			} else if (transactionType.equals(SSConstants.CUSTOMER_CREDIT_TRANSACTION)) {
				sourceUpdatedBalance += amount;
			} else if (transactionType.equals(SSConstants.CUSTOMER_TRANSFER_TRANSACTION)) {
				Account destinationAccount = accountService
						.getAccountByAccountId(updatedTransaction.getDestinationAccount());
				sourceUpdatedBalance -= amount;
				destinationAccount.setBalance(destinationAccount.getBalance() + amount);
				destinationAccount.setPendingbalance(
						ssFundTransactionService.computePendingBalance(destinationAccount.getAccountid()));
				accountService.updateAccountByAccountId(destinationAccount, updatedTransaction.getDestinationAccount());
			}
			sourceAccount.setBalance(sourceUpdatedBalance);
			sourceAccount
					.setPendingbalance(ssFundTransactionService.computePendingBalance(sourceAccount.getAccountid()));
			accountService.updateAccountByAccountId(sourceAccount, updatedTransaction.getSourceAccount());
			updatedTransaction.setStatus(SSConstants.APPROVED);
			updatedTransaction.setComment("Approved by " + auth.getName());
		} else {
			updatedTransaction.setStatus(SSConstants.REJECTED);
			updatedTransaction.setComment("Rejected by " + auth.getName());
		}
		}
		// Save the updated transaction information to the database
		ssFundTransactionService.updateFundTransaction(updatedTransaction, transactionId);

		model.addAttribute("transactions",
				ssFundTransactionService.getPendingFundTransaction(SSConstants.INTERNAL_MANAGER_ROLE));
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("error", true);
		model.addAttribute("TypeOf", 3);
		return "managerPage";
		} catch (Exception e) {
			return "redirect:/";
		}
	}

	// Display pending delete profile transaction in manager page:
	@RequestMapping(value = "/manager/deleteProfileRequest", method = RequestMethod.GET)
	public String managerDPresponse(Model model) {
		model.addAttribute("role", "Manager");
		model.addAttribute("PostRequest", "/cse/manager/deleteProfileRequest");
		model.addAttribute("error", true);
		model.addAttribute("TypeOf", 4);
		List<SSProfileTransaction> TransactionList = ssProfileTransactionService
				.getDeleteProfileTrnRequests(getUserModel());
		if (TransactionList == null || TransactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "managerPage";
		}

		List<SSUser> UserList = userService.getAllUsersByEditProfileRequests(TransactionList);
		if (UserList == null || TransactionList.size() != UserList.size()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "Internal Error: Contact Sys admin");
			model.addAttribute("recur", 0);
			return "managerPage";
		}
		model.addAttribute("transactionRecur", TransactionList);
		model.addAttribute("UserList", UserList);
		model.addAttribute("recur", UserList.size());
		return "managerPage";
	}

	@RequestMapping(value = "/manager/deleteProfileRequest", method = RequestMethod.POST)
	public String accessExUserAccDelete(@RequestParam("tranId1") String transactionId,
			@RequestParam("BoolVal") Boolean ApprovalStatus, @RequestParam("userId1") Integer userId, Model model) {
		try {
			model.addAttribute("role", "Manager");
			model.addAttribute("PostRequest", "/cse/manager/deleteProfileRequest");
			model.addAttribute("error", true);
			model.addAttribute("TypeOf", 4);
			SSProfileTransaction trandao = ssProfileTransactionDao
					.getProfileTransactionById(Integer.parseInt(transactionId));
			if (trandao == null){
				model.addAttribute("error", false);
				model.addAttribute("errorMessage", "Invalid Request");	
			}
			SSUser user = userDao.getUser(trandao.getUserId());
			if (transactionId == null || ApprovalStatus == null || user == null) {
				model.addAttribute("error", false);
				model.addAttribute("errorMessage", "Invalid Request");	
			}
			if (trandao !=null && user != null && (trandao.getTransactionType().toString().equalsIgnoreCase("CUST_PROFILE_DELETE")
					|| trandao.getTransactionType().toString().equalsIgnoreCase("MER_PROFILE_DELETE"))) {
				System.out.println("sdds");
				if (ApprovalStatus) {
					ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), true);
					SSProfileTransaction approvedTransaction = ssProfileTransactionDao
							.getProfileTransactionById(Integer.parseInt(transactionId));
					// userService.editUserAfterApproval(userId,
					// approvedTransaction);

					List<Account> userAccounts = accountService.getUserAccount(user.getUserid());
					for (Account userAcc : userAccounts) {
						accountService.deleteAccount(userAcc.getAccountid());
					}

					userService.deleteUser(user.getUserid());
					ssProfileTransactionService.deleteProfileMail(approvedTransaction.getEmail(), "Manager", true);

				} else {
					ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), false);
					SSProfileTransaction approvedTransaction = ssProfileTransactionDao
							.getProfileTransactionById(Integer.parseInt(transactionId));
					ssProfileTransactionService.deleteProfileMail(approvedTransaction.getEmail(), "Manager", false);
					userService.setEnabledBitAfterDeleteProfileSubmission(user.getUserid(), 1); // set
																								// enabled
																								// bit
					// to 1 if manager denies delete profile request
				}
				List<SSProfileTransaction> TransactionList = ssProfileTransactionService
						.getDeleteProfileTrnRequests(getUserModel());
				if (TransactionList == null || TransactionList.isEmpty()) {
					model.addAttribute("error", false);
					model.addAttribute("errorMessage", "No more pending requests");
					model.addAttribute("recur", 0);
					return "managerPage";
				}
				List<SSUser> UserList = userService.getAllUsersByEditProfileRequests(TransactionList);
				if (UserList == null || TransactionList.size() != UserList.size()) {
					model.addAttribute("error", false);
					model.addAttribute("errorMessage", "Internal Error: Contact Sys admin");
					model.addAttribute("recur", 0);
					return "managerPage";
				}

				model.addAttribute("transactionRecur", TransactionList);
				model.addAttribute("UserList", UserList);
				model.addAttribute("recur", UserList.size());
			} else {
				model.addAttribute("error", false);
				model.addAttribute("errorMessage", "Invalid Request");
				return "redirect:/";
			}
		} catch (Exception e) {
			return "redirect:/";
		}

		return "managerPage";
	}

	// edit and update profile for Manager
	@RequestMapping(value = "/manager/updateuserprofile", method = RequestMethod.GET)
	public String updateUserProfilePage(Model model) {
		model.addAttribute("TypeOf", 4); // Do not remove
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("title", "Update Profile");
		model.addAttribute("firstName", "Keyul");
		model.addAttribute("lastName", "Keyul");
		model.addAttribute("telephone", "1234567890");
		model.addAttribute("password", "Keyul");
		model.addAttribute("address", "Keyul");
		model.addAttribute("city", "Keyul");
		model.addAttribute("zipcode", "Keyul");
		model.addAttribute("state", "Keyul");
		model.addAttribute("country", "Keyul");
		model.addAttribute("email", "Keyul");
		model.addAttribute("activeUpdateProfile", "active");
		return "managerPage";
	}

	// System manager- access external users account for view and modify
	@RequestMapping(value = "/useraccounts", method = RequestMethod.GET)
	public String accessExUserAccPaSMaccessExUserAccge(Model model) {
		model.addAttribute("TypeOf", 5); // Do not remove
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("activeMuserAcc", "active");

		SSUser user = new SSUser();
		ArrayList<SSUser> list = new ArrayList<SSUser>();
		list.add(user);

		model.addAttribute("UserProfiles", list);
		model.addAttribute("activeAccessExUserAcc", "active");
		model.addAttribute("user", user);
		return "SMaccessExUserAcc";
	}

	// System manager- access external users account for view and modify
	@RequestMapping(value = "/useraccounts", method = RequestMethod.POST)
	public String accessExUserAccPaSMaccessExUserAccgeSearch(HttpSession session,
			@ModelAttribute("user") @Validated SSUser user, Model model) {
		// @RequestParam("searchString") String userNameSearch,
		model.addAttribute("TypeOf", 5); // Do not remove
		model.addAttribute("role", getUserRoleName());

		if (StringUtils.isBlank(user.getUserName()) || StringUtils.isEmpty(user.getUserName())
				|| (Pattern.matches("[a-zA-Z0-9_]+", user.getUserName()) == false)) {
			model.addAttribute("UserProfilesError", "Invalid Search string - please try again");
		} else {
			session.setAttribute("searchString", user.getUserName());

			List<SSUser> list = userService.getAllUsersByUserName(user.getUserName());
			if (list.isEmpty()) {
				model.addAttribute("UserProfilesError", "There are no registered users");
			}
			// Delete the user that was selected in the page:
			// userService.deleteUser(user.getUserid());
			// model.addAttribute("userNameSearch", "");
			else {
				model.addAttribute("UserProfiles", list);
				model.addAttribute("activeAccessExUserAcc", "active");
				model.addAttribute("user", user);
			}
		}

		return "SMaccessExUserAcc";
	}

	// System manager- access transaction
	@RequestMapping(value = "/manager/accesstransaction", method = RequestMethod.GET)
	public String AccessTransactionPage(Model model) {
		model.addAttribute("TypeOf", 6); // Do not remove
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("userName", "mark");
		model.addAttribute("customerName", "Mark zukerberg");
		model.addAttribute("view", "view");
		model.addAttribute("status", "get Access");
		model.addAttribute("transactionId", "876543210");
		model.addAttribute("activeAccessTransaction", "active");
		return "managerPage";
	}

	// Manager - Search External Users
	// @RequestMapping(value= "/manager/useraccounts", method =
	// RequestMethod.GET)
	// public String SearchExtUsers(Model model){
	//
	//
	//
	// return "managerPage";
	// }
}
