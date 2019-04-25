
package com.home.digital.adapter.persistence.service;

import java.util.concurrent.ThreadLocalRandom;

/**
 * One Time Password (OTP) generator
 * 
 * @author thomas
 */

public class OtpGenerator {

	public  static final int DEFAULT_LENGTH_OTP = 4;

	/** the current OTP length */
	private static int length = DEFAULT_LENGTH_OTP;

	/**
	 * Allow the OTP length to be overridden.
	 * 
	 * @param length the OTP length to set
	 */
	public void setLength(int length) {
		length = length;
	}

	/**
	 * Generate a pseudo-random OTP of the set length where characters are
	 * numeric and in the range 0-9.
	 * 
	 * @return a new OTP
	 */
	public static String createOtp() { // throws DigitalGeneratorException {
		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0, 10);
			sb.append(rand);
		}
		return sb.toString();
	}
}
