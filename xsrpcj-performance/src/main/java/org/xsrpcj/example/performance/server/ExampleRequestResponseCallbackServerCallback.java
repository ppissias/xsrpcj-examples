package org.xsrpcj.example.performance.server;

import org.xsrpcj.example.performance.comms.RemoteCommunicationsException;
import org.xsrpcj.example.performance.TestMessages.CallbackResponse;

/**
 * This interface is used for sending asynchronous callback replies to a client
 * who called requestResponseCallback
 * 
 * @author Petros Pissias
 *
 */
public interface ExampleRequestResponseCallbackServerCallback {

	public void requestResponseCallbackCallback(CallbackResponse cb) throws RemoteCommunicationsException;
}
