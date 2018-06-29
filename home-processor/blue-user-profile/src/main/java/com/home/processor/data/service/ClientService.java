package com.home.processor.data.service;

import java.util.List;
import java.util.Map;

import com.home.processor.data.dto.EventDetails;

public interface ClientService {
      void call(Map<String,List<EventDetails>> events);
}
