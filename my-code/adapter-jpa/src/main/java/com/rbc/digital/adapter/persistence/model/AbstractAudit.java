package com.home.digital.adapter.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractAudit {
	AbstractAudit() {
		this.timeStamp =new Timestamp(System.currentTimeMillis());
	}

	@Column(name="TIME_STAMP")	
	private Timestamp timeStamp;

	
	public Timestamp getTimeStamp() {
		return timeStamp;
	}
}
