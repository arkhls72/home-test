package com.home.processor.data.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EntityScan("com.home.processor")
@EnableConfigurationProperties
@EnableWebMvc
public class UserProfileConfig {

    @Value("${job-processor.serverS1Url}")
    private String serverS1Url;

    @Value("${job-processor.serverS2Url}")
    private String serverS2Url;

    @Value("${job-processor.ingestUrl}")
    private String ingestUrl;

   

    
    
    @Bean
    public RestTemplate restTempalte() {
        RestTemplate restTemplate = new RestTemplate();
        
        return restTemplate;
    }
    public String getServerS1Url() {
        return serverS1Url;
    }

    public String getServerS2Url() {
        return serverS2Url;
    }

    public String getIngestUrl() {
        return ingestUrl;
    }

}
