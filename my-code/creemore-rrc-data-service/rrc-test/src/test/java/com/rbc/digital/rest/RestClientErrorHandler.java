home.digital.rest;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

public class RestClientErrorHandler extends DefaultResponseErrorHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    /* error indicator */
    private boolean hasError = true;
    
    /* response code from the last invocation */
    private int responseCode;
    
    /* response text from the last invocation */
    private String responseText;

    public boolean hasError(ClientHttpResponse response) {
        try {
            responseCode = response.getStatusCode().value();
            responseText = response.getStatusText();
            logger.debug("checking for response error : [{}] [{}]", responseCode, responseText);
            hasError = super.hasError(response);
        } catch (IOException e) {
            hasError = true;
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        logger.debug("response error [{}]", hasError);
        return hasError;
    }

    /**
     * Get the response status code from the last request.
     * 
     * @return an Http status code
     */
    public int getHttpResponseCode() {
        return responseCode;
    }

    /**
     * Get the response text from the last request.
     * 
     * @return response message text
     */
    public String getHttpResponseText() {
        return responseText;
    }

    /**
     * Get the response status (code and text) from the last request.
     * 
     * @return response status
     */
    public String getHttpResponseStatus() {
        return responseCode + " " + responseText;
    }

    /**
     * Get success or failure of the last request.
     * 
     * @return success or failure
     */
    public boolean success() {
        return !hasError;
    }
    
}