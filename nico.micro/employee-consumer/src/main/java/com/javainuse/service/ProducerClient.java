package com.javainuse.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javainuse.controllers.Employee;

@FeignClient(name="producer-service")
public interface ProducerClient {
	@RequestMapping(method=RequestMethod.GET, value="/producer-service/employee")
	public Employee getData();

}