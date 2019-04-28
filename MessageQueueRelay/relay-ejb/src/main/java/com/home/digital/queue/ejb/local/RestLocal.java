
package com.home.digital.queue.ejb.local;

import javax.ejb.Local;

import com.home.digital.queue.ejb.model.EarnResponse;
import com.home.digital.queue.ejb.model.EarnTransaction;

/**
 * Local interface to be implemented by EJB bean
 * @author arash
 *
 */
@Local
public interface RestLocal {
	EarnResponse sendEarnTransaction(EarnTransaction data) throws Exception;
}
