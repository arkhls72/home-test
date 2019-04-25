package com.home.digital.adapter.convert;

import java.math.BigDecimal;

import com.home.digital.adapter.persistence.model.SecureContactType;
import com.home.digital.adapter.persistence.model.SecureStatusType;
import com.home.digital.adapter.persistence.model.SecureStepUp;
import com.home.digital.model.v1.secure.StepUp;
import com.home.digital.model.v1.types.ContactType;
import com.home.digital.model.v1.secure.StatusType;

public class SecureConvert {
	public static  SecureStepUp build (StepUp source) throws Exception {

		SecureStepUp target = new SecureStepUp();
		
		target.setClientId(new BigDecimal(source.getClientId()));
		target.setOtp(source.getOtp());
		target.setTransctionId(new BigDecimal(source.getTransactionId()));
		target.setSecureContactType(build(source.getContactType()));
		target.setSecureStatusType(build(source.getStatusType()));

		return target;
	}	

	public static  SecureContactType build (ContactType source) throws Exception {
		SecureContactType  target = new SecureContactType();
		target.setContactType(source.xmlValue());

		return target;
	}

	public static  SecureStatusType build (StatusType source) throws Exception {
		SecureStatusType  target = new SecureStatusType();
		target.setStatusType(source.xmlValue());

		return target;
	}
}
