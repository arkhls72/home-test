package com.home.processor.job.manager;
import java.util.List;

import com.home.processor.job.dto.EventDetails;
public interface JobProcessorManager {
    void executeAsynch(List<EventDetails> events);
}
