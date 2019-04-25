
package com.home.digital.adapter.remote;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.home.digital.adapter.provider.BuilderProvider;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.common.util.ValidateUtil;
import com.home.digital.model.v1.client.rewards.PointsConfirmation;
import com.home.digital.model.v1.client.rewards.PointsRedemption;
import com.home.digital.model.v1.mlp.client.BurnResponse;

/**
 * Extension of {@link BaseAdapter} that handles {@link PointsRedemption} data.
 * An Adapter service to fullfill Redemption inquiries
 * @author ralu
 * 
 */

public class PointsRedemptionServiceAdapter extends AbstractPointsAdapter<PointsRedemption> {
    private static Logger logger = LoggerFactory.getLogger(PointsRedemptionServiceAdapter.class);

    /**
     * calls Data power to POST redemption
     *@return  PointsConfirmation 
     */
    @Override        
    protected Object onPostForResource(PointsRedemption resource) {
        ResponseEntity<?> responseEntity = null;
        try {

            List<String> errors = this.validateBurnRequest(resource);
            ValidateUtil.parameters(errors,"RRC:PointsRedemptionServiceAdapter.onPostForResource().");

            URI uri = UriComponentsBuilder.fromUriString(remoteProperties.getDataPowerBurnURL()).build().toUri();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("json mlp request[{}]", commonJsonMessageConverter.marshal(resource));
            }

            HttpEntity<?> requestEntity = new HttpEntity<PointsRedemption>(resource, this.getJsonEntityHeader());
            responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,
            BurnResponse.class);

//            ValidateUtil.responseEntity(responseEntity, "Burn response");
            
            BurnResponse burnResponse = (BurnResponse) responseEntity.getBody();
            if (burnResponse == null) {
                throw new AdapterException("Data power Burn response object [jsonElement] has no body.");
            }
            
            PointsConfirmation response = new PointsConfirmation();
            BuilderProvider.build(burnResponse, response);
            
            if (StringUtils.isNotEmpty(burnResponse.getErrorReason())) {
                this.setAdapterStatus(AdapterStatus.UNPROCESSIBLE_ENTITY);
                this.setErrorText(burnResponse.getErrorReason());
            } else {
                this.setAdapterStatus(AdapterStatus.CREATED);    
            }

            return response;
        } catch (NumberFormatException e) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsRedemptionServiceAdapter. Invalid parameter error: {}", e);
            throw e;
        } catch (AdapterException e) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsRedemptionServiceAdapter.onGetListForParams error: {}", e);
            throw e;
        } catch (Exception e) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsRedemptionServiceAdapter.onGetListForParams error: {}", e);
            throw e;
        } catch (Throwable t) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsRedemptionServiceAdapter.onGetListForParams error: {}", t);
            throw new AdapterException(t.toString());
        }
    }
    /**
     * 
     * @param resource PointsRedemption
     * @return List of empty attributes in PointsRedemption 
     */
    private List<String> validateBurnRequest(PointsRedemption resource) {
        List<String> errors = new ArrayList<String>();
        
        if (resource.getAmountPaid() == null) {
            errors.add("AmountPaid");
        }

        if (StringUtils.isEmpty(resource.getCardMaskedNumber()) ) {
            errors.add("CardMaskNumber");
        }

        
        if (StringUtils.isEmpty(resource.getClientId())) {
            errors.add("ClientId");
        }

        if (StringUtils.isEmpty(resource.getCpc())) {
            errors.add("Cpc");
        }

        if (StringUtils.isEmpty(resource.getCurrencyCode())) {
            errors.add("CurrencyCode");
        }

        if (StringUtils.isEmpty(resource.getMerchantId())) {
            errors.add("MerchantId");
        }

        if (StringUtils.isEmpty(resource.getParentTransactionDate())) {
            errors.add("ParentTransactionDate");
        }

        if (resource.getPointsToRedeem()==null) {
            errors.add("PointsToRedeem");
        }

        if (StringUtils.isEmpty(resource.getRequestorCode())) {
            errors.add("RequestorCode");
        }

        if (StringUtils.isEmpty(resource.getRequestorCode())) {
            errors.add("RequestorCode");
        }
        
        if (resource.getTotalAmount() == null) {
            errors.add("TotalAmount");
        }
        return errors;
    }
}
