package com.home.digital.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.digital.core.service.ProxyServiceException;
import com.home.digital.core.service.SecurityService;
import com.home.digital.model.core.security.SecurityEvent;

public class SecurityEventLogger {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private Logger securityLogger = LoggerFactory.getLogger("security-logger");

	private SecurityService securityService;

	private boolean enableDatabaseLogger;

	private boolean enableFileLogger;

	/**
	 * Set the SecurityService implementation to use for logging.
	 * 
	 * @param securityService
	 *            the SecurityService
	 */
	public void setSecurityService(SecurityService securityService) {
		logger.debug("setting securityService");
		this.securityService = securityService;
	}

	/**
	 * @param enableDatabaseLogger the enableDatabaseLogger to set
	 */
	public void setEnableDatabaseLogger(boolean enableDatabaseLogger) {
		logger.debug("setting enableDatabaseLogger");
		this.enableDatabaseLogger = enableDatabaseLogger;
	}

	/**
	 * @param enableFileLogger the enableFileLogger to set
	 */
	public void setEnableFileLogger(boolean enableFileLogger) {
		logger.debug("setting enableFileLogger");
		this.enableFileLogger = enableFileLogger;
	}

	public void logEvent(SecurityEvent securityEvent) {

		if (enableDatabaseLogger) {
			try {
				securityService.createSecurityEvent(securityEvent);
			} catch (ProxyServiceException e) {
				logger.error("logging security event", e);
			}
		}

		if (enableFileLogger) {
			StringBuilder sb = new StringBuilder();

			sb.append(securityEvent.getEventTimestamp());
			sb.append("|");
			sb.append(securityEvent.getEventType());
			sb.append("|");
			sb.append(securityEvent.getUserName());
			sb.append("|");
			sb.append(securityEvent.getSessionId());
			sb.append("|");
			sb.append(securityEvent.getRemoteAddress());
			sb.append("|");
			sb.append(securityEvent.getDetails());

			securityLogger.info(sb.toString());
		}
	}
}
