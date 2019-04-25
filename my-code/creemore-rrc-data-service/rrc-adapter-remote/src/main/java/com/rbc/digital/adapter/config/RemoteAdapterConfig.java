
package com.home.digital.adapter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.home.digital.common.convertor.JsonMessageConverter;
/**
 * 
 * Spring startup java config indicates that a class declares one or more @Bean
 * methods and being processed by Spring container to generate bean definitions
 * and service requests for those beans at runtime
 * 
 * @author arash
 */
@Configuration 
public class RemoteAdapterConfig {

    /**
     *  Jackson json  
     * @return MappingJackson2HttpMessageConverter spring bean
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
    
    /**
     * JSON convertor utility from creemore-common 
     * @return Creemore-common JsonMessageConverter spring bean
     */
    @Bean(name = "commonJsonMessageConverter")
    public JsonMessageConverter getCommonJsonMessageConverter() {
        JsonMessageConverter bean = new JsonMessageConverter();
        
        return bean;
    }   
   
 
}
