
package com.home.digital.server.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;

import com.home.digital.common.config.ConfigInfo;

/**
 * StartupInit configures the ServletContext at Startup before
 * spring-context initializes its beans
 * 
 * @author arash
 * 
 */
public class StartupInit implements WebApplicationInitializer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Provides server context information and log to /var/secure-api.log file.
	 * It runs at startup before beans initialized. The following information
	 * are printed out: Application info, Host info, User info, Java system,
	 * Java class path and Java library path
	 */
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		logger.info("serverInfo   [{}]", servletContext.getServerInfo());
		logger.info("contextName  [{}]", servletContext.getServletContextName());
		logger.info("contextPath  [{}]", servletContext.getContextPath());

		ConfigInfo configInfo = new ConfigInfo();
		String configurationInfo = configInfo.getConfigInfo();
		logger.info(configurationInfo);

	}
}
