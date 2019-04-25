
package com.home.digital.server.v1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.home.digital.adapter.AdapterParam;
import com.home.digital.adapter.remote.PointsBalanceServiceAdapter;
import com.home.digital.adapter.remote.PointsRateServiecAdapter;
import com.home.digital.adapter.remote.PointsRedemptionServiceAdapter;
import com.home.digital.adapter.remote.PointsTransactionPageServiceAdapter;
import com.home.digital.adapter.remote.PointsTransactionServiceAdapter;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.convertor.JsonMessageConverter;
import com.home.digital.common.v1.URITemplates;
import com.home.digital.model.v1.client.rewards.Page;
import com.home.digital.model.v1.client.rewards.PageResponse;
import com.home.digital.model.v1.client.rewards.PointsBalance;
import com.home.digital.model.v1.client.rewards.PointsBalanceResponse;
import com.home.digital.model.v1.client.rewards.PointsConfirmation;
import com.home.digital.model.v1.client.rewards.PointsConfirmationResponse;
import com.home.digital.model.v1.client.rewards.PointsRate;
import com.home.digital.model.v1.client.rewards.PointsRateListResponse;
import com.home.digital.model.v1.client.rewards.PointsRedemption;
import com.home.digital.model.v1.client.rewards.PointsTransaction;
import com.home.digital.model.v1.client.rewards.PointsTransactionListResponse;
import com.home.digital.model.v1.service.response.Status;
import com.home.digital.queue.ejb.model.EarnTransaction;

import com.home.digital.queue.ejb.remote.QueueRelayRemote;
import com.home.digital.server.provider.PropertiesProvider;
import com.home.digital.common.v1.BaseController;
import com.home.digital.common.v1.InputParameterException;

/**
 * REST Service APIs for Rewards operations. 
 * @author arash
 */
@Controller
public class RewardsController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected PropertiesProvider propertiesProvider;
    @Autowired
    private PointsRedemptionServiceAdapter redemptionServiceAdapter;
    @Autowired  
    private PointsTransactionServiceAdapter transactionServiceAdapter;
    @Autowired
    private PointsRateServiecAdapter rateServiecAdapter;
    @Autowired
    private PointsBalanceServiceAdapter balanceServiceAdapter;
    @Autowired
    private PointsTransactionPageServiceAdapter pointsTransactionPageServiceAdapter;
    @Autowired
    private JsonMessageConverter commonJsonMessageConverter;

    /**
     * HTTP POST Method to create redemption The response status code is set as
     * follows:
     * <ul>
     * <li>Status code (201) indicating the request succeeded normally and
     * PointsRedemption object was created</li>
     * <li>In case of error the request will be forwarded to the relevant
     * Exception handler in BaseController</li>
     * 
     * @param PointsRedemption (request body)
     * @param response
     * @return An PointsConfirmationResponse object
     * @throws Exception and AdapterException
     */

    @RequestMapping(value = URITemplates.CLIENTS_REWARDS_REDEMPTIONS, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PointsConfirmationResponse postPointsRedemption(@RequestBody PointsRedemption pointsRedemption,
            HttpServletResponse response) throws Exception {
        try {
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] ", URITemplates.CLIENTS_REWARDS_REDEMPTIONS);
                logger.debug(commonJsonMessageConverter.marshal(pointsRedemption));
            }

            QueueRelayRemote bean = (QueueRelayRemote)  this.getInitialContext().lookup("java:jboss/exported/rrc-api/" + QueueRelayRemote.JNDI);
            bean.sendTextMessagetoQueue();
            if (pointsRedemption == null) {
                throw new InputParameterException("Inbound pointsRedemption object in request body is null. "
                        + " postPointsRedemption(pointsRedemption)");
            }

            PointsConfirmation pointsConfirm = (PointsConfirmation) redemptionServiceAdapter
                    .postForResource(pointsRedemption);

            AdapterStatus adapterStatus = redemptionServiceAdapter.getAdapterStatus();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] status [{}]", URITemplates.CLIENTS_REWARDS_REDEMPTIONS,
                        adapterStatus.xmlValue());
            }

            Status status = new Status();
            response.setStatus(AdapterStatus.OK.xmlValue());
            status.setHttpStatus(adapterStatus.xmlValue());

            if (adapterStatus != AdapterStatus.CREATED) {
                status.setErrorText(redemptionServiceAdapter.getErrorText());
                status.setErrorCode(String.valueOf(adapterStatus.xmlValue()));
            } else {
                String location = URITemplates.CLIENTS_REWARDS_REDEMPTIONS.concat("/").concat(
                        pointsConfirm.getRefNumber());
                response.setHeader("location", location);
            }

            PointsConfirmationResponse pointsResponse = new PointsConfirmationResponse();
            pointsResponse.setPointsConfirmation(pointsConfirm);

            pointsResponse.setStatus(status);
            
            return pointsResponse;

        } catch (Throwable t) {
            logger.error("postPointsRedemption error: {}", t);
            throw new AdapterException(t.toString());
        }
    }

    /**
     * HTTP GET Method to retrieve array of PointsTransactionListResponse. The
     * response status code is set as follows:
     * <ul>
     * <li>Status code (200) indicating the request succeeded normally</li>
     * <li>Status code (404) indicating the request was OK but an expected
     * result was not found</li>
     * <li>In case of error the request will be forwarded to the relevant
     * Exception handler in BaseController</li>
     * 
     * @param clientId , optional : HttpRequest parameters [startDate.endDate]
     *            and/or filter range
     * @param response
     * @return PointsTransactionListResponse object
     * @throws Exception and AdapterException
     */

    @RequestMapping(value = URITemplates.CLIENTS_REWARDS_TRANSACTIONS, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PointsTransactionListResponse getPointsTransactions(@RequestBody PointsTransaction pointsTransaction,
            HttpServletResponse response) throws Exception {

        try {
            if (pointsTransaction == null) {
                throw new InputParameterException("Request  pointsRedemption is null. "
                        + " getPointsTransactions(pointsRedemption)");
            }

            if (StringUtils.isEmpty(pointsTransaction.getClientId())) {
                throw new InputParameterException("Request pointsRedemption [clientId] is null. "
                        + " getPointsTransactions(pointsRedemption)");
            }

            if (StringUtils.isEmpty(pointsTransaction.getCpc())) {
                throw new InputParameterException("Request pointsRedemption [cpc] is null. "
                        + " getPointsTransactions(pointsRedemption)");
            }

            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] clientId [{}]", URITemplates.CLIENTS_REWARDS_TRANSACTIONS,
                        pointsTransaction.getClientId());
                logger.debug(commonJsonMessageConverter.marshal(pointsTransaction));
            }

            Map<String, Object> allParams = new HashMap<String, Object>();
            allParams.put(AdapterParam.CLIENT_ID.getCode(), pointsTransaction.getClientId());
            allParams.put(AdapterParam.CPC.getCode(), pointsTransaction.getCpc());

            if (StringUtils.isNotEmpty(pointsTransaction.getStartDate())) {
                allParams.put(AdapterParam.START_DATE.getCode(), pointsTransaction.getStartDate());
            }

            if (StringUtils.isNotEmpty(pointsTransaction.getEndDate())) {
                allParams.put(AdapterParam.END_DATE.getCode(), pointsTransaction.getEndDate());
            }

            List<PointsTransaction> points = (List<PointsTransaction>) transactionServiceAdapter
                    .getListForParams(allParams);

            AdapterStatus adapterStatus = transactionServiceAdapter.getAdapterStatus();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("GET [{}] status [{}]", URITemplates.CLIENTS_REWARDS_TRANSACTIONS,
                        adapterStatus.xmlValue());
            }

            response.setStatus(adapterStatus.xmlValue() == AdapterStatus.OK.xmlValue()
                    || adapterStatus.xmlValue() == AdapterStatus.NOT_FOUND.xmlValue() ? AdapterStatus.OK.xmlValue()
                    : adapterStatus.xmlValue());

            Status status = new Status();
            status.setHttpStatus(adapterStatus.xmlValue());
            status.setErrorText(transactionServiceAdapter.getErrorText());

            PointsTransactionListResponse pointResponse = new PointsTransactionListResponse();
            pointResponse.setPointsTransactionList(points);

            pointResponse.setStatus(status);
            return pointResponse;

        } catch (Throwable t) {
            logger.error("getPointsTransactions error: {}", t);
            throw new AdapterException(t.toString());
        }
    }

    /**
     * HTTP GET Method to retrieve PointsBalanceResponse The response status
     * code is set as follows:
     * <ul>
     * <li>Status code (200) indicating the request succeeded normally</li>
     * <li>Status code (404) indicating the request was OK but an expected
     * result was not found</li>
     * <li>In case of error the request will be forwarded to the relevant
     * Exception handler in BaseController</li>
     * 
     * @param clientId , optional : HttpRequest parameter [rewardsId] filter
     *            range
     * @param response
     * @return PointsBalanceListResponse object
     * @throws Exception and AdapterException
     */
    @RequestMapping(value = URITemplates.CLIENTS_REWARDS_BALANCES, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PointsBalanceResponse getPointsBalances(HttpServletResponse response,
            @RequestBody PointsBalance pointsBalance) throws Exception {
        try {
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}]", URITemplates.CLIENTS_REWARDS_BALANCES);
                logger.debug(commonJsonMessageConverter.marshal(pointsBalance));
            }

            if (pointsBalance == null) {
                throw new InputParameterException("Request pointBalanace is null."
                        + " getPointsBalances(pointsRedemption)");
            }

            Map<String, Object> allParams = new HashMap<String, Object>();
            allParams.put(AdapterParam.CLIENT_ID.getCode(), pointsBalance.getClientId());
            allParams.put(AdapterParam.CPC.getCode(), pointsBalance.getCpc());

            List<PointsBalance> points = balanceServiceAdapter.getListForParams(allParams);

            if (CollectionUtils.isEmpty(points)) {
                PointsBalance point = new PointsBalance();
                point.setClientId(allParams.get(AdapterParam.CLIENT_ID.getCode()).toString());
                point.setCpc(AdapterParam.CPC.getCode());

                points.add(point);
            }

            AdapterStatus adapterStatus = balanceServiceAdapter.getAdapterStatus();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] clientID [{}]", URITemplates.CLIENTS_REWARDS_BALANCES, adapterStatus.xmlValue());
            }

            response.setStatus(adapterStatus.xmlValue() == AdapterStatus.OK.xmlValue()
                    || adapterStatus.xmlValue() == AdapterStatus.NOT_FOUND.xmlValue() ? AdapterStatus.OK.xmlValue()
                    : adapterStatus.xmlValue());

            Status status = new Status();
            status.setHttpStatus(adapterStatus.xmlValue());
            status.setErrorText(balanceServiceAdapter.getErrorText());

            PointsBalanceResponse pointsResponse = new PointsBalanceResponse();
            pointsResponse.setPointsBalance(points.get(0));

            pointsResponse.setStatus(status);
            return pointsResponse;

        } catch (Throwable t) {
            logger.error("getPointsBalances error: {}", t);
            throw new AdapterException(t.toString());
        }
    }

    /**
     * HTTP GET Method to retrieve PointsRateListResponse The response status
     * code is set as follows:
     * <ul>
     * <li>Status code (200) indicating the request succeeded normally</li>
     * <li>Status code (404) indicating the request was OK but an expected
     * result was not found</li>
     * <li>In case of error the request will be forwarded to the relevant
     * Exception handler in BaseController</li>
     * 
     * @param clientId , optional : HttpRequest parameter [rewardsId] filter
     *            range
     * @param response
     * @return PointsRateListResponse object
     * @throws Exception and AdapterException
     */
    @RequestMapping(value = URITemplates.CLIENTS_REWARDS_RATES, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PointsRateListResponse getPointsRates(HttpServletResponse response, @RequestBody PointsRate pointsRate)
            throws Exception {
        try {
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}]", URITemplates.CLIENTS_REWARDS_RATES);
                logger.debug(commonJsonMessageConverter.marshal(pointsRate));
            }

            if (pointsRate.getClientId() == null) {
                throw new InputParameterException("Request pointsRate is null." + " getPointsRates(pointRate)");
            }

            Map<String, Object> allParameters = new HashMap<String, Object>();
            allParameters.put(AdapterParam.CLIENT_ID.getCode(), pointsRate.getClientId());

            List<PointsRate> points = rateServiecAdapter.getListForParams(allParameters);

            AdapterStatus adapterStatus = rateServiecAdapter.getAdapterStatus();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] status [{}]", URITemplates.CLIENTS_REWARDS_BALANCES, adapterStatus.xmlValue());
            }

            response.setStatus(adapterStatus.xmlValue());

            PointsRateListResponse pointsResponse = new PointsRateListResponse();
            pointsResponse.setPointsRateList(points);

            Status status = new Status();
            status.setHttpStatus(response.getStatus());

            pointsResponse.setStatus(status);
            return pointsResponse;
        } catch (Throwable t) {
            logger.error("getPointsBalances error: {}", t);
            throw new AdapterException(t);
        }
    }

    /**
     * HTTP GET Method to retrieve paginated TransactionHistory. The response
     * status code is set as follows:
     * <ul>
     * <li>Status code (200) indicating the request succeeded normally</li>
     * <li>Status code (404) indicating the request was OK but an expected
     * result was not found</li>
     * <li>In case of error the request will be forwarded to the relevant
     * Exception handler in BaseController</li>
     * 
     * @param page
     * @param response
     * @return PageResponse
     */
    @RequestMapping(value = URITemplates.CLIENTS_REWARDS_TRANSACTIONS_PAGE, method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public PageResponse getPointsTransactionsPage(@RequestBody Page page, HttpServletResponse response) {
        try {
            PageResponse pageResponse = null;

            if (page == null) {
                throw new InputParameterException("Request  page is null. " + "getPointsTransactionsPage(page)");
            }

            if (page.getClientId() == null) {
                throw new InputParameterException("Request page.[clientId] is null. "
                        + " getPointsTransactionsPage(page)");
            }

            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] clientId [{}]", URITemplates.CLIENTS_REWARDS_TRANSACTIONS_PAGE,
                        page.getClientId());
                logger.debug(commonJsonMessageConverter.marshal(page));
            }

            Map<String, Object> allParams = new HashMap<String, Object>();
            allParams.put(AdapterParam.CLIENT_ID.getCode(), page.getClientId());
            allParams.put(AdapterParam.CPC.getCode(), page.getCpc());


            if (StringUtils.isNotEmpty(page.getStartDate())) {
                allParams.put(AdapterParam.START_DATE.getCode(), page.getStartDate());
            }

            if (StringUtils.isNotEmpty(page.getEndDate())) {
                allParams.put(AdapterParam.END_DATE.getCode(), page.getEndDate());
            }

            if (page.getPageSize() != null) {
                allParams.put(AdapterParam.PAGE_SIZE.getCode(), page.getPageSize());
            }

            if (page.getPageNum() != null) {
                allParams.put(AdapterParam.PAGE_NUMBER.getCode(), page.getPageNum());
            }

            List<PageResponse> pageResponseList = pointsTransactionPageServiceAdapter.getListForParams(allParams);
            if (CollectionUtils.isNotEmpty(pageResponseList)) {
                pageResponse = pageResponseList.get(0);
            }

            AdapterStatus adapterStatus = pointsTransactionPageServiceAdapter.getAdapterStatus();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("POST [{}] status [{}]", URITemplates.CLIENTS_REWARDS_BALANCES, adapterStatus.xmlValue());
            }

            response.setStatus(adapterStatus.xmlValue() == AdapterStatus.OK.xmlValue()
                    || adapterStatus.xmlValue() == AdapterStatus.NOT_FOUND.xmlValue() ? AdapterStatus.OK.xmlValue()
                    : adapterStatus.xmlValue());

            Status status = new Status();
            status.setHttpStatus(adapterStatus.xmlValue());

            pageResponse.setStatus(status);
            return pageResponse;
        } catch (Throwable t) {
            logger.error("getPointsTransactionsPage error: {}", t);
            throw new AdapterException(toString());
        }
    }
    
    InitialContext getInitialContext() throws NamingException{
        InitialContext context=null;
        try {
            context = new InitialContext();
        }catch(Exception ex) {
            logger.error(ex.getMessage());
        }
        
        return context;
    }
    
    @RequestMapping(value = "/v1/test", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Object postEarn(@RequestBody EarnTransaction earn) {
        EarnTransaction ern2 = earn;
        return earn;
    }
}
