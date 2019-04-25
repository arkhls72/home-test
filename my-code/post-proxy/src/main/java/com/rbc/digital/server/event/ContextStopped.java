package com.home.digital.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;

public class ContextStopped implements ApplicationListener<ContextStoppedEvent> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void onApplicationEvent(ContextStoppedEvent event) {
		if (logger.isDebugEnabled()) {
			logger.debug("ContextStoppedEvent Received");
		}
	}
}