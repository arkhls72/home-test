
package com.home.digital.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import com.home.digital.server.config.ServiceConfig;
/**
 * It is application event listeners that implements ApplicationListener to listen to registered event in Spring context 
 * Implemented based on the standard {@code java.util.EventListener} interface for the Observer design pattern.
 * @ServiceConfig Indicates the class candidates for auto-detection when using annotation-based configuration and classpath scanning
 * @event  ContextStartedEvent
 * @author arash 
 */

@ServiceConfig
public class ContextStartedEventListener implements ApplicationListener<ContextStartedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * Handle an application event.
     * @param event. The event to respond to
     */
    public void onApplicationEvent(ContextStartedEvent event) {
        logger.info("Received ContextStartedEvent");
    }
}