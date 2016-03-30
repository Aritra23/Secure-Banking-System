package com.asu.cse.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asu.cse.SSConstants;
import com.asu.cse.dao.SSFundTransactionDao;
import com.asu.cse.dao.SSProfileTransactionDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.dao.SSPIIDao;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSOTP;
import com.asu.cse.model.SSPII;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSTransactionManagement;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserPII;
import com.asu.cse.model.SSUserRole;
import com.asu.cse.security.HttpPostRequest;
import com.asu.cse.service.AccountService;
import com.asu.cse.service.SSProfileTransactionService;
import com.asu.cse.service.SSTransactionTypeService;
import com.asu.cse.service.SendEmailService;
import com.asu.cse.service.UserService;
import com.asu.cse.service.SSPIIService;
import com.asu.cse.service.SSFundTransactionService;

/**
 * This will handle only Admin APIs.
 */
@Controller
public class AdminController extends AbstractHomeController {
	// System Admin
    
    @Autowired
    SSPIIService piiService;
    @Autowired
    SSPIIDao piiDao;
    

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminUpPage(Model model) {
		model.addAttribute("TypeOf", 2);
		model.addAttribute("role", "Admin");
		model.addAttribute("error", true);
		model.addAttribute("PostRequest", "/cse/admin");
		model.addAttribute("recur", 1);
		List<SSProfileTransaction> TransactionList = ssProfileTransactionService
				.getPendingEditProfileTransactionForUser(getUserModel());
		if (TransactionList == null || TransactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "AinternalUserRequest";
		}	
		List<SSUser> UserList = userService.getAllUsersByEditProfileRequests(TransactionList);
		if (UserList ==null || TransactionList.size() != UserList.size()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "AinternalUserRequest";
		}
		model.addAttribute("transactionRecur", TransactionList);
		model.addAttribute("UserList", UserList);
		model.addAttribute("recur", UserList.size());
		return "AinternalUserRequest";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.POST)
	public String adminUpPageResponse(@RequestParam("tranId1") String transactionId,
			@RequestParam("BoolVal") Boolean ApprovalStatus, @RequestParam("userId1") Integer userId, Model model) {
		model.addAttribute("TypeOf", 2);
		model.addAttribute("title", "AdminHome");
		model.addAttribute("role", "Admin");
		model.addAttribute("error", true);
		model.addAttribute("PostRequest", "/cse/admin");
		if (transactionId == null || userId == null || ApprovalStatus == null) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "Invalid Response");
		}
		if (ApprovalStatus) {
			ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), true);
			SSProfileTransaction approvedTransaction = ssProfileTransactionDao.getProfileTransactionById(Integer.parseInt(transactionId));
			userService.editUserAfterApproval(userId, approvedTransaction);
			ssProfileTransactionService.editProfileMail(approvedTransaction.getEmail(), "Manager",true);
		} else {
			ssProfileTransactionDao.updateProfileTransaction(Integer.parseInt(transactionId), false);
			SSProfileTransaction approvedTransaction = ssProfileTransactionDao.getProfileTransactionById(Integer.parseInt(transactionId));
			ssProfileTransactionService.editProfileMail(approvedTransaction.getEmail(), "Manager",false);
		}
	
		List<SSProfileTransaction> TransactionList = ssProfileTransactionService.getPendingEditProfileTransactionForUser(getUserModel());
		if (TransactionList == null ||TransactionList.isEmpty()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "AinternalUserRequest";
		}
		List<SSUser> UserList = userService.getAllUsersByEditProfileRequests(TransactionList);
		if (UserList ==null ||TransactionList.size() != UserList.size()) {
			model.addAttribute("error", false);
			model.addAttribute("errorMessage", "No more pending requests");
			model.addAttribute("recur", 0);
			return "AinternalUserRequest";
		}
		model.addAttribute("transactionRecur", TransactionList);
		model.addAttribute("UserList", UserList);
		model.addAttribute("recur", UserList.size());
		return "AinternalUserRequest";
	}

	@RequestMapping(value = "/admin/internalsignup", method = RequestMethod.GET)
	public String internalUserSignup(@ModelAttribute("ProfileTransaction") SSProfileTransaction Transaction,
			Model model) {
		model.addAttribute("Profile Transaction", new SSProfileTransaction());
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("postLink", "/cse/admin/addInternalUser");
		model.addAttribute("hide","none");
		return "signupPage";
	}

	@RequestMapping(value = "/admin/addInternalUser", method = RequestMethod.POST)
	public String internalUserSignupCompletion(@RequestParam("g-recaptcha-response") String captchaResponse,@ModelAttribute("ProfileTransaction") SSProfileTransaction transaction,
			Model model) {
		// TODO: Validation of transaction and internal parameters. Check each
		model.addAttribute("errorMessage", null);
		model.addAttribute("Profile Transaction", new SSProfileTransaction());
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("postLink", "/cse/admin/addInternalUser");
		model.addAttribute("hide","none");
		String role = getUserRole();
		try{
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL,SSConstants.CAPTCHASECRET,captchaResponse);
			if (valid){
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("postLink", "/cse/admin/addInternalUser");
				model.addAttribute("hide","none");
				model.addAttribute("Profile Transaction", new SSProfileTransaction());	
				transaction.setStatus(SSConstants.APPROVED);
				int transid = ssProfileTransactionService.addInternalUser(transaction);
				if (transid >= 0) {
					return "redirect:/";
				} else if (transid == -2) {
					model.addAttribute("errorMessageUserName", "Username already Exists");
					model.addAttribute("display", "inline");
				} else if (transid == -3) {
					model.addAttribute("errorMessageEmail", "Email already Exists");
					model.addAttribute("display", "inline");
				} else if (transid == -4) {
					model.addAttribute("errorMessageUserName", "SSN already Exists");
					model.addAttribute("display", "inline");
				}
				return "signupPage";
			} else
			{

				model.addAttribute("role", getUserRoleName());
				model.addAttribute("postLink", "/cse/admin/addInternalUser");
				model.addAttribute("hide","none");
				model.addAttribute("Profile Transaction", new SSProfileTransaction());
				model.addAttribute("errorMessage", "Invalid Captcha");
				return "signupPage";
			}
					
		}
		catch(Exception ex){
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("postLink", "/cse/admin/addInternalUser");
			model.addAttribute("hide","none");
			model.addAttribute("Profile Transaction", new SSProfileTransaction());
			model.addAttribute("errorMessage", "Internal Error Contact Sys admin");
			return "siginupPage";
		}

	}

	@RequestMapping(value = "/admin/editinternalprofile", method = RequestMethod.GET)
	public String editInternalUserRequest(@ModelAttribute("ProfileTransaction") SSProfileTransaction transaction,
			Model model, String transactionId) {
		// TODO: Validation like above
		if (transaction.getTransactionType() == "internal_user") {
			transaction.setTransactionType(SSConstants.INTERNAL_EMPLOYEE_UPDATE);
		}

		transaction.setComment("");
		model.addAttribute("role", "Admin");

		Integer transid = ssProfileTransactionService.updateProfileTransaction(transaction);
		if (transid > 0) {
			model.addAttribute("email", transaction.getEmail());
			model.addAttribute("otpActionUrl", "/cse/validateSignUpOTP");
			sendEmail.sendEmailUsingUserEmail(transaction.getEmail());
			return "otp";
		} else if (transid == -2) {
			model.addAttribute("title", "failed to update Internal User Profile  ");
			model.addAttribute("message", "failed signup: Username already Exist");
			model.addAttribute("Transid", transid);
			model.addAttribute("email", "");
			return "signUpSuccessPage";
		} else if (transid == -3) {
			model.addAttribute("title", "failed signup");
			model.addAttribute("message", "failed signup: email already Exists");
			model.addAttribute("Transid", transid);
			model.addAttribute("email", "");
			return "signUpSuccessPage";
		} else {
			model.addAttribute("email", transaction.getEmail());
			model.addAttribute("otpActionUrl", "/cse/validateSignUpOTP");
			sendEmail.sendEmailUsingUserEmail(transaction.getEmail());
			return "otp";
		}
	}
	
	@RequestMapping(value = "/piiRequest", method = RequestMethod.GET)
	public String accessPiiRequestPage(Model model){
		model.addAttribute("role","Admin");
		model.addAttribute("error", false);
		model.addAttribute("errorMessage","");
		model.addAttribute("message", "PII request submitted");
		return "ApiiRequest";
	}
	
	@RequestMapping(value = "/piiRequest", method = RequestMethod.POST)
	public String accessPiiRequestPageresponse(Model model,@RequestParam("ssn") String ssn,@RequestParam("first_name") String firstName, @RequestParam("email") String email ){
		model.addAttribute("role","Admin");
		if (piiDao.nameExists(firstName) == false || piiDao.emailExists(email) == false || piiDao.ssnExists(ssn) == false) {
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","PII requests not authenticated.");
			return "ApiiRequest";
		}
		
		model.addAttribute("error", true);
		model.addAttribute("errorMessage","PII requests authenticated.");
		return "ApiiRequest";
		
		
	}
	// System Admin- access internal users account for view and modify
	@RequestMapping(value = "/accessIntUserAcc", method = RequestMethod.GET)
	public String accessIntUserAccPage(@ModelAttribute("user") Model model) {
		model.addAttribute("message", "Admin");
		model.addAttribute("role", "Admin");
		model.addAttribute("edit", "Modify");
		model.addAttribute("delete", "Remove");
		model.addAttribute("activeAccessIntUserAcc", "active");
		return "AaccessIntUserAcc";
	}

	// System Admin- system log
	@RequestMapping(value = "/sysLog", method = RequestMethod.GET)
	public String sysLogPage(Model model) {
		model.addAttribute("role", "Admin");
		return "AsysLog";
	}
	
	@RequestMapping(value = "/sysLog", method = RequestMethod.POST)
	public String sysLogPostPage(Model model) {
		SSUser user = getUserModel();
		userService.downloadLogs(user);
		return "AsysLog";
	}

	@RequestMapping(value = "/admin/sysLog", method = RequestMethod.GET)
	public String viewLogs(Model model, HttpServletRequest request, HttpServletResponse response,
			final RedirectAttributes attributes) {
		model.addAttribute("role", "Admin");
		File logFile = new File("${catalina.home}/logs/sysadmin.log");
		String contents = "";
		try {
			if (!logFile.exists()) {
				contents = "log file not found";
			} else {
				FileReader fr = new FileReader(logFile);
				BufferedReader br = new BufferedReader(fr);
				String line;
				while (true) {
					line = br.readLine();
					if (line == null) {
						break;
					}
					contents = contents + line;
				}
				br.close();
			}
		} catch (Exception e) {
			contents = "Error reading log file";
		}

		model.addAttribute("logContents", contents);
		model.addAttribute("contentView", "viewLog");
		return "AsysLog";
	}


	@RequestMapping(value = "/admin/useraccount", method = RequestMethod.GET)
	public String accessUsers(Model model) {
		model.addAttribute("message", "Admin");
		model.addAttribute("TypeOf", 1); // Do not remove
		model.addAttribute("role", getUserRoleName());
		List<SSUser> userList=userService.getUsersByRole(SSConstants.EXTERNAL_CUSTOMER_ROLE);
		model.addAttribute("UserList",userList);
		if(userList==null){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","No users Currently Registered");
		}else{
		model.addAttribute("error", false);
		model.addAttribute("errorMessage","");		
		}
		return "AShowUser";
	}
	@RequestMapping(value = "/admin/merchantaccount", method = RequestMethod.GET)
	public String accessMerchant(Model model) {
		model.addAttribute("message", "Admin");
		model.addAttribute("TypeOf", 2); // Do not remove
		model.addAttribute("role", getUserRoleName());
		List<SSUser> userList=userService.getUsersByRole(SSConstants.EXTERNAL_MERCHANT_ROLE);
		model.addAttribute("UserList",userList);
		if(userList==null){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","No users Currently Registered");
		}else{
		model.addAttribute("error", false);
		model.addAttribute("errorMessage","");		
		}
		return "AShowUser";
	}
	@RequestMapping(value = "/admin/employeeaccount", method = RequestMethod.GET)
	public String accessemployee(Model model) {
		model.addAttribute("message", "Admin");
		model.addAttribute("TypeOf", 3); // Do not remove
		model.addAttribute("role", getUserRoleName());
		List<SSUser> userList=userService.getUsersByRole(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE);
		model.addAttribute("UserList",userList);
		if(userList==null){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","No users Currently Registered");
		}else{
		model.addAttribute("error", false);
		model.addAttribute("errorMessage","");		
		}
		return "AShowUser";
	}
	
	@RequestMapping(value = "/admin/manageraccount", method = RequestMethod.GET)
	public String accessManager(Model model) {
		model.addAttribute("message", "Admin");
		model.addAttribute("TypeOf", 4); // Do not remove
		model.addAttribute("role", getUserRoleName());
		List<SSUser> userList= userService.getUsersByRole(SSConstants.INTERNAL_MANAGER_ROLE);
		model.addAttribute("UserList",userList);
		if(userList==null){
			model.addAttribute("error", true);
			model.addAttribute("errorMessage","No users Currently Registered");
		}else{
		model.addAttribute("error", false);
		model.addAttribute("errorMessage","");		
		}
		return "AShowUser";
	}
	
	//System admin- access internal users account for view and modify
		@RequestMapping(value = "/Auseraccounts", method = RequestMethod.GET)
		public String accessExUserAccPaSMaccessExUserAccge(Model model) {
			model.addAttribute("message", "Admin");
			model.addAttribute("TypeOf", 5); // Do not remove
			model.addAttribute("role", "Admin");
			SSUser user = new SSUser();
			ArrayList<SSUser> list= new ArrayList<SSUser>();
			String display = "display";
			if(list.isEmpty())
				display = null;
			list.add(user);
			model.addAttribute("UserProfiles", list);
			model.addAttribute("display",display);
			model.addAttribute("activeAccessExUserAcc", "active");
			return "SMaccessExUserAcc";
		}
}
