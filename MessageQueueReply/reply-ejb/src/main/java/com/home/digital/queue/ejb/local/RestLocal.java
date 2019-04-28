
package com.home.digital.queue.ejb.local;

import javax.ejb.Local;

import com.home.digital.queue.ejb.model.GainResponse;
import com.home.digital.queue.ejb.model.GainTransaction;

/**
 * Local interface to be implemented by EJB bean
 * @author arash
 *
 */
@Local
public interface RestLocal {
	GainResponse sendGainTransaction(GainTransaction data) throws Exception;
}
