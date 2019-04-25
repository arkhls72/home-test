package com.home.digital.server;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiHostMap {

	private static final Logger logger = LoggerFactory.getLogger(ApiHostMap.class);
	
	private static final Map<String,String> apiHostMap = new HashMap<>();
	
	public static final void addMapping(String api, String hostname) {
		if (logger.isInfoEnabled()) {
			logger.info("adding api host mapping [{}] -> [{}]", api, hostname);
		}
		apiHostMap.put(api, hostname);
	}
	
	public static final String removeMapping(String api) {
		if (logger.isInfoEnabled()) {
			logger.info("removing api host mapping for [{}]", api);
		}
		return apiHostMap.get(api);
	}
	
	public static final String getHostname(String api) {
		return apiHostMap.get(api);
	}
	
}
