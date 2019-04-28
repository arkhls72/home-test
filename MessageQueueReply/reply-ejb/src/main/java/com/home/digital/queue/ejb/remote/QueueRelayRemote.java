

package com.home.digital.queue.ejb.remote;

import javax.ejb.Remote;
/**
 * EJB remote interface for stateless session bean 
 * @author arash
 */
@Remote
public interface QueuereplyRemote {
	static final String JNDI=QueuereplyBean.class.getSimpleName() + "!" + QueuereplyRemote.class.getName();
	String sendTextMessagetoQueue() throws Exception ;
	
}
