/*
 * Copyright 2016 by Royal Bank of Canada. All rights reserved.
 *
 * This source code, and resulting software, is the confidential and proprietary 
 * information ("Proprietary Information") and is the intellectual property 
 * of Royal Bank of Canada ("The Company"). 
 *
 * You shall not disclose such Proprietary Information and shall use it only in 
 * accordance with the term and conditions of any and all license agreements you 
 * have entered into with The Company.
 * 
 * Unauthorized copying of this file, via any medium is strictly prohibited.
 */

package com.home.digital.queue.ejb.model;

import java.io.Serializable;
/**
 * EarnTransaction request object being sent to DataPower
 * @author arash
 *
 */
public class EarnTransaction implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2042433970521620322L;
	private String data;

	public EarnTransaction(String data) {
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
