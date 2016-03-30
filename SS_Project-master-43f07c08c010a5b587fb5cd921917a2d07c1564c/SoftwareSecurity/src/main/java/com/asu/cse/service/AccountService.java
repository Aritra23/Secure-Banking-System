package com.asu.cse.service;

import java.util.List;

import com.asu.cse.model.Account;
import com.asu.cse.model.SSProfileTransaction;


public interface AccountService {
	
	 public void addAccount(Account account);
	 public void deleteAccount(Integer accountid);
	 public List<Account> getUserAccount(Integer userid);
	 public Account getAccountByAccountId(Integer accountid);
	 public Account getSavingAccount(Integer userid);
	 public Account getCheckingAccount(Integer userid);
	 public Account updateAccountByAccountId(Account updatedAccount, Integer accountId);
	 public void addAccountsAfterSignup(SSProfileTransaction ssProfileTransaction);
}
