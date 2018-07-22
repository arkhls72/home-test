package com.home.processor.job.service;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import com.home.processor.job.dto.EventDetails;
import com.home.processor.job.dto.StudentDTO;
import com.home.processor.job.persistant.domain.Student;
import com.home.processor.job.persistant.domain.StudentTarget;

public interface JobProcessorService {
	 void execute(EventDetails eventDetails)throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException;
	 public StudentTarget processStudent(StudentDTO source) ;
}
