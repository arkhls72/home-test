
package com.home.digital.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

/**
 * HeaderRequestInterceptor used to populate the HTTPRequest headers.
 * 
 * @author thomas
 *
 */
public class HeaderRequestInterceptor implements ClientHttpRequestInterceptor {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private String header;
	private String value;

	/**
	 * Construct an HeaderRequestInterceptor
	 * 
	 * @param header the header 
	 * @param value the value 
	 */
	public HeaderRequestInterceptor(String header, String value) {
		this.header = header;
		this.value = value;
		if (logger.isDebugEnabled()) {
			logger.debug("header [{}] value [{}]", header, value);
		}
	}

    @Override
    public ClientHttpResponse intercept(
            HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        HttpHeaders headers = request.getHeaders();
        headers.add(header, value);
		if (logger.isDebugEnabled()) {
			logger.debug("added header [{}] value [{}]", header, value);
		}
        return execution.execute(request, body);
    }

	/**
	 * 
	 * @param value the header value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}