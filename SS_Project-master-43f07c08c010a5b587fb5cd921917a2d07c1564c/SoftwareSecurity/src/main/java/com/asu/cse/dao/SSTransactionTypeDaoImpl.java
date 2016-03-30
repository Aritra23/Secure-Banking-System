package com.asu.cse.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.model.SSTransactionType;

public class SSTransactionTypeDaoImpl implements SSTransactionTypeDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List<SSTransactionType> getAllTransactions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SSTransactionType> getAllTransactionByTransactionType(String transactionType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SSTransactionType> getSignupTransactionForRole(String role) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Transactional
	@Override
	public String getAuthorizationRoleForType(String transactionType) {
		Session session = sessionFactory.openSession();
		String hql = "from SSTransactionType S 	where S.TransactionType = :transactionType";
		Query query = session.createQuery(hql);
		query.setParameter("transactionType", transactionType);
		List<SSTransactionType> ssTransactionType = query.list();
		session.flush();
		session.close();
		if (ssTransactionType == null || ssTransactionType.isEmpty())
			return null;
		return ssTransactionType.get(0).getAuthorizationRole();
	}

	
}
