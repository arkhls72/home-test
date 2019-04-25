
package com.home.digital.adapter.provider;

import java.util.List;

import com.home.digital.model.v1.client.rewards.PointsBalance;
import com.home.digital.model.v1.client.rewards.PointsConfirmation;
import com.home.digital.model.v1.client.rewards.PointsTransaction;
import com.home.digital.model.v1.mlp.client.BalanceResponse;
import com.home.digital.model.v1.mlp.client.BurnResponse;
import com.home.digital.model.v1.mlp.client.Event;
import com.home.digital.model.v1.mlp.client.TransactionResponse;
/**
 * <ul>
 *  <ui>Provides utility to converts DP response to Micro-service response</ui>
 * </ul>
 * // It will move to creemore-common once Data power implementation goes over
 * @author arash
 *
 */
public class BuilderProvider {

    
    /**
     * Convert MLP balance response to Micro service BalancePoint 
     * @param source:MLP response 
     * @return  Micro service BalancePoint request
     */
    public static PointsBalance build(BalanceResponse source) {
        PointsBalance target = new PointsBalance();
        target.setPointsBalance(source.getBalance());
        target.setClientId(source.getClientId());

        return target;
    }
    
    
    /**
     * Converts MLP Event to micro service PointTransaction 
     * @param source : MLP TransactionResponse object 
     * @param target :Micro service  PointsTransaction list
     * 
     */
    public static void build(List<TransactionResponse> source,List<PointsTransaction> target)  {
        PointsTransaction transaction = null;
        if (source != null) {
            for (TransactionResponse response : source) {
                transaction = new PointsTransaction();
                build(response.getEvent(), transaction);
                transaction.setPointsChange(response.getPoints());
                target.add(transaction);
            }
        }
    }
    /**
     * Converts MPL Event resposne object
     * @param source: A MLP Event response object
     * @param target: Micro service PointsTransaction object
     */
    private static void build(Event source, PointsTransaction target)  {
        target.setClientId(source.getClientId());
        target.setCpc(source.getCpc());
        target.setCurrencyCode(source.getCurrencyCode());
        target.setMerchantId(source.getMerchantId());
        target.setMerchantCategCode(source.getMerchantCategoryCode());
        target.setMerchantCity(source.getMerchantCity());
        target.setMerchantCountryCode(source.getMerchantCountryCode());
        target.setMerchantName(source.getMerchantName());
        target.setMerchantState(source.getStateProvince());
        target.setParentMerchList(source.getParentTransactionMerchandiseList());
        target.setParentTransDate(source.getParentTransactionDate());
        target.setParentTransNumber(source.getParentTransactionAccountNumber());
        target.setTerminalId(source.getTerminalId());
        target.setTransAmount(source.getTotalAmount());
    }

    /** 
     * @param source : MLP  BurnResponse response object 
     * @param target:  Micro service PointConfimration
     */
    public static void build(BurnResponse source, PointsConfirmation target) {
    	target.setAcctBalance(source.getRewardsBalance());
    	target.setPointsRedeem(source.getPointsRedeemed());
    	target.setRefNumber(source.getReferenceNumber());
    	target.setConvRate(source.getConverstionRate());
    }	
}
