package com.home.digital.queue.test.ejb;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.home.digital.queue.ejb.remote.QueuereplyRemote;
// This unit test is not working due to JBOSS jboss-client.jar class loading issue 
//I tried to deploy jboss-client.jar, the instance of EJB was received but cannot call the remote implementation
//I also included jboss-client.jar in ear but same failure

//for test please use reply-rest module . localhost:8080/rest/send
public class TestRemoteEjb {
	private Logger logger = LoggerFactory.getLogger(TestRemoteEjb.class);
	private static final String JNDI_PATH = "ejb:jboss/exported/reply-ejb/reply-ejb-0.0.1-SNAPSHOT/"; 

	static Context ctx = null;
	@Before
	public void init() {
		try {
        	final Hashtable<String, Comparable> jndiProperties = new Hashtable<String, Comparable>();
        	
        	jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        	jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        	jndiProperties.put(Context.PROVIDER_URL, "remote://localhost:4447");
        	jndiProperties.put(Context.SECURITY_PRINCIPAL, "remote");
        	jndiProperties.put(Context.SECURITY_CREDENTIALS, "remote123!");
        	jndiProperties.put("jboss.naming.client.ejb.context", true);

        	ctx = new InitialContext(jndiProperties);
        	Assert.assertNotNull(ctx);
        	logger.info("Ctx was created",ctx);

		}catch (Exception ex) {
			System.out.print(ex);
		}			
	}
	
	@Test
	public void sendMessageToQueeu() throws Exception  {
    	QueuereplyRemote remote =  (QueuereplyRemote)  ctx.lookup(JNDI_PATH + QueuereplyRemote.JNDI);
    	remote.sendTextMessagetoQueue();
	}

}
