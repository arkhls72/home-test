package com.home.processor.job.endpoint;

import java.sql.Types;
import java.util.Date;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.home.processor.job.dto.EventRequestDTO;
import com.home.processor.job.manager.JobProcessorManager;


@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class JobProcessorController {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private JobProcessorManager  jobProcessorManager;
    
    @RequestMapping(method = RequestMethod.POST, path = "/ingest")
    @ResponseStatus(value=HttpStatus.OK)
    public void post(@RequestBody EventRequestDTO eventRequestDTO) throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        jobProcessorManager.executeAsynch(eventRequestDTO.getEvents());
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/add")
    @ResponseStatus(value=HttpStatus.OK)
    public void add() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        Object[] params = null;
        
        System.out.println("Start-date:" + new Date());
        String insertSql = "INSERT INTO STUDENT(id,name,email) values(?,?,?)";
        
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR };
        for (int i=1;i<100000;i++) {
            params = new Object[] { String.valueOf(i), "A" + String.valueOf(i), String.valueOf(i) + "@rogers.com"};
            jdbcTemplate.update(insertSql, params, types);
        }
    }
    
    
}