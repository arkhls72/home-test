
package com.home.digital.queue.ejb.local;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.home.digital.queue.ejb.model.GainResponse;
import com.home.digital.queue.ejb.model.GainTransaction;
import com.home.digital.queue.util.EjbProperty;
import com.home.digital.queue.util.Property;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.ClientResponse.Status;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

/**
 * Implementation of RestLocal bean interface to call data power. 
 * @author arash
 
 */
@Stateless(mappedName="RestBean")
public class RestBean implements RestLocal,Serializable {
	private static final long serialVersionUID = -6062211276459509377L;
	private static Logger logger = LoggerFactory.getLogger(RestBean.class);
	
	/**
	 * @param  GainTarnsaction 
	 * Send a json message that was received from queue to data power
	 * @return GainResponse
	 *  @author arash
	 */
	// According to business design no need to return GainResponse but I return the response back in case we need it later on - arash
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public GainResponse sendGainTransaction(GainTransaction data) throws Exception {
		try {

			ClientConfig clientConfig = new DefaultClientConfig();
	        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

	        Client client = Client.create(clientConfig);
	        String url = EjbProperty.getProeprties().get(Property.DP_SERVER_URI) + EjbProperty.getProeprties().get(Property.DP_Gain_URI);

	        if (StringUtils.isEmpty(url))
	        	throw new Exception("empty url");
	        
	        Gson gson = new Gson();
			String json = gson.toJson(data);

	        WebResource entityRequest = client.resource(url);

	        entityRequest.setProperty("Content-Type", "application/json;charset=UTF-8");
	        entityRequest.setProperty("Accept", "application/json");

	        ClientResponse response = entityRequest.type("application/json").post(ClientResponse.class, json);
	        
	        if (response.getStatus() != Status.OK.getStatusCode()) 
	        	throw new Exception("Invalid response was recieved from DataPOwer");
	        
	        GainResponse GainResponse = (GainResponse) response.getEntity(GainResponse.class);
	        if (GainResponse == null || StringUtils.isNotEmpty(GainResponse.getErrorReason()))
	        	throw new  Exception("Gain response recieved from data power has error");
	        
	        logger.info("message was seccessfully sent to data power[{}]",GainResponse.getReferenceNumber());
	        
	        return GainResponse;
		}catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;

		} catch(Throwable t) {
			logger.error(t.getMessage());
			throw t;
		}
	}	

}
