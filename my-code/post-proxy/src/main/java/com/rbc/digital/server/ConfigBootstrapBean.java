
package com.home.digital.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.home.digital.ProxyRuntimeException;
import com.home.digital.common.converter.JibxMessageConverter;
import com.home.digital.model.core.config.ApiHost;
import com.home.digital.model.core.config.ApiHostList;

/**
 * Bootstrap the security data on system startup.
 * 
 * @author thomas
 *
 */
public class ConfigBootstrapBean {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private String resourceName;
	
	/**
	 * @param resourceName the resourceName to set
	 */
	@Autowired
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/**
	 * Load the configuration information from the named resource.
	 * 
	 */
	public void configure() {	
		if (StringUtils.isEmpty(resourceName)) {
			throw new ProxyRuntimeException("resource [{}] is undefined", resourceName);
		}
		try {
			String xml = this.loadClasspathResource(resourceName);
			ApiHostList apiHostList = (ApiHostList) new JibxMessageConverter(
					ApiHostList.class).marshal(xml);

			for (ApiHost apiHost : apiHostList.getApiHostList()) {
				ApiHostMap.addMapping(apiHost.getApi(), apiHost.getHostname());
			}
		} catch (Exception e) {
			logger.error("configuration failed", e);
		}
	}
	
	private String loadClasspathResource(String resourceName) {
		if (logger.isInfoEnabled()) {
			logger.info("loading classpath resource [{}]", resourceName);
		}
		String string = null;
		BufferedReader br = null;
		try {
			Resource resource = new ClassPathResource(resourceName);
			InputStreamReader isr = new InputStreamReader(
					resource.getInputStream());
			br = new BufferedReader(isr, 1024);
			StringBuilder stringBuilder = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append('\n');
			}
			br.close();
			string = stringBuilder.toString();
			if (logger.isDebugEnabled()) {
				logger.debug("loaded resource\n{}", string);
			}
		} catch (IOException e) {
			throw new ProxyRuntimeException(
					"error loading classpath resource : " + resourceName);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
				}
			}
		}
		return string;
	}
}