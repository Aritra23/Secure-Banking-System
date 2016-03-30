package com.asu.cse.dao;


import java.util.List;

import com.asu.cse.model.Account;


public interface AccountDao {
	
	 public Account addAccount(Account account);
	 public void deleteAccount(Integer accountid);
	 public List<Account> getUserAccount(Integer userid);
	 public Account getAccountByAccountId(Integer accountid);
	 public Account getCheckingAccount(Integer userid);
	 public Account getSavingAccount(Integer userid);
	 public List <Account> getAllUserAccount();
	 public Account updateAccountByAccountId(Account updatedAccount, Integer accountId);

}
