package com.asu.cse.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.dao.AccountDao;
import com.asu.cse.dao.SSFundTransactionDao;
import com.asu.cse.dao.SSTransactionTypeDao;
import com.asu.cse.model.Account;
import com.asu.cse.model.FileUpload;
import com.asu.cse.model.SSFundTransaction;

public class SSFundTransactionServiceImpl implements SSFundTransactionService {

	@Autowired
	private SSFundTransactionDao fundTransactionDao;
	@Autowired
	private SSTransactionTypeDao ssTransactionTypeDao;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private SendEmailService sendEmail;

	public SSFundTransactionDao getFundTransactionDao() {
		return fundTransactionDao;
	}

	public void setFundTransactionDao(SSFundTransactionDao fundTransactionDao) {
		this.fundTransactionDao = fundTransactionDao;
	}

	@Override
	@Transactional
	public void addFundTransaction(SSFundTransaction fundTransaction) {
		String transactionType = fundTransaction.getTransactionType();
		String authorizationUserRole = ssTransactionTypeDao.getAuthorizationRoleForType(transactionType);
		fundTransaction.setAuthorizationRole(authorizationUserRole);
		java.util.Date date = new java.util.Date();
		fundTransaction.setTimeStamp(new Timestamp(date.getTime()));
		fundTransactionDao.addFundTransaction(fundTransaction);
	}

	@Override
	@Transactional
	public List<SSFundTransaction> getFundTransactionByUserId(Integer userId) {
		return fundTransactionDao.getFundTransactionByUserId(userId);
	}

	@Override
	@Transactional
	public boolean cancelFundTransaction(Integer transactionId) {
		return fundTransactionDao.cancelFundTransaction(transactionId);
	}

	@Override
	@Transactional
	public boolean updateFundTransaction(SSFundTransaction fundTransaction, Integer transactionId) {
		return fundTransactionDao.updateFundTransaction(fundTransaction, transactionId);
	}

	@Override
	@Transactional
	public String getFundTransactionStatus(Integer transactionId) {
		return fundTransactionDao.getFundTransactionStatus(transactionId);
	}

	@Override
	@Transactional
	public List<SSFundTransaction> getPendingFundTransaction(String authorizationRole) {
		return fundTransactionDao.getPendingFundTransaction(authorizationRole);
	}

	@Override
	@Transactional
	public SSFundTransaction getFundTransaction(Integer transactionId) {
		return fundTransactionDao.getFundTransaction(transactionId);
	}

	@Override
	@Transactional
	public List<SSFundTransaction> getPendingFundTransactionFilteredByApproverId(String authorizationRole,
			Integer approverId) {
		return fundTransactionDao.getPendingFundTransactionFilteredByApproverId(authorizationRole, approverId);
	}

	@Override
	public List<SSFundTransaction> getUserFundTransactionsByMonth(Integer userId, String monthYear) {
		List<Account> userAccounts = accountDao.getUserAccount(userId);
		List<SSFundTransaction> fundTransactionsByMonth = new ArrayList<SSFundTransaction>();

		for (Account userAccount : userAccounts) {
			List<SSFundTransaction> fundTransactions = fundTransactionDao
					.getFundTransactionByAccountId(userAccount.getAccountid());
			for (SSFundTransaction fundTransaction : fundTransactions) {
				try {
					if (checkFundTransactionByMonth(fundTransaction, monthYear))
						fundTransactionsByMonth.add(fundTransaction);
				} catch (ParseException e) {
					return null;
				}
			}
		}
		return fundTransactionsByMonth;
	}

	private boolean checkFundTransactionByMonth(SSFundTransaction fundTransaction, String monthYear)
			throws ParseException {
		Timestamp timestamp = fundTransaction.getTimeStamp();
		Date startDate = getStartDateEndDate(monthYear).get(0);
		Date endDate = getStartDateEndDate(monthYear).get(1);
		if (timestamp.after(startDate) && timestamp.before(endDate))
			return true;
		return false;
	}

	private List<Date> getStartDateEndDate(String monthYear) throws ParseException {
		List<Date> startDateEndDate = new ArrayList<Date>();
		String month = monthYear.split("-")[0];
		String year = monthYear.split("-")[1];

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		String startDateString = "1-" + month + "-" + year + " 00:00:00";
		Date startDate = dateFormat.parse(startDateString);
		startDateEndDate.add(startDate);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		String endDateString = calendar.getActualMaximum(Calendar.DATE) + "-" + month + "-" + year + " 23:59:59";
		Date endDate = dateFormat.parse(endDateString);
		startDateEndDate.add(endDate);

		return startDateEndDate;
	}

	public List<SSFundTransaction> getPendingFundTransactionByAccountId(Integer sourceAccount) {
		return fundTransactionDao.getPendingFundTransactionByAccountId(sourceAccount);
	}

	@Override
	public float computePendingBalance(Integer sourceAccount) {
		return fundTransactionDao.computePendingBalance(sourceAccount);
	}

	@Override
	public List<SSFundTransaction> recentFundTransactions(Integer userId) {
		List<Account> userAccounts = accountDao.getUserAccount(userId);
		List<SSFundTransaction> fundTransactions = new ArrayList<SSFundTransaction>();
		for (Account userAccount : userAccounts) {
			fundTransactions.addAll(fundTransactionDao.getFundTransactionByAccountId(userAccount.getAccountid()));
		}

		Collections.sort(fundTransactions, new Comparator<SSFundTransaction>() {
			public int compare(SSFundTransaction fundTransaction1, SSFundTransaction fundTransaction2) {
				return fundTransaction1.getTimeStamp().compareTo(fundTransaction2.getTimeStamp());
			}
		});
		List<SSFundTransaction> recentFundTransactions = new ArrayList<SSFundTransaction>();
		if (fundTransactions.size() <= 5)
			return fundTransactions;
		else {
			int size = fundTransactions.size();
			for (int j = (size - 1); j > (size - 6); j--)
				recentFundTransactions.add(fundTransactions.get(j));
		}
		return recentFundTransactions;
	}
	@Override
	public SSFundTransaction convertFileUploadtoSSFundTransaction(FileUpload upload) {
		if (upload == null)
			return null;
		if (upload.getAmount() == null  || upload.getSourceAccount() == null )
			return null;
		SSFundTransaction transaction = new SSFundTransaction();
		transaction.setAmount(upload.getAmount());		
		if(upload.getComment()==null){
			transaction.setComment("");
		}else
			transaction.setComment(upload.getComment());
		transaction.setSourceAccount(upload.getSourceAccount());;
		return transaction;
	}
	
	
	@Override
	public List<SSFundTransaction> getPendingTransferFundTransactionsByDestinationAccountId(
			Integer destinationAccount) {
		return fundTransactionDao.getPendingTransferFundTransactionsByDestinationAccountId(destinationAccount);
	}

	@Override
	public List<SSFundTransaction> getPendingTransferFundTransactionByAuthAndSourceAccount(String authorizationRole,
			Integer destinationAccountId) {
		return fundTransactionDao.getPendingTransferFundTransactionByAuthAndSourceAccount(authorizationRole,
				destinationAccountId);
	}

}