package com.asu.cse.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.SSConstants;
import com.asu.cse.controller.HomeController;
import com.asu.cse.model.SSFundTransaction;
import com.asu.cse.model.SSOTP;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;

public class SSProfileTransactionDaoImpl implements SSProfileTransactionDao, Serializable {

	private static final long serialVersionUID = -8695420442104054125L;

	@Autowired
	SessionFactory sessionFactory;
	@Autowired
	SSUserManagementDao ssUserManagementDao;

	public SSUserManagementDao getSsUserManagementDao() {
		return ssUserManagementDao;
	}

	public void setSsUserManagementDao(SSUserManagementDao ssUserManagementDao) {
		this.ssUserManagementDao = ssUserManagementDao;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	@Override
	public Integer addProfileTransaction(SSProfileTransaction profileTransaction) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(profileTransaction);
		transaction.commit();
		if (transaction.wasCommitted()) {
			session.flush();
			session.close();
			return profileTransaction.getProfileTransactionId();
		}
		session.flush();
		session.close();
		return -1;
	}

	@Override
	@Transactional
	public void updateProfileTransaction(Integer profileTransactionId, boolean approved) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String validatorUserName = SecurityContextHolder.getContext().getAuthentication().getName();
		String newComment = "";
		String newStatus = "";
		if (approved) {
			newComment = "Approved by " + validatorUserName;
			newStatus = "approved";
		} else {
			newComment = "Rejected by " + validatorUserName;
			newStatus = "rejected";
		}
		String hql = "UPDATE SSProfileTransaction SET comment = :newComment , status = :newStatus WHERE profile_transaction_id = :profileTransactionId";
		Query query = session.createQuery(hql);
		query.setParameter("newComment", newComment);
		query.setParameter("newStatus", newStatus);
		query.setParameter("profileTransactionId", profileTransactionId);
		int rowCount = query.executeUpdate();
		transaction.commit();

		session.flush();
		session.close();
	}

	@Override
	@Transactional
	public Integer updateProfileTransaction(SSProfileTransaction trans) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(trans);
		transaction.commit();
		session.flush();
		session.close();
		return -1;
	}
	

	@Override
	@Transactional
	public void deleteProfileTransaction(SSProfileTransaction trans) {
		String hql = "FROM SSProfileTransaction S WHERE S.profileTransactionId = :id";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("id", trans.getProfileTransactionId());
		
		if (query.list() == null || query.list().isEmpty()) {
			session.close();
			return;
		}
		SSProfileTransaction t = (SSProfileTransaction) query.list().get(0);
		session.flush();
		if (null != t) {
			
			this.sessionFactory.getCurrentSession().delete(t);
		}
		session.close();
	}

	@Override
	public List<SSProfileTransaction> getProfileTransactionStatus(String status) {
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(SSProfileTransaction.class);
		cr.add(Restrictions.eq("status", status));
		List<SSProfileTransaction> profileTransByStatus = cr.list();
		session.flush();
		session.close();
		
		return profileTransByStatus;
	}

	@Override
	@Transactional
	public List<SSProfileTransaction> getAllProfileTransactions() {
		try {
			List<SSProfileTransaction> listProfileTransaction = (List<SSProfileTransaction>) sessionFactory
					.getCurrentSession().createCriteria(SSProfileTransaction.class).list();
			return listProfileTransaction;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<SSProfileTransaction> getAllExternalSignupByStatus(String status) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSProfileTransaction.class)
				.add(Restrictions.in("transactionType",
						new String[] { SSConstants.CUSTOMER_SIGNUP_TRANSACTION, SSConstants.MERCHANT_SIGNUP }))
				.add(Restrictions.eq("status", status));
		List<SSProfileTransaction> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<SSProfileTransaction> getAllExternalEditRequestsByStatus(String status) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSProfileTransaction.class)
				.add(Restrictions.in("transactionType",
						new String[] { SSConstants.CUSTOMER_UPDATE_TRANSACTION, SSConstants.MERCHANT_UPDATE }))
				.add(Restrictions.eq("status", status));
		List<SSProfileTransaction> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}
	
	
	public List<SSProfileTransaction> getAllInternalSignupByStatus(String status) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSProfileTransaction.class)
				.add(Restrictions.in("transactionType", new String[] { SSConstants.INTERNAL_EMPLOYEE_SIGNUP }))
				.add(Restrictions.eq("status", status));
		List<SSProfileTransaction> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}

	
	public List<SSProfileTransaction> getAllInternalEditRequestsByStatus(String status) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSProfileTransaction.class)
				.add(Restrictions.in("transactionType", new String[] { SSConstants.INTERNAL_EMPLOYEE_UPDATE }))
				.add(Restrictions.eq("status", status));
		
		if (criteria.list() == null || criteria.list().isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		List<SSProfileTransaction> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}
	
	@Override
	public List<SSProfileTransaction> getProfileTransactionByStatus(SSUser user, String role, String status) {
		List<Integer> approveeUserIdList = ssUserManagementDao.getApproveeId(user.getUserid());
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSProfileTransaction.class)
				.add(Restrictions.eq("authorizationUserRole", role))
				.add(Restrictions.eq("status", status))
				.add(Restrictions.in("userId", approveeUserIdList.toArray(new Integer[approveeUserIdList.size()])));
		List<SSProfileTransaction> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public SSProfileTransaction getProfileTransactionById(Integer transactionId) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSProfileTransaction.class)
				.add(Restrictions.eq("profileTransactionId", transactionId));
		List<SSProfileTransaction> ssProfileTransactionList = criteria.list();
		if (ssProfileTransactionList == null || ssProfileTransactionList.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return ssProfileTransactionList.get(0);
	}

	@Override
	public SSProfileTransaction userExists(String userName) {
		String hql = "FROM SSProfileTransaction S WHERE S.userName = :userName and (status=:status1 or status=:status2)";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		
		query.setParameter("userName", userName);
		query.setParameter("status1", "OTP");
		query.setParameter("status2", "Pending");
		
		List<SSProfileTransaction> user = (List<SSProfileTransaction>) query.list();

		if (user == null || user.size() <= 0 || user.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return user.get(0);
	}

	@Override
	public SSProfileTransaction emailExists(String email) {
		String hql = "FROM SSProfileTransaction S WHERE S.email = :email and (status=:status1 or status=:status2)";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		
		query.setParameter("email", email);
		query.setParameter("status1", "OTP");
		query.setParameter("status2", "Pending");
		
		List<SSProfileTransaction> user = (List<SSProfileTransaction>) query.list();

		if (user == null || user.size() <= 0 || user.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return user.get(0);
	}

	@Override
	public SSProfileTransaction ssnExists(String ssn) {
		String hql = "FROM SSProfileTransaction S WHERE S.social = :ssn and (status=:status1 or status=:status2)";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		
		query.setParameter("ssn", ssn);
		query.setParameter("status1", "OTP");
		query.setParameter("status2", "Pending");
		
		List<SSProfileTransaction> user = (List<SSProfileTransaction>) query.list();

		if (user == null || user.size() <= 0 || user.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return user.get(0);
	}

	@Override
	public SSProfileTransaction getProfileTransactionByEmail(String email) {
		String hql = "FROM SSProfileTransaction S WHERE S.email = :email and S.status = :status";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		query.setParameter("status", "OTP");
		List<SSProfileTransaction> user = (List<SSProfileTransaction>) query.list();
		
		if (user == null || user.size() <= 0) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return user.get(0);
	}
	@Override
	public SSProfileTransaction getProfileTransactionByUserId(Integer userId) {
		String hql = "FROM SSProfileTransaction S WHERE S.userId = :userId and S.status = :status";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		query.setParameter("status", "Pending");
		List<SSProfileTransaction> user = (List<SSProfileTransaction>) query.list();

		if (user == null || user.size() <= 0 || user.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return user.get(0);
	}
}
