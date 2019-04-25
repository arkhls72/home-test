
package com.home.digital.adapter.remote;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.home.digital.adapter.provider.RemoteProperties;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.common.convertor.JsonMessageConverter;

/**
 * A generic Abstract  class that extends BaseAdapter. Provides
 * access to the shared spring bean definitions and services in service adapter subclasses 
 * 
 * @author ralu
 */
public abstract class AbstractPointsAdapter <T> extends BaseAdapter<T>{
     
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected RemoteProperties remoteProperties;
    @Autowired
    protected MappingJackson2HttpMessageConverter jsonMessageConverter;
    @Autowired
    protected JsonMessageConverter commonJsonMessageConverter;
    @Autowired
    protected RemoteProperties  propertiesProvider;
    @Autowired
    protected GsonHttpMessageConverter gsonConverter;

//    /**
//     *  Generates AdapterException if passing error list is not empty 
//     */
//    protected void validateParameters(List<String> errors,String serviceName) {
//        if (CollectionUtils.isNotEmpty(errors)) {
//            throw new AdapterException("Inbound " +  this.getErrors(errors)  + " parameter(s) are null. " + serviceName );
//        }
//    }

    /**
     * 
     * @return HttpHeaders to embed in client Spring HttpEnity request object
     */
    protected HttpHeaders getJsonEntityHeader() {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
        acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
        header.setAccept(acceptableMediaTypes);

        return header;
    }

 
}
