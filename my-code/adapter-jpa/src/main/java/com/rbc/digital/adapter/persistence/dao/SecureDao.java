package com.home.digital.adapter.persistence.dao;

import java.sql.SQLException;

import com.home.digital.adapter.persistence.model.SecureStepUp;

/**
 * StepAuthentocation Data Access Object
 * 
 * @author arash
 *
 */
public interface SecureDao {

	/**
	 * Create an Account.
	 * 
	 * @param account the source Account object
	 * @return a unique identifier for the Account
	 * @throws YoyoStoreException on any data store errors
	 */
	String createSecure(SecureStepUp secureStepUp) throws SQLException;

	/**
	 * Retrieve an Account.
	 * 
	 * @param accountId unique identifier of the Account
	 * @return a Account structure
	 * @throws YoyoStoreException on any data store errors
	 */
	SecureStepUp findSecure(String guid) throws Exception;

	
	/**
	 * Update an Account..
	 * 
	 * @param account the Account to update
	 * @return the number of rows updated
	 * @throws YoyoStoreException on any data store errors
	 */
	SecureStepUp updateSecure(SecureStepUp stepAuth) throws SQLException;

	Boolean deleteSecure(String guid) throws SQLException;
}
