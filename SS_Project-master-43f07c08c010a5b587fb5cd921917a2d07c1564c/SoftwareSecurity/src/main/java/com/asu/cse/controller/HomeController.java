package com.asu.cse.controller;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Validator;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.asu.cse.SSConstants;
import com.asu.cse.dao.SSFundTransactionDao;
import com.asu.cse.dao.SSProfileTransactionDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSOTP;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSTransactionManagement;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserRole;
import com.asu.cse.security.HttpPostRequest;
import com.asu.cse.service.AccountService;
import com.asu.cse.service.SSProfileTransactionService;
import com.asu.cse.service.SSTransactionTypeService;
import com.asu.cse.service.SendEmailService;
import com.asu.cse.service.UserService;
import com.asu.cse.service.SSFundTransactionService;

/**
 * This will handle only unauth RESTapis.
 */
@Controller
public class HomeController extends AbstractHomeController {
	@Qualifier("signUpValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}

	@RequestMapping(value = { "/" }, method = RequestMethod.GET)
	public String welcomePage(Model model) {

		String role = getUserRole();
		if (role == null)
			return "redirect:login";
		if (resetPassword()) {
			return "redirect:editpassword";
		}
		SSUser user = getUserModel();
		if (user != null) {
			getUUIDSHA(user.getUserName());
		}
		if (role.equalsIgnoreCase(SSConstants.EXTERNAL_CUSTOMER_ROLE)) {
			return "redirect:CaccountDetails";
		} else if (role.equalsIgnoreCase(SSConstants.EXTERNAL_MERCHANT_ROLE)) {
			return "redirect:CaccountDetails";
		} else if (role.equalsIgnoreCase(SSConstants.INTERNAL_MANAGER_ROLE)) {
			return "redirect:manager";
		} else if (role.equalsIgnoreCase(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE)) {
			return "redirect:employee";
		} else if (role.equalsIgnoreCase(SSConstants.INTERNAL_ADMIN_ROLE)) {
			return "redirect:admin";
		}
		return "redirect:login";
	}

	

	@RequestMapping(value = "/aboutus", method = RequestMethod.GET)
	public String aboutus(Model model) {
		return "AboutUs";
	}


	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signupPage(@ModelAttribute("ProfileTransaction") SSProfileTransaction Transaction, Model model) {
		String role = getUserRole();
		model.addAttribute("role", role);
		model.addAttribute("postLink", "/cse/addUser");
		model.addAttribute("ProfileTransaction", new SSProfileTransaction());
		model.addAttribute("errorMessage", null);
		return "signupPage";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String signupCompletion(@RequestParam("g-recaptcha-response") String captchaResponse,
			@ModelAttribute("ProfileTransaction") SSProfileTransaction transaction, Model model) {
		model.addAttribute("errorMessage", null);
		try {
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {

				if (transaction == null) {
					return "signupPage";
				}
				String role = getUserRole();
				model.addAttribute("role", role);
				model.addAttribute("postLink", "/cse/addUser");

				if (transaction.getTransactionType().equals("custoumer")) {
					transaction.setTransactionType(SSConstants.CUSTOMER_SIGNUP_TRANSACTION);
				} else if (transaction.getTransactionType().equals("merchant")) {
					transaction.setTransactionType(SSConstants.MERCHANT_SIGNUP);
				}
				transaction.setStatus("OTP");
				transaction.setComment("");

				Integer transid = ssProfileTransactionService.addProfileTransaction(transaction);
				if (transid > 0) {
					model.addAttribute("email", transaction.getEmail());
					model.addAttribute("otpActionUrl", "/cse/validateSignUpOTP");
					sendEmail.sendEmailUsingUserEmail(transaction.getEmail());
					return "otp";
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
			} else {
				model.addAttribute("role", getUserRole());
				model.addAttribute("postLink", "/cse/addUser");
				model.addAttribute("ProfileTransaction", new SSProfileTransaction());
				model.addAttribute("errorMessage", "Invalid Captcha");
				return "signupPage";
			}

		} catch (Exception ex) {
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("postLink", "/cse/addUser");
			model.addAttribute("ProfileTransaction", new SSProfileTransaction());
			model.addAttribute("errorMessage", "Internal Error Contact Sys admin");
			return "siginupPage";
		}

	}

	@RequestMapping(value = "/validateSignUpOTP", method = RequestMethod.POST)
	public String valdateSignupOTP(@RequestParam("pin") String pin, @RequestParam("email") String email, Model model) {
		// First validate the opt pin
		if (email == null)
			return "redirect:/";
		try {

			if (!sendEmail.validatePin(email, Integer.parseInt(pin))) {
				model.addAttribute("email", email);
				model.addAttribute("otpActionUrl", "/cse/validateSignUpOTP");
				sendEmail.sendEmailUsingUserEmail(email);
				return "otp";
			}
		}

		catch (NumberFormatException e) {
			return "redirect:/";
		}

		SSProfileTransaction profile = ssProfileTransactionService.getProfileByEmail(email);
		if (profile == null) {
			model.addAttribute("email", email);
			model.addAttribute("otpActionUrl", "/cse/validateSignUpOTP");
			sendEmail.sendEmailUsingUserEmail(email);
			return "otp";
		}
		profile.setStatus("Pending");
		ssProfileTransactionService.updateProfileTransaction(profile);
		model.addAttribute("title", "Signup Success");
		model.addAttribute("message", "Welcome   " + profile.getUserName());
		model.addAttribute("Transid", profile.getProfileTransactionId());
		model.addAttribute("email", profile.getEmail());
		return "signUpSuccessPage";
	}

	@RequestMapping(value = "/regenerateSignUpOTP", method = RequestMethod.POST)
	public String regenerateSignupOTP(@RequestParam("email") String email, Model model) {

		if (email == null) {
			return "signupPage";
		}
		model.addAttribute("email", email);
		sendEmail.sendEmailUsingUserEmail(email);
		return "otp";
	}

	// Assuming a form will send email field
	@RequestMapping(value = "/getunauthuserotp", method = RequestMethod.POST)
	public Model unauthUserOTP(@RequestParam("email") String email, Model model) {
		// TODO: Validate email.
		sendEmail.sendEmailUsingUserEmail(email);
		return model;
	}

	// Assuming a form will send email field
	@RequestMapping(value = "/getusertoken", method = RequestMethod.POST)
	public String getUserToken(@RequestParam("userName") String username, Model model) {
		// TODO Validation
		if (userDao.userExists(username) == false) {
			model.addAttribute("title", "Incorrect User!! Re-Enter Your Username");
			model.addAttribute("show", "username");
			model.addAttribute("errorMessage", "Incorrect User!! Re-Enter Your Username");
			return "loginPage";
		}
		model.addAttribute("show", "password");
		model.addAttribute("token", getUUIDSHA(username));
		model.addAttribute("username", username);
		model.addAttribute("message", "Enter Your Password:");
		return "loginPage";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model) {
		model.addAttribute("title", "Login");
		model.addAttribute("show", "username");
		model.addAttribute("message", "Enter Your Username");
		return "loginPage";
	}

	@RequestMapping(value = "/loginafterfailure", method = RequestMethod.GET)
	public String loginPageAfterFailure(Model model) {
		model.addAttribute("title", "Login");
		model.addAttribute("show", "username");
		model.addAttribute("message", "Enter Your Username");
		model.addAttribute("errorMessage", "Session is Expired");
		return "loginPage";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotpassword(Model model) {
		model.addAttribute("title", "Forgot Password");
		model.addAttribute("errorMessage", "");
		model.addAttribute("display", true);
		return "ForgotPassword";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String forgotpasswordResponse(@RequestParam("g-recaptcha-response") String captchaResponse, Model model,
			@RequestParam("email") String email) {
		model.addAttribute("title", "forgot password");
		try {
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {

				if (email.isEmpty()) {
					model.addAttribute("errorMessage", "Please enter a valid email id");
					model.addAttribute("display", true);
					return "ForgotPassword";
				}
				if (!userDao.emailExists(email)) {
					model.addAttribute("errorMessage", "e-mail id does not match with any of our records.");
					model.addAttribute("display", true);
					return "ForgotPassword";
				}
				model.addAttribute("errorMessage", "Please check your e-mail for further instructions.");
				model.addAttribute("display", false);
				userService.forgotPassword(email);
				return "ForgotPassword";
			} else {
				model.addAttribute("title", "Forgot Password");
				model.addAttribute("errorMessage", "Invalid captcha");
				model.addAttribute("display", true);
				return "ForgotPassword";
			}

		} catch (Exception ex) {
			model.addAttribute("title", "Forgot Password");
			model.addAttribute("errorMessage", "INternal Error: contact your local pitch fork state bank branch");
			model.addAttribute("display", true);
			return "ForgotPassword";
		}
	}
}
