package com.home.processor.job.manager;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.home.processor.job.dto.EventDetails;
import com.home.processor.job.service.JobProcessorService;


@Component
public class JobProcessorManagerImpl implements JobProcessorManager {
    private static final Logger logger = LoggerFactory.getLogger(JobProcessorManagerImpl.class);
    
    @Autowired
    @Qualifier(value="threadPoolLauncher")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
   
    @Autowired
    private JobProcessorService jobProcessorService;

    
    @Override
    public void executeAsynch(List<EventDetails> events) {
        logger.info("****** Job Processor started Time : " + new Date());
        
        int i = 1;
        for (EventDetails item:events) {
            
         
                        try {
							jobProcessorService.execute(item);
						} catch (JobExecutionAlreadyRunningException | JobRestartException
								| JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
              
          
        }
    }
}
