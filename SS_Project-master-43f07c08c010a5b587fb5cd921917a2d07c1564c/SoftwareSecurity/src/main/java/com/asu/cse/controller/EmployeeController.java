package com.asu.cse.controller;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.asu.cse.SSConstants;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserPII;
import com.asu.cse.model.SSUserRole;

/**
 * This will handle only Employee APIs.
 */
@Controller
public class EmployeeController extends AbstractHomeController{
    	
	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public String employeePage(Model model) {
		model.addAttribute("title", getUserRoleName());
		model.addAttribute("role", getUserRoleName());
		model.addAttribute("message", "Employee Page - This is protected page!");
		model.addAttribute("pendingTransactions",
				ssFundTransactionService
				.getPendingFundTransactionFilteredByApproverId(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE, 
						getUserModel().getUserid()));
		return "employeePage";
	}

	@RequestMapping(value = "/employee", method = RequestMethod.POST)
	public String employeeResponsePage(
						@RequestParam("updatedTransactionId") Integer updatedTransactionId, @RequestParam("BoolVal") boolean approve,
						Model model) {
		SSFundTransaction updatedTransaction = ssFundTransactionService.getFundTransaction(updatedTransactionId);
		SSUser user = getUserModel();
		
		if (!(updatedTransaction.getAuthorizationRole().equals("INT_REMP"))) {
			return "redirect:/";
		}

 		float amount = updatedTransaction.getAmount();
 		String transactionType = updatedTransaction.getTransactionType();
 		
 		Account sourceAccount = accountService.getAccountByAccountId(updatedTransaction.getSourceAccount());
		float sourceUpdatedBalance = sourceAccount.getBalance();
 				
		// If the amount of transaction is above the critical transaction limit (default or user specific),
		// then escalate the transaction to be
		// approved/rejected by a bank manager
		if (updatedTransaction.getAmount() >= SSConstants.CRITICAL_TRANSACTION_LIMIT || updatedTransaction.getAmount() > sourceAccount.getTranlimit()) {
				updatedTransaction.setAuthorizationRole(SSConstants.INTERNAL_MANAGER_ROLE);
				if (approve) {
					updatedTransaction.setComment("Approved by " + user.getFirstName());
				} else if (!approve) {
					updatedTransaction.setComment("Rejected by " + user.getFirstName());
				}
		} else if(transactionType.equalsIgnoreCase(SSConstants.MERCHANT_REQUEST_MONEY)) {
			if (approve) {
				updatedTransaction.setAuthorizationRole(userRoleDao
						.getRole(accountService
								.getAccountByAccountId(updatedTransaction.getSourceAccount())
										.getUserid()).getRole());
				updatedTransaction.setComment("Approved by " + user.getFirstName());
			}
		} else if (approve) {
			if (transactionType.equalsIgnoreCase(SSConstants.CUSTOMER_DEBIT_TRANSACTION)) {
				sourceUpdatedBalance -= amount;
			} else if (transactionType.equalsIgnoreCase(SSConstants.CUSTOMER_CREDIT_TRANSACTION)) {
				sourceUpdatedBalance += amount;
			} else if (transactionType.equalsIgnoreCase(SSConstants.CUSTOMER_TRANSFER_TRANSACTION)) {
				Account destinationAccount = accountService.getAccountByAccountId(updatedTransaction.getDestinationAccount());
				sourceUpdatedBalance -= amount;
				destinationAccount.setBalance(destinationAccount.getBalance() + amount);
				destinationAccount.setPendingbalance(ssFundTransactionService.computePendingBalance(destinationAccount.getAccountid()));
				accountService.updateAccountByAccountId(destinationAccount, updatedTransaction.getDestinationAccount());
			}
			sourceAccount.setBalance(sourceUpdatedBalance);
			sourceAccount.setPendingbalance(ssFundTransactionService.computePendingBalance(sourceAccount.getAccountid()));
			accountService.updateAccountByAccountId(sourceAccount,
					updatedTransaction.getSourceAccount());
 			updatedTransaction.setStatus(SSConstants.APPROVED);
 			updatedTransaction.setComment("Approved by " + user.getFirstName());
 		} else {
 			updatedTransaction.setStatus(SSConstants.REJECTED);
 			updatedTransaction.setComment("Rejected by " + user.getFirstName());
 		}
		
		// Save the updated transaction information to the database
		ssFundTransactionService.updateFundTransaction(updatedTransaction, updatedTransactionId);
		model.addAttribute("title", getUserRoleName());
		model.addAttribute("role", getUserRoleName());
		// return the view for employee Page
 		model.addAttribute("pendingTransactions", 
 				ssFundTransactionService.getPendingFundTransactionFilteredByApproverId(SSConstants.INTERNAL_REGULAR_EMPLOYEE_ROLE, user.getUserid()));
 		sendEmail.sendAccountTransferUpdate(updatedTransaction, approve, user);
 		return "employeePage";
	}
}
