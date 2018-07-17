


import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.xsrpcj.example.grpcperformance.ExampleGrpc;
import org.xsrpcj.example.grpcperformance.TestMessages;
import org.xsrpcj.example.grpcperformance.ExampleGrpc.ExampleImplBase;
import org.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse;
import org.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse.Builder;
import org.xsrpcj.example.grpcperformance.TestMessages.MessageRequest;
import org.xsrpcj.example.grpcperformance.TestMessages.MessageResponse;
import org.xsrpcj.example.grpcperformance.TestMessages.MessageResponse.responseEnum;


/**
 * @author Petros Pissias
 *
 */
public class TestGRpcServer {

	private final Logger logger = Logger.getLogger(TestGRpcServer.class);
	
	public class ExampleGrpcServiceImpl extends ExampleGrpc.ExampleImplBase {
	
		@Override
		public void requestResponse(MessageRequest request,
				StreamObserver<MessageResponse> responseObserver) {
			
			logger.info("received request for "+request.getRequestData());
			MessageResponse response = TestMessages.MessageResponse.newBuilder().setResponse(responseEnum.ACCEPTED).build();
			
			responseObserver.onNext(response);
			responseObserver.onCompleted();
		}
		

		@Override
		public void requestResponseCallback(MessageRequest request, io.grpc.stub.StreamObserver<TestMessages.CallbackResponse> responseObserver) {
			logger.info("received request (with a callback) for "+request.getRequestData()+" callbacks requested:"+request.getNumberOfCallbacks());
			
			ServerCallbackReplySender callbackHandler = new ServerCallbackReplySender(request.getRequestData(), request.getNumberOfCallbacks(), responseObserver);
			callbackHandler.start();			
		}

	}
	
	public class ServerCallbackReplySender extends Thread {
		
		private final String expectedText;
		private final int hMany;
		private final StreamObserver<CallbackResponse> responseObserver;
		
		public ServerCallbackReplySender(String expectedText, int hMany, StreamObserver<CallbackResponse> responseObserver) {
			this.expectedText = expectedText;
			this.hMany = hMany;
			this.responseObserver = responseObserver;
		}

		@Override
		public void run() {
			for (int i=1;i<=hMany;i++) {
				Builder cbResponseBuilder = CallbackResponse.newBuilder();
				for (int j=1;j<=hMany;j++) {
					cbResponseBuilder.addCallbackText(expectedText+hMany);
				}
				responseObserver.onNext(cbResponseBuilder.build());				
			}
			responseObserver.onCompleted();
		}
	}


	/**
	 * @param args
	 * @throws IOException 
	 * @throws RemoteCommunicationsException 
	 */
	public static void main(String[] args) throws Exception  {
		
		try {
			PropertyConfigurator.configure("resources/log4j.properties");
		}catch (Exception e) {
			e.printStackTrace();
		}		
		new TestGRpcServer().startServer();
	}

	private void startServer() throws  Exception  {

	      // Create a new server to listen on port 8080
	      Server server = ServerBuilder.forPort(22101)
	        .addService(new ExampleGrpcServiceImpl())
	        .build();

	      // Start the server
	      server.start();

	      // Server threads are running in the background.
	      System.out.println("Server started");
	      // Don't exit the main thread. Wait until server is terminated.
	      server.awaitTermination();

	}
	

}
