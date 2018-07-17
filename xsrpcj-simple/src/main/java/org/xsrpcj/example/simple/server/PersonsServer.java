package org.xsrpcj.example.simple.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Petros Pissias
 *
 */
public class PersonsServer extends Thread {
	
	
	private final PersonsServerService serviceHandler;
			
	private final int port;	
	private final int defaultport = 22100; 
			
	public PersonsServer(PersonsServerService serviceHandler) {
		port = defaultport;
		this.serviceHandler = serviceHandler;				
	}
	
	public PersonsServer(PersonsServerService serviceHandler, int port) {
		this.port = port;
		this.serviceHandler = serviceHandler;				
	}
	
	public void run() {
		
		try {
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("Listening for connections on port:"+port);		
			
			while (true) {
				final Socket incomingConnection = serverSocket.accept();
				incomingConnection.setTcpNoDelay(true);
				
				
				try {
					//handle client 
					PersonsClientHandler handler = new PersonsClientHandler(incomingConnection,serviceHandler);
					
					handler.initialize();
				} catch (IOException ioex) {
					System.out.println("Error: Problem with client connection originating from:"+incomingConnection.getInetAddress());
				}
			}
		} catch (IOException ioex) {
			System.out.println("Error: Cannot accept connection on port:"+port);		
			return;
		} 
	}
}
