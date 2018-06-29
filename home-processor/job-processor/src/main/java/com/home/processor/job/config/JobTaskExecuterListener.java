package com.home.processor.job.config;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

import com.home.processor.job.manager.JobProcessorManagerImpl;

public class JobTaskExecuterListener extends JobExecutionListenerSupport   {
    private static final Logger logger = LoggerFactory.getLogger(JobTaskExecuterListener.class);
	@Override
	public void afterJob(JobExecution jobExecution) {
	    
	    long start = jobExecution.getStartTime().getTime();
	    long end = jobExecution.getEndTime().getTime();
	    long duration = end-start;
	    
	    String diffInMiliSeconds = String.valueOf(TimeUnit.MILLISECONDS.toMillis(duration));
	    String diffInSeconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(duration));
	    String diffInMinutes = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(duration));
	    String diffInHours = String.valueOf(TimeUnit.MILLISECONDS.toHours(duration));
	    
	    String hms = String.format("%02d:%02d:%02d:%02d",
	            TimeUnit.MILLISECONDS.toHours(duration),
	            TimeUnit.MILLISECONDS.toMinutes(duration) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(duration)),
	            TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)),
	            TimeUnit.MILLISECONDS.toMillis(duration) - TimeUnit.SECONDS.toMillis(TimeUnit.MILLISECONDS.toSeconds(duration)));
	    
		logger.info("***********************************");
		logger.info("\n");
		logger.info("Name:" + jobExecution.getJobId());
		logger.info("Status:" + jobExecution.getStatus().name());
		logger.info("start-time:" + jobExecution.getStartTime());
		logger.info("end-time:" + jobExecution.getEndTime());
		logger.info("duration:" + hms);
		
		
		Collection<StepExecution> steps = jobExecution.getStepExecutions();
		List<StepExecution> st = new ArrayList<>(steps);
		st.get(0).getCommitCount();
		logger.info("# of commit:" + st.get(0).getCommitCount());
		logger.info("\n");
		logger.info("***********************************");
	}
	
}