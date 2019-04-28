
Before you build this application please ensufhomsre that your JBOSS is configured properly for EAR deployment and MDB integration

JBOSS Configuartion

1. Open C:\tools\jboss-eap-6.4\standalone\configuration\standaone.xml and standalone-full.xml file
2. find <hornetq-server>  tag insdie above 2 xml file and add  <security-enabled>false</security-enabled> inside <hornetq-server>
3. Please do search in your xml file inside your jboss instalation folder and ensure that <security-enabled> tag is set to false
4. Please note that every deployment of EAR will cache standalone.xml file under history folder. Please ensure that they are removed each time you do fresh deployment

5. start JBOSS in full feature  from [your Jboss folder]/bin  by statement below
		call standalone.bat -c standalone-full.xml
		
    This will run JBOSS in Enterprise mode when  we include -C standalone-full.xml 

6. Ensure that JBOSS instance is created and server is running 
7. from [your Jboss folder]/bin  Execute add-user.bat from windows command and follow the questions which are printed on the console.

	user:remote
	password:remote123!
	
8. Close the console
 
9. Login to JBOSS console 

In jboss console 

	1. Click on configuration label on top of the page
	2. From the left panel click on messaging menu then click on destination 
	3. On the Winodws JMS messaging provider,first column on the right please click on "view" link 
	4. Click on the Add button adn create jmsEarnTransactionQueue 

		name:jmsEarnTransactionQueue
		jndi-name  :  jms/jmsEarnTransactionQueue

		make sure the name of queue shas no "/" and JNDI must has "/"
		
	5.keep the default and exit page

10.Retsrat the server. Now you are able to build the source code and deploy to JBSS 
11.Please remember that EAR must be deploy from the JBOSS console/deplyment tab

Build MessageQueueRelay
The source code is maven based and has 1 parent project and 4 maven module embeded as follows 
MessageQueueRelay
	1.relay-ejb
	2.relay-rest
	3.relay-ear
	4.relay-test

1.relay-ejb 
Contains 2 major package:
	
		com.home.digital.queue.ejb	
			Local:  Internal Stateless session bean to REST call DataPower
			mdb :   JMS listener to jmsEarnTransactionQueue, pick up the message, convert it to JSON and send to Datapower via localBean
			model:  Reperesents DataPower request and response object
			remote: For test purpose only, you can call this remotely to send a fake message to queue as EJB often has issues in unit test 

		com.home.digital.queue.util	
			EJBProperty: a class that is instanciated at startup to provide ejb.properties value
			Propert : enum that maps the ejb.Properties name to enum code

2.relay-rest
It is Spring MVC based REST service module. This is for test purpose only to call remote EJB QueueRelayBean.sendTextMessagetoQueue() 
HttpMethod : GET
URI /send
Full URI : http://localhost:8080/rest/send

3.relay-test
	unit test module to test EJB remote. The configurationis correct but there is jboss-client.jar calss loading issue. it can not  be used until the jar client has been deployed 
	successfully. I tried different way but no luck. we can obtain remote JNDI but class loading issue casue not the bean service getting called. 
	Instead of this unit test you can use REST module for your test and that is working perfectly 
	
4.relay-ear 	
	Package the modules and make it ready for deployment. , 
	Context path of rest moudle is set to --> /rest in ear file
	To build the entire backage please execute parent maven modul. MessageQueueRelay/pom.xml 

Deployment: 
1. Please ensure your Jboss is configured in section first of this document
2. Execure JBOSS in enterprise mode as described in JBOSS configuration section 2
3. Login to JBOSS console
4. Click on deployment tab  
5. Press "add" button
6. Navigate to your soucre code where you have built already your ear file. 
7. Select the file and press OK 
8. once successfully deployed without error press En/Disable button on the right hand top corner to start the App
9. check out your console , you should be able to see the following in JBOSS console or server.log file 

	java:global/relay-ejb/relay-ejb-0.0.1-SNAPSHOT/QueueRelayRemote!com.home.digital.queue.ejb.remote.QueueRelayRemote
	java:app/relay-ejb-0.0.1-SNAPSHOT/QueueRelayRemote!com.home.digital.queue.ejb.remote.QueueRelayRemote
	java:module/QueueRelayRemote!com.home.digital.queue.ejb.remote.QueueRelayRemote
	java:jboss/exported/relay-ejb/relay-ejb-0.0.1-SNAPSHOT/QueueRelayRemote!com.home.digital.queue.ejb.remote.QueueRelayRemote
	java:global/relay-ejb/relay-ejb-0.0.1-SNAPSHOT/QueueRelayRemote
	java:app/relay-ejb-0.0.1-SNAPSHOT/QueueRelayRemote
	java:module/QueueRelayRemote


	
	
	


	
		
 	