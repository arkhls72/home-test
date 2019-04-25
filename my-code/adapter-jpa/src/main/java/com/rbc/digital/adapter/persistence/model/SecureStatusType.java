package com.home.digital.adapter.persistence.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the SEC_STEP_UP_STATUS_TYPE database table.
 * 
 */
@Entity
@Table(name="SEC_STEP_UP_STATUS_TYPE")
@NamedQuery(name=SecureStatusType.FIND_BY_ID, query="select s from SecureStatusType s where s.statusType=:type")

public class SecureStatusType implements Serializable {	
	private static final long serialVersionUID = 1L;
	public static final String FIND_BY_ID = "SecureStatusType.FIND.BY.ID";

	@Id
	@Column(name="STATUS_TYPE")
	private String statusType;

	@Column(name="DESCRIPTION")
	private String description;

	@OneToMany(mappedBy="secureStatusType",cascade=CascadeType.ALL)
	private List<SecureStepUp> secureStepUpList;

	public SecureStatusType() {
	}

	public String getStatusType() {
		return this.statusType;
	}

	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<SecureStepUp> getSecureStepUpList() {
		return this.secureStepUpList;
	}

	public void setSecStepUpList(List<SecureStepUp> secureStepUpList) {
		this.secureStepUpList = secureStepUpList;
	}

	public SecureStepUp addSecureStepUp(SecureStepUp secureStepUp) {
		getSecureStepUpList().add(secureStepUp);
		secureStepUp.setSecureStatusType(this);

		return secureStepUp;
	}

	public SecureStepUp removeSecureStepUp(SecureStepUp secureStepUp) {
		getSecureStepUpList().remove(secureStepUp);
		secureStepUp.setSecureStatusType(null);

		return secureStepUp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((secureStepUpList == null) ? 0 : secureStepUpList.hashCode());
		result = prime * result
				+ ((statusType == null) ? 0 : statusType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SecureStatusType other = (SecureStatusType) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (secureStepUpList == null) {
			if (other.secureStepUpList != null)
				return false;
		} else if (!secureStepUpList.equals(other.secureStepUpList))
			return false;
		if (statusType == null) {
			if (other.statusType != null)
				return false;
		} else if (!statusType.equals(other.statusType))
			return false;
		return true;
	}

}