package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.FileUpload;
import com.asu.cse.model.SSFundTransaction;

public interface SSFundTransactionService {
	
	public void addFundTransaction(SSFundTransaction fundTransaction);
	public List<SSFundTransaction> getFundTransactionByUserId(Integer userId);
	public boolean cancelFundTransaction(Integer transactionId);
	public boolean updateFundTransaction(SSFundTransaction fundTransaction, Integer transactionId);
	public String getFundTransactionStatus(Integer transactionId);
	public List<SSFundTransaction> getPendingFundTransaction(String authorizationRole);
	public SSFundTransaction getFundTransaction(Integer transactionId);
	public List<SSFundTransaction> getPendingFundTransactionFilteredByApproverId(String authorizationRole, Integer approverId);
	public List<SSFundTransaction> getUserFundTransactionsByMonth(Integer userId, String monthYear);
	public List<SSFundTransaction> getPendingFundTransactionByAccountId(Integer sourceAccount);
	public List<SSFundTransaction> getPendingTransferFundTransactionsByDestinationAccountId(Integer destinationAccount);
	public float computePendingBalance(Integer sourceAccount);
	public List<SSFundTransaction> recentFundTransactions(Integer userId);
	public List<SSFundTransaction> getPendingTransferFundTransactionByAuthAndSourceAccount(String authorizationRole, Integer destinationAccountId);
	public SSFundTransaction convertFileUploadtoSSFundTransaction(FileUpload upload);
}
