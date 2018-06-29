package com.home.processor.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;



@SpringBootApplication
@EnableAutoConfiguration
public class JobProcessorApplication  extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(JobProcessorApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(JobProcessorApplication.class);
    }
}
