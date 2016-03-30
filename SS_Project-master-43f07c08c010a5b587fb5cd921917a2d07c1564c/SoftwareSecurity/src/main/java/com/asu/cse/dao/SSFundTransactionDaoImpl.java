package com.asu.cse.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.SSConstants;
import com.asu.cse.model.SSFundTransaction;

@Repository
public class SSFundTransactionDaoImpl implements SSFundTransactionDao, Serializable {
	
	private static final long serialVersionUID = 6339262735117348118L;
	@Autowired
    private SessionFactory sessionFactory;
	@Autowired
	private SSUserManagementDao ssUserManagementDao;
	@Autowired
	private AccountDao accountDao;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getPendingFundTransaction(String authorizationRole) {
		Session session = sessionFactory.openSession();
		String hql = "FROM SSFundTransaction WHERE authorization_role = :authorizationRole AND status='Pending'";
		Query query = session.createQuery(hql);
		query.setParameter("authorizationRole", authorizationRole);
		List<SSFundTransaction> pendingFundTransactions = (List<SSFundTransaction>) query.list();
		session.flush();
		session.close();
		return pendingFundTransactions;
	}
	
	/* A certain approver (regular employee or bank manager) have certain customers assigned to them
	 * Show them the pending transactions of only those set of users who they have the authority to approve
	 * and not the pending transactions of all users
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getPendingFundTransactionFilteredByApproverId(String authorizationRole, Integer approverId) {
		Session session = sessionFactory.openSession();
		List<SSFundTransaction> pendingFundTransactions = new ArrayList<SSFundTransaction>();
		List<Integer> usersForApprover = ssUserManagementDao.getApproveeId(approverId);
		
		for(Integer userId : usersForApprover) {
			Criteria cr = session.createCriteria(SSFundTransaction.class);
			cr.add(Restrictions.eq("status", SSConstants.PENDING));
			cr.add(Restrictions.eq("authorizationRole", authorizationRole));
			cr.add(Restrictions.eq("userId", userId));
			pendingFundTransactions.addAll(cr.list());
		}
		session.flush();
		session.close();
		return pendingFundTransactions;
	}
	
	@Override
	@Transactional
	public void addFundTransaction(SSFundTransaction fundTransaction) {
		Session session = sessionFactory.openSession();
 		Transaction transaction = session.beginTransaction();
 		session.save(fundTransaction);
 		transaction.commit();
 		session.flush();
 		session.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getFundTransactionByUserId(Integer userId) {
		Session session = sessionFactory.openSession();
		String hql = "FROM SSFundTransaction WHERE user_id = :userId";
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		List<SSFundTransaction> userFundTransactions = (List<SSFundTransaction>) query.list();
		session.flush();
		session.close();
		return userFundTransactions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getFundTransactionByAccountId(Integer accountId) {
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(SSFundTransaction.class);
		
		Criterion sourceRestriction = Restrictions.eq("sourceAccount", accountId);
		Criterion destinationRestriction = Restrictions.eq("destinationAccount", accountId);
		
		cr.add(Restrictions.or(sourceRestriction, destinationRestriction));
		List<SSFundTransaction> fundTransactions = cr.list();
		
		session.flush();
		session.close();
		
		return fundTransactions;
	}

	@Override
	@Transactional
	public boolean cancelFundTransaction(Integer transactionId) {
		Session session = sessionFactory.openSession();
		SSFundTransaction fundTransaction = (SSFundTransaction) session.get(
				SSFundTransaction.class, transactionId);
		if(!fundTransaction.equals(null) && fundTransaction.getStatus() == "pending") {
			this.sessionFactory.getCurrentSession().delete(fundTransaction);
			session.flush();
	 		session.close();
			return true;
		}
		session.flush();
 		session.close();
		return false;
	}

	@Override
	@Transactional
	public boolean updateFundTransaction(SSFundTransaction fundTransaction, Integer transactionId) {
		Session session = sessionFactory.openSession();
		SSFundTransaction fundTransactionFromDB = (SSFundTransaction) session.get(
				SSFundTransaction.class, transactionId);
		if(!fundTransactionFromDB.equals(null)) {
			this.sessionFactory.getCurrentSession().update(fundTransaction);
			session.flush();
	 		session.close();
			return true;
		}
		session.flush();
 		session.close();
		return false;
		
	}

	@Override
	@Transactional
	public String getFundTransactionStatus(Integer transactionId) {
		Session session = sessionFactory.openSession();
		SSFundTransaction fundTransaction = (SSFundTransaction) session.get(
				SSFundTransaction.class, transactionId);
		if(!fundTransaction.equals(null)) {
			session.flush();
	 		session.close();
			return fundTransaction.getStatus();
		}
		session.flush();
 		session.close();
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public SSFundTransaction getFundTransaction(Integer transactionId) {
		Session session = sessionFactory.openSession();
		String hql = "FROM SSFundTransaction WHERE fund_transaction_id = :transactionId";
		Query query = session.createQuery(hql);
		query.setParameter("transactionId", transactionId);
		List<SSFundTransaction> fundTransactionsById = (List<SSFundTransaction>) query.list();
		session.flush();
 		session.close();
 		if (fundTransactionsById == null)
 			return null;
		return fundTransactionsById.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getPendingFundTransactionByAccountId(Integer sourceAccount) {
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(SSFundTransaction.class);
		cr.add(Restrictions.eq("status", SSConstants.PENDING));
		cr.add(Restrictions.eq("sourceAccount", sourceAccount));
		List<SSFundTransaction> pendingFundTransactions = cr.list();
		session.flush();
		session.close();
		return pendingFundTransactions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getPendingTransferFundTransactionsByDestinationAccountId(Integer destinationAccount) {
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(SSFundTransaction.class);
		cr.add(Restrictions.eq("status", SSConstants.PENDING));
		cr.add(Restrictions.eq("destinationAccount", destinationAccount));
		List<SSFundTransaction> pendingFundTransactions = cr.list();
		session.flush();
		session.close();
		return pendingFundTransactions;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<SSFundTransaction> getPendingTransferFundTransactionByAuthAndSourceAccount(String authorizationRole, Integer sourceAccountId) {
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(SSFundTransaction.class);
		cr.add(Restrictions.eq("status", SSConstants.PENDING));
		cr.add(Restrictions.eq("sourceAccount", sourceAccountId));
		cr.add(Restrictions.eq("authorizationRole", authorizationRole));
		cr.add(Restrictions.eq("transactionType", SSConstants.MERCHANT_REQUEST_MONEY));
		List<SSFundTransaction> pendingFundTransactions = cr.list();
		session.flush();
		session.close();
		return pendingFundTransactions;
	}
	
	@Override
	@Transactional
	public float computePendingBalance(Integer sourceAccount) {
		Session session = sessionFactory.openSession();
		List<SSFundTransaction> pendingFundTransactions = this.getPendingFundTransactionByAccountId(sourceAccount);
		List<SSFundTransaction> pendingTransferFundTransactions = this.getPendingTransferFundTransactionsByDestinationAccountId(sourceAccount);
		
		if(pendingFundTransactions.size() == 0 && pendingTransferFundTransactions.size() == 0) return (float) 0;
		
		float balance = accountDao.getAccountByAccountId(sourceAccount).getBalance();
		
		for(SSFundTransaction transaction : pendingFundTransactions) {
			if(transaction.getTransactionType().equals(SSConstants.CUSTOMER_DEBIT_TRANSACTION)) {
				balance -= transaction.getAmount();
			} else if(transaction.getTransactionType().equals(SSConstants.CUSTOMER_CREDIT_TRANSACTION)) {
				balance += transaction.getAmount();
			} else if(transaction.getTransactionType().equals(SSConstants.CUSTOMER_TRANSFER_TRANSACTION)) {
				balance -= transaction.getAmount();
			}
		}
		
		for(SSFundTransaction transaction : pendingTransferFundTransactions) {
			if(transaction.getTransactionType().equals(SSConstants.CUSTOMER_TRANSFER_TRANSACTION) || transaction.getTransactionType().equals(SSConstants.MERCHANT_REQUEST_MONEY)) {
				balance += transaction.getAmount();
			}
		}
		session.flush();
		session.close();
		return balance;
	}

}