package com.home.digital.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.home.digital.core.audit.CredentialProvider;

/**
 * An implementation of {@link CredentialProvider} that extracts the username
 * from the {@link SecurityContextHolder} in the ThreadLocal context. This is
 * defined the Spring configuration context.
 * 
 * @author thomas
 */
public class UsernameProvider implements CredentialProvider {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public String getUsername() {
		String username = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				username = authentication.getName();
			}
		}
		return username;
	}

}
