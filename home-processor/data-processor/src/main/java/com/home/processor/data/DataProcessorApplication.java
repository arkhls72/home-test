package com.home.processor.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;



@SpringBootApplication
@EnableAutoConfiguration
public class DataProcessorApplication  extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(DataProcessorApplication.class, args);
	}
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(DataProcessorApplication.class);
    }
}
