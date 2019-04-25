package com.home.digital.adapter.persistence.dao;

import java.sql.SQLException;

import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.home.digital.adapter.persistence.model.SecureContactType;
import com.home.digital.adapter.persistence.model.SecureStatusType;
import com.home.digital.adapter.persistence.model.SecureStepUp;
import com.home.digital.common.adaptor.service.AdapterException;


@Repository("secureDaoImp")
public class SecureDaoImp  extends AbstractJpaDAO<SecureStepUp> implements SecureDao{
	private Logger logger = LoggerFactory.getLogger(SecureDaoImp.class);

	@Override
	public String createSecure(SecureStepUp secureStepUp) throws SQLException {
		String response=null;
		try {

			SecureContactType contactType = (SecureContactType) this.entityManager.createNamedQuery(SecureContactType.FIND_BY_ID).setParameter("type", secureStepUp.getSecureContactType().getContactType()).getSingleResult();
			SecureStatusType statusType = (SecureStatusType) this.entityManager.createNamedQuery(SecureStatusType.FIND_BY_ID).setParameter("type", secureStepUp.getSecureStatusType().getStatusType()).getSingleResult();

			secureStepUp.setSecureStatusType(statusType);
			secureStepUp.setSecureContactType(contactType);

			this.create(secureStepUp);

			response=String.valueOf(secureStepUp.getGuid()); 

		}catch (Exception ex) {
			logger.error("creaet SecureStepup",ex);
			throw new AdapterException(ex.getCause());

		} catch (Throwable t) {
			logger.error("creaet SecureStepup",t);
			throw new AdapterException(t.getCause());

		}

		return response;
	}

	@Override
	public SecureStepUp findSecure(String guid) throws SQLException  {
		Query query = this.entityManager.createNamedQuery(SecureStepUp.FIND_BY_ID).setParameter("guid", guid);
		return getSingleResult(query);
	}

	@Override
	public SecureStepUp updateSecure(SecureStepUp stepUp)
			throws SQLException {

		SecureStepUp exsiting = (SecureStepUp) getSingleResult(this.entityManager.createNamedQuery(SecureStepUp.FIND_BY_ID).setParameter("guid", stepUp.getGuid()));
		if (exsiting !=null) {
			exsiting = stepUp;
			this.update(exsiting);
		}	

		return exsiting;
	}

	

	@Override
	public Boolean deleteSecure(String guid) throws SQLException {

		SecureStepUp exsiting = (SecureStepUp) getSingleResult(this.entityManager.createNamedQuery(SecureStepUp.FIND_BY_ID).setParameter("guid", guid));
		if (exsiting !=null) {
			entityManager.remove(exsiting);
			return true;
		}	

		return false;
	}

}
