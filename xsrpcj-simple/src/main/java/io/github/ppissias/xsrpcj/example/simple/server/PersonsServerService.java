package io.github.ppissias.xsrpcj.example.simple.server;

import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import io.github.ppissias.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;


/**
 * Service interface on the server-side.
 * 
 * @author Petros Pissias
 *
 */
public interface PersonsServerService {

	public SearchPersonResponse search(SearchPersonRequest request);		
		
	public PersonNotificationResponse notify(PersonNotificationRequest request, PersonsNotifyServerCallback callback);	
		
}
