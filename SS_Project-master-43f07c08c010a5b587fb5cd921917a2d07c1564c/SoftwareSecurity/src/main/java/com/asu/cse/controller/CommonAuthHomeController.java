package com.asu.cse.controller;

import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asu.cse.SSConstants;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserRole;
import com.asu.cse.security.HttpPostRequest;

/**
 * Put all common API's which are authenticated
 */
@Controller
public class CommonAuthHomeController extends AbstractHomeController {


	@RequestMapping(value = "/logoutSuccessful", method = RequestMethod.GET)
	public String logoutSuccessfulPage(Model model) {
		model.addAttribute("title", "Logout Successful");
		// after logout, move back to login page.
		return "redirect:login";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		model.addAttribute("title", "Access Denied!");
		if (principal != null) {
			model.addAttribute("message",
					"Hi " + principal.getName() + "<br> You do not have permission to access this page!");
		} else {
			model.addAttribute("msg", "You do not have permission to access this page!");
		}
		return "403Page";
	}

	@RequestMapping(value = "/getotp", method = RequestMethod.GET)
	public String authUserOTP(Model model) {
		SSUser user = getUserModel();
		// TODO: Validation: user
		if (sendEmail.sendEmailUsingUserName(user.getUserName()))
			// TODO: Add otpstatus in jsp
			model.addAttribute("optstatus", "OPT Email Sent To " + user.getEmail());
		return "otp";
	}

	@RequestMapping(value = "/validateOTP", method = RequestMethod.POST)
	public String valdateUserOTP(@RequestParam("pin") String pin, Model model) {
		// TODO: Validation
		String roleName = getUserRole();
		// TODO: Validation
		SSUser user = getUserModel();
		// TODO: Validation
		if (sendEmail.validatePin(user.getEmail(), Integer.parseInt(pin))) {
			if (roleName.equalsIgnoreCase(SSConstants.EXTERNAL_CUSTOMER_ROLE)) {
				return "redirect:customer";
			} else if (roleName.equalsIgnoreCase(SSConstants.EXTERNAL_MERCHANT_ROLE)) {
				return "redirect:merchant";
			}
		}
		return "redirect:logout";
	}

	@RequestMapping(value = "/editprofile", method = RequestMethod.GET)
	public String editProfilePage(Model model) {

		// TODO: Validation
		model.addAttribute("user", getUserModel());
		model.addAttribute("PostLink", "/cse/editprofile");
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("RequestTrue",
				ssProfileTransactionService.editProfileTransactionCheck(getUserModel().getUserid()));
		model.addAttribute("title", "Update Profile");
		model.addAttribute("editPassword", false);
		model.addAttribute("errorMessage", "");
		return "editProfile";
	}

	@RequestMapping(value = "/editprofile", method = RequestMethod.POST)
	public String editProfilePost(@RequestParam("g-recaptcha-response") String captchaResponse,
			@ModelAttribute("user") @Validated SSUser user, BindingResult result, Model model) {
		// TODO: Validation of all input parameters
		SSUser OldUser = getUserModel();
		try {
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {
				model.addAttribute("editPassword", false);
				model.addAttribute("RequestTrue",
						ssProfileTransactionService.editProfileTransactionCheck(OldUser.getUserid()));
				model.addAttribute("PostLink", "/cse/editprofile");
				String role = getUserRole();
				model.addAttribute("user", OldUser);
				// TODO: Validation of role
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("title", "Update Profile");
				model.addAttribute("activeCeditProf", "active");
				model.addAttribute("errorMessage", "Error input please check");
				if (!result.hasErrors()) {
					// TODO Backend validation is not complete yet
					Integer transid = ssProfileTransactionService.createNewEditProfileTransaction(user, OldUser, role);
					model.addAttribute("errorMessage", "Update Request Submitted");
					user.setEmail(OldUser.getEmail());
					user.setUserName(OldUser.getUserName());
					model.addAttribute("user", user);
					if (transid < 0) {
						model.addAttribute("errorMessage", "Internal error: Please try again after a while");
						model.addAttribute("user", OldUser);
					} else {
						sendEmail.sendProfileApprovalRequest(
								ssProfileTransactionService.getProfileTransactionbyId(transid), OldUser);
					}

				}
				return "editProfile";
			} else {
				model.addAttribute("editPassword", false);
				model.addAttribute("RequestTrue",
						ssProfileTransactionService.editProfileTransactionCheck(OldUser.getUserid()));
				model.addAttribute("PostLink", "/cse/editprofile");
				model.addAttribute("user", OldUser);
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("title", "Update Profile");
				model.addAttribute("activeCeditProf", "active");
				model.addAttribute("errorMessage", "Invalid Captcha");
				return "editProfile";
			}

		} catch (Exception ex) {
			model.addAttribute("editPassword", false);
			model.addAttribute("RequestTrue",
					ssProfileTransactionService.editProfileTransactionCheck(OldUser.getUserid()));
			model.addAttribute("PostLink", "/cse/editprofile");
			model.addAttribute("user", OldUser);
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("title", "Update Profile");
			model.addAttribute("activeCeditProf", "active");
			model.addAttribute("errorMessage", "Internal error:- Please try again after a while");
			return "editProfile";
		}
	}
	
	@RequestMapping(value = "/deleteProfile", method = RequestMethod.GET)
	public String deleteProfileGet (Model model) {
		model.addAttribute("title", "Welcome to delete profile page");
		model.addAttribute("message", "Are you sure you want to delete your profile?");
		//model.addAttribute("user", getUserModel());
		model.addAttribute("activeCdeleteProf", "active");
		model.addAttribute("role", getUserRoleName());
		return "deleteProfile";
	}
	
	@RequestMapping(value = "/deleteProfile", method = RequestMethod.POST)
	public String deleteProfileRequest (Model model) {
		SSUser user = getUserModel();
		String role = getUserRole();
		Integer transid = ssProfileTransactionService.newDeleteProfileTransaction(user, role);
		//Set enabled bit to zero to prevent user from logging in after his request has been
		//submitted, and before it is approved by the manager.
		Integer enabled = 0;
		userService.setEnabledBitAfterDeleteProfileSubmission(user.getUserid(), enabled);
		if(!transid.equals(null)) {
			sendEmail.sendEmailBasic(getUserModel().getEmail(), "Account Deletion Request", 
				"Hi, Your request has been submitted and it is pending manager approval" );
			model.addAttribute("postMessage", "Your request has been submitted");
		}
		else{
			model.addAttribute("postMessage", "Error submitting request");
		}
		
		model.addAttribute("activeCdeleteProf", "active");
		model.addAttribute("role", getUserRoleName());
		return "redirect:/logout";
	}
	
	
	@RequestMapping(value = "/editpassword", method = RequestMethod.GET)
	public String editPasswordPage(Model model) {
		model.addAttribute("editPassword", true);
		model.addAttribute("PostLink", "/cse/editpassword");
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("title", "Update Profile");
		model.addAttribute("errorMessage", "");
		return "editProfile";
	}

	@RequestMapping(value = "/editpassword", method = RequestMethod.POST)
	public String editPasswordPost(@RequestParam("g-recaptcha-response") String captchaResponse,
			@RequestParam("password") String password, @RequestParam("newpassword") String newPassword,
			@RequestParam("confirmpassword") String changePassword, Model model) {
		try {
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {
				SSUser user = getUserModel();
				model.addAttribute("editPassword", true);
				model.addAttribute("PostLink", "/cse/editpassword");
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("title", "Update Profile");
				if (newPassword.length() < 8){
					model.addAttribute("errorMessage", "The password entered does not match the criteria");
					return "editProfile";
				}
				if (!newPassword.equals(changePassword)) {
					model.addAttribute("errorMessage", "Confirm Password not matched with new password!");
					return "editProfile";
				}
				// Is there a security flaw if I send password and check in
				// backend?
				// Right now for login, spring is handling it.
				if (password.equals(user.getPassword()) == false) {
					model.addAttribute("errorMessage", "Password does not match with the existing Passowrd!");
					return "editProfile";
				}
				if (password.equals(user.getPassword()) == true && newPassword.equals(changePassword)) {
					userService.updatePassword(user, newPassword);
				}

				return "redirect:/";
			} else {
				model.addAttribute("editPassword", true);
				model.addAttribute("PostLink", "/cse/editpassword");
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("title", "Update Profile");
				model.addAttribute("errorMessage", "error captcha ");
				return "editProfile";
			}

		} catch (Exception ex) {
			model.addAttribute("editPassword", true);
			model.addAttribute("PostLink", "/cse/editpassword");
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("title", "Update Profile");
			model.addAttribute("errorMessage", "Internal error: contact your local pitchfork state bank branch");
			return "editProfile";
		}
	}

}
