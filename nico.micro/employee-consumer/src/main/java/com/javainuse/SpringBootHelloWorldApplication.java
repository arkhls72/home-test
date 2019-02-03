package com.javainuse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.ribbon.RibbonClientSpecification;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClientException;

import com.javainuse.controllers.ConsumerResource;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
@EnableFeignClients
public class SpringBootHelloWorldApplication {
	 @Autowired
	 private EurekaClient eurekaClient;
	@Autowired(required = false)
    private List<RibbonClientSpecification> configurations = new ArrayList<>();
 
	public static void main(String[] args) throws RestClientException, IOException {
		ApplicationContext ctx = SpringApplication.run(
				SpringBootHelloWorldApplication.class, args);
		
		ConsumerResource consumerControllerClient=ctx.getBean(ConsumerResource.class);
		System.out.println(consumerControllerClient);
		consumerControllerClient.getEmployee();
		
		
	}
	
	
//	@Bean
//	public  ConsumerResource  consumerControllerClient()
//	{
//		return  new ConsumerResource();
//	}
	@Bean
    public SpringClientFactory springClientFactory() {
        SpringClientFactory factory = new SpringClientFactory();
        factory.setConfigurations(this.configurations);
        return factory;
    }
}
