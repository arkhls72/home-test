package com.home.processor.data.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.home.processor.data.dto.EventDetails;
import com.home.processor.data.dto.EventRequestDTO;
import com.home.processor.data.service.UserProfileService;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserProfileController {
    
    
    @Autowired
    private UserProfileService userProfileService;
    
    @RequestMapping(method = RequestMethod.GET, path = "/data/test")
    @ResponseStatus(value=HttpStatus.OK)
    public void get() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
       userProfileService.getAuthentication();
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/add")
    @ResponseStatus(value=HttpStatus.OK)
    public EventRequestDTO add() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
    
        EventRequestDTO dto = new EventRequestDTO();
        
        EventDetails details = new EventDetails();
        
        details.setEventId("");
        details.setEnd(100L);
        details.setStart(1L);
        
        List<EventDetails> list = new ArrayList<EventDetails>();
        list.add(details);
        
        dto.setEvents(list);
        
        return dto;
    }
}
