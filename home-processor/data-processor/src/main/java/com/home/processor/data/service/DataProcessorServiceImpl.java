package com.home.processor.data.service;

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

import com.home.processor.data.dto.EventDetails;

@Service
public class DataProcessorServiceImpl implements DataProcessorService {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessorServiceImpl.class);
    private static final long SIZE = 5000;


    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    
    @Autowired
    private ClientService clientService;
    private final String QUERY = "SELECT count(*) FROM STUDENT";

   
    private Map<String, List<EventDetails>> createEvents() {
        logger.info("****** Job Processor started Time : " + new Date());
        Integer rows = (Integer) jdbcTemplate.queryForObject(QUERY, Integer.class);
        Map<String, List<EventDetails>> map = new HashMap<>();

        map.put("S1", new ArrayList<>());
        map.put("S2", new ArrayList<>());

        long page = 0L;

        double devide = rows / SIZE;

        page = rows % SIZE == 0 ? (long) devide : (long) devide + 1;

        Long index = 0L;

        for (int i = 1; i <= page; i++) {
            EventDetails event = new EventDetails();

            event.setStart(1 + index);
            index = SIZE * i;
            event.setEnd(Long.valueOf(index));

            event.setName("page-" + String.valueOf(i));
            if (i % 2 == 0) {
                map.get("S1").add(event);
            } else {
                map.get("S2").add(event);
            }
        }

        return map;
    }


    @Override
    public void ingest() {
        Map<String, List<EventDetails>>  events = createEvents();
        clientService.call(events);
        
    }

}
