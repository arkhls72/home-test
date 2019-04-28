
package com.home.digital.queue.ejb.remote;
import java.io.Serializable;

import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.home.digital.queue.util.EjbProperty;
import com.home.digital.queue.util.Property;

/**
 *  Implementation of Remote QueuereplyRemote. This remote is for test purpose only. 
 *  It sends mock text to internal jms/GainTransactionQueue
 *  It shall be replaced by IBM MQ resource adapter  
 * @author arash
 *
 */

@Stateless
public class QueuereplyBean implements QueuereplyRemote,Serializable{
	private static final long serialVersionUID = -8638502826387997464L;

	private static Logger logger = LoggerFactory.getLogger(QueuereplyBean.class);
	
	@Override
	public String sendTextMessagetoQueue() throws Exception {
			Connection connection = null;
	        ConnectionFactory cfactory;
	        Session session = null;
	        MessageProducer producer = null;
	        TextMessage textMessage = null;

	        try {
	        	
	            InitialContext ic = new InitialContext();
	            String jndiPath = EjbProperty.getProperty(Property.JBOSS_JNDI_PATH);

	            cfactory = (ConnectionFactory) ic.lookup(jndiPath+EjbProperty.getProperty(Property.HORNET_CONNECTION));

	            Queue theQueue = (Queue) ic.lookup(jndiPath + EjbProperty.getProperty(Property.INTERNAL_Gain_PUBLISH_QUEUE));
				connection = cfactory.createConnection(EjbProperty.getProperty(Property.HORNET_USER),EjbProperty.getProperty(Property.HORNET_PASSWORD));

	            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

	            producer = session.createProducer(theQueue);
	            textMessage = session.createTextMessage();
	            textMessage.setText("Attention Dan, This is a message sending by Micor-servoces to data power.");

	            producer.send(textMessage);
	        } catch (Exception e) {
	        	logger.error(e.getMessage());
	        	
	        } catch (Throwable t) {
	        	logger.error(t.getMessage());

	        } finally {
	            try {
	                producer.close();
	            } catch (Exception e) {
	            	logger.error(e.getMessage());
	            }

	            try {
	                session.close();
	            } catch (Exception e) {
	            	logger.error(e.getMessage());
	            }
	            
	            try {
	                connection.close();
	            } catch (Exception e) {
	            	logger.error(e.getMessage());
	            }
	        }

	        return textMessage.getText();
	}
}
