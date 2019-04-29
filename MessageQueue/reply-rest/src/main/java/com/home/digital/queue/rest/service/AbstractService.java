

package com.home.digital.queue.rest.service;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Abstract class to provide InitialContext
 * @author arash
 *
 */
public class AbstractService {
	private static Logger logger = LoggerFactory.getLogger(AbstractService.class);
	
	/**
	 * 
	 * @return InitialContext
	 * @throws NamingException
	 */
	
	public InitialContext getInitialContext() throws NamingException {
		InitialContext context = null;

		try {
			context = new InitialContext();
		}catch (Exception ex) {
			logger.error("Error initial context",ex);
		}

		return context;
	}	
}
