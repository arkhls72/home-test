package com.home.processor.job.service.impl;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.home.processor.job.dto.EventDetails;
import com.home.processor.job.service.JobProcessorService;

@Service
public class JobProcessorServiceImpl implements JobProcessorService{
	@Autowired
    private JobLauncher jobLauncher;

	@Autowired
	@Qualifier(value="firstJob")
    private Job job;
   

    @Override
    public void execute(EventDetails eventDetails) throws JobExecutionAlreadyRunningException, JobRestartException,
            JobInstanceAlreadyCompleteException, JobParametersInvalidException {
       
            JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
            
            jobParametersBuilder.addLong("time",System.currentTimeMillis());
            jobParametersBuilder.addString("start", String.valueOf(eventDetails.getStart()));
            jobParametersBuilder.addString("end", String.valueOf(eventDetails.getEnd()));
            jobParametersBuilder.addString("name", eventDetails.getName());
            
            
            jobLauncher.run(job, jobParametersBuilder.toJobParameters());
            
            
        
    }
	
	
}
