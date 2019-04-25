
package com.home.digital.server.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.server.config.ServiceConfig;

/**
 * It is application event listeners that implements ApplicationListener to listen to registered event in Spring context 
 * Implemented based on the standard {@code java.util.EventListener} interface for the Observer design pattern.
 * @ServiceConfig Indicates the class candidates for auto-detection when using annotation-based configuration and classpath scanning
 * @event  ContextClosedEvent
 * @author ralu 
 */
@ServiceConfig
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * Handle an application event.
     * @param event. The event to respond to
     */
    public void onApplicationEvent(ContextClosedEvent event) {
        logger.info("Received ContextClosedEvent");

        ApplicationContext applicationContext = event.getApplicationContext();
        String[] names = applicationContext.getBeanNamesForType(BaseAdapter.class);

        for (String name : names) {
            logger.info("Shutdown ServiceAdapter [{}]", name);
            BaseAdapter<?> baseServiceAdapter = (BaseAdapter<?>) applicationContext.getBean(name);
            baseServiceAdapter.onShutdown();
        }
    }
}