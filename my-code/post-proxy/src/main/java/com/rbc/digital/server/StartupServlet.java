package com.home.digital.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.digital.common.ConfigInfo;

/**
 * Start-up servlet used to report configuration information.
 * 
 * @author thomas
 *
 */
public class StartupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3496625584527372001L;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	public void init(ServletConfig servletConfig) throws ServletException {
		
		String serverInfo = servletConfig.getServletContext().getServerInfo();
		String contextName = servletConfig.getServletContext().getServletContextName();
		String contextPath = servletConfig.getServletContext().getContextPath();

		logger.info("serverInfo  [{}]", serverInfo);
		logger.info("contextName [{}]", contextName);
		logger.info("contextPath [{}]", contextPath);

		String configInfo = new ConfigInfo().getConfigInfo();
		if (logger.isInfoEnabled()) {
			logger.info(configInfo);
		}
	}

//	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//	}
}
