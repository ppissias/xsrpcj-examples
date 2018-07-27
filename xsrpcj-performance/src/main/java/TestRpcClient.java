/**
 * 
 */


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import io.github.ppissias.xsrpcj.example.performance.TestMessages;
import io.github.ppissias.xsrpcj.example.performance.TestMessages.CallbackResponse;
import io.github.ppissias.xsrpcj.example.performance.TestMessages.MessageResponse;
import io.github.ppissias.xsrpcj.example.performance.client.ExampleClientService;
import io.github.ppissias.xsrpcj.example.performance.client.ExampleClientServiceImpl;
import io.github.ppissias.xsrpcj.example.performance.client.ExampleRequestResponseCallbackClientCallback;
import io.github.ppissias.xsrpcj.example.performance.comms.RemoteCommunicationsException;

/**
 * @author Petros Pissias
 *
 */
public class TestRpcClient {

	private final Logger logger = Logger.getLogger(TestRpcClient.class);
	
	/**
	 * This is the callback handler, that gets all callbacks and then reports back
	 * when it received all expected callback replies
	 * @author Petros Pissias
	 *
	 */
	public class ClientCallbackHandler implements ExampleRequestResponseCallbackClientCallback {
		//expected number of replies and text in each reply
		private int expectedReplies = 0;
		private String expectedString = null;
		
		private int receivedReplies = 0;

		//reply queue, used to signal the reception of all expected callbacks
		private final  BlockingQueue<Boolean> completionQueue;

		/**
		 * Creates a new callback handler
		 * @param completionQueue
		 */
		public ClientCallbackHandler(BlockingQueue<Boolean> completionQueue) {
			this.completionQueue = completionQueue;
		}
		
		@Override
		public void requestResponseCallbackCallback(CallbackResponse cb) {
			receivedReplies++;
			
			//sanity check
			if (cb.getCallbackTextCount() != getExpectedReplies()) {
				logger.error("Received different number of replies within message. expected "+getExpectedReplies()+" received "+cb.getCallbackTextCount());
			}
		
			//confirm correct reception the data in the callback
			for (int i=1;i<=cb.getCallbackTextList().size();i++) {
				if (! (cb.getCallbackText((i-1)).equals(getExpectedString()+getExpectedReplies()))) {
					logger.error("Received different reply text. expected "+getExpectedString()+getExpectedReplies()+" received "+cb.getCallbackText((i-1)));
				}
			}	
			
			//if we received all expected callbacks, report back to the queue
			if (receivedReplies == getExpectedReplies()) {
				receivedReplies = 0;
				try {
					completionQueue.put(Boolean.TRUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				logger.debug("Succesfully processed "+getExpectedReplies()+" callbacks");
			}
		}

		//sets the expected information to the callback handler
		public synchronized void setConfirmationInfo(int numReplies, String text) {
			this.expectedReplies = numReplies;
			this.expectedString = text;
		}
		
		//returns the expected information
		private synchronized int getExpectedReplies() {
			return expectedReplies;
		}
		
		//returns the expected information
		private synchronized String getExpectedString() {
			return expectedString;
		}	

		
	}


	/**
	 * @param args
	 * @throws RemoteCommunicationsException 
	 */
	public static void main(String[] args) throws RemoteCommunicationsException {
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		if (args.length != 4) {
			System.out.println("Expected Arguments: <request data> <repetitions> <rounds> <server host>");
			System.out.println("<request data> : data to send as part of the request");
			System.out.println("<repetitions> : number of calls per rpc method. For a method with a callback it is the number of callbacks");
			System.out.println("<rounds> : number of times to repeat this test");			
			System.out.println("<server host> : server hostname");
			return;
		}
		
		String reqData = args[0];
		int repetitions = Integer.parseInt(args[1]);
		int rounds = Integer.parseInt(args[2]);
		String server = args[3];
		new TestRpcClient().startInvocationTest(reqData, repetitions , rounds, server);
	}

	/**
	 * Starts an interaction performance test
	 * @param reqData 
	 * @param repetitions the number of times each interaction pattern will be called
	 * @param rounds the number of times to repeat the test
	 * @param server 
	 * @throws RemoteCommunicationsException in case the client cannot communicate with the server
	 */
	private void startInvocationTest(String reqData, int repetitions, int rounds, String server) throws RemoteCommunicationsException {
		//the reply queue
		BlockingQueue<Boolean> completionQueue = new LinkedBlockingQueue<Boolean>();
		
		//the client callback handler
		ClientCallbackHandler cbHandler = new ClientCallbackHandler(completionQueue);
		
		//the service implementation
		ExampleClientService serverRef = new ExampleClientServiceImpl(server, cbHandler);			
				
		logger.info("Starting one way interaction test");
				
		for (int j=0;j<rounds;j++) {
			long timeOneWayStart = System.currentTimeMillis();
			for (int i=1;i<=repetitions;i++) {
				logger.debug("invoking server oneWayRequest with SendBackTimes="+i);
				TestMessages.MessageRequest request = TestMessages.MessageRequest.newBuilder().setRequestData(reqData).build();
				serverRef.oneWayRequest(request);			
			}		
			long timeOneWayStop = System.currentTimeMillis();
			logger.info("It took "+(timeOneWayStop-timeOneWayStart)+" ms to complete the one way interaction test "+repetitions+" times");
		}
		
		
		logger.info("Starting request response interaction test");
		
		for (int j=0;j<rounds;j++) {
			long reqResStart = System.currentTimeMillis();
			for (int i=1;i<=repetitions;i++) {
				logger.debug("invoking server requestResponse with SendBackTimes="+i);
				TestMessages.MessageRequest request = TestMessages.MessageRequest.newBuilder().setRequestData(reqData).build();
				MessageResponse response = serverRef.requestResponse(request);
				logger.debug("Received response:"+response.getResponse().name());
			}
			long reqResStop = System.currentTimeMillis();
			logger.info("It took "+(reqResStop-reqResStart)+" ms to complete the request response interaction test "+repetitions+" times");
		}
		
		
		logger.info("Starting request response callback interaction test");
		for (int j=0;j<rounds;j++) {
			logger.debug("invoking server requestResponseCallback with NumberOfCallbacks="+repetitions);
			cbHandler.setConfirmationInfo(repetitions, reqData);
			TestMessages.MessageRequest request = TestMessages.MessageRequest.newBuilder().setRequestData(reqData).setNumberOfCallbacks(repetitions).build();
			long time1 = System.currentTimeMillis();
			MessageResponse response = serverRef.requestResponseCallback(request);
			logger.debug("Received response:"+response.getResponse().name());
			
			//wait until all callbacks are processed
			try {
				completionQueue.take();
				long time2 = System.currentTimeMillis();
				logger.info("It took :"+(time2-time1)+" ms to process "+repetitions+" callbacks");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		logger.info("Completed tests");
		((ExampleClientServiceImpl)serverRef).finished();
	}
	

}
