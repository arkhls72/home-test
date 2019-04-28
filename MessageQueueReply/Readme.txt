
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
	4. Click on the Add button adn create jmsGainTransactionQueue 

		name:jmsGainTransactionQueue
		jndi-name  :  jms/jmsGainTransactionQueue

		make sure the name of queue shas no "/" and JNDI must has "/"
		
	5.keep the default and exit page

10.Retsrat the server. Now you are able to build the source code and deploy to JBSS 
11.Please remember that EAR must be deploy from the JBOSS console/deplyment tab

Build MessageQueuereply
The source code is maven based and has 1 parent project and 4 maven module embeded as follows 
MessageQueuereply
	1.reply-ejb
	2.reply-rest
	3.reply-ear
	4.reply-test

1.reply-ejb 
Contains 2 major package:
	
		com.home.digital.queue.ejb	
			Local:  Internal Stateless session bean to REST call DataPower
			mdb :   JMS listener to jmsGainTransactionQueue, pick up the message, convert it to JSON and send to Datapower via localBean
			model:  Reperesents DataPower request and response object
			remote: For test purpose only, you can call this remotely to send a fake message to queue as EJB often has issues in unit test 

		com.home.digital.queue.util	
			EJBProperty: a class that is instanciated at startup to provide ejb.properties value
			Propert : enum that maps the ejb.Properties name to enum code

2.reply-rest
It is Spring MVC based REST service module. This is for test purpose only to call remote EJB QueuereplyBean.sendTextMessagetoQueue() 
HttpMethod : GET
URI /send
Full URI : http://localhost:8080/rest/send

3.reply-test
	unit test module to test EJB remote. The configurationis correct but there is jboss-client.jar calss loading issue. it can not  be used until the jar client has been deployed 
	successfully. I tried different way but no luck. we can obtain remote JNDI but class loading issue casue not the bean service getting called. 
	Instead of this unit test you can use REST module for your test and that is working perfectly 
	
4.reply-ear 	
	Package the modules and make it ready for deployment. , 
	Context path of rest moudle is set to --> /rest in ear file
	To build the entire backage please execute parent maven modul. MessageQueuereply/pom.xml 

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

	java:global/reply-ejb/reply-ejb-0.0.1-SNAPSHOT/QueuereplyRemote!com.home.digital.queue.ejb.remote.QueuereplyRemote
	java:app/reply-ejb-0.0.1-SNAPSHOT/QueuereplyRemote!com.home.digital.queue.ejb.remote.QueuereplyRemote
	java:module/QueuereplyRemote!com.home.digital.queue.ejb.remote.QueuereplyRemote
	java:jboss/exported/reply-ejb/reply-ejb-0.0.1-SNAPSHOT/QueuereplyRemote!com.home.digital.queue.ejb.remote.QueuereplyRemote
	java:global/reply-ejb/reply-ejb-0.0.1-SNAPSHOT/QueuereplyRemote
	java:app/reply-ejb-0.0.1-SNAPSHOT/QueuereplyRemote
	java:module/QueuereplyRemote


	
	
	


	
		
 	