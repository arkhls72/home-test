package com.home.digital.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class ContextRefreshed implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("ContextRefreshedEvent Received");
		}
	}
}