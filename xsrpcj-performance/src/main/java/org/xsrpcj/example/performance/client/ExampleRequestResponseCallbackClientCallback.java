package org.xsrpcj.example.performance.client;

import org.xsrpcj.example.performance.TestMessages.CallbackResponse;

/**
 * This interface is used for receiving asynchronous callback replies for service requestResponseCallback
 * 
 * @author Petros Pissias
 *
 */
public interface ExampleRequestResponseCallbackClientCallback {

	public void requestResponseCallbackCallback(CallbackResponse cb);
}
