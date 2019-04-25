package com.home.digital.server.proxy;

import org.springframework.http.HttpMethod;
/**
 * Mapping constant org.spring.framework.HttpMethod to proxy passing method
 * @author arash
 */
public enum ProxyMethod {
	GET("GET",HttpMethod.GET),
	POST("POST",HttpMethod.POST),
	DELETE("DELETE",HttpMethod.DELETE),
	TRACE("TRACE",HttpMethod.TRACE),
	PUT("PUT",HttpMethod.PUT),
	OPTIONS("OPTIONS",HttpMethod.OPTIONS);
	
	private String code;
	private HttpMethod method;

	private ProxyMethod(String code,HttpMethod method) {
		this.code = code;
		this.method = method;
	}
	
	public String getCode() {
		return code;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public static ProxyMethod parseByCode(String code) {
		for (ProxyMethod item:ProxyMethod.values()) {
			if (item.getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
