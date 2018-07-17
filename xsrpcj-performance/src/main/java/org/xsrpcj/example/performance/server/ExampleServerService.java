package org.xsrpcj.example.performance.server;

import org.xsrpcj.example.performance.TestMessages.MessageRequest;
import org.xsrpcj.example.performance.TestMessages.MessageResponse;
import org.xsrpcj.example.performance.TestMessages.CallbackResponse;


/**
 * Service interface on the server-side.
 * 
 * @author Petros Pissias
 *
 */
public interface ExampleServerService {

	public void oneWayRequest(MessageRequest request);		
	
	
	public MessageResponse requestResponse(MessageRequest request);		
		
	public MessageResponse requestResponseCallback(MessageRequest request, ExampleRequestResponseCallbackServerCallback callback);	
		
}
