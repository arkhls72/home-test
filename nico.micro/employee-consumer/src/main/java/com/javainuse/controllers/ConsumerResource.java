package com.javainuse.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.javainuse.service.ProducerClient;
@RestController
public class ConsumerResource {
	
	//	@Autowired
//	private LoadBalancerClient loadBalancer;
	
	@Autowired
	private ProducerClient loadBalancer;

	@RequestMapping(value = "/employee", method = RequestMethod.GET)
	public Employee getEMplyeeFromMicroService() {
		return  loadBalancer.getData();
	}
	public void getEmployee() throws RestClientException, IOException {
		
//		List<ServiceInstance> instances=discoveryClient.getInstances("employee-producer");
//		ServiceInstance serviceInstance=instances.get(0);
//		
//		String baseUrl=serviceInstance.getUri().toString();
//		System.out.println("BaseLineL:" + baseUrl);
//		baseUrl=baseUrl+"/employee";
//		
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response=null;
//		try{
//		response=restTemplate.exchange(baseUrl,
//				HttpMethod.GET, getHeaders(),String.class);
//		}catch (Exception ex)
//		{
//			System.out.println(ex);
//		}
		
		Employee emp = loadBalancer.getData();
		System.out.println("Check this out arash :   " + emp.getName());
	
	}

	private static HttpEntity<?> getHeaders() throws IOException {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return new HttpEntity<>(headers);
	}

	@RequestMapping(method=RequestMethod.GET, value="/employees")
	public Employee getData() {
		return loadBalancer.getData();
	}
}