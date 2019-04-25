package com.home.digital.adapter.persistence.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.home.digital.adapter.persistence.dao.SecureDao;
import com.home.digital.adapter.persistence.dao.SecureDaoImp;
import com.home.digital.adapter.persistence.model.SecureStepUp;


@Transactional
@Component
public class SecurePersistenceService {
	private Logger logger = LoggerFactory.getLogger(SecureDaoImp.class);
	
	@Autowired
	private SecureDao secureDao;
	public String createSecure(SecureStepUp secureStepUp) throws Exception {
		String response = null;
		try {

			response = this.secureDao.createSecure(secureStepUp);

		}catch(Exception ex) {
			throw ex;

		}

		return response;
	}

	public SecureStepUp getSecure(String guid) throws Exception {
		SecureStepUp response = null;
		try {
			
			response = this.secureDao.findSecure(guid);
			
		}catch(Exception ex) {
			throw ex;
		}
		return response;
	}
	
	public SecureStepUp updateSecure(SecureStepUp stepUp) throws Exception {
		SecureStepUp response = null;
		try {
			response = this.secureDao.updateSecure(stepUp);
			
		}catch(Exception ex) {
			throw ex;
		}
		return response;
	}

	public Boolean removeStepUp(String guid) throws Exception {
		Boolean response = false;
		try {
			response = this.secureDao.deleteSecure(guid);
			
		}catch(Exception ex) {
			throw ex;
		}

		return response;
	}
}
