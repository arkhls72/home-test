package com.home.digital.server.security;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.util.ClassUtils;

import com.home.digital.model.core.security.SecurityEvent;

/**
 * LogoutEventListener listens for logout events and logs them using the
 * registered SecurityEventLogger.
 * 
 * @author thomas
 * 
 */
public class LogoutEventListener implements ApplicationListener<SessionDestroyedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private SecurityEventLogger securityEventLogger;
	
	/**
	 * @param securityEventLogger the securityEventLogger to set
	 */
	public void setSecurityEventLogger(SecurityEventLogger securityEventLogger) {
		this.securityEventLogger = securityEventLogger;
	}
	
	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {

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
		long timeStamp;

		sessionId = event.getId();
		timeStamp = event.getTimestamp();
		Date eventTimestamp = new Date(timeStamp);

		List<SecurityContext> securityContexts = event.getSecurityContexts();

		/* should typically be one credential however... run them all in case */
		for (SecurityContext securityContext : securityContexts) {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication == null) {
				logger.warn("authentication is null - nothing to log");
				break;
			}
			
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			username = userDetails.getUsername();
			if (logger.isDebugEnabled()) {
				logger.debug("session destroyed for [{}]", userDetails.getUsername());
			}
			
			Object detailObject = securityContext.getAuthentication().getDetails();
			if (detailObject instanceof WebAuthenticationDetails) {
				WebAuthenticationDetails wad = (WebAuthenticationDetails) detailObject;
				remoteAddress = wad.getRemoteAddress();
				sessionId = wad.getSessionId();
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
		}
	}
	
}