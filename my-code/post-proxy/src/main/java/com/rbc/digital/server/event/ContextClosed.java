package com.home.digital.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class ContextClosed implements ApplicationListener<ContextClosedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void onApplicationEvent(ContextClosedEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("ContextClosedEvent Received");
		}
	}
}