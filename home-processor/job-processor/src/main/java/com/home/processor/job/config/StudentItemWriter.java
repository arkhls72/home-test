package com.home.processor.job.config;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.home.processor.job.persistant.domain.StudentTarget;
import com.home.processor.job.persistant.domain.Teacher;
import com.home.processor.job.service.JobProcessorService;

public class StudentItemWriter implements ItemWriter<StudentTarget>{
	
	@Autowired
	private EntityManager entityManager;
	
	
	@Override
	public void write(List<? extends StudentTarget> source) throws Exception {
		
		for (StudentTarget item:source ) {
			entityManager.persist(item);
			
		}
		
	}

}
