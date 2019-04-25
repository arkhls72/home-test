

package com.home.digital.adapter.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.home.digital.adapter.AdapterParam;
import com.home.digital.common.adapter.AdapterException;
import com.home.digital.common.adapter.AdapterStatus;
import com.home.digital.common.adapter.base.BaseAdapter;
import com.home.digital.common.util.Paginatore;
import com.home.digital.common.util.ValidateUtil;
import com.home.digital.model.v1.client.rewards.Page;
import com.home.digital.model.v1.client.rewards.PageResponse;
import com.home.digital.model.v1.client.rewards.PointsTransaction;

/**
 *Extension of {@link BaseAdapter} that handles paginated {@link PointsTransaction} data.
 * An Adapter service to fullfill paginated TransactionHistory inquiries  
 * @author arash
 *
 */

public class PointsTransactionPageServiceAdapter extends AbstractPointsAdapter <PageResponse>{

    @Autowired
    private PointsTransactionServiceAdapter pointsTransactionServiceAdapter;

    protected List<PageResponse> onGetListForParams(Map<String, Object> parameters) {
            List<PageResponse> response = new ArrayList<PageResponse>();                
            PageResponse pageResponse = new PageResponse();
            Page page = new Page();
            
            try {
                Paginatore<PointsTransaction> paginator=null;
                if (MapUtils.isEmpty(parameters)) {
                    throw new AdapterException("Map parameters is empty.");
                }
                
                page.setClientId( (String) parameters.get(AdapterParam.CLIENT_ID.getCode()));
                page.setCpc((String) parameters.get(AdapterParam.CPC.getCode()));

                List<String> errors = this.validateTransactionPageRequest(page);
                ValidateUtil.parameters(errors,"PointsTransactionPageServiceAdapter.onGetListForParams().");
                
                Integer pageNumber= (Integer) parameters.get(AdapterParam.PAGE_NUMBER.getCode());
                Integer pageSize= (Integer) parameters.get(AdapterParam.PAGE_SIZE.getCode());
                
                List<PointsTransaction> points = pointsTransactionServiceAdapter.getListForParams(parameters);
                AdapterStatus transactionStatus = pointsTransactionServiceAdapter.getAdapterStatus();
                
                if (transactionStatus == AdapterStatus.OK && CollectionUtils.isNotEmpty(points)) {
                    paginator= new Paginatore<PointsTransaction>(points,pageSize);

                    pageResponse.setPointsTransactionList((List<PointsTransaction>)paginator.getListByPageNumber(pageNumber));
                    // the number of available page
                    page.setPageNum(paginator.getActualPageNumber());
                    // maximum number of page
                    page.setPageCount(paginator.getLastPage());
                    // page size
                    page.setPageSize(paginator.getPageSize());

                    this.setAdapterStatus(AdapterStatus.OK);
                } else {
                    page.setPageCount(0);
                    page.setPageNum(0);
                    page.setPageSize(0);
                    
                    this.setAdapterStatus(AdapterStatus.NOT_FOUND);
                }
                
                pageResponse.setPage(page);
                
                response.add(pageResponse);
                return  response;                
            } catch (AdapterException e) {
                this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
               logger.error("PointsTransactionPageServiceAdapter.onGetListForParams error: {}", e);
               throw e;
           } catch (Exception e) {
               this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
               logger.error("PointsTransactionPageServiceAdapter.onGetListForParams error: {}", e);
               throw new AdapterException(e);
           } catch (Throwable t) {
               this.setAdapterStatus(AdapterStatus.INTERNAL_SERVER_ERROR);
               logger.error("PointsTransactionPageServiceAdapter.onGetListForParams error: {}", t);
               throw new AdapterException(t.toString());
           }        
    }

    /** 
     * @param resource Page
     * @return List of empty attributes in Page object 
     */
    
    private List<String> validateTransactionPageRequest(Page resource) {
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
