package com.asu.cse.dao;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.model.SSUserRole;

@Repository
public class UserRoleDaoImpl implements UserRoleDao, Serializable{
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
	public SSUserRole getRole(Integer userId) {
		String hql = "FROM SSUserRole S WHERE S.userId = :userId";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("userId", userId);
		if(query.list()==null || query.list().isEmpty()){
			session.flush();
			session.close();
			return null;
		}
		SSUserRole user = (SSUserRole)query.list().get(0);
		session.flush();
		session.close();
		return user;
	}


	@Override
	@Transactional
	public void addUserRole(SSUserRole ssUserRole) {
	
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(ssUserRole);
		transaction.commit();
		session.flush();
		session.close();
		
	}
	
	@Override
	@Transactional
	public void deleteUserRole(Integer userId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		SSUserRole userRole = (SSUserRole) session.get(SSUserRole.class, userId);
		if(userRole != null) {
			session.delete(userRole);
			transaction.commit();
		}
		session.flush();
		session.close();
	}
}
