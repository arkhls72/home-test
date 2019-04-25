
package com.home.digital.adapter.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.digital.adapter.AdapterParam;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.model.v1.client.rewards.PointsRate;



/**
 * Extension of {@link BaseAdapter} that handles {@link PointsRate} data.
 * An Adapter service to fullfill Rates inquiries
 * @author arash
 *
 */

public class PointsRateServiecAdapter  extends BaseAdapter<PointsRate>{
	private static Logger logger = LoggerFactory.getLogger(PointsRateServiecAdapter.class);
	
	@Override
    protected List<PointsRate> onGetListForParams(Map<String, Object> parameters) {
        try {
        	if (MapUtils.isEmpty(parameters)) {
        		throw new AdapterException("Map parameters is empty.");
        	}
        	
            List<PointsRate> response = this.getListSample(parameters);            
            if (CollectionUtils.isEmpty(response)) {
                response = new ArrayList<PointsRate>();
                this.setAdapterStatus(AdapterStatus.NOT_FOUND);

                return response;
            }

            this.setAdapterStatus(AdapterStatus.OK);
            return response;
        } catch (NumberFormatException e) {
            this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
           logger.error("Invalid parameter error: {}", e);
           throw e;
        } catch (AdapterException e) {
             this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("onGetListforParams error: {}", e);
            throw e;
        } catch (Exception e) {
            this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("onGetListForParams error: {}", e);
            throw e;
        } catch (Throwable t) {
            this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
            logger.error("onGetListForParams error: {}", t);
            throw new AdapterException(t.toString());
        }        
    }
	
	private  List<PointsRate> getListSample(Map<String,Object> parameters) throws NumberFormatException {
		logger.debug("getListSample called");
		List<PointsRate> response = new ArrayList<PointsRate>();
		
		String clientId = parameters.get(AdapterParam.CLIENT_ID.getCode()) !=null ?  (String) parameters.get(AdapterParam.CLIENT_ID.getCode()):null;
		logger.debug("clientId:" , clientId);
	
		logger.debug("[PointRate 1]");
		PointsRate sample = new PointsRate();

		sample.setClientId(clientId);
		logger.debug("clientId[{}]:  " ,sample.getClientId());
		sample.setBurnRate(65874d);
		logger.debug("burnRate:  " ,sample.getBurnRate());
		
		sample.setEarnRate(14785.00);
		logger.debug("earnRate:[{}]" , sample.getEarnRate());

		response.add(sample);
		
		logger.debug("[PointRate 2]");
		sample = new PointsRate();

		sample.setClientId(clientId);
		logger.debug("clientId[{}]:  " ,sample.getClientId());
		
		sample.setBurnRate(985.00);
		logger.debug("burnRate:[{}] " , sample.getBurnRate());
		
		sample.setEarnRate(554.00);
		logger.debug("earnRate:[{}]" , sample.getEarnRate());
		
		response.add(sample);
		return response;
	}
}
