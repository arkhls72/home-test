package com.home.digital.adapter.persistence.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the SEC_STEP_UP_CONTACT_TYPE database table.
 * 
 */
@Entity
@Table(name="SEC_STEP_UP_CONTACT_TYPE")
@NamedQuery(name=SecureContactType.FIND_BY_ID, query="select c from SecureContactType c where c.contactType=:type")

public class SecureContactType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String FIND_BY_ID = "SecureContactType.FIND.BY.ID";
	
	@Id
	@Column(name="CONTACT_TYPE")
	private String contactType;

	@Column(name="DESCRIPTION")
	private String description;

	@OneToMany(mappedBy="secureContactType",cascade=CascadeType.ALL)
	private List<SecureStepUp> secureStepUpList;

	public SecureContactType() {
	}

	public String getContactType() {
		return this.contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
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

	public void setSecureStepUpList(List<SecureStepUp> secStepUpList) {
		this.secureStepUpList = secStepUpList;
	}

	public SecureStepUp addSecureStepUp(SecureStepUp secureStepUp) {
		getSecureStepUpList().add(secureStepUp);
		secureStepUp.setSecureContactType(this);

		return secureStepUp;
	}

	public SecureStepUp removeSecStepUp(SecureStepUp secStepUp) {
		getSecureStepUpList().remove(secStepUp);
		secStepUp.setSecureContactType(null);

		return secStepUp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((contactType == null) ? 0 : contactType.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime
				* result
				+ ((secureStepUpList == null) ? 0 : secureStepUpList.hashCode());
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
		SecureContactType other = (SecureContactType) obj;
		if (contactType == null) {
			if (other.contactType != null)
				return false;
		} else if (!contactType.equals(other.contactType))
			return false;
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
		return true;
	}
	
}