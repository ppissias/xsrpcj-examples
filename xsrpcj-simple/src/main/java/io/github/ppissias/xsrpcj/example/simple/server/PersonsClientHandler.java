package io.github.ppissias.xsrpcj.example.simple.server;

import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

//infrastructure imports
import io.github.ppissias.xsrpcj.example.simple.comms.DataHandler;
import io.github.ppissias.xsrpcj.example.simple.comms.ErrorHandler;
import io.github.ppissias.xsrpcj.example.simple.comms.RemoteCommunicationsErrorType;
import io.github.ppissias.xsrpcj.example.simple.comms.RemoteCommunicationsException;
import io.github.ppissias.xsrpcj.example.simple.comms.SocketDataTransceiver;

//message type imports
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;

//generic message type import
import io.github.ppissias.xsrpcj.example.simple.types.Persons.MessageContainer;
import io.github.ppissias.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType;

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
public class PersonsClientHandler implements DataHandler, ErrorHandler, PersonsNotifyServerCallback {

	
	//the service handler
	private final PersonsServerService serviceHandler;
	
	private SocketDataTransceiver serverDataTransceiver;
	
	/**
	 * Constructs a new client handler
	 * @param socket
	 * @throws IOException
	 */
	public PersonsClientHandler(Socket socket, PersonsServerService serviceHandler) throws IOException {
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
				case searchRequest : {
					try {
						final SearchPersonRequest request = SearchPersonRequest.parseFrom(envelope.getMessageData().toByteArray());
						
 							 						//handle message
						SearchPersonResponse response = serviceHandler.search(request);						
												
						
						//convert to generic message
						MessageContainer outgoingMessage = 
								MessageContainer.newBuilder().setMessageType(MessageType.searchResponse).setMessageData(ByteString.copyFrom(response.toByteArray())).build();
						
						
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
				case notifyRequest : {
					try {
						final PersonNotificationRequest request = PersonNotificationRequest.parseFrom(envelope.getMessageData().toByteArray());
						
 							 						//handle message
						PersonNotificationResponse response = serviceHandler.notify(request, this);
						
												
						
						//convert to generic message
						MessageContainer outgoingMessage = 
								MessageContainer.newBuilder().setMessageType(MessageType.notifyResponse).setMessageData(ByteString.copyFrom(response.toByteArray())).build();
						
						
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
	public void notifyCallback(SearchPersonResponse callbackMessage) throws RemoteCommunicationsException {
		
		//convert to generic message
		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.notifyCallback).setMessageData(ByteString.copyFrom(callbackMessage.toByteArray())).build();
		
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
