package com.home.processor.job.config;

import org.springframework.batch.item.ItemProcessor;

import com.home.processor.job.dto.StudentDTO;
import com.home.processor.job.model.Student;


public class StudentItemProcessor implements ItemProcessor<StudentDTO, Student>{

	@Override
	public Student process(StudentDTO source) throws Exception {
	    Student target = new Student();
	    target.setId(source.getId());
	    target.setName(source.getName());
	    target.setEmail(source.getEmail());
	    
		return target;
	}
}
