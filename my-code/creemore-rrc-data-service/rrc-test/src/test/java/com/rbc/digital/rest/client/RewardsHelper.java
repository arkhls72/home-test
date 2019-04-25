
package com.home.digital.rest.client;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.home.digital.model.v1.client.rewards.PointsBalance;
import com.home.digital.model.v1.client.rewards.PointsBalanceResponse;
import com.home.digital.model.v1.client.rewards.PointsConfirmationResponse;
import com.home.digital.model.v1.client.rewards.PointsRateListResponse;
import com.home.digital.model.v1.client.rewards.PointsRedemption;
import com.home.digital.model.v1.client.rewards.PointsTransaction;
import com.home.digital.model.v1.client.rewards.PointsTransactionListResponse;

public class RewardsHelper {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
    *  return new  PointsRedemption to post
    */
   public PointsRedemption newPointsRedemption() {
       PointsRedemption resource = new PointsRedemption();
       resource.setClientId("4519031635834535");
       resource.setCpc("GUS");
       resource.setCardMaskedNumber("451403******9282");
       resource.setMerchantId("aegan-dev");
       resource.setRequestorCode("InApp");
       resource.setTotalAmount(59.99);
       resource.setAmountPaid(645.00);
       resource.setPointsAmount(28.5);
       resource.setPointsToRedeem(150);
       resource.setCurrencyCode("CAD");
       
       resource.setParentTransactionDate("20160309");
//       resource.setParentTransactionAccountNumber("55190000000000000");
//       resource.setParentTransactionTaxes(125.58);
       resource.setMerchantName("maegan-dev");
       
//       resource.setMerchantCity("Toronto");
//       resource.setMerchantCountryCode("SPA");
//       resource.setMerchantId("maegan-dev");
//       resource.setTerminalId("1287");
//       resource.setStateProvince("ON");
//       resource.setMerchantCountryCode("CAN");

       return resource;
   }
   /**
    * 
    * @return new instance of PointsTransaction
    */
   public PointsTransaction newPointsTransaction() {
       PointsTransaction resource = new PointsTransaction();
       resource.setClientId("4519031635834535");
       resource.setCpc("GUS");
//       resource.setStartDate("2012-10-15");
//       resource.setEndDate("2014-03-21");
       
       return resource;
   }
   /**
    * 
    * @return new instance of PointsBalance
    */
   public PointsBalance newPointsBalance() {
       PointsBalance resource = new PointsBalance();
       resource.setClientId("4519031635834535");
       resource.setCpc("GUS");
       return resource;
   }
   /**
   *  Print PointsRedemption object list before post 
   */
   public void printPostRedemptionRequestBody(PointsRedemption resource) {
       logger.debug("\n");
       logger.debug("postSample called");
       logger.info("-----------+------------");
       logger.info("ClientId:[{}]", resource.getClientId());
       logger.info("CurrencyCode:[{}]", resource.getCurrencyCode());
       logger.info("Merchant city:[{}]", resource.getMerchantCity());
       logger.info("Merchant country code:[{}]", resource.getMerchantCountryCode());
       logger.info("Merchant ID:[{}]", resource.getMerchantId());
       logger.info("Merchant name:[{}]", resource.getMerchantName());
       logger.info("Merchant list:[{}]", resource.getParentTransactionMerchandList());
       logger.info("Parent trans number:[{}]", resource.getParentTransactionAccountNumber());
       logger.info("Parent trans taxes:[{}]", resource.getParentTransactionTaxes());
       logger.info("Requestor code:[{}]", resource.getRequestorCode());
       logger.info("Total amount:[{}]", resource.getTotalAmount());
       logger.info("-----------+------------");
   }
   
   /**
   *  Print  POST response object  
    * @throws IOException 
    * @throws FileNotFoundException 
   */
   public void printPostResponse(PointsConfirmationResponse response) throws FileNotFoundException, IOException {
       logger.info("\n");
       logger.info(" Post Response: [PointsConfirmation]");
       logger.info(" -----------------+----------------");

       ObjectMapper mapper = new ObjectMapper();
       String st = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getPointsConfirmation());
       
       logger.info("\n" + st);
       logger.info("-----------+------------");

   }

   /**
   *  Print  GET PointTransaction response   
    * @throws IOException 
    * @throws JsonMappingException 
    * @throws JsonParseException 
   */
   public void printPointTransactionResponse(PointsTransactionListResponse response) throws JsonParseException, JsonMappingException, IOException {
       logger.info("\n");
       logger.info(" POST Response: [PointsTransaction[]]");
       logger.info(" -----------------+-----------------");

       ObjectMapper mapper = new ObjectMapper();
       String st = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getPointsTransactionList());
       logger.info("\n", st);
   }

   
   /**
   *  Print GET PointsBalance response   
    * @throws JsonProcessingException 
   */
   public void printPointBalanceResponse(PointsBalanceResponse response) throws JsonProcessingException {
       logger.info("\n");
       logger.info(" POST Response: [PointsBalance[]]");
       logger.info(" --------------+----------------");
       
        ObjectMapper mapper = new ObjectMapper();
        String st = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getPointsBalance());

        logger.info("\n" + st);
   }
   
   /**
   *  Print GET PointsRate response   
    * @throws JsonProcessingException 
   */
   public void printPointRateResponse(PointsRateListResponse response) throws JsonProcessingException {
       logger.info("\n");
       logger.info(" POST Response: [PointsRate[]]");
       logger.info(" -------------+--------------");

        ObjectMapper mapper = new ObjectMapper();
        String st = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(response.getPointsRateList());
        logger.info("\n" + st);
        logger.info("-----------+------------");
   }

}
