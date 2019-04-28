
package com.home.digital.queue.rest.service;


import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.home.digital.queue.ejb.remote.QueueRelayRemote;
/**
 * 
 * A simple get REST service call to test remote EJB  QueueRelayRemote
 * This REST service is used for triggering remote EJB and send a mock message to local queue
 * Once IBM MQ is integarted with JBOSS the local queue will be repalced by IBM MQ adapter
 * full url: http://localhost:8080/rest/send
 ** rest is the name of context path  
 * @author arash
 *
 */

@RestController
public class RestService extends AbstractService{
	private static Logger logger = LoggerFactory.getLogger(RestService.class);
	
	// this jndi_prifix will be changed upon deployment. check the JBOSS console in view JNDI to 
	// get the correct one if not working. 
	private final static String _jndi_prefix = "java:jboss/exported/relay-ejb/relay-ejb/";
	
	@RequestMapping(value = "/send", method = RequestMethod.GET, produces = "application/json")
    public String sendMessageToQueue(HttpServletResponse response) throws Exception {
		String ejbResponse= null;
		try {
			
			QueueRelayRemote queueRelayRemote = (QueueRelayRemote) this.getInitialContext().lookup(_jndi_prefix + QueueRelayRemote.JNDI);
			ejbResponse = queueRelayRemote.sendTextMessagetoQueue();
			response.setStatus(200);

			return ejbResponse;
		}catch (Exception ex) {
			logger.error(ex.getMessage());
			throw ex;
		}
	}

}
