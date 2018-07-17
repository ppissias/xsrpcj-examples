package org.xsrpcj.example.performance.client;

import java.io.IOException;
import java.util.Arrays;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.xsrpcj.example.performance.comms.ClientReplyHandler;
import org.xsrpcj.example.performance.comms.DataHandler;
import org.xsrpcj.example.performance.comms.RemoteCommunicationsErrorType;
import org.xsrpcj.example.performance.comms.RemoteCommunicationsException;
import org.xsrpcj.example.performance.comms.ServiceProxy;
import org.xsrpcj.example.performance.comms.SocketDataTransceiver;

//message type imports
import org.xsrpcj.example.performance.TestMessages.MessageRequest;
import org.xsrpcj.example.performance.TestMessages.MessageResponse;
import org.xsrpcj.example.performance.TestMessages.CallbackResponse;

//generic message type import
import org.xsrpcj.example.performance.types.Example.MessageContainer;
import org.xsrpcj.example.performance.types.Example.MessageContainer.MessageType;


public class ExampleClientServiceImpl extends ServiceProxy implements DataHandler, ExampleClientService{
	
	//the reply handlers for each interaction pattern
 	private final ClientReplyHandler<MessageResponse> clientrequestResponseReplyHandler; 
 	private final ClientReplyHandler<MessageResponse> clientrequestResponseCallbackReplyHandler; 
	
	//keep references to all service callbacks
 	private final ExampleRequestResponseCallbackClientCallback clientrequestResponseCallbackCallback;
	
	//timeout waiting for a reply from the server
	private static final int timeoutSeconds = 60;
	
	public ExampleClientServiceImpl(String host, int port, ExampleRequestResponseCallbackClientCallback clientrequestResponseCallbackCallback) {
		super(host, port);
		
		//create all request / reply Handlers
 		clientrequestResponseReplyHandler = new ClientReplyHandler<MessageResponse>(timeoutSeconds); 
 		clientrequestResponseCallbackReplyHandler = new ClientReplyHandler<MessageResponse>(timeoutSeconds); 
		
		//keep references to all service callbacks
 		this.clientrequestResponseCallbackCallback = clientrequestResponseCallbackCallback;
	}

	//constructor with default port
	public ExampleClientServiceImpl(String host, ExampleRequestResponseCallbackClientCallback clientrequestResponseCallbackCallback) {
		super(host, 22100);
		
		//create all request / reply Handlers
 		clientrequestResponseReplyHandler = new ClientReplyHandler<MessageResponse>(timeoutSeconds); 
 		clientrequestResponseCallbackReplyHandler = new ClientReplyHandler<MessageResponse>(timeoutSeconds); 
		
		//keep references to all service callbacks
 		this.clientrequestResponseCallbackCallback = clientrequestResponseCallbackCallback;
	}	
	
	//high level access methods. All Req-Reply operations are atomic 
	//and should not overlap, thus all methods are synchronized
	@Override
	public synchronized void oneWayRequest(MessageRequest request) throws RemoteCommunicationsException {		
		//create new connection of necessary
		checkDataTransceiver();

		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.oneWayRequestRequest).setMessageData(ByteString.copyFrom(request.toByteArray())).build();
				
		try {
			//send
			clientDataTransceiver.send(outgoingMessage.toByteArray());
		}catch (IOException e) {
			handleSendException(e);
		}			
		
	}
	
	@Override
	public synchronized MessageResponse requestResponse(MessageRequest request) throws RemoteCommunicationsException {		
	
		//create new connection of necessary
		checkDataTransceiver();

		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.requestResponseRequest).setMessageData(ByteString.copyFrom(request.toByteArray())).build();
				
		try {
			//send
			clientDataTransceiver.send(outgoingMessage.toByteArray());
		}catch (IOException e) {
			handleSendException(e);
		}			
		
		//get reply
		MessageResponse reply = clientrequestResponseReplyHandler.getReply();
			
		return reply;
	}
	
	@Override
	public synchronized MessageResponse requestResponseCallback(MessageRequest request) throws RemoteCommunicationsException {		
	
		//create new connection of necessary
		checkDataTransceiver();

		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.requestResponseCallbackRequest).setMessageData(ByteString.copyFrom(request.toByteArray())).build();
				
		try {
			//send
			clientDataTransceiver.send(outgoingMessage.toByteArray());
		}catch (IOException e) {
			handleSendException(e);
		}			
		
		//get reply
		MessageResponse reply = clientrequestResponseCallbackReplyHandler.getReply();
			
		return reply;
	}
	
	

	@Override
	public void handleDataIndication(byte[] payload) {
		
		MessageContainer incomingMessage = null;
		try {
			incomingMessage = MessageContainer.parseFrom(payload);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("Error: Error: Cannot decode data. Protocol error:"+e.getMessage());
			System.out.println("Error: Error: Data that cannot be decoded:"+Arrays.toString(payload));
			return;
		}
		
		if (incomingMessage != null) {
			switch (incomingMessage.getMessageType()) {
				//handle normal replies
				case requestResponseResponse : {
					try {
						MessageResponse response = MessageResponse.parseFrom(incomingMessage.getMessageData().toByteArray());
						clientrequestResponseReplyHandler.insertReply(response);
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Error: Data that cannot be decoded:"+Arrays.toString(incomingMessage.getMessageData().toByteArray()));
						
					} catch (InterruptedException e) {
						System.out.println("Error: Error: Interrupted while trying to insert reply to handler");							
					}									
					break;
				}
				case requestResponseCallbackResponse : {
					try {
						MessageResponse response = MessageResponse.parseFrom(incomingMessage.getMessageData().toByteArray());
						clientrequestResponseCallbackReplyHandler.insertReply(response);
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Error: Data that cannot be decoded:"+Arrays.toString(incomingMessage.getMessageData().toByteArray()));
						
					} catch (InterruptedException e) {
						System.out.println("Error: Error: Interrupted while trying to insert reply to handler");							
					}									
					break;
				}
			
				//handle callbacks
				case requestResponseCallbackCallback : {
					try {
						CallbackResponse callbackMessage = CallbackResponse.parseFrom(incomingMessage.getMessageData().toByteArray());
						clientrequestResponseCallbackCallback.requestResponseCallbackCallback(callbackMessage);						
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Error: Data that cannot be decoded:"+Arrays.toString(incomingMessage.getMessageData().toByteArray()));						
					} 				
					break;
				}
				
				default : { 
					System.out.println("Error: Error: Received message type:"+incomingMessage.getMessageType().name()+" that cannot be processed. Protocol error");					
					break;
				}
			}
		}
	}

	@Override
	protected SocketDataTransceiver getDataTransceiverInstance() throws IOException {
		return new SocketDataTransceiver(host, port, this, this);
	}
	
	/**
	 * Handles an IO Exception during a send operation. 
	 * It will report the error and handling and will throw a 
	 * RemoteCommunicationsException for the caller
	 * 
	 * @param e the IOException that caused the send operation to fail
	 * @throws RemoteCommunicationsException indicating the communications problem
	 */
	private void handleSendException(IOException e) throws RemoteCommunicationsException {
		
		//indicate communications error
		handleCommunicationsError();
		
		//throw exception for caller
		throw (new RemoteCommunicationsException(RemoteCommunicationsErrorType.DISCONNECTED, e.getMessage()));		
	}
}
