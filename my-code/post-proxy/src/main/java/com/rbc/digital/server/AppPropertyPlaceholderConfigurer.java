package com.home.digital.server;

import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * Extended PropertyPlaceholderConfigurer that provides access to the loaded properties.
 * 
 * @author thomas
 *
 */
public class AppPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Properties loadedProperties;
	
	@Override
	public void afterPropertiesSet() throws IOException {
		if (logger.isDebugEnabled()) {
			loadedProperties = this.mergeProperties();
			for (Entry<Object, Object> singleProperty : loadedProperties.entrySet()) {
				String key = (String) singleProperty.getKey();
				Object value = singleProperty.getValue();

				if (key.toLowerCase().contains("password")) {
					logger.debug("property [{}] [{}]", key, "********");
				} else {
					logger.debug("property [{}] [{}]", key, value);
				}
			}
		}
	}
	
	/**
	 * Get the loaded properties
	 * 
	 * @return the loaded properties
	 */
	public Properties getLoadedProperties() {
		return this.loadedProperties;
	}
}
