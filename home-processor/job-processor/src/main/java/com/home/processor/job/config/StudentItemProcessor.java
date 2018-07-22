package com.home.processor.job.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import com.home.processor.job.dto.StudentDTO;
import com.home.processor.job.persistant.domain.StudentTarget;
import com.home.processor.job.persistant.domain.Teacher;
import com.home.processor.job.service.JobProcessorService;


public class StudentItemProcessor implements ItemProcessor<StudentDTO, StudentTarget>{
	
	@Autowired
	private JobProcessorService jobProcessorService;
	
	@Override
	public StudentTarget process(StudentDTO source) throws Exception {
	   return jobProcessorService.processStudent(source);
	}
}
