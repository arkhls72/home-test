

package com.home.digital.queue.ejb.model;

import java.io.Serializable;
/**
 * GainTransaction request object being sent to DataPower
 * @author arash
 *
 */
public class GainTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2042433970521620322L;
	private String data;

	public GainTransaction(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
