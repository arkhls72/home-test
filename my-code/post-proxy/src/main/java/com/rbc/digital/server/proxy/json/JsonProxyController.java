package com.home.digital.server.proxy.json;

import java.net.URI;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.home.digital.common.converter.JsonMessageConverter;
import com.home.digital.common.model.ProxyRequest;
import com.home.digital.common.model.ProxyResponse;
import com.home.digital.server.proxy.ProxyApiException;
import com.home.digital.server.proxy.ProxyMethod;
import com.home.digital.server.proxy.ProxyPreconditionException;
import com.home.digital.server.proxy.ProxyUtility;

/**
 * REST Controller that serves as a JSON Proxy for REST operations.
 * 
 * <p>
 * A {@link JsonProxyRestClient} is used to send the wrapped request to target
 * REST API end-point.
 * </p>
 * 
 * <p>
 * Future enhancements may include support for XML.
 * </p>
 * 
 * @author thomas
 * 
 */
@Controller
public class JsonProxyController extends BaseController { 
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final ProxyResponse emptyProxyResponse = new ProxyResponse();

	
	@Autowired
	private JsonMessageConverter commonJsonMessageConverter; 
	@Autowired
	 private RestTemplate restTemplate;
	
	/**
	 * Handle a JSON Proxy Request.
	 * 
	 * <p>
	 * This method return a status of 200 on success and 500 on error. To be
	 * fully compliant it should return a 201 on success however, due to
	 * upstream infrastructure limitations, only 200 and 500 are acceptable.
	 * This should be correct in future when practical.
	 * </p>
	 * 
	 * @param proxyRequest
	 *            the ProxyRequest
	 * @param request
	 *            the HttpServletRequest
	 * @param response
	 *            the HttpServletResponse
	 * @return a ProxyResponse and an HttpStatus of 200 on success and 500 on error
	 * @throws Exception
	 *             on any errors
	 */
	@RequestMapping(value = "/json-proxy", method = RequestMethod.POST)
	@ResponseBody
	public ProxyResponse proxyJsonPost(@RequestBody ProxyRequest proxyRequest, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// should be SC_CREATED !
		int httpStatus = HttpServletResponse.SC_OK;
		ProxyResponse proxyResponse = emptyProxyResponse;
		long startTime = System.currentTimeMillis();
		try {

			String method = proxyRequest.getMethod();
			ProxyMethod proxyMethod = ProxyMethod.parseByCode(method);

			String remoteAddr = request.getRemoteAddr();
			logger.info("proxying [{}] request from [{}]", method, remoteAddr);

			HttpMethod httpMethod = proxyMethod.getMethod();
			if (httpMethod == null) {
				logger.error("bad request method [{}]", method);
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return proxyResponse;
			}

			ProxyUtility.headerCheck(proxyRequest, request);
			ProxyUtility.setProxyUrl(proxyRequest);

			HttpEntity<?> requestEntity = this.getRequestEntity(proxyRequest, request);
			URI uri = UriComponentsBuilder.fromUriString(proxyRequest.getUrl()).build().toUri();

			ResponseEntity<?> resposneEntity = restTemplate.exchange(uri, httpMethod, requestEntity, Object.class);
			httpStatus = resposneEntity.getStatusCode().value();

			switch (proxyMethod) {
				case DELETE:
					break;
				default:
					if (!resposneEntity.hasBody() || resposneEntity.getStatusCode().value() !=HttpStatus.OK.value()) {
						response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
						return proxyResponse;
					} else {
						proxyResponse = new ProxyResponse();
						proxyResponse.setResponseBody(resposneEntity.getBody());	
					}
					break;
			}

			if (resposneEntity.getHeaders() !=null && resposneEntity.getHeaders().getLocation() !=null) {
				proxyResponse.setLocation(resposneEntity.getHeaders().getLocation().toString());	
			}

			proxyResponse.setMethod(method);
			proxyResponse.setUrl(uri.toString());
			proxyResponse.setHttpStatus(httpStatus);			
			proxyResponse.setResponseHeaders(ProxyUtility.getResponseHeaders(resposneEntity.getHeaders()));

			response.setStatus(httpStatus);

		} catch (ProxyPreconditionException e) {
			logger.error(e.getMessage());
			proxyResponse = new ProxyResponse();
			proxyResponse.setMethod(proxyRequest.getMethod());
			proxyResponse.setUrl(proxyRequest.getUrl());
			proxyResponse.setHttpStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
		} catch (ProxyApiException e) {
			logger.error(e.getMessage());
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			proxyResponse = new ProxyResponse();
			proxyResponse.setMethod(proxyRequest.getMethod());
			proxyResponse.setUrl(proxyRequest.getUrl());
			proxyResponse.setHttpStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			logger.error(e.toString());
			e.printStackTrace();
		}
		
		long endTime = System.currentTimeMillis();
		logger.info("returning http status [{}] proxy response status [{}] elapsed [{}]", httpStatus, proxyResponse.getHttpStatus(), endTime - startTime);
		return proxyResponse;
	}
	
	private HttpEntity<?>  getRequestEntity (ProxyRequest proxyRequest,HttpServletRequest request) {
		HttpEntity<?> response = null;

		ProxyMethod proxyMethod = ProxyMethod.parseByCode(proxyRequest.getMethod());
		HttpHeaders allHeaders = ProxyUtility.getHttpHeaders(proxyRequest.getRequestHeaders(),request);

		switch (proxyMethod) {
			case DELETE:
				 response=  new HttpEntity<Object>(allHeaders);
				 break;
			default:
				response = new HttpEntity<String>(proxyRequest.getRequestBody(), allHeaders);
				break;
		}

		return response;
	}

//	switch (httpMethod) {
//	case POST:
//		proxyResponse = doPost(proxyRequest, request, response);
//		break;
//	case GET:
//		proxyResponse = doGet(proxyRequest, request, response);
//		break;
//	case PUT:
//		proxyResponse = doPut(proxyRequest, request, response);
//		break;
//	case DELETE:
//		proxyResponse = doDelete(proxyRequest, request, response);
//		break;
//	// ----------------------------------------
//	// unsupported methods
//	// ----------------------------------------
//	case PATCH:
//	// case HEAD:
//	// case OPTIONS:
//	// case TRACE:
//	default:
//		logger.error("unsupported method [{}]", method);
//		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
//		return proxyResponse;
//	}
//
//	if (logger.isDebugEnabled()) {
//		unmarshal(proxyResponse, ProxyResponse.class);
//	}	
//	
//	
//	switch (httpMethod) {
//	case POST:
//		proxyResponse = doPost(proxyRequest);
//		break;
//	case GET:
//		proxyResponse = doGet(proxyRequest);
//		break;
//	case PUT:
//		proxyResponse = doPut(proxyRequest);
//		break;
//	case DELETE:
//		proxyResponse = doDelete(proxyRequest);
//		break;
	// ----------------------------------------
	// unsupported methods
	// ----------------------------------------
//	case PATCH:
//	// case HEAD:
//	// case OPTIONS:
//	// case TRACE:
//	default:
//		logger.error("unsupported method [{}]", method);
//		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
//		return proxyResponse;
//	}
//
//	if (logger.isDebugEnabled()) {
//		unmarshal(proxyResponse, ProxyResponse.class);
//	}
	
//	private ProxyResponse doPost(ProxyRequest proxyRequest) {
//		String method = proxyRequest.getMethod();
//		String url = proxyRequest.getUrl();
//		String requestBody = proxyRequest.getRequestBody();
//		if (logger.isDebugEnabled()) {
//			logger.debug("ProxyRequest: method [{}] url [{}] request body\n{}", method, url, requestBody);
//		}
//
//		//----------------------------------------
//		// send the request
//		//----------------------------------------
//		JsonProxyRestClient restClient = getProxyRestClient(proxyRequest);
//		
//		Object object = this.marshal(requestBody, Object.class); 
//		
//		String location = "";
//		try {
//			location = restClient.postForLocation(url, object);
//		} catch (Exception e) {
//			logger.error("proxy POST failed", e);
//		}
//
//		int httpStatus = restClient.getHttpResponseCode();
//		if (logger.isDebugEnabled()) {
//			logger.debug("POST for location [{}] status [{}]", location, httpStatus);
//		}
//		
//		//----------------------------------------
//		// process the response
//		//----------------------------------------
//		ProxyResponse proxyResponse = new ProxyResponse();
//		proxyResponse.setMethod(method);
//		proxyResponse.setUrl(url);
//		proxyResponse.setResponseBody(null);
//		proxyResponse.setHttpStatus(httpStatus);
//		proxyResponse.setLocation(location);
//		
//		return proxyResponse;
//	}
//
//	private ProxyResponse doGet(ProxyRequest proxyRequest) {
//		String method = proxyRequest.getMethod();
//		String url = proxyRequest.getUrl();
//		if (logger.isDebugEnabled()) {
//			logger.debug("ProxyRequest: method [{}] url [{}]", method, url);
//		}
//		
//		//----------------------------------------
//		// send the request
//		//----------------------------------------
//		JsonProxyRestClient restClient = getProxyRestClient(proxyRequest);
//		
//		String responseBody = "";
//		try {
//			responseBody = restClient.getForObject(url, String.class);
//		} catch (Exception e) {
//			logger.error("proxy GET failed", e);
//		}
//
//		int httpStatus = restClient.getHttpResponseCode();
//		if (logger.isDebugEnabled()) {
//			logger.debug("GET for object status [{}]", httpStatus);
//		}
//		
//		//----------------------------------------
//		// process the response
//		//----------------------------------------
//		ProxyResponse proxyResponse = new ProxyResponse();
//		proxyResponse.setMethod(method);
//		proxyResponse.setUrl(url);
//		proxyResponse.setResponseBody(null);
//		proxyResponse.setHttpStatus(httpStatus);
//		proxyResponse.setResponseBody(responseBody);
//		
//		return proxyResponse;
//	}
//
//	private ProxyResponse doPut(ProxyRequest proxyRequest) {
//		String method = proxyRequest.getMethod();
//		String url = proxyRequest.getUrl();
//		String requestBody = proxyRequest.getRequestBody();
//		if (logger.isDebugEnabled()) {
//			logger.debug("ProxyRequest: method [{}] url [{}] request body\n{}", method, url, requestBody);
//		}
//		
//		//----------------------------------------
//		// send the request
//		//----------------------------------------
//		JsonProxyRestClient restClient = getProxyRestClient(proxyRequest);
//		
//		Object object = this.marshal(requestBody, Object.class); 
//		
//		try {
//			restClient.put(url, object);
//		} catch (Exception e) {
//			logger.error("proxy PUT failed", e);
//		}
//
//		int httpStatus = restClient.getHttpResponseCode();
//		if (logger.isDebugEnabled()) {
//			logger.debug("PUT status [{}]", httpStatus);
//		}
//
//		//----------------------------------------
//		// process the response
//		//----------------------------------------
//		ProxyResponse proxyResponse = new ProxyResponse();
//		proxyResponse.setMethod(method);
//		proxyResponse.setUrl(url);
//		proxyResponse.setResponseBody(null);
//		proxyResponse.setHttpStatus(httpStatus);
//		
//		return proxyResponse;
//	}
//
//	private ProxyResponse doDelete(ProxyRequest proxyRequest) {
//		String method = proxyRequest.getMethod();
//		String url = proxyRequest.getUrl();
//		if (logger.isDebugEnabled()) {
//			logger.debug("ProxyRequest: method [{}] url [{}]", method, url);
//		}
//		
//		//----------------------------------------
//		// send the request
//		//----------------------------------------
//		JsonProxyRestClient restClient = getProxyRestClient(proxyRequest);
//		
//		try {
//			restClient.delete(url);
//		} catch (Exception e) {
//			logger.error("proxy DELETE failed", e);
//		}
//
//		int httpStatus = restClient.getHttpResponseCode();
//		if (logger.isDebugEnabled()) {
//			logger.debug("DELETE status [{}]", httpStatus);
//		}
//
//		//----------------------------------------
//		// process the response
//		//----------------------------------------
//		ProxyResponse proxyResponse = new ProxyResponse();
//		proxyResponse.setMethod(method);
//		proxyResponse.setUrl(url);
//		proxyResponse.setResponseBody(null);
//		proxyResponse.setHttpStatus(httpStatus);
//		
//		return proxyResponse;
//	}
//	
//	private JsonProxyRestClient getProxyRestClient(ProxyRequest proxyRequest) {
//		JsonProxyRestClient restClient = new JsonProxyRestClient();
//		
//		Map<String, String> requestHeaders = proxyRequest.getRequestHeaders();
//		for (String header : requestHeaders.keySet()) {
//			String value = requestHeaders.get(header);
//			restClient.addHeader(header, value);
//			if (logger.isDebugEnabled()) {
//				logger.debug("added header [{}] [{}]", header, value);
//			}
//		}
//		return restClient;
//	}
	
//	private String unmarshal(Object object, Class<?> clazz) {
//		String source = null;
//		try {
//			source = new JsonMessageConverter(clazz).unmarshal(object);
//			if (logger.isDebugEnabled()) {
//				logger.debug("unmarshalled object [{}] to\n{}", object.getClass().getSimpleName(), source);
//			}
//		} catch (RuntimeException e) {
//			logger.error("converter error", e);
//		}
//		return source;
//	}
//
//	private Object marshal(String source, Class<?> clazz) {
//		Object object = null;
//		try {
//			object = new JsonMessageConverter(clazz).marshal(source);
//			if (logger.isDebugEnabled()) {
//				logger.debug("marshaled\n{} to [{}]", source, object.getClass().getSimpleName());
//			}
//		} catch (RuntimeException e) {
//			logger.error("converter error", e);
//		}
//		return object;
//	}
	
	
}
