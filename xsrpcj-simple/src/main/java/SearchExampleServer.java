/**
 * 
 */


import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse.responseEnum;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import io.github.ppissias.xsrpcj.example.simple.comms.RemoteCommunicationsException;
import io.github.ppissias.xsrpcj.example.simple.server.PersonsNotifyServerCallback;
import io.github.ppissias.xsrpcj.example.simple.server.PersonsServer;
import io.github.ppissias.xsrpcj.example.simple.server.PersonsServerService;

/**
 * @author Petros Pissias
 *
 */
public class SearchExampleServer {

	private final Logger logger = Logger.getLogger(SearchExampleServer.class);
	
	private final Random random = new Random();
	
	public class ServerCallbackReplySender extends Thread {
		
		private final Random random = new Random();
		
		private final int hMany;
		private final String name;
		
		private final PersonsNotifyServerCallback callback;
		
		public ServerCallbackReplySender(String name, int hMany, PersonsNotifyServerCallback callback) {
			this.hMany = hMany;
			this.name = name;
			this.callback = callback;			
		}

		@Override
		public void run() {
			for (int i=1;i<=hMany;i++) {
				try {
					Thread.sleep(random.nextInt(5000));
				} catch (InterruptedException e1) {
				}
				
				io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse.Builder cbResponseBuilder = SearchPersonResponse.newBuilder();
				
				io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse.Person.Builder 
					personBuilder = SearchPersonResponse.Person.newBuilder();
				
				buildRandomPerson(name, personBuilder);
				
				cbResponseBuilder.setResponse(personBuilder.build());
				
				try {					
					callback.notifyCallback(cbResponseBuilder.build());
				} catch (RemoteCommunicationsException e) {
					logger.error("comms error",e);
				}
			}
		}
		
		//builds random person
		private void buildRandomPerson(String fname, io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse.Person.Builder personBuilder) {
			String[] lastNames = {"Smith","Jones","Parsons","Jackson","Anderson","Stamatiou","Lalakis","Tailor","Pirina","Glassy"};
			
			personBuilder.setFirstName(fname);
			personBuilder.setLastName(lastNames[random.nextInt(10)]);
			
			String telephone = "0049-"+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+
					random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+"-"+random.nextInt(10)+
					random.nextInt(10)+random.nextInt(10);
			
			personBuilder.setTelephone(telephone);
			
		}
		
		
	}


	/**
	 * @param args
	 * @throws RemoteCommunicationsException 
	 */
	public static void main(String[] args) throws RemoteCommunicationsException {
		
		//configure logging
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		}catch (Exception e) {
			e.printStackTrace();
		}		
		
		//start the server
		new SearchExampleServer().startServer();
	}

	//defines the service implementation and starts the server
	private void startServer() throws RemoteCommunicationsException {

		/**
		 * This is the service implementation.
		 * It replies a random Person on the search service
		 * and starts a thread that replies 20 times asynchronously every 5 seconds on the notify service
		 */
		PersonsServerService serviceImplementation = new PersonsServerService() {

			@Override
			public SearchPersonResponse search(SearchPersonRequest request) {
				
				io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse.Builder responseBuilder = SearchPersonResponse.newBuilder();
				
				io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse.Person.Builder 
					personBuilder = SearchPersonResponse.Person.newBuilder();
				
				buildRandomPerson(request.getName(), personBuilder);
				
				try {
					Thread.sleep(random.nextInt(2000));
				} catch (InterruptedException e1) {
				}
				
				responseBuilder.setResponse(personBuilder.build());			
				return responseBuilder.build();
			}

			@Override
			public PersonNotificationResponse notify(
					PersonNotificationRequest request,
					PersonsNotifyServerCallback callback) {

				new ServerCallbackReplySender(request.getName(), 20, callback).start();
				
				return PersonNotificationResponse.newBuilder().setResponse(responseEnum.ACCEPTED).build();
			}

		};
		
		//start on default port
		new PersonsServer(serviceImplementation).start();
	}
	

	//builds random person, based on a first name
	private void buildRandomPerson(String fname, io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse.Person.Builder personBuilder) {
		String[] lastNames = {"Smith","Jones","Parsons","Jackson","Anderson","Stamatiou","Lalakis","Tailor","Pirina","Glassy"};
		
		personBuilder.setFirstName(fname);
		personBuilder.setLastName(lastNames[random.nextInt(10)]);
		
		String telephone = "0049-"+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+
				random.nextInt(10)+random.nextInt(10)+random.nextInt(10)+"-"+random.nextInt(10)+
				random.nextInt(10)+random.nextInt(10);
		
		personBuilder.setTelephone(telephone);
		
	}
}
