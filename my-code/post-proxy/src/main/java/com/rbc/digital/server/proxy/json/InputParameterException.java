
package com.home.digital.server.proxy.json;

import com.home.digital.common.BaseRuntimeException;

/**
 * Throws if the input request parameters are null.
 * 
 * @author arash
 * 
 */
public class InputParameterException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    public InputParameterException(Throwable cause, Object... parameters) {
        super(cause, parameters);
    }

    public InputParameterException(String message, Object... parameters) {
        super(message, parameters);
    }

    public InputParameterException(String message, Throwable cause, Object... parameters) {
        super(message, cause, parameters);
    }
}
