package io.github.ppissias.xsrpcj.example.performance.server;

import io.github.ppissias.xsrpcj.example.performance.TestMessages.MessageRequest;
import io.github.ppissias.xsrpcj.example.performance.TestMessages.MessageResponse;
import io.github.ppissias.xsrpcj.example.performance.TestMessages.CallbackResponse;


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
