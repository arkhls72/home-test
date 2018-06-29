package com.home.processor.data.config;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EntityScan("com.home.processor")
@EnableConfigurationProperties
@EnableWebMvc
public class DataProcessorConfig {

    @Value("${job-processor.serverS1Url}")
    private String serverS1Url;

    @Value("${job-processor.serverS2Url}")
    private String serverS2Url;

    @Value("${job-processor.ingestUrl}")
    private String ingestUrl;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataProcessorConfig(EntityManager entityManager, DataSource dataSource, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    
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
