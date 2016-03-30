package com.asu.cse.service;

import com.asu.cse.model.Account;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;

public interface SendEmailService {
	boolean sendEmailUsingUserName(String userName);
	boolean sendEmailUsingUserName(SSUser user);
	boolean sendEmailUsingUserEmail(String email);
	boolean validatePin(String email, int pin);
	boolean validatePin(SSUser user, int pin);
	void sendEmailBasic(String to, String subject, String text);
	void sendEmailClientCertificate(SSUser user, String certificate);
	void sendEmailAccountCreation(SSProfileTransaction user, Account ss);
	void sendAccountTransferUpdate(SSFundTransaction transaction, boolean isapproved, SSUser user);
	void sendAccountProfileUpdate(SSProfileTransaction transaction, boolean isapproved, SSUser user);
	void sendFundApprovalRequest(SSFundTransaction transaction, SSUser user);
	void sendProfileApprovalRequest(SSProfileTransaction transaction, SSUser user);
	void sendInsufficientBalance(SSFundTransaction transaction, SSUser user);
	void sendFundTranferRequest(SSFundTransaction transaction, SSUser user);
	void sendEmailWithAttachment(String to, String subject , String text , String attachmentPath);
}
