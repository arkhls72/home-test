
package com.home.digital.adapter.persistence.service;

import java.util.UUID;

/**
 * A simple GUID generator
 * 
 * @author thomas 
 */

public class GuidGenerator { 
    
	/**
	 * Uses UUID to generate a pseudo-random GUID with no embedded '-'. The
	 * result GUID is 32 characters in otpLength.
	 */
	public static String createGuid() {
		String guid = UUID.randomUUID().toString();
		guid = guid.replaceAll("-", "");
		return guid;
	}
}
