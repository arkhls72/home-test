package com.home.digital.server.proxy;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.home.digital.common.model.ProxyRequest;
import com.home.digital.server.ApiHostMap;
/**
 * Utilities to process HttpRequest and HttpResponse headers. 
 * @author arash
 *
 */
public class ProxyUtility {
	private static Logger logger = LoggerFactory.getLogger(ProxyUtility.class);
	private static final String CLIENT_ID_HEADER = "clientId";

	public static HttpHeaders getHttpHeaders(Map<String, String> proxyHeaders,HttpServletRequest request) {
		Map<String,String> httpRequestHeaderMap = getRequestHeaders(request);
        HttpHeaders header = new HttpHeaders();

        header.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);

        header.setAccept(acceptableMediaTypes);
        for (String item:httpRequestHeaderMap.keySet()) {        	
        	if (!item.equals("content-type") && !item.equals("accept")) {
        		header.add(item, httpRequestHeaderMap.get(item));	
        	}
        }
        for (String item:proxyHeaders.keySet()) {
        	if (httpRequestHeaderMap.get(item) == null && !item.equals("content-type") && !item.equals("accept")) {
        		header.add(item, proxyHeaders.get(item));	
        	}
        }
        return header;
    }	
	
	public static Map<String,String> getResponseHeaders(final HttpHeaders headers) {
		Map<String,String> map = new HashMap<String, String>();
		if (headers ==null) {
			return map;
		}
		
		Enumeration<?> lists = fromIterator(headers.keySet().iterator());
		while (lists.hasMoreElements()) {
            String key = (String) lists.nextElement();
            map.put(key, headers.get(key).get(0));
        }
		return map;
	}

	private  static  Map<String,String> getRequestHeaders(HttpServletRequest request) {
        Map<String,String> map = new HashMap<String, String>();

        Enumeration<String> headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String key = headers.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }

        return map;
    }	
	
	 private static Enumeration<?> fromIterator(final Iterator<?> iterator) { 
		return new Enumeration<Object>() { 
				@Override 
				public boolean hasMoreElements() { 
					return iterator.hasNext(); 
				} 
	 
				@Override 
				public Object nextElement() { 
						return iterator.next(); 
				} 
			}; 
	 } 	
	 /**
		 * Perform a check on the header contents to ensure that:
		 * 
		 * <ol>
		 * <li>if the ProxyRequest clientId is not empty and the ProxyRequest
		 * clientId header is not empty they must match</li>
		 * <li>if the ProxyRequest clientId is empty then the ProxyRequest clientId
		 * header must be empty</li>
		 * </ol>
		 * 
		 * @param proxyRequest
		 *            the ProxyRequest
		 * @param request
		 *            the HttpServletRequest
		 * @throws ProxyPreconditionException
		 *             thrown in violation of any check noted
		 */
		public static void headerCheck(ProxyRequest proxyRequest, HttpServletRequest request) throws ProxyPreconditionException {
			
			String clientId = proxyRequest.getClientId();
			String proxyClientId = proxyRequest.getRequestHeader(CLIENT_ID_HEADER);
			if (!StringUtils.isEmpty(clientId)) {
				if (StringUtils.isEmpty(proxyClientId) ) {
					return;
				}
				if (!clientId.equals(proxyClientId)) {
					throw new ProxyPreconditionException("provided clientId [{}] does not match the proxy request header clientId [{}]",
							clientId, proxyClientId);
				}
			} else {
				if (!StringUtils.isEmpty(proxyClientId) ) {
					throw new ProxyPreconditionException(
							"unexpected clientId [{}] found in proxy request header",
							proxyClientId);
				}
			}
		}
		
		public static void setProxyUrl(ProxyRequest proxyRequest) {
			String uri = proxyRequest.getUrl();
			
			if (uri.startsWith("/")) {
				uri = uri.substring(1);
			}
			int index = uri.indexOf('/');
			String api = uri.substring(0, index);
			if (logger.isDebugEnabled()) {
				logger.debug("extracted api [{}]", api);
			}
			String hostname = ApiHostMap.getHostname(api);
			if (StringUtils.isEmpty(hostname)) {
				throw new ProxyApiException("host mapping not found for proxy request uri [{}]", uri);
			}
			/*
			 * build full URL
			 */
			StringBuilder sb = new StringBuilder(hostname);
			sb.append('/');
			sb.append(uri);
			String url = sb.toString();
			proxyRequest.setUrl(url);
		}		
}
