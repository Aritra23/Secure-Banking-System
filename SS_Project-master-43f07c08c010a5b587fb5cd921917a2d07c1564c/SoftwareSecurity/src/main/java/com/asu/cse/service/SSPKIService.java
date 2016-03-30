package com.asu.cse.service;

import com.asu.cse.model.SSUser;

public interface SSPKIService {
	String generateClientCertificate(SSUser user);
	boolean verifyClientCertificate(SSUser user, String certificate);
}
