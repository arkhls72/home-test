package com.home.digital.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.home.digital.common.converter.JsonMessageConverter;

@Configuration
public class ProxyConfig {

	/**
     * JSON convertor utility from creemore-common 
     * @return Creemore-common JsonMessageConverter spring bean
     */
    @Bean(name = "commonJsonMessageConverter")
    public JsonMessageConverter getCommonJsonMessageConverter() {
        JsonMessageConverter bean = new JsonMessageConverter();
        
        return bean;
    }   
    
    
    @Bean(name = "restTemplate")
    public RestTemplate restTemplate() {
    	RestTemplate bean = new RestTemplate();
    	
    	return bean;
    }

}
