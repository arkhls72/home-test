package com.home.digital.server.proxy.json;

import java.io.IOException;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.home.digital.server.proxy.HeaderRequestInterceptor;

/**
 * A Rest Client used by {@link ProxyController} to proxy requests to target end-points.
 * 
 * @author thomas
 *
 */
public class JsonProxyRestClient extends DefaultResponseErrorHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final RestTemplate restTemplate = new RestTemplate();

	private MappingJackson2HttpMessageConverter jsonMarshaller = new MappingJackson2HttpMessageConverter();

    /* error indicator */
    private boolean hasError = true;
    
    /* response code from the last invocation */
    private int responseCode;
    
    /* response text from the last invocation */
    private String responseText;

	private boolean initClient = false;
	
	protected boolean isJson = true;
	
	/**
	 * 
	 * @param hostname
	 */
	public JsonProxyRestClient() {

		if (initClient) {
			/*
			 * set a message converter
			 */
			restTemplate.getMessageConverters().add(jsonMarshaller);
			/*
			 * add any headers
			 */
			String mediaType = MediaType.APPLICATION_JSON_VALUE;
			restTemplate.getInterceptors().add(new HeaderRequestInterceptor(HttpHeaders.CONTENT_TYPE, mediaType));
			restTemplate.getInterceptors().add(new HeaderRequestInterceptor(HttpHeaders.ACCEPT, mediaType));
		}
		
		restTemplate.setErrorHandler(this);
	}
	
	/**
	 * 
	 * @param header
	 * @param value
	 */
	public final void addHeader(String header, String value) {
		HeaderRequestInterceptor headerRequestInterceptor = new HeaderRequestInterceptor(header, value);
		restTemplate.getInterceptors().add(headerRequestInterceptor);
	}

	/**
	 * 
	 * @param url
	 * @param object
	 * @return
	 */
	public final String postForLocation(String url, Object object) {

		URI uri = restTemplate.postForLocation(url, object);

		return uri.toString();
	}  

	/**
	 * 
	 * @param proxyUrl
	 * @param object
	 * @param clazz
	 * @return
	 */
	public final <T> T postForObject(String proxyUrl, Object object, Class<T> clazz) {
		return restTemplate.postForObject(proxyUrl, object, clazz);
	}
	
	/**
	 * 
	 * @param url
	 * @param clazz
	 * @return
	 */
	public final <T> T getForObject(String url, Class<T> clazz) {
		return restTemplate.getForObject(url, clazz);
	}

	/**
	 * 
	 * @param url
	 * @param object
	 */
	public final void put(String url, Object object) {
		restTemplate.put(url, object);
	}

	/**
	 * 
	 * @param url
	 */
	public final void delete(String url) {
		restTemplate.delete(url);
	}

	/**
	 * 
	 * @param uri
	 * @return
	 */
//	public final String getUrl(String uri) {
//		String url = hostname + uri;
//		logger.info("url [{}]", url);
//		return url;
//	}

    /**
     * Override to enable customer error resolution.
     */
	public final boolean hasError(ClientHttpResponse response) {
        try {
            responseCode = response.getStatusCode().value();
            responseText = response.getStatusText();
            if (logger.isDebugEnabled()) {
            	logger.debug("checking for response error : [{}] [{}]", responseCode, responseText);
            }
            hasError = super.hasError(response);
        } catch (IOException e) {
            hasError = true;
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        if (logger.isDebugEnabled()) {
        	logger.debug("response error [{}]", hasError);
        }
        return hasError;
    }

    /**
     * Override to exclude 404 NOT FOUND as an error.
     */
	protected final boolean hasError(HttpStatus statusCode) {
		boolean isError = 
				(statusCode.series() == HttpStatus.Series.CLIENT_ERROR || 
				statusCode.series() == HttpStatus.Series.SERVER_ERROR) && 
				statusCode != HttpStatus.NOT_FOUND;
		return isError;
	}

   /**
     * Get the response status code from the last request.
     * 
     * @return an Http status code
     */
    public final int getHttpResponseCode() {
        return responseCode;
    }

    /**
     * Get the response text from the last request.
     * 
     * @return response message text
     */
    public final String getHttpResponseText() {
        return responseText;
    }

    /**
     * Get the response status (code and text) from the last request.
     * 
     * @return response status
     */
    public final String getHttpResponseStatus() {
        return responseCode + " " + responseText;
    }
}
