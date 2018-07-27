/**
 * 
 */


import java.util.Random;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import io.github.ppissias.xsrpcj.example.simple.client.PersonsClientService;
import io.github.ppissias.xsrpcj.example.simple.client.PersonsClientServiceImpl;
import io.github.ppissias.xsrpcj.example.simple.client.PersonsNotifyClientCallback;
import io.github.ppissias.xsrpcj.example.simple.comms.RemoteCommunicationsException;

/**
 * @author Petros Pissias
 *
 */
public class SearchExampleClient {

	private final Logger logger = Logger.getLogger(SearchExampleClient.class);

	private final Random random = new Random();
	private final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * async notification callback handler
	 * @author Petros Pissias
	 *
	 */
	public class ClientCallbackHandler implements PersonsNotifyClientCallback {		
		@Override
		public void notifyCallback(SearchPersonResponse cb) {
			logger.info("Received asynchronous notification matching person :"+cb.getResponse().getFirstName()+" "+cb.getResponse().getLastName()+" "+cb.getResponse().getTelephone());
		}	
	
	}


	/**
	 * @param args
	 * @throws RemoteCommunicationsException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws RemoteCommunicationsException, InterruptedException {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		}catch (Exception e) {
			e.printStackTrace();
		}

		if (args.length != 1) {
			System.out.println("Expected Arguments: <server host>");
			System.out.println("<server host> : server hostname");
			return;
		}

		
		int hManySearches = 20;
		int hmanyAsyncsearches = 5;
		
		new SearchExampleClient().startSearchTest(hManySearches, hmanyAsyncsearches, args[0]);
	}

	/**
	 * Starts an interaction performance test
	 * @param serverHost 
	 * @param repetitions the number of times each interaction pattern will be called
	 * @param rounds the number of times to repeat the test
	 * @throws RemoteCommunicationsException in case the client cannot communicate with the server
	 * @throws InterruptedException 
	 */
	private void startSearchTest(int hManySearches, int hmanyAsyncsearches, String serverHost) throws RemoteCommunicationsException, InterruptedException {
		
		//the client callback handler
		ClientCallbackHandler cbHandler = new ClientCallbackHandler();
		
		//the service implementation
		PersonsClientService serverRef = new PersonsClientServiceImpl(serverHost, cbHandler);			
				
		logger.info("Starting person search");
				
		for (int j=0;j<hManySearches;j++) {
			String firstName = getFirstName();
			logger.info("searching for person with a first name:"+firstName);
			
			SearchPersonResponse resp = serverRef.search(SearchPersonRequest.newBuilder().setName(firstName).build());
			logger.info("Received response:"+resp.getResponse().getFirstName()+" "+resp.getResponse().getLastName()+" "+resp.getResponse().getTelephone());			
		}
		
		
		logger.info("Starting person notifications search");
		
		for (int j=0;j<hmanyAsyncsearches;j++) {
			Thread.sleep(2000);
			String firstName = getFirstName();
			logger.info("requesting notifications (async) for new appearances for a person with first name:"+firstName);
			
			serverRef.notify(PersonNotificationRequest.newBuilder().setName(firstName).build());
		}
		
		
	}
	

	
	//returns a random word of 10 characters
	private String getFirstName(){
	    final int N = 10;
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < N; i++) {
	        sb.append(alphabet.charAt(random.nextInt(alphabet.length())));
	    }
	    return sb.toString();	
	}

}
