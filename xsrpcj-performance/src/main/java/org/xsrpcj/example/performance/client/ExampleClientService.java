package org.xsrpcj.example.performance.client;

import org.xsrpcj.example.performance.comms.RemoteCommunicationsException;
import org.xsrpcj.example.performance.TestMessages.MessageRequest;
import org.xsrpcj.example.performance.TestMessages.MessageResponse;
import org.xsrpcj.example.performance.TestMessages.CallbackResponse;

/**
 * Service interface on the client-side.
 * all methods throw a @link{RemoteCommunicationsException} in case of commmunication problems
 * @author Petros Pissias
 *
 */
public interface ExampleClientService {

	public void oneWayRequest(MessageRequest request) throws RemoteCommunicationsException;		
	
	public MessageResponse requestResponse(MessageRequest request) throws RemoteCommunicationsException;		
		
	public MessageResponse requestResponseCallback(MessageRequest request) throws RemoteCommunicationsException;		
		
}

