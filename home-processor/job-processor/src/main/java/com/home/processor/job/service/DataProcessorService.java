package com.home.processor.job.service;

import java.util.List;
import java.util.Map;

import com.home.processor.job.dto.EventDetails;


public interface DataProcessorService {
	  Map<String, List<EventDetails>> createEvents();
}
