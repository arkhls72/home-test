

package com.home.digital.rest;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Support utilities for REST operations
 * 
 * @author thomas
 * 
 */
public class RestUtils {

    private static Logger logger = LoggerFactory.getLogger(RestUtils.class);

    /**
     * Extract the identifier from the location header returned by a doPost
     * call. The identifier is the last component is the resource location.
     * 
     * @param location the resource location returned
     * @return the extracted identifier of null if not found
     */
    public static String extractLocationIdentifier(String location) {
        String identifier = null;
        if (location != null) {

            String args[] = location.split("/");
            if (logger.isDebugEnabled()) {
                for (int i = 0; i < args.length; i++) {
                    logger.debug("args[{}] = [{}]", i, args[i]);
                }
            }

            if (args.length > 0) {
                identifier = args[args.length - 1];
             }
        }
        return identifier;
    }

    /**
    * Extract the identifier from the location header returned by a doPost
     * call. The identifier is the last component is the resource location.
     * 
     * @param uri resource uri returned
     * @return the extracted identifier of null if not found
     */
    public static String extractLocationIdentifier(URI uri) {
        return extractLocationIdentifier(uri.toString());
    }
}