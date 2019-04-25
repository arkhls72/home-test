

package com.home.digital.adapter.provider;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <ul>
 * The component reads property attributes [key,value] from SystemProperties in
 * class Path and application resource.
 * <li>1. creemore.properties - SystemProeprties accessed by JVM parameter inside application server
 * 'creemore.config.path'</li>
 * <li>2. remote-adapter.properties - src/main/resources</li>
 * <li>Please note the spring configuration of placeholder is in Service level</li> 
 * <ul>
 * 
 * @author ralu
 */

@Component
public class RemoteProperties {
    /**
     * Identifies whether or not logging information is allowed. 
     */
    @Value("${isDebugging}")
    private String debugging;

    /**
     * Data power server base URI
     */
    @Value("${datapower.server.uri}")
    private String dataPowerBaseUri;

    /**
     * Data power URI for RRC Transaction history
     */
    @Value("${datapower.services.history.uri}")
    private String dataPowerTransactionHistoryUri;

    /**
     * Data power URI for RRC balance 
     */
    @Value("${datapower.services.balance.uri}")
    private String dataPowerBalanceUri;

    
    /**
     * Data power URI for RRC redemption 
     */
    @Value("${datapower.services.burn.uri}")
    private String dataPowerBurnUri;

    /**
     * @return  Data power Balance URL
     */
    public String getDataPowerBalanceURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(dataPowerBaseUri).append(dataPowerBalanceUri);
        
        return sb.toString();
    }

    /**
     * @return  Data power TransactionHistory URL
     */
    public String getDataPowerTranactionHistoryURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(dataPowerBaseUri).append(dataPowerTransactionHistoryUri);

        return sb.toString();
    }

    /**
     * @return  Data power burn redemption URL
     */

    public String getDataPowerBurnURL() {
        StringBuilder sb = new StringBuilder();
        sb.append(dataPowerBaseUri).append(dataPowerBurnUri);

        return sb.toString();
    }
    /**
     * 
     * @return debugging  
     */
    public Boolean isDebugging() {
        if (!StringUtils.isEmpty(debugging)) {
            return Boolean.valueOf(debugging); 
        }

        return false;
    }
}
