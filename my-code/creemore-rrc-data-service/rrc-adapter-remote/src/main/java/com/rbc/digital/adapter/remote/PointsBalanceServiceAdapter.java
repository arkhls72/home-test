
package com.home.digital.adapter.remote;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.JsonElement;
import com.home.digital.adapter.AdapterParam;
import com.home.digital.adapter.provider.BuilderProvider;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.common.util.ValidateUtil;
import com.home.digital.model.v1.client.rewards.PointsBalance;
import com.home.digital.model.v1.mlp.client.BalanceRequest;
import com.home.digital.model.v1.mlp.client.BalanceResponse;
import com.home.digital.model.v1.mlp.client.MlpErrorResponse;

/**
 * Extension of {@link BaseAdapter} that handles {@link PointsBalance} data.
 * An Adapter service to fullfill Balances inquiries
 * @author arash
 * 
 */


public class PointsBalanceServiceAdapter extends AbstractPointsAdapter<PointsBalance> {
    private static Logger logger = LoggerFactory.getLogger(PointsBalanceServiceAdapter.class);
    /**
     * @return PointBalance
     */
    protected List<PointsBalance> onGetListForParams(Map<String, Object> parameters) throws AdapterException{
        try {
            List<PointsBalance> response = new ArrayList<PointsBalance>();

            if (MapUtils.isEmpty(parameters)) {
                throw new AdapterException("Map parameters is empty.");
            }
            
            BalanceRequest entity = new BalanceRequest();

            entity.setClientId((String) parameters.get(AdapterParam.CLIENT_ID.getCode()));
            entity.setCpc((String) parameters.get(AdapterParam.CPC.getCode()));
            
            List<String> errors = this.validateBalanceRequest(entity);
            ValidateUtil.parameters(errors,"RRC:PointsBalanceServiceAdapter.onGetListForParams().");

            URI uri = UriComponentsBuilder.fromUriString(remoteProperties.getDataPowerBalanceURL()).build().toUri();
            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("json mlp request[{}]",commonJsonMessageConverter.marshal(entity));    
            }

            HttpEntity<?> requestEntity = new HttpEntity<BalanceRequest>(entity,this.getJsonEntityHeader());
            ResponseEntity<JsonElement> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,JsonElement.class);
            
            ValidateUtil.responseEntity(responseEntity, "DP:Balances");

            JsonElement jsonElement = (JsonElement)  responseEntity.getBody();
            if (jsonElement ==null) {
                throw new AdapterException("Data power Balance response object [jsonElement] has no body.");
            }

            MlpErrorResponse source = gsonConverter.getGson().fromJson(responseEntity.getBody().toString(),MlpErrorResponse.class);
            if (source == null) {
                throw new AdapterException("Data power Burn response object [MlpErrorResponse] has no body."); 
            }
             // statusCode would be the error received from DP. It is empty when there is no error 
            if (StringUtils.isNotEmpty(source.getStatusCode())) {
                 int errorStatus = Integer.valueOf(source.getStatusCode());
                 AdapterStatus status = (errorStatus == AdapterStatus.NOT_FOUND.xmlValue()) ? AdapterStatus.NOT_FOUND:AdapterStatus.INTERNAL_SERVER_ERROR;

                 this.setAdapterStatus(status);
                 this.setErrorText(source.getErrorReason());

                 return response;
            }                 

            BalanceResponse balance = gsonConverter.getGson().fromJson(responseEntity.getBody(), BalanceResponse.class);
            
            PointsBalance target = BuilderProvider.build(balance);            
            if (balance == null || target == null) {
                this.setAdapterStatus(AdapterStatus.NOT_FOUND);
                return response;
            }

            this.setAdapterStatus(AdapterStatus.OK);
            response.add(target);
            return response;
        } catch (NumberFormatException e) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsBalanceServiceAdapter. Invalid parameter error: {}", e);
            throw e;
        } catch (AdapterException e) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsBalanceServiceAdapter.onGetListForParams error: {}", e);
            throw e;
        } catch (Exception e) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsBalanceServiceAdapter.onGetListForParams error: {}", e);
            throw e;
        } catch (Throwable t) {
            setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsBalanceServiceAdapter.onGetListForParams error: {}", t);
            throw new AdapterException(t.toString());
        }
    }

    /** 
     * @param resource BalanceRequest
     * @return List of empty attributes in BalacneRequest 
     */
    private List<String> validateBalanceRequest(BalanceRequest resource) {
        List<String> errors = new ArrayList<String>();
        if (StringUtils.isEmpty(resource.getClientId())) {
            errors.add("ClientId");
        }

        if (StringUtils.isEmpty(resource.getCpc())) {
            errors.add("Cpc");
        }

        return errors;
    }
}
