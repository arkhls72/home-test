

package com.home.digital.queue.ejb.model;

import java.io.Serializable;
/**
 * GainTarnsaction DataPower response object
 * @author arash
 *
 */
public class GainResponse implements Serializable{
	private static final long serialVersionUID = -417974010405236823L;
	private String statusCode;
    private String errorReason;
    private Long rewardsBalance;
    private String referenceNumber;
    private Long pointsGained;

	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getErrorReason() {
		return errorReason;
	}
	public void setErrorReason(String errorReason) {
		this.errorReason = errorReason;
	}
	public Long getRewardsBalance() {
		return rewardsBalance;
	}
	public void setRewardsBalance(Long rewardsBalance) {
		this.rewardsBalance = rewardsBalance;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public Long getPointsGained() {
		return pointsGained;
	}
	public void setPointsGained(Long pointsGained) {
		this.pointsGained = pointsGained;
	}
    
    
}
