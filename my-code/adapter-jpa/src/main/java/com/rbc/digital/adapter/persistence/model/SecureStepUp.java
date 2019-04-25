package com.home.digital.adapter.persistence.model;
import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.home.digital.adapter.persistence.service.GuidGenerator;
/**
 * The persistent class for the SEC_STEP_UP database table.
 * 
 */
@Entity
@Table(name="SEC_STEP_UP")


@NamedQueries({ 
		@NamedQuery(name=SecureStepUp.FIND_ALL, query="select s from SecureStepUp s"),
		@NamedQuery(name=SecureStepUp.FIND_BY_ID, query="select s from SecureStepUp s where guid=:guid")
		}) 

public class SecureStepUp extends AbstractAudit implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String FIND_ALL   = "SecureStepUp.FIND.ALL";
	public static final String FIND_BY_ID = "SecureStepUp.FIND.BY.ID";
	
	@Id
//	@SequenceGenerator(name="SEC_STEP_UP_GUID_GENERATOR", sequenceName="SEQ_SEC_STEP_UP")
//	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEC_STEP_UP_GUID_GENERATOR")
	@Column(name="GUID",columnDefinition="character")
	private String guid;

	@Column(name="CLIENT_ID",columnDefinition="decimal",scale = 0, precision = 32)
	private BigDecimal clientId;

	@Column(name="CONTACT")
	private String contact;

	@Column(name="OTP")
	private String otp;

	@Column(name="TRANSACTION_ID",columnDefinition="decimal",scale = 0, precision = 32)
	private BigDecimal transctionId;

	@ManyToOne
	@JoinColumn(name="CONTACT_TYPE", referencedColumnName="CONTACT_TYPE")
	
	private SecureContactType secureContactType;
	
	@ManyToOne
	@JoinColumn(name="STATUS_TYPE",referencedColumnName="STATUS_TYPE")
	private SecureStatusType secureStatusType;


	public String getGuid() {
		return this.guid;
	}

	@PrePersist
	public void setGuid() {
		this.guid = GuidGenerator.createGuid();
	}
	
	public BigDecimal getClientId() {
		return this.clientId;
	}

	public void setClientId(BigDecimal clientId) {
		this.clientId = clientId;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getOtp() {
		return this.otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}
	
	public BigDecimal getTransctionId() {
		return this.transctionId;
	}

	public void setTransctionId(BigDecimal transctionId) {
		this.transctionId = transctionId;
	}

	public SecureContactType getSecureContactType() {
		return this.secureContactType;
	}

	public void setSecureContactType(SecureContactType secureContactType) {
		this.secureContactType = secureContactType;
	}

	public SecureStatusType getSecureStatusType() {
		return this.secureStatusType;
	}

	public void setSecureStatusType(SecureStatusType secureStatusType) {
		this.secureStatusType = secureStatusType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((guid == null) ? 0 : guid.hashCode());
		result = prime * result + ((otp == null) ? 0 : otp.hashCode());
		result = prime
				* result
				+ ((secureContactType == null) ? 0 : secureContactType
						.hashCode());
		result = prime
				* result
				+ ((secureStatusType == null) ? 0 : secureStatusType.hashCode());
		result = prime * result
				+ ((transctionId == null) ? 0 : transctionId.hashCode());
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
		SecureStepUp other = (SecureStepUp) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (guid == null) {
			if (other.guid != null)
				return false;
		} else if (!guid.equals(other.guid))
			return false;
		if (otp == null) {
			if (other.otp != null)
				return false;
		} else if (!otp.equals(other.otp))
			return false;
		if (secureContactType == null) {
			if (other.secureContactType != null)
				return false;
		} else if (!secureContactType.equals(other.secureContactType))
			return false;
		if (secureStatusType == null) {
			if (other.secureStatusType != null)
				return false;
		} else if (!secureStatusType.equals(other.secureStatusType))
			return false;
		if (transctionId == null) {
			if (other.transctionId != null)
				return false;
		} else if (!transctionId.equals(other.transctionId))
			return false;
		return true;
	}

}