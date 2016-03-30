package com.asu.cse.dao;

import java.util.ArrayList;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.asu.cse.model.SSUserManagement;

public class SSUserManagementDaoImpl implements SSUserManagementDao {
	
	private static final long serialVersionUID = -8695420422104054125L;

	@Autowired
	SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Integer> getApproverId(Integer userId) {
		List<Integer> aproverUserIdList= null;
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(SSUserManagement.class)
				.add(Restrictions.eq("userId", userId)
				);
		List<SSUserManagement> aproverList = criteria.list();
		for(SSUserManagement userManagement: aproverList) {
			aproverUserIdList.add(userManagement.getApproverId());
		}
		session.flush();
		session.close();
		return aproverUserIdList; 
	}

	@Override
	public List<Integer> getApproveeId(Integer userId) {
		List<Integer> aproveeUserIdList= new ArrayList<Integer>();
		Session session = sessionFactory.openSession();
		
		Criteria criteria = session.createCriteria(SSUserManagement.class)
				.add(Restrictions.eq("approverId", userId)
				);
		
		List<SSUserManagement> aproveeList = criteria.list();
		for(SSUserManagement userManagement: aproveeList) {
			aproveeUserIdList.add(userManagement.getUserId());
		}
		
		session.flush();
		session.close();
		
		return aproveeUserIdList;
	}
	
	@Override
	public void addUserManagement(SSUserManagement ssUserManagement) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(ssUserManagement);
		transaction.commit();
		session.flush();
		session.close();
		
	}
	@Override
	@Transactional
	public void deleteUserManagement(Integer userId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		SSUserManagement ssUserMgmt = (SSUserManagement) session.get(SSUserManagement.class, userId);
		if(ssUserMgmt != null)
		{
			if(ssUserMgmt.getUserId() > 0)
			{
				session.delete(ssUserMgmt);
				transaction.commit();
			}
		} 
		session.flush();
		session.close();
		//SSUserManagement ssUserMgmt = (SSUserManagement) session.get(SSUserManagement.class, userId);
//		if(!ssUserMgmt.equals(null)) {
//			this.sessionFactory.getCurrentSession().delete(ssUserMgmt);
//			session.flush();
//			
//		} else {
//			
//		}
	}
}
