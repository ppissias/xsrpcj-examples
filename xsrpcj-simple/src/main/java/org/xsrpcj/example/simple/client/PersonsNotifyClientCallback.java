package org.xsrpcj.example.simple.client;

import org.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;

/**
 * This interface is used for receiving asynchronous callback replies for service notify
 * 
 * @author Petros Pissias
 *
 */
public interface PersonsNotifyClientCallback {

	public void notifyCallback(SearchPersonResponse cb);
}
