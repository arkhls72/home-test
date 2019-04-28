

package com.home.digital.queue.ejb.remote;

import javax.ejb.Remote;
/**
 * EJB remote interface for stateless session bean 
 * @author arash
 */
@Remote
public interface QueueRelayRemote {
	static final String JNDI=QueueRelayBean.class.getSimpleName() + "!" + QueueRelayRemote.class.getName();
	String sendTextMessagetoQueue() throws Exception ;
	
}
