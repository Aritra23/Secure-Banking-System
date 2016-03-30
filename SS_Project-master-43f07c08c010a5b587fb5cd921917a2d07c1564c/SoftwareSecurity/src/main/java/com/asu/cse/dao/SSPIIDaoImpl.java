package com.asu.cse.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.SSConstants;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSUserPII;
import com.asu.cse.model.SSUserRole;
import com.asu.cse.model.SSPII;

@Repository
public class SSPIIDaoImpl implements SSPIIDao, Serializable {
	@Autowired
	SessionFactory sessionFactory;

	public List<SSUser> getAllPIIInfo() {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(SSPII.class);
		List<SSUser> list = (ArrayList<SSUser>) criteria.list();
		if (list == null || list.isEmpty()) {
			session.flush();
			session.close();
			return null;
		}
		session.flush();
		session.close();
		return list;

	}

	@Override
	public boolean nameExists(String firstName) {
		String hql = "FROM SSUser S WHERE S.firstName = :firstName";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("firstName", firstName);
		List<SSPII> user = (List<SSPII>) query.list();
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
		List<SSPII> user = (List<SSPII>) query.list();
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
		List<SSPII> user = (List<SSPII>) query.list();
		session.flush();
		session.close();
		if (user == null || user.size() <= 0 || user.isEmpty()) {
			return false;
		}
		return true;
	}

}
