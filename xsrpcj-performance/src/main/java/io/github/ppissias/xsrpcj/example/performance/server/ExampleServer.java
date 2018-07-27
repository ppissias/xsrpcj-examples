package io.github.ppissias.xsrpcj.example.performance.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * @author Petros Pissias
 *
 */
public class ExampleServer extends Thread {
	
	
	private final ExampleServerService serviceHandler;
			
	private final int port;	
	private final int defaultport = 22100; 
			
	public ExampleServer(ExampleServerService serviceHandler) {
		port = defaultport;
		this.serviceHandler = serviceHandler;				
	}
	
	public ExampleServer(ExampleServerService serviceHandler, int port) {
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
					ExampleClientHandler handler = new ExampleClientHandler(incomingConnection,serviceHandler);
					
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
