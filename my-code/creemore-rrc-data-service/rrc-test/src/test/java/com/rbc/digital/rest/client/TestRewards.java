
package com.home.digital.rest.client;

import java.net.URI;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.v1.URITemplates;
import com.home.digital.model.v1.client.rewards.PointsBalance;
import com.home.digital.model.v1.client.rewards.PointsBalanceResponse;
import com.home.digital.model.v1.client.rewards.PointsConfirmationResponse;
import com.home.digital.model.v1.client.rewards.PointsRateListResponse;
import com.home.digital.model.v1.client.rewards.PointsRedemption;
import com.home.digital.model.v1.client.rewards.PointsTransaction;
import com.home.digital.model.v1.client.rewards.PointsTransactionListResponse;
import com.home.digital.rest.BaseTestClient;

/**
 * Unit test for Rewards
 * @author arash
 *
 */
public class TestRewards extends BaseTestClient {
	@Autowired
	private String hostname;
	
	private RewardsHelper rewardsHelper = new RewardsHelper();
	
	@Test
	public void  rewardsTest() {
	    this.postRewards();
		this.getPointsTransaction();
		this.getPointsBalance();
		this.getPointsRate();
	}

	/**
	 *  Unit test POST PointsRedemption object 
	 */
	public void postRewards() {
		String postUrl = hostname + URITemplates.CLIENTS_REWARDS_REDEMPTIONS;
		logger.info("posting PointsRedemption rewards to [{}]", postUrl);

		try {
			PointsRedemption redemption = rewardsHelper.newPointsRedemption();
			rewardsHelper.printPostRedemptionRequestBody(redemption);

			URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();
			logger.info("Request url: [{}]", postURI.toString());

			HttpEntity<PointsRedemption> requestEntity = new HttpEntity<PointsRedemption>(redemption);
			ResponseEntity<PointsConfirmationResponse> entityResponse = restTemplate.exchange(postURI, HttpMethod.POST,
					requestEntity, PointsConfirmationResponse.class);

			Assert.assertNotNull(entityResponse);
			Assert.assertTrue(entityResponse.getStatusCode().value() == AdapterStatus.OK.xmlValue());
			Assert.assertNotNull(entityResponse.getBody());
			Assert.assertNotNull(entityResponse.getHeaders().getLocation().toString());
			PointsConfirmationResponse response = (PointsConfirmationResponse) entityResponse.getBody();
			rewardsHelper.printPostResponse(response);
		} catch (Exception ex) {
			logger.error("", ex);
		}
	}
	
    /**
     *  Unit test GET a list of PointsTransaction objects 
     */
	public void getPointsTransaction() {
		logger.info("\n");
		ResponseEntity<PointsTransactionListResponse> entityResponse = null;
		String postUrl = hostname + URITemplates.CLIENTS_REWARDS_TRANSACTIONS;
		logger.info("POST Pointstransaction rewards to [{}]", postUrl);

		PointsTransaction transaction = rewardsHelper.newPointsTransaction();

        try {

            logger.info("Client ID:[{}]", transaction.getClientId());

            // optional
            logger.info("Optional Start date:[{}]", transaction.getStartDate());
            logger.info("Optional End date:[{}]", transaction.getEndDate());     
    	
            URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();
    
            HttpEntity<PointsTransaction> requestEntity = new HttpEntity<PointsTransaction>(transaction);
            entityResponse = restTemplate.exchange(postURI, HttpMethod.POST,
                    requestEntity, PointsTransactionListResponse.class);
    
            Assert.assertNotNull(entityResponse);
            Assert.assertTrue(entityResponse.getStatusCode().value() == AdapterStatus.OK.xmlValue());
            Assert.assertNotNull(entityResponse.getBody());
            rewardsHelper.printPointTransactionResponse(entityResponse.getBody());
		} catch (Exception e) {
			logger.error("Error:", e);
		}
	}
	
    /**
     *  Unit test POST  PointsRewards objects 
     */
	public void getPointsBalance() {
		logger.info("\n");

		String postUrl =hostname + URITemplates.CLIENTS_REWARDS_BALANCES; 
		logger.info("POST PointsBalance rewards to [{}]", postUrl);

		try {
		    PointsBalance pointsBalance = rewardsHelper.newPointsBalance();
		    logger.info("Client ID:[{}]", pointsBalance.getClientId());

	        URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();

	        HttpEntity<PointsBalance> requestEntity = new HttpEntity<PointsBalance>(pointsBalance);
	        ResponseEntity<PointsBalanceResponse> entityResponse = restTemplate.exchange(postURI, HttpMethod.POST,
	                requestEntity, PointsBalanceResponse.class);

	        Assert.assertNotNull(entityResponse);
	        Assert.assertTrue(entityResponse.getStatusCode().value() == AdapterStatus.OK.xmlValue());
	        Assert.assertNotNull(entityResponse.getBody());

	        rewardsHelper.printPointBalanceResponse(entityResponse.getBody());
		} catch (Exception e) {
			logger.error("Error:", e);
		}
	}
    /**
     *  Unit test POST PointsRate object
     */
	public void getPointsRate() {
	    logger.info("\n");

        String postUrl =hostname + URITemplates.CLIENTS_REWARDS_RATES; 
        logger.info("POST PointsRate rewards to [{}]", postUrl);

        try {
            PointsBalance pointsBalance = rewardsHelper.newPointsBalance();
            logger.info("Client ID:[{}]", pointsBalance.getClientId());

            URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();

            HttpEntity<PointsBalance> requestEntity = new HttpEntity<PointsBalance>(pointsBalance);
            ResponseEntity<PointsRateListResponse> entityResponse = restTemplate.exchange(postURI, HttpMethod.POST,
                    requestEntity, PointsRateListResponse.class);

            Assert.assertNotNull(entityResponse);
            Assert.assertTrue(entityResponse.getStatusCode().value() == AdapterStatus.OK.xmlValue());
            Assert.assertNotNull(entityResponse.getBody());

            rewardsHelper.printPointRateResponse(entityResponse.getBody());
        } catch (Exception e) {
            logger.error("Error:", e);
        }
	}

	
}
