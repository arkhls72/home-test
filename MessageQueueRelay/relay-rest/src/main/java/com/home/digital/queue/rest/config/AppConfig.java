
package com.home.digital.queue.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
@Configuration
public class AppConfig{

    /** 
     * @return spring requestMappingHandlerMapping spring bean to add to spring context 
     */
    @Bean(name = "requestMappingHandlerAdapter")
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
        RequestMappingHandlerAdapter bean = new RequestMappingHandlerAdapter();
        bean.getMessageConverters().add(getJsonMessageConverter());

        return bean;
    }

    /** 
     * @return spring requestMappingHandlerMapping spring bean to add to spring context 
     */
    @Bean(name = "requestMappingHandlerMapping")
    public org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        RequestMappingHandlerMapping bean = new RequestMappingHandlerMapping();

        bean.setOrder(1);
        bean.setUseRegisteredSuffixPatternMatch(false);
        bean.setUseSuffixPatternMatch(false);

        return bean;
    }
    
    /** 
     * @return spring contentNegotiationManagerFactoryBean bean to add to spring context 
     */
    @Bean(name = "contentNegotiationManagerFactoryBean")
    public ContentNegotiationManagerFactoryBean getContentNegotiationManagerFactoryBean() {
        ContentNegotiationManagerFactoryBean bean = new ContentNegotiationManagerFactoryBean();
        bean.setDefaultContentType(MediaType.APPLICATION_JSON);
        return bean;
    }

    /**
     * @return instance of MappingJackson2HttpMessageConverter bean to add to spring context
     */
    @Bean(name = "jsonMessageConverter")
    public MappingJackson2HttpMessageConverter getJsonMessageConverter() {
        MappingJackson2HttpMessageConverter bean = new MappingJackson2HttpMessageConverter();
        bean.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        
        bean.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);
        bean.getObjectMapper().disable(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS);
        bean.getObjectMapper().disable(SerializationFeature.WRITE_NULL_MAP_VALUES);

        return bean;
    }
}
