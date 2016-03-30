package com.asu.cse.dao;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.controller.HomeController;
import com.asu.cse.model.SSOTP;
import com.asu.cse.model.SSUser;

@Repository
public class SSOTPDaoImpl implements SSOTPDao, Serializable {
	private static final long serialVersionUID = 1L;
	@Autowired
	private SessionFactory sessionFactory;
	final int MILISEC = 1000;
	final int TENMIN = 10 * 60 * MILISEC;
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Integer getPin(String email) {
		SSOTP otp = new SSOTP();
		Random randomGenerator = new Random();
		otp.setEmail(email);
		otp.setPin(randomGenerator.nextInt(899999)+100000);
		removePin(email);
		this.sessionFactory.getCurrentSession().save(otp);
		return otp.getPin();
	}

	@Override
	@Transactional
	public void removePin(String email) {
		String hql = "FROM SSOTP S WHERE S.email = :email";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
		
		if (query.list() == null || query.list().isEmpty()) {
			session.close();
			return;
		}
		SSOTP otp = (SSOTP) query.list().get(0);
		session.flush();
		if (null != otp) {
			this.sessionFactory.getCurrentSession().delete(otp);
		}
		session.close();
	}

	@Override
	@Transactional
	public boolean isValidPin(String email, Integer pin) {
		String hql = "FROM SSOTP S WHERE S.email = :email";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
		query.setParameter("email", email);
	
		List<SSOTP> otplist = query.list();
		session.flush();
		session.close();
		if (otplist == null || otplist.size() <= 0)
			return false;
		SSOTP otp = otplist.get(0);
        
		if (otp.getPin().intValue() == pin) {
			Date date = new Date();
			Timestamp time_cur = new Timestamp(date.getTime());
			long diff = time_cur.getTime() - otp.getTimestamp().getTime();
			removePin(email);
			if (diff > TENMIN) {
				return false;
			} else {
				return true;
			}
		}
		
		return false;
	}
}
