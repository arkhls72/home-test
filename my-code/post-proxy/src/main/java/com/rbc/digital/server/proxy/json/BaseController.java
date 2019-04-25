

package com.home.digital.server.proxy.json;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


//import com.home.digital.common.adapter.AdapterException;
import com.home.digital.model.v1.service.response.ErrorResponse;
import com.home.digital.model.v1.service.response.Status;


/**
 * Represents an Exception handler component which are raised in application
 * context. It is a class that is extended by Controllers The following
 * exception are supported: java.lang.Exception java.lang.Throwable
 * com.home.digital.server.api.v1.InputParameterException;
 * com.home.digital.common.adapter.AdapterException;
 * 
 * @return ErrorResponse in JSON format , HttpResponse error code
 * @author arash
 */

@ControllerAdvice
public class BaseController {

    /**
     * Handles AdapterException
     * 
     * @param e
     * @param request
     * @param response
     * @return ErorResponse, set HttpResponse code : 500
     */
//    @ExceptionHandler(AdapterException.class)
//    @ResponseBody
//    public  ErrorResponse handleAdapterException(AdapterException e, HttpServletRequest request, HttpServletResponse response) {
//        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//        ErrorResponse errorResponse = new ErrorResponse();
//        Status status = new Status();
//        
//        status.setHttpStatus(response.getStatus());
//        status.setErrorText(e.getMessage());
//        status.setErrorCode(String.valueOf(response.getStatus()));
//        errorResponse.setStatus(status);
//        return errorResponse;
//    }

    /**
     * Handles java.lang.Exception
     * 
     * @param e
     * @param request
     * @param response
     * @return ErorResponse, set HttpResponse error : 500
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorResponse handleException(Exception e, HttpServletResponse response) {

        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ErrorResponse errorResponse = new ErrorResponse();
        Status status = new Status();
        
        status.setHttpStatus(response.getStatus());
        status.setErrorText(e.getMessage());
        status.setErrorCode(String.valueOf(response.getStatus()));
        errorResponse.setStatus(status);
        return errorResponse;
    }

    /**
     * Handles NumberFormatException
     * 
     * @param e
     * @param request
     * @param response
     * @return ErorResponsea , set HttpResponse code : 400
     */
    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public  ErrorResponse handleNumberFormatException(NumberFormatException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ErrorResponse errorResponse = new ErrorResponse();
        Status status = new Status();
        
        status.setHttpStatus(response.getStatus());
        status.setErrorText(e.getMessage());
        status.setErrorCode(String.valueOf(response.getStatus()));
        errorResponse.setStatus(status);
        return errorResponse;
        
    }

    /**
     * Handles InputParameterException
     * 
     * @param e
     * @param request
     * @param response
     * @return ErorResponsea , set HttpResponse code : 400
     */
    @ExceptionHandler(InputParameterException.class)
    @ResponseBody
    public  ErrorResponse handleInputParameterException(InputParameterException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        ErrorResponse errorResponse = new ErrorResponse();
        Status status = new Status();
        
        status.setHttpStatus(response.getStatus());
        status.setErrorText(e.getMessage());
        status.setErrorCode(String.valueOf(response.getStatus()));
        errorResponse.setStatus(status);
        return errorResponse;
    }
    /**
     * Handles java.lang.Throwable
     * 
     * @param e
     * @param request
     * @param response
     * @return ErorResponsea , HttpResponse code : 500
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody 
    public ErrorResponse handleThrowableException(Throwable t, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        ErrorResponse errorResponse = new ErrorResponse();
        Status status = new Status();
        
        status.setHttpStatus(response.getStatus());
        status.setErrorText(t.getMessage());
        status.setErrorCode(String.valueOf(response.getStatus()));
        errorResponse.setStatus(status);
        return errorResponse;
     }
}
