package com.home.processor.job.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.home.processor.job.dto.EventDetails;
import com.home.processor.job.service.DataProcessorService;



@Service
public class DataProcessorServiceImpl implements DataProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessorServiceImpl.class);
    private static final long SIZE = 5000;


    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private final String QUERY = "SELECT count(*) FROM niko.TB_STUDENT";

   
    public Map<String, List<EventDetails>> createEvents() {
        logger.info("****** Job Processor started Time : " + new Date());
        Integer rows = (Integer) jdbcTemplate.queryForObject(QUERY, Integer.class);
        Map<String, List<EventDetails>> map = new HashMap<>();

        map.put("S1", new ArrayList<>());

        long page = 0L;

        double devide = rows / SIZE;
        
        if (rows < SIZE) {
        	page = 1;
        	 EventDetails event = new EventDetails();

             event.setStart(1L);
             event.setEnd(Long.valueOf(rows));

             event.setName("page-" + String.valueOf(1));
            
              map.get("S1").add(event);
        	
        } else {
        	  long index = 0L; 
        	  page = rows % SIZE == 0 ? (long) devide : (long) devide + 1;
        	  
        	  for (int i = 1; i <= page; i++) {
                  EventDetails event = new EventDetails();

                  event.setStart(1 + index);
                  index = SIZE * i;
                  event.setEnd(Long.valueOf(index));

                  event.setName("page-" + String.valueOf(i));
                 
                  map.get("S1").add(event);
                  
              }
        }
        return map;
    }


   

}
