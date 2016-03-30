package com.asu.cse.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
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
import org.springframework.web.multipart.MultipartFile;

import com.asu.cse.SSConstants;
import com.asu.cse.model.Account;
import com.asu.cse.model.FileUpload;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.security.HttpPostRequest;
import com.asu.cse.service.SSFundTransactionService;

/**
 * This will handle only User APIs.
 */
@Controller
public class UserController extends AbstractHomeController {

	@Qualifier("FileUploadValidator")
	private Validator validator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(validator);
	}
	
	@RequestMapping(value = "/authorize", method = RequestMethod.GET)
	public String customerAuthorizePage(Model model) {
		model.addAttribute("title", getUserRoleName());
		model.addAttribute("role", getUserRoleName());

		List<Account> userAccounts = accountService.getUserAccount(getUserModel().getUserid());
		List<SSFundTransaction> pendingTransactions = new ArrayList<SSFundTransaction>();

		for (Account userAccount : userAccounts) {
			pendingTransactions.addAll(ssFundTransactionService.getPendingTransferFundTransactionByAuthAndSourceAccount(
					getUserRole(), userAccount.getAccountid()));
		}

		model.addAttribute("pendingTransactions", pendingTransactions);
		return "Cauthorize";
	}

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String customerAuthorizeResponse(@RequestParam("updatedTransactionId") Integer updatedTransactionId,
			@RequestParam("BoolVal") boolean approve, Model model) {

		SSFundTransaction updatedTransaction = ssFundTransactionService.getFundTransaction(updatedTransactionId);
		SSUser user = getUserModel();
		if (!(updatedTransaction.getAuthorizationRole().equals("EXT_CUSTOMER") || updatedTransaction.getAuthorizationRole().equals("EXT_MERCHANT"))) {
			return "redirect:/";
		}
		if (approve) {

			float amount = updatedTransaction.getAmount();

			updatedTransaction.setStatus(SSConstants.APPROVED);
			updatedTransaction.setComment("Approved by " + user.getFirstName());
			ssFundTransactionService.updateFundTransaction(updatedTransaction, updatedTransactionId);

			Account sourceAccount = accountService.getAccountByAccountId(updatedTransaction.getSourceAccount());
			sourceAccount.setBalance(sourceAccount.getBalance() - amount);
			sourceAccount
					.setPendingbalance(ssFundTransactionService.computePendingBalance(sourceAccount.getAccountid()));
			accountService.updateAccountByAccountId(sourceAccount, updatedTransaction.getSourceAccount());

			Account destinationAccount = accountService
					.getAccountByAccountId(updatedTransaction.getDestinationAccount());
			destinationAccount.setBalance(destinationAccount.getBalance() + amount);
			destinationAccount.setPendingbalance(
					ssFundTransactionService.computePendingBalance(destinationAccount.getAccountid()));
			accountService.updateAccountByAccountId(destinationAccount, updatedTransaction.getDestinationAccount());

		} else {
			updatedTransaction.setStatus(SSConstants.REJECTED);
			updatedTransaction.setComment("Rejected by " + user.getFirstName());
			ssFundTransactionService.updateFundTransaction(updatedTransaction, updatedTransactionId);
		}

		model.addAttribute("title", getUserRoleName());
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("pendingTransactions",
				ssFundTransactionService.getPendingFundTransaction(SSConstants.EXTERNAL_CUSTOMER_ROLE));

		return "Cauthorize";
	}

	private void getAccountDetails(Model model) {
		SSUser user = getUserModel();
		String roleName = getUserRole();
		if (roleName.equalsIgnoreCase(SSConstants.EXTERNAL_CUSTOMER_ROLE)
				|| roleName.equalsIgnoreCase(SSConstants.EXTERNAL_MERCHANT_ROLE)) {
			Account accountC = accountService.getCheckingAccount(user.getUserid());
			if (accountC != null) {
				model.addAttribute("CAccountNo", accountC.getAccountid());
				model.addAttribute("CheckingBalance", "$" + accountC.getBalance());
				model.addAttribute("CpendingBalance", "$" + (accountC.getPendingbalance() == 0 ? 0
						: accountC.getPendingbalance() - accountC.getBalance()));
				model.addAttribute("CAccountType", "Checking");
				model.addAttribute("CVal", "1");

			}
			Account accountS = accountService.getSavingAccount(user.getUserid());
			if (accountS != null) {
				model.addAttribute("SAccountNo", accountS.getAccountid());
				model.addAttribute("SpendingBalance", "$" + (accountS.getPendingbalance() == 0 ? 0
						: accountS.getPendingbalance() - accountS.getBalance()));
				model.addAttribute("SavingsBalance", "$" + accountS.getBalance());
				model.addAttribute("SAccountType", "Saving");
				model.addAttribute("SVal", "2");
			}
		}
		model.addAttribute("userName", user.getUserName());
	}

	@RequestMapping(value = "/CaccountDetails", method = RequestMethod.GET)
	public String accountDetailsPageC(Model model) {
		getAccountDetails(model);
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("customerName", "Mark zukerberg");
		model.addAttribute("Approve", "approve");
		model.addAttribute("fieldName", "Last Name");
		model.addAttribute("oldValue", "zukerberg");
		model.addAttribute("updatedValue", "Modi");
		model.addAttribute("activeAccDetail", "active");
		return "CaccountDetails";
	}

	@RequestMapping(value = "/CrecentTransaction", method = RequestMethod.GET)
	public String recentTransactionPageC(Model model) {
		model.addAttribute("title", getUserRoleName());
		model.addAttribute("role", getUserRoleName());
		Integer userId = getUserModel().getUserid();
		List<SSFundTransaction> list = ssFundTransactionService.recentFundTransactions(userId);
		model.addAttribute("FundTransactions", list);
		model.addAttribute("activeCrecentTrans", "active");
		return "CrecentTransaction";
	}

	@RequestMapping(value = "/CrecentTransaction", method = RequestMethod.POST)
	public String recentTransactionPagePost(@RequestParam("month") String month, Model model) {
		model.addAttribute("title", getUserRoleName());
		model.addAttribute("role", getUserRoleName());
		Integer userId = getUserModel().getUserid();
		List<SSFundTransaction> list = ssFundTransactionService.recentFundTransactions(userId);
		model.addAttribute("FundTransactions", list);
		model.addAttribute("activeCrecentTrans", "active");
		userService.emailStatement(userId, month + "-2015");
		return "CrecentTransaction";
	}

	// This page only has static links to redirect to transaction pages.
	@RequestMapping(value = "/CnewTransaction", method = RequestMethod.GET)
	public String newFundTransaction(Model model) {
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("activeCnewTrans", "active");
		// Reverse Hack, if Customer, then show merchant request link
		if (getUserRoleName().equalsIgnoreCase("Customer"))
			model.addAttribute("showReqLink", "showReqLink");
		return "CnewTransaction";
	}

	// Only for page render
	@RequestMapping(value = "/CdebitOrCreditTransaction", method = RequestMethod.GET)
	public String newDebitCreditTrnPgRender(@ModelAttribute("FileUpload") FileUpload Transaction,
			Model model) {
		getAccountDetails(model);
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("activeCnewTrans", "active");
		return "CDebitCreditTransactionPage";
	}

	// WIP - Need to validate the page - the page should show only the accounts
	// the user has
	@RequestMapping(value = "/CdebitOrCreditTransaction", method = RequestMethod.POST)
	public String newCustDebitCreditTrn(@RequestParam("g-recaptcha-response") String captchaResponse,
			@ModelAttribute("FileUpload") FileUpload fileUpload, Model model) {
		SSFundTransaction transaction = ssFundTransactionService.convertFileUploadtoSSFundTransaction(fileUpload);
		if(transaction==null || (fileUpload.getTransactionType()==null)){
			model.addAttribute("transId", 0);
			model.addAttribute("message1", "Invalid Input");
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("activeCnewTrans", "active");
			getAccountDetails(model);
			return "CDebitCreditTransactionPage";
		}
		transaction.setTransactionType(fileUpload.getTransactionType());
		try {
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {
				MultipartFile multipartFile = (MultipartFile) fileUpload.getFile();
				if (!validateCertificate(multipartFile)) {
					getAccountDetails(model);
					model.addAttribute("transId", transaction.getFundTransactionId());
					model.addAttribute("role", getUserRoleName());
					model.addAttribute("message1", "Please Upload Correct Certificate File");
					model.addAttribute("activeCnewTrans", "active");
					return "CDebitCreditTransactionPage";
				}
				model.addAttribute("message", "Welcome to Secure Debit/Credit transaction page");
				model.addAttribute("role", getUserRoleName());
				Integer userid = getUserModel().getUserid();
				// Retrieve checking and savings account - Needed for Debit
				// transaction's source account
				// and credit transaction's destination account
				Account account = new Account();
				transaction.setUserId(userid);

				transaction.setDestinationAccount(00000000000);
				if (transaction.getSourceAccount().equals(1)) {
					if (accountService.getCheckingAccount(userid) != null) {
						account = accountService.getCheckingAccount(userid);
						transaction.setSourceAccount(account.getAccountid());
					}
				} else {
					if (accountService.getSavingAccount(userid) != null) {
						account = accountService.getSavingAccount(userid);
						transaction.setSourceAccount(account.getAccountid());
					}
				}

				if (transaction.getTransactionType().equals("Debit")) {
					transaction.setTransactionType(SSConstants.CUSTOMER_DEBIT_TRANSACTION);
				} else if (transaction.getTransactionType().equals("Credit")) {
					transaction.setTransactionType(SSConstants.CUSTOMER_CREDIT_TRANSACTION);
				}

				transaction.setStatus(SSConstants.PENDING);
				ssFundTransactionService.addFundTransaction(transaction);

				float pendingBalance = ssFundTransactionService.computePendingBalance(account.getAccountid());

				if (pendingBalance < 0) {
					transaction.setStatus(SSConstants.REJECTED);
					transaction.setComment("Auto-rejected due to insufficient funds");
					ssFundTransactionService.updateFundTransaction(transaction, transaction.getFundTransactionId());
					model.addAttribute("transId", transaction.getFundTransactionId());
					model.addAttribute("message1", "Transaction not successful");
					sendEmail.sendInsufficientBalance(transaction, getUserModel());
				} else {
					account.setPendingbalance(pendingBalance);
					accountService.updateAccountByAccountId(account, account.getAccountid());
					model.addAttribute("transId", transaction.getFundTransactionId());
					model.addAttribute("message1", "Transaction submitted successfully");
					sendEmail.sendFundTranferRequest(transaction, getUserModel());
				}
				return "SMsubmitSuccess";
			} else {
				model.addAttribute("transId", transaction.getFundTransactionId());
				model.addAttribute("message1", "Invalid Captcha");
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("activeCnewTrans", "active");
				getAccountDetails(model);
				return "CDebitCreditTransactionPage";
			}

		} catch (Exception ex) {
			model.addAttribute("transId", transaction.getFundTransactionId());
			model.addAttribute("message1", "Internal error contact sys admin");
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("activeCnewTrans", "active");
			getAccountDetails(model);
			return "CDebitCreditTransactionPage";
		}
	}

	public boolean validateCertificate(MultipartFile multipartFile) {
		InputStream fileName;
		try {
			if (multipartFile != null && multipartFile.getSize() > 0 && multipartFile.getSize() < 20000) {
				fileName = multipartFile.getInputStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(fileName));
				String certificate = "";
				String line = "";
				while ((line = br.readLine()) != null) {
					certificate += line;
					certificate += "\n";
				}
				if (sSPKIService.verifyClientCertificate(getUserModel(), certificate)) {
					return true;
				}
			}
		} catch (Exception e) {
		}
		return false;
	}

	@RequestMapping(value = "/CfundTransfer", method = RequestMethod.POST)
	public String newCustFundTransfer(@RequestParam("g-recaptcha-response") String captchaResponse,
			@ModelAttribute("FileUpload") FileUpload fileUpload, Model model) throws IOException {
		try
		{
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {
				MultipartFile multipartFile = (MultipartFile) fileUpload.getFile();
				SSFundTransaction transaction = ssFundTransactionService.convertFileUploadtoSSFundTransaction(fileUpload);
				if(fileUpload.getDestinationAccount()==null || transaction==null){
					getAccountDetails(model);
					model.addAttribute("role", getUserRoleName());
					model.addAttribute("message1", "Invalid Input");
					return "CFundTransactionPage";
				}
				transaction.setDestinationAccount(fileUpload.getDestinationAccount());
				if (!validateCertificate(multipartFile)) {
					getAccountDetails(model);
					model.addAttribute("role", getUserRoleName());
					model.addAttribute("message1", "Please Upload Correct Certificate File");
					return "CFundTransactionPage";
				}
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("title", "Welcome to Secure Transfer Funds page");
				Integer userid = getUserModel().getUserid();

				if (accountService.getAccountByAccountId(transaction.getDestinationAccount()) != null) {

					Account sourceAccount = new Account();
					Account destinationAccount = accountService
							.getAccountByAccountId(transaction.getDestinationAccount());

					// Set source account
					if (transaction.getSourceAccount().equals(1)) {
						if (accountService.getCheckingAccount(userid) != null) {
							sourceAccount = accountService.getCheckingAccount(userid);
							transaction.setSourceAccount(sourceAccount.getAccountid());
						}
					} else {
						if (accountService.getSavingAccount(userid) != null) {
							sourceAccount = accountService.getSavingAccount(userid);
							transaction.setSourceAccount(sourceAccount.getAccountid());
						}
					}

					if (sourceAccount.getAccountid().equals(destinationAccount.getAccountid())) {
						model.addAttribute("message1", "'To' account number cannot be same as that of 'From'");
					} else {

						transaction.setUserId(userid);
						transaction.setTransactionType(SSConstants.CUSTOMER_TRANSFER_TRANSACTION);

						transaction.setAuthorizationRole(ssTransactionTypeService
								.getAuthorizationRoleForType(SSConstants.CUSTOMER_TRANSFER_TRANSACTION));
						transaction.setStatus(SSConstants.PENDING);
						ssFundTransactionService.addFundTransaction(transaction);

						float sourcePendingBalance = ssFundTransactionService
								.computePendingBalance(sourceAccount.getAccountid());

						if (sourcePendingBalance < 0) {
							transaction.setStatus(SSConstants.REJECTED);
							transaction.setComment("Auto-rejected due to insufficient funds");
							ssFundTransactionService.updateFundTransaction(transaction,
									transaction.getFundTransactionId());
							model.addAttribute("message1", "Insufficient funds in source account");
							sendEmail.sendFundTranferRequest(transaction, getUserModel());
						} else {
							sourceAccount.setPendingbalance(
									ssFundTransactionService.computePendingBalance(sourceAccount.getAccountid()));
							accountService.updateAccountByAccountId(sourceAccount, sourceAccount.getAccountid());

							destinationAccount.setPendingbalance(
									ssFundTransactionService.computePendingBalance(destinationAccount.getAccountid()));
							accountService.updateAccountByAccountId(destinationAccount,
									destinationAccount.getAccountid());

							model.addAttribute("transId", transaction.getFundTransactionId());
							model.addAttribute("message1", "Transaction submitted successfully");
						}
					}
				} else {
					model.addAttribute("message1", "'To' account number is invalid");
				}

				sendEmail.sendFundTranferRequest(transaction, getUserModel());
				return "SMsubmitSuccess";
			} else {
				getAccountDetails(model);
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("message1", "error Capctha");
				return "CFundTransactionPage";
			}

		} catch (

		Exception ex)

		{
			getAccountDetails(model);
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("message1", "INternal error: contact sys admin");
			return "CFundTransactionPage";
		}

	}

	// External customer - transfer funds
	@RequestMapping(value = "/CfundTransfer", method = RequestMethod.GET)
	public String customerFundTransactionPgRender(@ModelAttribute("FileUpload") FileUpload Transaction, Model model) {
		getAccountDetails(model);
		model.addAttribute("role", getUserRoleName());
		return "CFundTransactionPage";
	}

	@RequestMapping(value = "/MRequestMoney", method = RequestMethod.GET)
	public String merchantRequestMoney(@ModelAttribute("FileUpload") FileUpload Transaction, Model model) {
		getAccountDetails(model);
		model.addAttribute("role", getUserRoleName());
		return "MRequestMoney";
	}

	@RequestMapping(value = "/MRequestMoney", method = RequestMethod.POST)
	public String merchantRequestMoneyResponse(@RequestParam("g-recaptcha-response") String captchaResponse,
			@ModelAttribute("FileUpload") FileUpload fileUpload, Model model) {
		try {
			HttpPostRequest http = new HttpPostRequest();
			boolean valid = http.sendPost(SSConstants.CAPTCHAURL, SSConstants.CAPTCHASECRET, captchaResponse);
			if (valid) {
				MultipartFile multipartFile = (MultipartFile) fileUpload.getFile();
				SSFundTransaction Transaction = ssFundTransactionService.convertFileUploadtoSSFundTransaction(fileUpload);
				if(Transaction==null){
					getAccountDetails(model);
					model.addAttribute("role", getUserRoleName());
					model.addAttribute("message1", "Invalid Input");
					return "MRequestMoney";
				}
				if (!validateCertificate(multipartFile)) {
					getAccountDetails(model);
					model.addAttribute("role", getUserRoleName());
					model.addAttribute("message1", "Please Upload Correct Certificate File");
					return "MRequestMoney";
				}
				if (accountService.getAccountByAccountId(Transaction.getSourceAccount()) != null) {
					Integer userid = getUserModel().getUserid();
					Account sourceAccount = accountService.getAccountByAccountId(Transaction.getSourceAccount());
					Account destinationAccount = accountService
							.getAccountByAccountId(accountService.getCheckingAccount(userid).getAccountid());

					Transaction.setUserId(userid);
					Transaction.setTransactionType(SSConstants.MERCHANT_REQUEST_MONEY);
					Transaction.setDestinationAccount(destinationAccount.getAccountid());
					Transaction.setAuthorizationRole(
							ssTransactionTypeService.getAuthorizationRoleForType(SSConstants.MERCHANT_REQUEST_MONEY));
					Transaction.setStatus(SSConstants.PENDING);
					ssFundTransactionService.addFundTransaction(Transaction);

					sourceAccount.setPendingbalance(
							ssFundTransactionService.computePendingBalance(sourceAccount.getAccountid()));
					destinationAccount.setPendingbalance(
							ssFundTransactionService.computePendingBalance(destinationAccount.getAccountid()));

					accountService.updateAccountByAccountId(sourceAccount, sourceAccount.getAccountid());
					accountService.updateAccountByAccountId(destinationAccount, destinationAccount.getAccountid());

					model.addAttribute("response", "Successful! Request submitted");

					if (sourceAccount.getAccountid() == destinationAccount.getAccountid()) {

						Transaction.setStatus(SSConstants.REJECTED);
						Transaction.setComment("Cannot request money from own Account!!");
						model.addAttribute("transId", Transaction.getFundTransactionId());
						model.addAttribute("message1",
								"Money is being requested from the same account. Please provide a different account number..");
					}
					model.addAttribute("message1", "Successful! Request submitted");

				} else {
					model.addAttribute("message1", "Invalid account number");
				}

				getAccountDetails(model);
				model.addAttribute("role", getUserRoleName());
				return "MRequestMoney";
			} else {
				getAccountDetails(model);
				model.addAttribute("role", getUserRoleName());
				model.addAttribute("message1", "error Captcha");
				return "MRequestMoney";
			}

		} catch (Exception ex) {
			getAccountDetails(model);
			model.addAttribute("role", getUserRoleName());
			model.addAttribute("message1", "Interna Error : contact sys admin");
			return "MRequestMoney";
		}

	}
}
