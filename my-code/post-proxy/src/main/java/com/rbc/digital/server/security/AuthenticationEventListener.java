package com.home.digital.server.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.security.web.authentication.switchuser.AuthenticationSwitchUserEvent;
import org.springframework.util.ClassUtils;

import com.home.digital.model.core.security.SecurityEvent;

/**
 * AuthenticationEventListener listens for authentication events and logs them
 * using the registered SecurityEventLogger.
 * 
 * @author thomas
 * 
 */
public class AuthenticationEventListener implements ApplicationListener<AbstractAuthenticationEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private SecurityEventLogger securityEventLogger;
	
	/**
	 * @param securityEventLogger the securityEventLogger to set
	 */
	public void setSecurityEventLogger(SecurityEventLogger securityEventLogger) {
		logger.debug("setting securityEventLogger");
		this.securityEventLogger = securityEventLogger;
	}
	
	@Override
    public void onApplicationEvent(AbstractAuthenticationEvent event) {
		
		logger.debug("logging event {}", event);
		
		if (securityEventLogger == null) {
			return;
		}
        
		String eventType = ClassUtils.getShortName(event.getClass());
		
		if (logger.isDebugEnabled()) {
			logger.debug("handling [{}]", eventType);
		}
		
		String username = event.getAuthentication().getName();
		String remoteAddress = null;
		String sessionId = null;
		String details = null;
		long timeStamp = event.getTimestamp();
		Date eventTimestamp = new Date(timeStamp);
		Object detailObject = event.getAuthentication().getDetails();
		
		if (detailObject instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails wad = (WebAuthenticationDetails) detailObject;
			remoteAddress = wad.getRemoteAddress();
			sessionId = wad.getSessionId();
		} 
        
		/*
		 * Events to be logged (pretty much all for now...)
		 * AuthenticationSuccessEvent AuthenticationSwitchUserEvent
		 * InteractiveAuthenticationSuccessEvent, SessionFixationProtectionEvent
		 * AuthenticationFailureBadCredentialsEvent
		 * AuthenticationFailureCredentialsExpiredEvent
		 * AuthenticationFailureDisabledEvent AuthenticationFailureExpiredEvent
		 * AuthenticationFailureLockedEvent
		 * AuthenticationFailureProviderNotFoundEvent
		 * AuthenticationFailureProxyUntrustedEvent
		 * AuthenticationFailureServiceExceptionEvent
		 */

		if (event instanceof AbstractAuthenticationFailureEvent) {
			details = detailObject.toString();
		} else if (event instanceof AuthenticationSuccessEvent) {
			// nothing exceptional required
		} else if (event instanceof AuthenticationSwitchUserEvent) {
			// nothing exceptional required
		} else if (event instanceof InteractiveAuthenticationSuccessEvent) {
			// nothing exceptional required
		} else if (event instanceof SessionFixationProtectionEvent) {
			// nothing exceptional required
		}
		SecurityEvent securityEvent = new SecurityEvent();
		securityEvent.setEventTimestamp(eventTimestamp);
		securityEvent.setEventType(eventType);
		securityEvent.setUserName(username);
		securityEvent.setSessionId(sessionId);
		securityEvent.setRemoteAddress(remoteAddress);
		securityEvent.setDetails(details);
		
		securityEventLogger.logEvent(securityEvent);
    }

}
