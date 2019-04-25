package com.home.digital.adapter;


import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import com.home.digital.adapter.persistence.model.SecureContactType;
import com.home.digital.adapter.persistence.model.SecureStatusType;
import com.home.digital.adapter.persistence.model.SecureStepUp;
import com.home.digital.adapter.persistence.service.SecurePersistenceService;
import com.home.digital.model.v1.secure.StatusType;
import com.home.digital.model.v1.types.ContactType;
@ContextConfiguration(locations={"classpath:/adapter-test-context.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDao extends AbstractJUnit4SpringContextTests  {

	@Before
	public void setUp() {
		System.out.println("Running TestDao");
		
	}

	@Autowired
	SecurePersistenceService SecurePersistenceService;

	
	@Test
	public void createStepUp()  throws Exception {
		SecureStepUp newStepUp = null;
		newStepUp = new SecureStepUp();
		newStepUp.setOtp(com.home.digital.adapter.persistence.service.OtpGenerator.createOtp());
		
		newStepUp.setClientId(new BigDecimal(2000));
		
		SecureContactType  target = new SecureContactType();
		target.setContactType(ContactType.EMAIL.xmlValue());
		newStepUp.setSecureContactType(target);
		newStepUp.setContact("test@home.com");

		SecureStatusType  target2 = new SecureStatusType();
		target2.setStatusType(StatusType.PENDING.xmlValue());	
		newStepUp.setSecureStatusType(target2);

		String response = SecurePersistenceService.createSecure(newStepUp);
		Assert.notNull(response);
		
	
	}
	
	@Ignore
	@Test
	public void getStepUp()  throws Exception {
		String guid = "73bfa7f38233440f86a72e0989e4d815";
		SecureStepUp stepUp = SecurePersistenceService.getSecure(guid);
		Assert.notNull(stepUp,"no Response.");
	
	}

	@Ignore
	@Test
	public void updateStepUp()  throws Exception {
		String guid = "73bfa7f38233440f86a72e0989e4d815";

		SecureStepUp stepUp = SecurePersistenceService.getSecure(guid);
		stepUp.setContact("tom@gmail.com");

		SecureStatusType  target2 = new SecureStatusType();
		target2.setStatusType(StatusType.SUCCESS.xmlValue());	
		stepUp.setSecureStatusType(target2);
		SecureStepUp newStepUp = SecurePersistenceService.updateSecure(stepUp);
		Assert.notNull(newStepUp,"no Response.");
	}

	@Ignore
	@Test
	public void deleteStepUp()  throws Exception {
		String guid = "81395b248cff425eaeb4ac29508fc09d";
		SecureStepUp stepUp = SecurePersistenceService.getSecure(guid);
		stepUp.setContact("tom@gmail.com");
		
		Boolean isUpDate = SecurePersistenceService.removeStepUp(guid);
		Assert.isTrue(isUpDate);
	}

}
