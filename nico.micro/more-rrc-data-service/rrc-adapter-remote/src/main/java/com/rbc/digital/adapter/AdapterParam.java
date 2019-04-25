
/**
 * Adapter parameter constants
 * Used in controller and adapter layer. holds input parameters and passed through the layers
 * @author ralu
 */
package com.home.digital.adapter;
/**
 * Defines URI and HttpRequest parameters which are passed to adapter 
 * @author ralu
 *
 */
public enum AdapterParam {
    CLIENT_ID("clientId"),
    CPC("cpc"),
    REWARDS_ID("rewardsId"),
    START_DATE("startDate"),
    END_DATE("endDate"),
    PAGE_SIZE("pageSize"),
    PAGE_NUMBER("pageNum");

    private String code;

    private AdapterParam(String code) {
        this.code=code;
    }

    public String getCode() {
        return code;
    }

    public static AdapterParam  parse(String code) {
        AdapterParam parameter=null;
        
        for (AdapterParam item:AdapterParam.values()) {
            if (item.getCode().equals(code)) {
                parameter = item;
                return parameter;
            }
        }
        return parameter;
    }
}
