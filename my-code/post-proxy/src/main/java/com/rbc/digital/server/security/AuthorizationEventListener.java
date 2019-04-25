package com.home.digital.server.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.access.event.AbstractAuthorizationEvent;
import org.springframework.security.access.event.AuthenticationCredentialsNotFoundEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.access.event.AuthorizedEvent;
import org.springframework.security.access.event.PublicInvocationEvent;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.ClassUtils;

import com.home.digital.model.core.security.SecurityEvent;

/**
 * AuthorizationEventListener listens for authorization events and logs them
 * using the registered SecurityEventLogger.
 * 
 * @author thomas
 * 
 */
public class AuthorizationEventListener implements ApplicationListener<AbstractAuthorizationEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SecurityEventLogger securityEventLogger;
	
	/**
	 * @param securityEventLogger the securityEventLogger to set
	 */
	public void setSecurityEventLogger(SecurityEventLogger securityEventLogger) {
		this.securityEventLogger = securityEventLogger;
	}
	
	@Override
    public void onApplicationEvent(AbstractAuthorizationEvent event) {

		if (securityEventLogger == null) {
			return;
		}
        
		String eventType = ClassUtils.getShortName(event.getClass());
		
		if (logger.isDebugEnabled()) {
			logger.debug("handling [{}]", eventType);
		}
		
		String username = null;
    	String remoteAddress = null;
    	String sessionId = null;
    	String details = null;
    	long timeStamp = event.getTimestamp();
		Date eventTimestamp = new Date(timeStamp);

        if (event instanceof AuthenticationCredentialsNotFoundEvent) {
            AuthenticationCredentialsNotFoundEvent authEvent = (AuthenticationCredentialsNotFoundEvent) event;
			details = "secure object: " + authEvent.getSource() + "; configuration attributes: "
					+ authEvent.getConfigAttributes();
			
    		SecurityEvent securityEvent = new SecurityEvent();
    		securityEvent.setEventTimestamp(eventTimestamp);
    		securityEvent.setEventType(eventType);
    		securityEvent.setUserName(username);
    		securityEvent.setSessionId(sessionId);
    		securityEvent.setRemoteAddress(remoteAddress);
    		securityEvent.setDetails(details);
    		
    		securityEventLogger.logEvent(securityEvent);
    		
        } else if (event instanceof AuthorizationFailureEvent) {
            AuthorizationFailureEvent authEvent = (AuthorizationFailureEvent) event;

			username = authEvent.getAuthentication().getName();
			Object detailObject = authEvent.getAuthentication().getDetails();
			if (detailObject instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails wad = (WebAuthenticationDetails) detailObject;
				remoteAddress = wad.getRemoteAddress();
				sessionId = wad.getSessionId();
				details = authEvent.getSource().toString();
			} else {
				details = detailObject.toString();
			}
			
			SecurityEvent securityEvent = new SecurityEvent();
			securityEvent.setEventTimestamp(eventTimestamp);
    		securityEvent.setEventType(eventType);
			securityEvent.setUserName(username);
			securityEvent.setSessionId(sessionId);
			securityEvent.setRemoteAddress(remoteAddress);
			securityEvent.setDetails(details);
			
			securityEventLogger.logEvent(securityEvent);

        } else if (event instanceof AuthorizedEvent) {
            AuthorizedEvent authEvent = (AuthorizedEvent) event;

			username = authEvent.getAuthentication().getName();
			Object detailObject = authEvent.getAuthentication().getDetails();
			if (detailObject instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails wad = (WebAuthenticationDetails) detailObject;
				remoteAddress = wad.getRemoteAddress();
				sessionId = wad.getSessionId();
				details = authEvent.getSource().toString();
			} else {
				details = detailObject.toString();
			}

			SecurityEvent securityEvent = new SecurityEvent();
			securityEvent.setEventTimestamp(eventTimestamp);
    		securityEvent.setEventType(eventType);
			securityEvent.setUserName(username);
			securityEvent.setSessionId(sessionId);
			securityEvent.setRemoteAddress(remoteAddress);
			securityEvent.setDetails(details);
			
			securityEventLogger.logEvent(securityEvent);

        } else if (event instanceof PublicInvocationEvent) {
            /*
            if (logger.isInfoEnabled()) {
                logger.info("XXX Authorization Event: Security interception not required for public secure object: " + authEvent.getSource());
            }
			*/
        }
    }
}