package com.home.processor.job.service;

import java.util.concurrent.Callable;

public class CalculateService implements Callable<Integer>{
	
	private Integer value;
	public CalculateService(Integer value) {
		this.value = value;
	}

	
	@Override
	public Integer call() throws Exception {
		value = value * 2;
		return value;
	}
}
