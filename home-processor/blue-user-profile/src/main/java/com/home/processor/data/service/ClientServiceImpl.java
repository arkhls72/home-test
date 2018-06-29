package com.home.processor.data.service;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.home.processor.data.config.UserProfileConfig;
import com.home.processor.data.dto.EventDetails;
import com.home.processor.data.dto.EventRequestDTO;

@Service
public class ClientServiceImpl implements ClientService{
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    @Autowired
    private UserProfileConfig dataProcessorConfig;

    @Autowired
    private RestTemplate restTemplate;
    
    @Override
    public void call(   Map<String,List<EventDetails>> events) {
        HttpHeaders requestHeaders = getRequestHeaders();
        EventRequestDTO request = new EventRequestDTO();
        request.setEvents(events.get("S1"));
        HttpEntity<?> httpEntity = new HttpEntity<Object>(request, requestHeaders); 
        
        URI uri = UriComponentsBuilder.fromHttpUrl(dataProcessorConfig.getServerS1Url())
                .path(dataProcessorConfig.getIngestUrl()).buildAndExpand().toUri();
//        callAsync(httpEntity, uri);
        
        requestHeaders = getRequestHeaders();
        request = new EventRequestDTO();
        request.setEvents(events.get("S2"));
        request.getEvents().addAll(events.get("S1"));
        
        httpEntity = new HttpEntity<Object>(request, requestHeaders); 
        
        uri = UriComponentsBuilder.fromHttpUrl(dataProcessorConfig.getServerS2Url())
                .path(dataProcessorConfig.getIngestUrl()).buildAndExpand().toUri();
        callAsync(httpEntity, uri);
    }
    
    @Async
    public void callAsync(HttpEntity<?> httpEntity,URI uri) {
        ResponseEntity<?> responseEntity = restTemplate.exchange(uri.toString(), HttpMethod.POST,
                httpEntity, Object.class);
       logger.info("call [{}] status code [{}]",uri.toString(),responseEntity.getStatusCode()); 
    }
    
    private HttpHeaders getRequestHeaders() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        return header;
    }
}
