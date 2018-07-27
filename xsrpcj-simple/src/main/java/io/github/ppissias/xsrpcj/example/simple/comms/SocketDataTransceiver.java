package io.github.ppissias.xsrpcj.example.simple.comms;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;


/**
 * This is a class used to send data over a socket 
 * using a simple packet encoding : 
 * 
 * [4 byte header=payload size][payload]
 * 
 * The class offers a method in order to send data over a socket it manages
 * and has an internal thread that reads data from the same socket and 
 * forwards it to its DataHandler. 
 * 
 * Send and read operations are atomic. Read operations are guaranteed
 * to be atomic as they are done by the reader thread sequentially. 
 * 
 * Parallel access to the server from multiple threads
 * (if needed) is achieved by having multiple SocketDataTransceiver
 * instances from a client to a server, managed by multiple
 * concrete instances of ServiceProxy objects.
 * 
 * @author Petros Pissias
 *
 */
public class SocketDataTransceiver {

	//the socket for this data Transceiver
	private final Socket socket;
	
	//Output interface for sending data through the Socket
	private final DataOutputStream outputInterface; 	
	
	//its operating thread
	private final SocketDataTransceiverReaderThread socketReadThread;
	
	
	//locks for reading and writing atomically
	private final ReentrantLock writeLock = new ReentrantLock();


	/**
	 * Constructs a new SocketDatatransceiver object with a prodided socket.
	 * @param socket the socket
	 * @param dataHandler the data handler to receive dat aindications
	 * @param errorHandler the error handler which will be informed in case of a communications error
	 * @throws IOException in case is I/O problems
	 */
	public SocketDataTransceiver(Socket socket, DataHandler dataHandler, ErrorHandler errorHandler) throws IOException {
		this.socket = socket;
		
		//create the data streams
		outputInterface = new DataOutputStream(socket.getOutputStream());
		
		//the thread is started via the initialize method
		socketReadThread = new SocketDataTransceiverReaderThread(socket, errorHandler, dataHandler);
		
	}
	
	/**
	 * Constructs a new SocketDatatransceiver object
	 * @param host the host to connect to 
	 * @param port the port to connect to
	 * @param dataHandler the data handler to receive dat aindications
	 * @param errorHandler the error handler which will be informed in case of a communications error
	 * @throws IOException in case is I/O problems
	 */
	public SocketDataTransceiver(String host, int port, DataHandler dataHandler, ErrorHandler errorHandler) throws IOException {
		socket = new Socket(host, port);
		socket.setTcpNoDelay(true);

		
		//create the data streams
		outputInterface = new DataOutputStream(socket.getOutputStream());
		
		//the thread is started via the initialize method
		socketReadThread = new SocketDataTransceiverReaderThread(socket, errorHandler, dataHandler);
	
	}
	
	/**
	 * Starts the processing of this SocketDataTransceiver
	 */
	public void initialize() {
		socketReadThread.start();
	}

	/**
	 * Sends the specified payload data via the socket
	 * using the predefined packet encoding.
	 * 
	 * The send operation is atomic
	 *  
	 * @param data the payload data
	 * @throws IOException in case of communication issues 
	 */
	public void send(byte[] data) throws IOException {
		//lock
		writeLock.lock();
		
		try {
			//write payload size	
			int payloadSize = data.length;
		
			outputInterface.writeInt(payloadSize);
			
			//write payload
			outputInterface.write(data);
			
			outputInterface.flush();	
		} finally  {
			writeLock.unlock();
		}
	}
	
	/**
	 * Checks if the provided thread is this data transceiver thread.
	 * This is used in order to determine from where an error indication originates
	 * in order to handle race conditions.
	 * 
	 * @param thread the thread to check against
	 * @return true if the thread is the data transceiver thread
	 */
	public boolean isDataTransceiverThread(Thread thread) {
		return (thread == socketReadThread);
	}

	/**
	 * Attempts to close the socket
	 * @return true if succesful false if an exception is thrown while trying to close the socket
	 */
	public boolean closeSocket() {
		try {
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}	
}
