package io.github.ppissias.xsrpcj.example.simple.client;

import io.github.ppissias.xsrpcj.example.simple.comms.RemoteCommunicationsException;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;

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

