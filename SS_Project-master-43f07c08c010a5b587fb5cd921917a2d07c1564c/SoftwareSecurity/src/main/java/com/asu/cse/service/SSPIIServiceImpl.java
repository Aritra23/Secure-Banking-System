package com.asu.cse.service;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.asu.cse.SSConstants;
import com.asu.cse.dao.SSProfileTransactionDao;
import com.asu.cse.dao.SSTransactionTypeDao;
import com.asu.cse.dao.UserDao;
import com.asu.cse.dao.UserRoleDao;
import com.asu.cse.dao.SSPIIDao;
import com.asu.cse.model.SSProfileTransaction;
import com.asu.cse.model.SSUser;
import com.asu.cse.model.SSPII;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

public class SSPIIServiceImpl implements SSPIIService, Serializable {
	
	private static final long serialVersionUID = 1L;
	@Autowired
	SSPIIDao piiDao;
	
	public SSPIIDao getPiiDao() {
		return piiDao;
	}

	public void setPiiDao(SSPIIDao piiDao) {
		this.piiDao = piiDao;
	}

	public List<SSUser> getAllPIIInfo(){
		return piiDao.getAllPIIInfo();
	}
	
	

}
