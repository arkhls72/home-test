

package com.home.digital.queue.ejb.model;

import java.io.Serializable;
/**
 * EarnTarnsaction DataPower response object
 * @author arash
 *
 */
public class EarnResponse implements Serializable{
	private static final long serialVersionUID = -417974010405236823L;
	private String statusCode;
    private String errorReason;
    private Long rewardsBalance;
    private String referenceNumber;
    private Long pointsEarned;

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
	public Long getPointsEarned() {
		return pointsEarned;
	}
	public void setPointsEarned(Long pointsEarned) {
		this.pointsEarned = pointsEarned;
	}
    
    
}
