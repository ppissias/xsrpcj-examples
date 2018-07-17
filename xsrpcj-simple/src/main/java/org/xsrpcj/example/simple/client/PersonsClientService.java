package org.xsrpcj.example.simple.client;

import org.xsrpcj.example.simple.comms.RemoteCommunicationsException;
import org.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import org.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import org.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import org.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;

/**
 * Service interface on the client-side.
 * all methods throw a @link{RemoteCommunicationsException} in case of commmunication problems
 * @author Petros Pissias
 *
 */
public interface PersonsClientService {

	public SearchPersonResponse search(SearchPersonRequest request) throws RemoteCommunicationsException;		
		
	public PersonNotificationResponse notify(PersonNotificationRequest request) throws RemoteCommunicationsException;		
		
}

