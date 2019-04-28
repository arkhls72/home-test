
package com.home.digital.queue.ejb;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.digital.queue.util.EjbProperty;
import com.home.digital.queue.util.Property;
/**
 * loads ejb.properties file at startup and initialize the EjbProperty class
 * @author arash
 */

@Startup
@Singleton
public class InitConfigBean {
	private static Logger logger = LoggerFactory.getLogger(InitConfigBean.class);

	@PostConstruct
	private void init() throws Exception {
		logger.info("Initializing of ejb.properties at startup");
		 Map<Property,String> proeprties = EjbProperty.getProeprties();
		 
		if (MapUtils.isEmpty(proeprties)) {
			logger.error("empty ejbProperties.properties");
			throw new Exception("empty ejbProperties.properties");
		}	
	}
}	


