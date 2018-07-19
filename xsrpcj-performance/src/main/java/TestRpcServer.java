/**
 * 
 */


import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xsrpcj.example.performance.TestMessages;
import org.xsrpcj.example.performance.TestMessages.CallbackResponse;
import org.xsrpcj.example.performance.TestMessages.CallbackResponse.Builder;
import org.xsrpcj.example.performance.TestMessages.MessageRequest;
import org.xsrpcj.example.performance.TestMessages.MessageResponse;
import org.xsrpcj.example.performance.TestMessages.MessageResponse.responseEnum;
import org.xsrpcj.example.performance.comms.RemoteCommunicationsException;
import org.xsrpcj.example.performance.server.ExampleRequestResponseCallbackServerCallback;
import org.xsrpcj.example.performance.server.ExampleServer;
import org.xsrpcj.example.performance.server.ExampleServerService;

/**
 * @author Petros Pissias
 *
 */
public class TestRpcServer {

	private final Logger logger = Logger.getLogger(TestRpcServer.class);
	
	public class ServerCallbackReplySender extends Thread {
		
		private final String expectedText;
		private final int hMany;
		private final ExampleRequestResponseCallbackServerCallback callback;
		
		public ServerCallbackReplySender(String expectedText, int hMany, ExampleRequestResponseCallbackServerCallback callback) {
			this.expectedText = expectedText;
			this.hMany = hMany;
			this.callback = callback;
		}

		@Override
		public void run() {
			for (int i=1;i<=hMany;i++) {
				Builder cbResponseBuilder = CallbackResponse.newBuilder();
				for (int j=1;j<=hMany;j++) {
					cbResponseBuilder.addCallbackText(expectedText+hMany);
				}
				try {					
					callback.requestResponseCallbackCallback(cbResponseBuilder.build());
				} catch (RemoteCommunicationsException e) {
					logger.error("comms error",e);
				}
			}
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
		new TestRpcServer().startServer();
	}

	private void startServer() throws RemoteCommunicationsException {

		ExampleServerService serviceImplementation = new ExampleServerService() {
			@Override
			public void oneWayRequest(MessageRequest request) {
				logger.debug("received one way request. Data: "+request.getRequestData());		
			}
			
			@Override
			public MessageResponse requestResponse(MessageRequest request) {
				logger.debug("received request for "+request.getRequestData());
				return TestMessages.MessageResponse.newBuilder().setResponse(responseEnum.ACCEPTED).build();
			}
			
			@Override
			public MessageResponse requestResponseCallback(
					MessageRequest request,
					final ExampleRequestResponseCallbackServerCallback callback) {
				logger.info("received request (with a callback) for "+request.getRequestData()+" callbacks requested:"+request.getNumberOfCallbacks());
				
				ServerCallbackReplySender callbackHandler = new ServerCallbackReplySender(request.getRequestData(), request.getNumberOfCallbacks(), callback);
				callbackHandler.start();
				
				return TestMessages.MessageResponse.newBuilder().setResponse(responseEnum.ACCEPTED).build();				
			}
		};
		
		//start on default port
		new ExampleServer(serviceImplementation).start();
		//start on other port than the pre-defined
		//new ExampleServer(serviceImplementation, 22123).start();
	}
	

}
