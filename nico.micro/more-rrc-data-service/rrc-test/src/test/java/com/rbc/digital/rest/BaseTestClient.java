
package com.home.digital.rest;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

/**
 * Base class for Rest Client testing
 * 
 * @author thomas
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:rest-client-context.xml" })
public class BaseTestClient {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	protected final RestTemplate restTemplate = new RestTemplate();

	protected final RestClientErrorHandler restClientErrorHandler = new RestClientErrorHandler();
	
	protected final MappingJackson2HttpMessageConverter jsonMarshaller = new MappingJackson2HttpMessageConverter();

	@Autowired
	private String hostname;
	
	@Autowired
	private String headerAccept = MediaType.APPLICATION_JSON_VALUE;

	@Autowired
	private String headerContentType = MediaType.APPLICATION_JSON_VALUE;

	@Before
	public void setUp() {
		restTemplate.setErrorHandler(restClientErrorHandler);
		/*
		 * set a message converter
		 */
		restTemplate.getMessageConverters().add(jsonMarshaller);
		/*
		 * set any headers
		 */
		HeaderRequestInterceptor acceptHeaderRequestInterceptor = new HeaderRequestInterceptor("Accept", headerAccept);
		HeaderRequestInterceptor contentTypeHeaderRequestInterceptor = new HeaderRequestInterceptor("ContentType", headerContentType);
		restTemplate.getInterceptors().add(acceptHeaderRequestInterceptor);
		restTemplate.getInterceptors().add(contentTypeHeaderRequestInterceptor);

		logger.info("host name  [{}]", hostname);;
		logger.info("media type [{}]", headerAccept);;
		
	}

	protected String getUrl(String uri) {
		String url = hostname + uri;
		logger.info("url [{}]", url);
		return url;
	}
}
