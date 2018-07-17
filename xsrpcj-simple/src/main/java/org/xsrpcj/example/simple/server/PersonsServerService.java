package org.xsrpcj.example.simple.server;

import org.xsrpcj.example.simple.SearchMessages.SearchPersonRequest;
import org.xsrpcj.example.simple.SearchMessages.SearchPersonResponse;
import org.xsrpcj.example.simple.SearchMessages.PersonNotificationRequest;
import org.xsrpcj.example.simple.SearchMessages.PersonNotificationResponse;


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
