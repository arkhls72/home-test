package com.home.processor.job.config;

import org.springframework.batch.item.ItemProcessor;

import com.home.processor.job.dto.StudentDTO;
import com.home.processor.job.persistant.domain.StudentTarget;


public class StudentItemProcessor implements ItemProcessor<StudentDTO, StudentTarget>{

	@Override
	public StudentTarget process(StudentDTO source) throws Exception {
	    StudentTarget target = new StudentTarget();
	    target.setId(source.getId());
	    target.setName(source.getName());
	    target.setEmail(source.getEmail());
	    
		return target;
	}
}
