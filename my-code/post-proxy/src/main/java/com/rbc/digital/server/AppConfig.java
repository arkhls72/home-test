package com.home.digital.server;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Implementation of ApplicationContextAware used to log the application's
 * configuration.
 * 
 * @author thomas
 * 
 */
public class AppConfig implements ApplicationContextAware {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long MILLISECOND = 1;
	private static final long SECOND_MILLIS = MILLISECOND * 1000;
	private static final long MINUTE_MILLIS = SECOND_MILLIS * 60;
	private static final long HOUR_MILLIS = MINUTE_MILLIS * 60;
	private static final long DAY_MILLIS = HOUR_MILLIS * 24;

	private static long startTime = System.currentTimeMillis();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

		String[] names = applicationContext.getBeanDefinitionNames();
		Object[] beans = new Object[names.length];

		// report the beans in use
		if (logger.isDebugEnabled()) {
			for (int i = 0; i < names.length; i++) {
				Object bean = applicationContext.getBean(names[i]);
				beans[i] = bean;
			}

			for (int i = 0; i < names.length; i++) {
				logger.debug("Bean name [{}] -> [{}]", names[i], beans[i]);
			}
		}
	}

	/**
	 * Get the application start time.
	 * 
	 * @return a formatted Date String
	 */
	public static String startTime() {
		return new Date(startTime).toString();
	}

	/**
	 * Get the application cumulative uptime.
	 * 
	 * <pre>
	 * Formatted as 000 days, 00 hours, 00 minutes, 00 seconds
	 * </pre>
	 * 
	 * @return a formatted String
	 */
	public static String upTime() {
		long now = System.currentTimeMillis();
		long elapsed = now - startTime;

		long days = elapsed / DAY_MILLIS;
		elapsed = elapsed % DAY_MILLIS;
		long hours = elapsed / HOUR_MILLIS;
		elapsed = elapsed % HOUR_MILLIS;
		long minutes = elapsed / MINUTE_MILLIS;
		elapsed = elapsed % MINUTE_MILLIS;
		long seconds = elapsed / SECOND_MILLIS;

		String up = String.format("%3d days, %2d hours, %2d minutes, %2d seconds", days, hours, minutes, seconds);

		return up;
	}
}
