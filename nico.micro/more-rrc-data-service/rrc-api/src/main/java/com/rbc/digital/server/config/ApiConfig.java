
package com.home.digital.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * 
 * Spring startup java config. Indicates that a class declares one or more @Bean
 * methods and being processed by Spring container to generate bean definitions
 * and service requests for those beans at runtime
 * 
 * @author ralu
 */

@Configuration
public class ApiConfig {
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource bean = new ReloadableResourceBundleMessageSource();
        bean.setBasenames("classpath:messages/messages");
        bean.setDefaultEncoding("UTF-8");

        return bean;
    }
}
