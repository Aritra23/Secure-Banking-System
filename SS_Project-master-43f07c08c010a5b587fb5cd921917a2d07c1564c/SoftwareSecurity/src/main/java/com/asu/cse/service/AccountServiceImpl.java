package com.asu.cse.service;


import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.dao.AccountDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserRole;



public class AccountServiceImpl implements AccountService, java.io.Serializable {
	
	@Autowired
    private AccountDao accountDao;
	@Autowired
	private SendEmailService sendEmail;
	@Autowired
 	private UserDao userDao;
		
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public AccountDao getAccountDao() {
		return accountDao;
	}

	public void setAccountDao(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

    @Override
    @Transactional
	public void addAccount(Account account) {
		// TODO Auto-generated method stub
		accountDao.addAccount(account);
	}

	@Override
	@Transactional
	public void deleteAccount(Integer accountid) {
		// TODO Auto-generated method stub
		accountDao.deleteAccount(accountid);
	}

	@Override
	@Transactional
	public List<Account> getUserAccount(Integer userid) {
		// TODO Auto-generated method stub
		return accountDao.getUserAccount(userid);
	}

	@Override
	@Transactional
	public Account getAccountByAccountId(Integer accountid) {
		// TODO Auto-generated method stub
		return accountDao.getAccountByAccountId(accountid);
	}

	@Override
	public Account getSavingAccount(Integer userid) {
		return accountDao.getSavingAccount(userid);
	}

	@Override
	public Account getCheckingAccount(Integer userid) {
		return accountDao.getCheckingAccount(userid);
	}
	
	@Override
	public Account updateAccountByAccountId(Account updatedAccount, Integer accountId) {
		return accountDao.updateAccountByAccountId(updatedAccount, accountId);
	}

	@Override
	public void addAccountsAfterSignup(SSProfileTransaction ssProfileTransaction) {
		Integer userId = userDao.getUserByUserName(ssProfileTransaction.getUserName()).getUserid();
		String transactionType = ssProfileTransaction.getTransactionType();
		
		byte savingsEnabled = ssProfileTransaction.getSavingsEnabled();
		String accountType = savingsEnabled == 1 ? "savings" : "checking";
		Float balance = (float) 1000.0;
		Float pendingBalance = (float) 0.0;
		Integer transactionLimit = 2000;
		byte isActive = 1;
		
		Account account = new Account();
		account.setAccounttype(accountType);
		account.setUserid(userId);
		account.setBalance(balance);
		account.setPendingbalance(pendingBalance);
		account.setTranlimit(transactionLimit);
		account.setIsactive(isActive);

		account = accountDao.addAccount(account);
		sendEmail.sendEmailAccountCreation(ssProfileTransaction, account);		
	}
}
