package com.asu.cse.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.controller.HomeController;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSUser;

public class AccountDaoImpl implements AccountDao, java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
 
	@Override
	@Transactional

	public Account addAccount(Account account) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(account);
		transaction.commit();
		session.flush();
		session.close();
		return account;

	}

	@Override
	@Transactional
	public void deleteAccount(Integer accountid) {

		Account account = (Account) sessionFactory.getCurrentSession().load(
                Account.class, accountid);
        if (null != account) {
    		Session session = sessionFactory.openSession();
    		Transaction transaction = session.beginTransaction();
            this.sessionFactory.getCurrentSession().delete(account);
            transaction.commit();
            session.flush();
            session.close();
        }
	}

	@Override
	public List<Account> getUserAccount(Integer userid) {
		String hib = "FROM Account a WHERE a.userid = :userid";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hib);
		query.setParameter("userid", userid);
        @SuppressWarnings("unchecked")
		List<Account> accounts = (List<Account>)query.list();
		session.flush();
		session.close();
		return accounts;
	}

	@Override
	@Transactional
	public List<Account> getAllUserAccount() {
		 try {
	        List<Account> listAccount = (List<Account>) sessionFactory.getCurrentSession().createCriteria(Account.class).list();
	        return listAccount;
		 } catch (Exception e) {
		 }
		 return null;
	}

	@Override
	public Account getAccountByAccountId(Integer accountid) {
		String hib = "FROM Account a WHERE a.accountid = :accountid";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hib);
		query.setParameter("accountid", accountid);

		if (query.list() == null || query.list().isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		Account account = (Account)query.list().get(0);
		
		session.flush();
		session.close();
		return account;
	}

	@Override
	public Account getCheckingAccount(Integer userid) {
		String hib = "FROM Account a WHERE a.userid = :accountid and a.accounttype = :accounttype";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hib);
		query.setParameter("accountid", userid);
		query.setParameter("accounttype", "CHECKING");
		if (query == null ||  query.list()==null || query.list().isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		Account account = (Account)query.list().get(0);
		session.flush();
		session.close();
		return account;
	}

	@Override
	public Account getSavingAccount(Integer userid) {
		String hib = "FROM Account a WHERE a.userid = :accountid and a.accounttype = :accounttype";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hib);
		query.setParameter("accountid", userid);
		query.setParameter("accounttype", "SAVING");
		if (query == null || query.list().isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
			
		Account account = (Account)query.list().get(0);
		session.flush();
		session.close();
		return account;
	}
	
	@Override
	@Transactional
	public Account updateAccountByAccountId(Account updatedAccount, Integer accountId) {
		Account account = getAccountByAccountId(accountId);
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(updatedAccount);
		transaction.commit();
		session.flush();
		session.close();
		return account;
	}
}