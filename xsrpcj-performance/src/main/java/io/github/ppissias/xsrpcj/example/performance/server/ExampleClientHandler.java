package io.github.ppissias.xsrpcj.example.performance.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

//infrastructure imports
import io.github.ppissias.xsrpcj.example.performance.comms.DataHandler;
import io.github.ppissias.xsrpcj.example.performance.comms.ErrorHandler;
import io.github.ppissias.xsrpcj.example.performance.comms.RemoteCommunicationsErrorType;
import io.github.ppissias.xsrpcj.example.performance.comms.RemoteCommunicationsException;
import io.github.ppissias.xsrpcj.example.performance.comms.SocketDataTransceiver;

//message type imports
import io.github.ppissias.xsrpcj.example.performance.TestMessages.MessageRequest;
import io.github.ppissias.xsrpcj.example.performance.TestMessages.MessageResponse;
import io.github.ppissias.xsrpcj.example.performance.TestMessages.CallbackResponse;

//generic message type import
import io.github.ppissias.xsrpcj.example.performance.types.Example.MessageContainer;
import io.github.ppissias.xsrpcj.example.performance.types.Example.MessageContainer.MessageType;

/**
 * This class handles a connection with a client. 
 * 
 *  When a connection is established with a client an instance of this class is created in order
 *  to handle messages from and to that client. 
 *  
 * 
 * @author Petros Pissias
 *
 */
public class ExampleClientHandler implements DataHandler, ErrorHandler, ExampleRequestResponseCallbackServerCallback {

	
	//the service handler
	private final ExampleServerService serviceHandler;
	
	private SocketDataTransceiver serverDataTransceiver;
	
	/**
	 * Constructs a new client handler
	 * @param socket
	 * @throws IOException
	 */
	public ExampleClientHandler(Socket socket, ExampleServerService serviceHandler) throws IOException {
		this.serviceHandler = serviceHandler;
		
		serverDataTransceiver = new SocketDataTransceiver(socket, this, this);		
	}
	
	public void initialize() {
		serverDataTransceiver.initialize();		
	}

	@Override
	//handles incoming data from a client
	public void handleDataIndication(byte[] payload) {
		//new data has arrived 
		MessageContainer envelope = null;
		try {
			envelope = MessageContainer.parseFrom(payload);
		} catch (InvalidProtocolBufferException e) {
			System.out.println("Error: Cannot decode data. Protocol error:"+e.getMessage());
			System.out.println("Error: Data that cannot be decoded:"+Arrays.toString(payload));							
		}
		
		
		if (envelope != null) {
			switch (envelope.getMessageType()) {
				case oneWayRequestRequest : {
					try {
						final MessageRequest request = MessageRequest.parseFrom(envelope.getMessageData().toByteArray());
						
 						 						serviceHandler.oneWayRequest(request);
						
						
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Data that cannot be decoded:"+Arrays.toString(envelope.getMessageData().toByteArray()));												
					} 
					
					break;
				}
				case requestResponseRequest : {
					try {
						final MessageRequest request = MessageRequest.parseFrom(envelope.getMessageData().toByteArray());
						
 							 						//handle message
						MessageResponse response = serviceHandler.requestResponse(request);						
												
						
						//convert to generic message
						MessageContainer outgoingMessage = 
								MessageContainer.newBuilder().setMessageType(MessageType.requestResponseResponse).setMessageData(ByteString.copyFrom(response.toByteArray())).build();
						
						
						//send
						try {
							serverDataTransceiver.send(outgoingMessage.toByteArray());	
						} catch (IOException e) {
							//do nothing, just log, when the reader thread of the data transceiver will try to read data from the socket it will terminate
							System.out.println("Error: Communications Error while trying to send reply to client."+e.getMessage());							
						}
						
						
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Data that cannot be decoded:"+Arrays.toString(envelope.getMessageData().toByteArray()));												
					} 
					
					break;
				}
				case requestResponseCallbackRequest : {
					try {
						final MessageRequest request = MessageRequest.parseFrom(envelope.getMessageData().toByteArray());
						
 							 						//handle message
						MessageResponse response = serviceHandler.requestResponseCallback(request, this);
						
												
						
						//convert to generic message
						MessageContainer outgoingMessage = 
								MessageContainer.newBuilder().setMessageType(MessageType.requestResponseCallbackResponse).setMessageData(ByteString.copyFrom(response.toByteArray())).build();
						
						
						//send
						try {
							serverDataTransceiver.send(outgoingMessage.toByteArray());	
						} catch (IOException e) {
							//do nothing, just log, when the reader thread of the data transceiver will try to read data from the socket it will terminate
							System.out.println("Error: Communications Error while trying to send reply to client."+e.getMessage());							
						}
						
						
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Data that cannot be decoded:"+Arrays.toString(envelope.getMessageData().toByteArray()));												
					} 
					
					break;
				}
				
				default : { 
					System.out.println("Error: Received message type:"+envelope.getMessageType().name()+" that cannot be processed. Protocol error");			
					break;
				}
			}
		}
		

		
	}

	@Override
	public void handleCommunicationsError() {
		//received communications error trying to read data from the client.
		//the reader thread will terminate so there is nothing more to do.
		//TODO if the application wants to be notified for these events, then we should get an error handler on the constructor and report the error there
	}
	
	@Override
	//sends a callback message to a client
	public void requestResponseCallbackCallback(CallbackResponse callbackMessage) throws RemoteCommunicationsException {
		
		//convert to generic message
		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.requestResponseCallbackCallback).setMessageData(ByteString.copyFrom(callbackMessage.toByteArray())).build();
		
		//send
		try {
			serverDataTransceiver.send(outgoingMessage.toByteArray());							
		} catch (IOException e) {
			//do nothing, just log, when the reader thread will try to read data from the socket it will terminate
			
			//rethrow for caller
			throw(new RemoteCommunicationsException(RemoteCommunicationsErrorType.DISCONNECTED, "Communications Error while trying to send reply to client. Info:"+e.getMessage()));
		}
	}



}
