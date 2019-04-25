

package com.home.digital.adapter.remote;

import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.home.digital.adapter.AdapterParam;
import com.home.digital.adapter.provider.BuilderProvider;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.common.util.ValidateUtil;
import com.home.digital.model.v1.client.rewards.PointsTransaction;
import com.home.digital.model.v1.mlp.client.MlpErrorResponse;
import com.home.digital.model.v1.mlp.client.TransactionRequest;
import com.home.digital.model.v1.mlp.client.TransactionResponse;

/**
 *Extension of {@link BaseAdapter} that handles paginated {@link PointsTransaction} data.
 * An Adapter service to fullfill TransactionHistory inquiries  
 * @author ralu
 *
 */

public class PointsTransactionServiceAdapter extends AbstractPointsAdapter<PointsTransaction> {
    private static Logger logger = LoggerFactory.getLogger(PointsTransactionServiceAdapter.class);

    /**
     * Implementation of super class onGetListForParams()
     * 
     * @param Map of parameters
     * @return list of PointsTransaction
     */
    protected List<PointsTransaction> onGetListForParams(Map<String, Object> parameters) {
        try {
            List<PointsTransaction> response = new ArrayList<PointsTransaction>();

            if (MapUtils.isEmpty(parameters)) {
                throw new AdapterException("Map parameters is empty.");
            }

            TransactionRequest entity = new TransactionRequest();

            entity.setClientId((String) parameters.get(AdapterParam.CLIENT_ID.getCode()));
            entity.setCpc((String) parameters.get(AdapterParam.CPC.getCode()));
            
            List<String> errors = validateTransactionRequest(entity);
            ValidateUtil.parameters(errors,"PointsTransactionServiceAdapter.onGetListForParams().");

            if (propertiesProvider.isDebugging() && logger.isDebugEnabled()) {
                logger.debug("json mlp request[{}]",commonJsonMessageConverter.marshal(entity));    
            }

            URI uri = UriComponentsBuilder.fromUriString(remoteProperties.getDataPowerTranactionHistoryURL()).build().toUri();

            HttpEntity<?> requestEntity = new HttpEntity<TransactionRequest>(entity, this.getJsonEntityHeader());
            ResponseEntity<JsonElement> responseEntity = restTemplate.exchange(uri, HttpMethod.POST, requestEntity,JsonElement.class);

            ValidateUtil.responseEntity(responseEntity, "DP:Transaction History");
            JsonElement jsonElement = (JsonElement)  responseEntity.getBody();

            if (jsonElement == null) {
                throw new AdapterException("Data power Transaction history response object has no body.");
            }

            if (!jsonElement.isJsonArray()) {
                 MlpErrorResponse source = new Gson().fromJson(responseEntity.getBody().toString(),MlpErrorResponse.class);

                 int errorStatus = Integer.valueOf(source.getStatusCode());
                 AdapterStatus status = (errorStatus == AdapterStatus.NOT_FOUND.xmlValue()) ? AdapterStatus.NOT_FOUND:AdapterStatus.INTERNAL_SERVER_ERROR;

                 this.setAdapterStatus(status);
                 this.setErrorText(source.getErrorReason());

                 return response;
            } 

            Type listType = new TypeToken<ArrayList<TransactionResponse>>()  {}.getType();
            List<TransactionResponse> source = new Gson().fromJson(responseEntity.getBody().toString(), listType);

            BuilderProvider.build(source, response);
            if (CollectionUtils.isEmpty(response)) {
                response = new ArrayList<PointsTransaction>();
                this.setAdapterStatus(AdapterStatus.NOT_FOUND);

                return response;
            }

            this.setAdapterStatus(AdapterStatus.OK);
            return response;
        } catch (AdapterException e) {
            this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsTransactionServiceAdapter.onGetListForResource error: {}", e);
            throw e;
        } catch (Exception e) {
            this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsTransactionServiceAdapter.onGetListForResource error: {}", e);
            throw new AdapterException(e);
        } catch (Throwable t) {
            this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("PointsTransactionServiceAdapter.onGetListForResource error: {}", t);
            throw new AdapterException(t.toString());
        }
    }
    /**
     * 
     * @param resource
     * @return a list of empty attributes in TransactionRequest object
     */
    private List<String> validateTransactionRequest(TransactionRequest resource) {
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
