package com.home.digital.server.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.home.digital.common.adapter.base.AdapterThreadLocal;

/**
 * Intercepts incoming request before hits Spring controllers. It is going to log the request information to  
 * Web application's audit log  
 * @author ralu
 *
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger("audit-logger");
    private static final String API_NAME = "rrc-api";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        logger.info("{}|{}|{}|{}|{}" ,
                API_NAME, request.getRemoteAddr(), request.getRequestURL(), request.getMethod(), authorization);
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info("after complation is called.");
        
    }   
}
