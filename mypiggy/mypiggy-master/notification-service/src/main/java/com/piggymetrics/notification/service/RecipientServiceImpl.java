package com.piggymetrics.notification.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.piggymetrics.notification.domain.Recipient;

@Service
public class RecipientServiceImpl implements RecipientService {

	private final Logger log = LoggerFactory.getLogger(getClass());



	@Override
	public Recipient findByAccountName(String accountName) {
		Assert.hasLength(accountName);
		Recipient r = new Recipient();
		r.setEmail("ara@yahoo.com");
		return r;
		
	}

	
	
}
