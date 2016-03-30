package com.asu.cse.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.SSConstants;
import com.asu.cse.model.Account;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserManagement;
import com.asu.cse.model.SSUserRole;

@Repository
public class UserDaoImpl implements UserDao, Serializable {

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
	public SSUser addUser(SSUser user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
		session.flush();
		session.close();
		return user;
	}

	@Override
	@Transactional
	public SSUser updateUser(SSUser user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(user);
		transaction.commit();
		session.flush();
		session.close();
		return user;
	}

	@Override
	@Transactional
	public void deleteUser(Integer userId) {
		Session session = sessionFactory.openSession();
		// Account account = (Account) session.get(Account.class, userId);
		// if(!account.equals(null)) {
		// this.sessionFactory.getCurrentSession().delete(account);
		// session.flush();
		//
		// }
		Transaction transaction = session.beginTransaction();

		// Moved to userroledao impl:
		// SSUserRole userRole = (SSUserRole) session.get(SSUserRole.class,
		// userId);
		// if(!userRole.equals(null)) {
		// this.sessionFactory.getCurrentSession().delete(userRole);
		// session.flush();
		//
		// }
		// Moved to ssusermgmtdaoimpl:
		// SSUserManagement ssUserMgmt = (SSUserManagement)
		// session.get(SSUserManagement.class, userId);
		// if(!ssUserMgmt.equals(null)) {
		// this.sessionFactory.getCurrentSession().delete(ssUserMgmt);
		// session.flush();
		//
		// } else {
		//
		// }
		SSUser user = (SSUser) session.get(SSUser.class, userId);
		if (user != null) {
			this.sessionFactory.getCurrentSession().delete(user);
		}
		transaction.commit();
		session.flush();
		session.close();
		// SSUser user = (SSUser) sessionFactory.getCurrentSession().load(
		// SSUser.class, userId);
		// if (null != user) {
		// this.sessionFactory.getCurrentSession().delete(user);
		//
		// }
	}

	@Override
	public SSUser getUser(Integer userId) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction t = session.beginTransaction();
		SSUser user = (SSUser) session.get(SSUser.class, userId);
		session.flush();
		t.commit();
		session.close();
		return user;
	}

	@Override
	@Transactional
	public List<SSUser> getAllUsers() {
		try {
			List<SSUser> listUser = (List<SSUser>) sessionFactory.getCurrentSession().createCriteria(SSUser.class)
					.list();
			return listUser;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public SSUser getUserByUserName(String userName) {
		SSUser user = null;
		String hql = "FROM SSUser S WHERE S.userName = :userName";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		if (query == null || query.list()==null || query.list().isEmpty()){
			session.flush();
			session.close();
			return null;
		
		}
		user = (SSUser) query.list().get(0);
		session.flush();
		session.close();
		return user;
	}

	@Override
	public List<SSUser> getUsersByUserNameSearch(String userNameSearch) {
		String hql = "FROM SSUser S " + "WHERE S.userName = (:userNameSearch)"
				+ "AND S.userid IN (SELECT SSR.userId FROM SSUserRole SSR WHERE SSR.role IN('EXT_CUSTOMER', 'EXT_MERCHANT'))";
		// String hql = "SELECT S.* FROM SSUser S JOIN SSUserRole SSR ON
		// S.userid = SSR.userId "
		// + "WHERE S.userName LIKE CONCAT('%', :userNameSearch, '%') "
		// + "AND SSR.role IN('EXT_CUSTOMER', 'EXT_MERCHANT')";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userNameSearch", userNameSearch);
		List<SSUser> list = query.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public List<SSUser> getAllUsersByUserids(List<Integer> UserIDs) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSUser.class).add(Restrictions.in("userid", UserIDs));
		List<SSUser> list = criteria.list();
		session.flush();
		session.close();
		return list;
	}

	@Override
	public boolean userExists(String userName) {
		String hql = "FROM SSUser S WHERE S.userName = :userName";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		List<SSUser> user = (List<SSUser>) query.list();
		session.flush();
		session.close();
		if (user == null || user.size() <= 0 || user.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean ssnExists(String ssn) {
		String hql = "FROM SSUser S WHERE S.social = :SSN";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("SSN", ssn);
		List<SSUser> user = (List<SSUser>) query.list();
		session.flush();
		session.close();
		if (user == null || user.size() <= 0 || user.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean emailExists(String email) {
		String hql = "FROM SSUser S WHERE S.email = :email";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		List<SSUser> user = (List<SSUser>) query.list();
		session.flush();
		session.close();
		if (user == null || user.size() <= 0 || user.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public List<String> getUserNamesByRole(String role) {
		String userRoleHql = "FROM SSUserRole UR WHERE UR.role = :role";
		Session session = sessionFactory.openSession();
		Query userRoleQuery = session.createQuery(userRoleHql);
		userRoleQuery.setParameter("role", role);
		if (userRoleQuery == null || userRoleQuery.list() == null || userRoleQuery.list().isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		List<SSUserRole> userRoleList = userRoleQuery.list();
		List<String> userList = new ArrayList<String>();
		for (SSUserRole ssUserRole : userRoleList) {
			Integer userId = ssUserRole.getUserId();
			String ssUserHql = "FROM SSUser S WHERE S.userid = :userid";
			Query ssUserQuery = session.createQuery(ssUserHql);
			ssUserQuery.setParameter("userid", userId);
			if (ssUserQuery == null || ssUserQuery.list()==null || ssUserQuery.list().isEmpty()) {
				session.flush();
				session.close();
				return null;
			}
			SSUser ssUser = (SSUser) ssUserQuery.list().get(0);
			userList.add(ssUser.getUserName());
		}
		session.flush();
		session.close();
		return userList;
	}

	@Override
	public List<SSUser> getUsersByRole(String role) {
		if (role == null)
			return null;
		String userRoleHql = "FROM SSUserRole UR WHERE UR.role = :role";
		Session session = sessionFactory.openSession();
		Query userRoleQuery = session.createQuery(userRoleHql);
		userRoleQuery.setParameter("role", role);
		List<SSUserRole> userRoleList = userRoleQuery.list();
		if (userRoleList == null || userRoleList.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		List<SSUser> userList = new ArrayList<SSUser>();
		for (SSUserRole ssUserRole : userRoleList) {
			Integer userId = ssUserRole.getUserId();
			String ssUserHql = "FROM SSUser S WHERE S.userid = :userid";
			Query ssUserQuery = session.createQuery(ssUserHql);
			ssUserQuery.setParameter("userid", userId);
			List<SSUser> list = ssUserQuery.list();
			if (list == null) {
				session.flush();
				session.close();
				return null;
			}
			SSUser ssUser = (SSUser) list.get(0);
			if (ssUser != null)
				userList.add(ssUser);
		}
		session.flush();
		session.close();
		return userList;
	}

	@Override
	public void setSecrect(String userName, String hash) {
		String hql = "FROM SSUser S WHERE S.userName = :userName";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userName", userName);
		List<SSUser> user = (List<SSUser>) query.list();
		session.flush();
		session.close();
		if (user == null || user.isEmpty())
			return;
		user.get(0).setSecret(hash);
		updateUser(user.get(0));
	}

	@Override
	public SSUser getUserByEmail(String email) {
		SSUser user = null;
		String hql = "FROM SSUser S WHERE S.email = :email";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		if (query != null)
			user = (SSUser) query.list().get(0);
		session.flush();
		session.close();
		return user;
	}

	@Override
	@Transactional
	public void setEnabledBitAfterDeleteProfileSubmission(Integer userId, Integer enabled) {
		String hql = "UPDATE SSUser set enabled = :enabled WHERE userid = :userid";
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		query.setParameter("enabled", enabled);
		query.executeUpdate();
		transaction.commit();
		session.flush();
		session.close();
	}

	@Override
	public boolean resetpassword(String userName) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSUser.class).add(Restrictions.eq("userName", userName));
		Object result = criteria.uniqueResult();
		if (result != null) {
			Byte byte1 = new Byte("1");
			SSUser user = (SSUser) result;
			if (byte1.equals(user.getForgotPasswordEnabled())) {
				return true;
			}
		}
		session.flush();
		session.close();
		return false;
	}
}
