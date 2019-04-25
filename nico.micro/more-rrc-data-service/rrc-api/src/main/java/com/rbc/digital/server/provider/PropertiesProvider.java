

package com.home.digital.server.provider;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <ul>
 * The component reads property attributes [key,value] from SystemProperties in
 * class Path and application resource.
 * <li>1. creemore.properties - SystemProeprties accessed by JVM parameter
 * 'creemore.config.path'</li>
 * <li>2. application.properties - src/main/resources</li>
 * <li>Please note the spring configuration of placeholder is in Servlet level</li>
 * <ul>
 * 
 * @author ralu
 */

@Component
public class PropertiesProvider {
    /**
     * It is a flag to enable debugging mode 
     */
    @Value("${isDebugging}")
    private String debugging;
    
    public Boolean isDebugging() {
        if (!StringUtils.isEmpty(debugging)) {
            return Boolean.valueOf(debugging); 
        }

        return false;
    }
}
