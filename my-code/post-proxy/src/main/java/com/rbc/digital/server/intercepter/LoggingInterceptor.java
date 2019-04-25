package com.home.digital.server.intercepter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Intercept all incoming requests before they hit any Spring controllers.
 * <p>
 * All requests are logged to the define audit log
 * </p>
 * 
 * @author thomas
 */
public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger("audit-logger");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		logger.info("{}|{}|{}", request.getRemoteAddr(), request.getRequestURL(), request.getMethod());
        return true;
    }
}
