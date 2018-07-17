package org.xsrpcj.example.simple.server;

import org.xsrpcj.example.simple.comms.RemoteCommunicationsException;
import org.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;

/**
 * This interface is used for sending asynchronous callback replies to a client
 * who called notify
 * 
 * @author Petros Pissias
 *
 */
public interface PersonsNotifyServerCallback {

	public void notifyCallback(SearchPersonResponse cb) throws RemoteCommunicationsException;
}
