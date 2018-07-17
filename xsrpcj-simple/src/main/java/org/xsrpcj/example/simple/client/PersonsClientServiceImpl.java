package org.xsrpcj.example.simple.client;

import java.io.IOException;
import java.util.Arrays;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import org.xsrpcj.example.simple.comms.ClientReplyHandler;
import org.xsrpcj.example.simple.comms.DataHandler;
import org.xsrpcj.example.simple.comms.RemoteCommunicationsErrorType;
import org.xsrpcj.example.simple.comms.RemoteCommunicationsException;
import org.xsrpcj.example.simple.comms.ServiceProxy;
import org.xsrpcj.example.simple.comms.SocketDataTransceiver;

//message type imports
import org.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import org.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import org.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import org.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;

//generic message type import
import org.xsrpcj.example.simple.types.Persons.MessageContainer;
import org.xsrpcj.example.simple.types.Persons.MessageContainer.MessageType;


public class PersonsClientServiceImpl extends ServiceProxy implements DataHandler, PersonsClientService{
	
	//the reply handlers for each interaction pattern
 	private final ClientReplyHandler<SearchPersonResponse> clientsearchReplyHandler; 
 	private final ClientReplyHandler<PersonNotificationResponse> clientnotifyReplyHandler; 
	
	//keep references to all service callbacks
 	private final PersonsNotifyClientCallback clientnotifyCallback;
	
	//timeout waiting for a reply from the server
	private static final int timeoutSeconds = 60;
	
	public PersonsClientServiceImpl(String host, int port, PersonsNotifyClientCallback clientnotifyCallback) {
		super(host, port);
		
		//create all request / reply Handlers
 		clientsearchReplyHandler = new ClientReplyHandler<SearchPersonResponse>(timeoutSeconds); 
 		clientnotifyReplyHandler = new ClientReplyHandler<PersonNotificationResponse>(timeoutSeconds); 
		
		//keep references to all service callbacks
 		this.clientnotifyCallback = clientnotifyCallback;
	}

	//constructor with default port
	public PersonsClientServiceImpl(String host, PersonsNotifyClientCallback clientnotifyCallback) {
		super(host, 22100);
		
		//create all request / reply Handlers
 		clientsearchReplyHandler = new ClientReplyHandler<SearchPersonResponse>(timeoutSeconds); 
 		clientnotifyReplyHandler = new ClientReplyHandler<PersonNotificationResponse>(timeoutSeconds); 
		
		//keep references to all service callbacks
 		this.clientnotifyCallback = clientnotifyCallback;
	}	
	
	//high level access methods. All Req-Reply operations are atomic 
	//and should not overlap, thus all methods are synchronized
	@Override
	public synchronized SearchPersonResponse search(SearchPersonRequest request) throws RemoteCommunicationsException {		
	
		//create new connection of necessary
		checkDataTransceiver();

		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.searchRequest).setMessageData(ByteString.copyFrom(request.toByteArray())).build();
				
		try {
			//send
			clientDataTransceiver.send(outgoingMessage.toByteArray());
		}catch (IOException e) {
			handleSendException(e);
		}			
		
		//get reply
		SearchPersonResponse reply = clientsearchReplyHandler.getReply();
			
		return reply;
	}
	
	@Override
	public synchronized PersonNotificationResponse notify(PersonNotificationRequest request) throws RemoteCommunicationsException {		
	
		//create new connection of necessary
		checkDataTransceiver();

		MessageContainer outgoingMessage = 
				MessageContainer.newBuilder().setMessageType(MessageType.notifyRequest).setMessageData(ByteString.copyFrom(request.toByteArray())).build();
				
		try {
			//send
			clientDataTransceiver.send(outgoingMessage.toByteArray());
		}catch (IOException e) {
			handleSendException(e);
		}			
		
		//get reply
		PersonNotificationResponse reply = clientnotifyReplyHandler.getReply();
			
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
				case searchResponse : {
					try {
						SearchPersonResponse response = SearchPersonResponse.parseFrom(incomingMessage.getMessageData().toByteArray());
						clientsearchReplyHandler.insertReply(response);
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Error: Data that cannot be decoded:"+Arrays.toString(incomingMessage.getMessageData().toByteArray()));
						
					} catch (InterruptedException e) {
						System.out.println("Error: Error: Interrupted while trying to insert reply to handler");							
					}									
					break;
				}
				case notifyResponse : {
					try {
						PersonNotificationResponse response = PersonNotificationResponse.parseFrom(incomingMessage.getMessageData().toByteArray());
						clientnotifyReplyHandler.insertReply(response);
					} catch (InvalidProtocolBufferException e) {
						System.out.println("Error: Error: Cannot decode data. Protocol error:"+e.getMessage());
						System.out.println("Error: Error: Data that cannot be decoded:"+Arrays.toString(incomingMessage.getMessageData().toByteArray()));
						
					} catch (InterruptedException e) {
						System.out.println("Error: Error: Interrupted while trying to insert reply to handler");							
					}									
					break;
				}
			
				//handle callbacks
				case notifyCallback : {
					try {
						SearchPersonResponse callbackMessage = SearchPersonResponse.parseFrom(incomingMessage.getMessageData().toByteArray());
						clientnotifyCallback.notifyCallback(callbackMessage);						
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
