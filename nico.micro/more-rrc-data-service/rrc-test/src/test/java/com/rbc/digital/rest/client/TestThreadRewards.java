
package com.home.digital.rest.client;

import java.net.URI;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.digital.common.v1.URITemplates;
import com.home.digital.model.v1.client.rewards.PointsBalance;
import com.home.digital.model.v1.client.rewards.PointsBalanceResponse;
import com.home.digital.model.v1.client.rewards.PointsConfirmationResponse;
import com.home.digital.model.v1.client.rewards.PointsRedemption;
import com.home.digital.model.v1.client.rewards.PointsTransaction;
import com.home.digital.model.v1.client.rewards.PointsTransactionListResponse;
import com.home.digital.rest.BaseTestClient;
/**
 *  Test Rewards controller calling with multiple threads
 * @author ralu
 *
 */
public class TestThreadRewards extends BaseTestClient {
    private static final int NUM_THREADS_PER_SERVICE = 3;
    // There are 3 services to be called by threads
    private static final int NUM_SERVICES  = 3;
 
    private static final int MAX_NUM_THREADS = NUM_THREADS_PER_SERVICE * NUM_SERVICES;
    private static final int NUM_ITEMS = 3;

    @Autowired
    private String hostname;
    private Thread[] thread = new Thread[MAX_NUM_THREADS];
    private RewardsHelper rewardsHelper = new RewardsHelper();

    @Test
    public void testRewards() {
        executeRunable();

    }

    /******************************************************************************/
    /********************************** Unit tests *******************************/
    /******************************************************************************/
    // post Redemption - Burn
    public void executeRunable() {
        logger.info("--------------------------------------------------");
        logger.info("running with {} threads processing {} items", MAX_NUM_THREADS, NUM_ITEMS);
        logger.info("--------------------------------------------------");

        int numThreads = MAX_NUM_THREADS;

        logger.info("creating burn threads");

        for (int i = 0; i < numThreads; i++) {
            WorkRunable work = new WorkRunable();
            thread[i] = new Thread(work);
        }
         
        logger.info("starting burn threads");
        for (int i = 0; i < numThreads ; i++) {
            thread[i].start();
        }

        logger.info("joining burn threads");
        for (int i = 0; i < numThreads; i++) {
            try {
                thread[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    /** 
     * 
     * POST Redemption - Burn
     * @author 318434917
     *
     */
    private class WorkRunable implements Runnable {

        @SuppressWarnings("rawtypes")
        @Override
        public void run() {
            int numItems = MAX_NUM_THREADS;

            ResponseEntity[] responseEntities = new ResponseEntity[numItems];
            for (int i = 0; i < NUM_THREADS_PER_SERVICE; i++) {
                responseEntities[i] = postRedemption();
            }
            
            for (int i=NUM_THREADS_PER_SERVICE; i < NUM_THREADS_PER_SERVICE * 2; i++) {
                responseEntities[i] = postRetrieveBalance();
            }

            for (int i = NUM_THREADS_PER_SERVICE * 2; i < NUM_THREADS_PER_SERVICE * 3; i++) {
                responseEntities[i] = postRetrievePointsTransaction();
            }
        }
    }
    
    /*****************************************************************************/
    /*********************************** Services ********************************/
    /*****************************************************************************/    
    /**
     * retrieve PointsTransaction 
     * @return ResponseEntity
     */
    private ResponseEntity<?> postRetrievePointsTransaction() {
        logger.info("\n");
        
        long start = System.currentTimeMillis();
        String postUrl = hostname + URITemplates.CLIENTS_REWARDS_TRANSACTIONS;

        logger.info("POST PointsTransaction rewards to [{}]", postUrl);

        PointsTransaction transaction = rewardsHelper.newPointsTransaction();
        ResponseEntity<PointsTransactionListResponse> entityResponse = null;
        try {

            logger.info("Client ID:[{}]", transaction.getClientId());

            // optional
            logger.info("Optional Start date:[{}]", transaction.getStartDate());
            logger.info("Optional End date:[{}]", transaction.getEndDate());

            URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();

            HttpEntity<PointsTransaction> requestEntity = new HttpEntity<PointsTransaction>(transaction);
            entityResponse = restTemplate.exchange(postURI, HttpMethod.POST, requestEntity,
                    PointsTransactionListResponse.class);
            PointsTransactionListResponse response = (PointsTransactionListResponse) entityResponse.getBody();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(response.getPointsTransactionList());

            long end = System.currentTimeMillis();
            logger.info("POST- retrieve transaction history [{}] in [{}] milliseconds", json, end - start);

            return entityResponse;
        } catch (Exception e) {
            logger.error("Error:", e);
        }

        return entityResponse;
    }

        /**
         * POST redemption
         * @return ResponseEntity
         */
        public ResponseEntity<?> postRedemption() {
            logger.info("\n");        
        long start = System.currentTimeMillis();
        ResponseEntity<PointsConfirmationResponse> entityResponse = null;

        String postUrl = hostname + URITemplates.CLIENTS_REWARDS_REDEMPTIONS;

        logger.info("posting PointsRedemption rewards to [{}]", postUrl);

        try {
            PointsRedemption redemption = rewardsHelper.newPointsRedemption();
            rewardsHelper.printPostRedemptionRequestBody(redemption);

            URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();
            logger.info("Request url: [{}]", postURI.toString());

            HttpEntity<PointsRedemption> requestEntity = new HttpEntity<PointsRedemption>(redemption);
            entityResponse = restTemplate.exchange(postURI, HttpMethod.POST, requestEntity,
                    PointsConfirmationResponse.class);

            PointsConfirmationResponse response = (PointsConfirmationResponse) entityResponse.getBody();

            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(response.getPointsConfirmation());

            long end = System.currentTimeMillis();
            logger.info("POST- burn redemption  [{}] in [{}] milliseconds", json, end - start);

        } catch (Exception ex) {
            logger.error("", ex);
        }

        return entityResponse;
    }
    
        /**
         * 
         * @return Spring ResponseEnity for balance 
         */
    public ResponseEntity<?> postRetrieveBalance() {
        logger.info("\n");
        long start = System.currentTimeMillis();

        ResponseEntity<PointsBalanceResponse> entityResponse  = null;
        String postUrl =hostname + URITemplates.CLIENTS_REWARDS_BALANCES;

        logger.info("POST PointsBalance rewards to [{}]", postUrl);
        try {
            PointsBalance pointsBalance = rewardsHelper.newPointsBalance();
            logger.info("Client ID:[{}]", pointsBalance.getClientId());

            URI postURI = UriComponentsBuilder.fromUriString(postUrl).build().toUri();

            HttpEntity<PointsBalance> requestEntity = new HttpEntity<PointsBalance>(pointsBalance);
            entityResponse = restTemplate.exchange(postURI, HttpMethod.POST,
                    requestEntity, PointsBalanceResponse.class);
            
            PointsBalanceResponse response = (PointsBalanceResponse) entityResponse.getBody();
            
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(response.getPointsBalance());

            long end = System.currentTimeMillis();
            logger.info("POST- retrieve balance  [{}] in [{}] milliseconds", json, end - start);
        } catch (Exception e) {
            logger.error("Error:", e);
        }
        
        return entityResponse;
    }
    
}
