


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import io.github.ppissias.xsrpcj.example.grpcperformance.ExampleGrpc;
import io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages;
import io.github.ppissias.xsrpcj.example.grpcperformance.ExampleGrpc.ExampleBlockingStub;
import io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest;
import io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse;
import io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse;


/**
 * @author Petros Pissias
 *
 */
public class TestGRpcClient {

	private final Logger logger = Logger.getLogger(TestGRpcClient.class);

	private int expectedReplies = 0;
	private String expectedString = null;

	public synchronized void setConfirmationInfo(int numReplies, String text) {
		this.expectedReplies = numReplies;
		this.expectedString = text;
	}
	
	private synchronized int getExpectedReplies() {
		return expectedReplies;
	}

	private synchronized String getExpectedString() {
		return expectedString;
	}		


	/**
	 * 
	 */
	public TestGRpcClient() {
	}

	/**
	 * @param args
	 * @throws RemoteCommunicationsException 
	 */
	public static void main(String[] args)  {
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
		
		new TestGRpcClient().startInvocationTest(reqData, repetitions , rounds, server);
	}

	private void startInvocationTest(String reqData, int repetitions, int rounds, String server)  {
		
		//Channel is the abstraction to connect to a service endpoint
		//Let's use plaintext communication because we don't have certs
		final ManagedChannel channel = ManagedChannelBuilder.forTarget(server+":22101").usePlaintext().build();
	
		// It is up to the client to determine whether to block the call
		//Here we create a blocking stub, but an async stub,
		//or an async stub with Future are always possible.
		ExampleGrpc.ExampleBlockingStub stub = ExampleGrpc.newBlockingStub(channel);
	      
		BlockingQueue<Boolean> completionQueue = new LinkedBlockingQueue<Boolean>();		
		
		logger.info("Starting request response interaction test");
		
		for (int j=0;j<rounds;j++) {
			long reqResStart = System.currentTimeMillis();
			for (int i=1;i<=repetitions;i++) {
				logger.debug("invoking server requestResponse with SendBackTimes="+i);
				TestMessages.MessageRequest request = TestMessages.MessageRequest.newBuilder().setRequestData(reqData).build();
				MessageResponse response = stub.requestResponse(request);
				logger.debug("Received response:"+response.getResponse().name());
			}
			long reqResStop = System.currentTimeMillis();
			logger.info("It took "+(reqResStop-reqResStart)+" ms to complete the request response interaction test "+repetitions+" times");
		}
		
		
		logger.info("Starting request response callback interaction test");
		for (int j=0;j<rounds;j++) {
			logger.debug("invoking server requestResponseCallback with NumberOfCallbacks="+repetitions);
			setConfirmationInfo(repetitions, reqData);
			TestMessages.MessageRequest request = TestMessages.MessageRequest.newBuilder().setRequestData(reqData).setNumberOfCallbacks(repetitions).build();
			long time1 = System.currentTimeMillis();

			Iterator<CallbackResponse> responses = stub.requestResponseCallback(request);
			int receivedReplies = 0;
			while (responses.hasNext()) {
				CallbackResponse cb = responses.next();
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
			}

			if (receivedReplies == getExpectedReplies()) {
				long time2 = System.currentTimeMillis();
				logger.info("It took :"+(time2-time1)+" ms to process "+repetitions+" callbacks");
				logger.debug("Succesfully processed "+getExpectedReplies()+" callbacks");
			} else {
				logger.error("Received different number of replies. Expected"+getExpectedReplies()+" received:"+receivedReplies);
			}
			
		}
		
		logger.info("Completed tests");
	}
	

}
