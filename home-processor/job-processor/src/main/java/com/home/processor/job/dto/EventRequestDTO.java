package com.home.processor.job.dto;

import java.io.Serializable;
import java.util.List;

public class EventRequestDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<EventDetails> events;
    
    public List<EventDetails> getEvents() {
        return events;
    }
    public void setEvents(List<EventDetails> events) {
        this.events = events;
    }
}
