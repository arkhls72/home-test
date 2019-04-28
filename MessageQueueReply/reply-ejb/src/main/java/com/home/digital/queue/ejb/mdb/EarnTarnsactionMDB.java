

package com.home.digital.queue.ejb.mdb;


import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.home.digital.queue.ejb.local.RestLocal;
import com.home.digital.queue.ejb.model.GainTransaction;

/**
 * Listens to internal queue queue = GainTransactionQueue. 
 * Consumes the message and calls local session bean within a new transaction among with the message
 * This listener shall be replaced by IBM MQ resource adapter listener
 * Obtain connection factory from RemoteConnectionFactory from internal JBOSS HornetQueue adapter
 * \jboss-eap-6.4\modules\system\layers\base\org\hornetq    
 * @author arash
 *
 */
@Resource(name="java:jboss/exported/jms/RemoteConnectionFactory", type=javax.jms.ConnectionFactory.class)
@MessageDriven(name = "GainTransactionMDB", activationConfig = {
		// JMS queue type
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        
        // jndi jms/jmsGainTransactionQueue
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "jmsGainTransactionQueue"),
        
        // please note Queue message is in Admin server port
        @ActivationConfigProperty(propertyName = "ConnectionURL", propertyValue = "mq://localhost:9990"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge") })
public class GainTarnsactionMDB  implements MessageListener {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private RestLocal restLocal;
	
	@Override	
	public void onMessage(Message inMessage) {
		logger.info("Test JMS listener.");

		try { 
			if (inMessage instanceof TextMessage ) {
				TextMessage message = (TextMessage) inMessage;
				if (message == null || StringUtils.isNotEmpty(message.getText())) {
				    GainTransaction data = new GainTransaction(message.getText());
				    restLocal.sendGainTransaction(data);

				}  else {
					throw new Exception("Message text was empty.");
				}

			} else {
				throw new Exception("Consumed invalid message."); 
			}

			logger.info("Test JMS listener.");
		}catch (Exception ex) {
			logger.error(ex.getMessage());
			
		} catch (Throwable t) {
			logger.error(t.toString());
		}
	}


}
