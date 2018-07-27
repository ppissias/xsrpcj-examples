
# xsrpcj-examples
This repository contains examples using xsrpcj and some performance measurements of xsrpcj and grpc using the same services.

## Running the examples 
You need to get the repository and compile the top level project, which will in turn compile all the sub project examples. 

### Compiling xsrpcj 
If you would like to compile xsrpcj yourself, you can download the repository and execute:

    cd xsrpcj
    mvn clean install 

This should compile and install the required jars to your local maven repository for using the xsrpcj code generator for the examples.
 
### Compiling and running the examples
You need to have an environment variable 
`PROTOC_PATH` pointing to the protoc executable `PROTOC_PATH=/path/to/protoc` (please contribute to the project if there is a good way to setup this environment variable globally from the top level maven pom) 
You can download the compiled protoc compiler usually here: (http://central.maven.org/maven2/com/google/protobuf/protoc/3.5.1/)

for the grpc example, you also need to have an environment variable
`PROTOC_GRPC_PATH` pointing to the grpc java generator executable `PROTOC_GRPC_PATH=/path/to/protoc-gen-grpc-java` 
You can download the compiled grpc java generator usually here: (http://central.maven.org/maven2/io/grpc/protoc-gen-grpc-java/0.13.2/)
 
Then you are all set ! 

    cd xsrpcj-examples
    mvn clean compile 
after a few seconds you should see something like 

    [INFO] Reactor Summary:
    [INFO]
    [INFO] examples ........................................... SUCCESS [  0.219 s]
    [INFO] xsrpcj performance example ......................... SUCCESS [  5.538 s]
    [INFO] xsrpcj simple example .............................. SUCCESS [  2.592 s]
    [INFO] grpc-performance ................................... SUCCESS [  2.621 s]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESS
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 11.156 s
    [INFO] Finished at: 2018-07-17T16:23:04+02:00
    [INFO] Final Memory: 19M/172M
    [INFO] ------------------------------------------------------------------------
which means everything compiled fine. 
## running the examples
Now that everything compiled fine you can run the examples. 

### Starting the xsrpcj performance test example
For convenience we will use maven for this task as well: 

    cd xsrpcj-examples/xsrpcj-performance 
    mvn exec:java -Dexec.mainClass="TestRpcServer"

you should see something like 

    [INFO] Scanning for projects...
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] Building xsrpcj performance example 1.0.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO]
    [INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ performance ---
    Listening for connections on port:22100

which means we have started the server 

**Now to start the client** 

    cd xsrpcj-examples/xsrpcj-performance
    mvn exec:java -Dexec.mainClass="TestRpcClient" -Dexec.args="hello 2000 20 localhost" 

This will start the following test 

 - make 2000 oneway calls using as an argument "hello" to the server, repeated 20 times. 
 - make 2000 request/response calls using as an argument "hello" to the server, repeated 20 times. 
 - make a call 20 times to a request/response/callback rpc method, that results in 2000 callback calls each time, with each callback message reply having 2000 times the word "hello" as a response. 

and measure the times for each round. 
The output is many lines in the form of

    2018-07-17 16:50:39,759 [TestRpcClient.main()] INFO  TestRpcClient  - It took 282 ms to complete the request response interaction test 2000 times
    2018-07-17 16:50:40,009 [TestRpcClient.main()] INFO  TestRpcClient  - It took 250 ms to complete the request response interaction test 2000 times
    2018-07-17 16:50:40,260 [TestRpcClient.main()] INFO  TestRpcClient  - It took 251 ms to complete the request response interaction test 2000 times
    2018-07-17 16:50:40,494 [TestRpcClient.main()] INFO  TestRpcClient  - It took 234 ms to complete the request response interaction test 2000 times

### Starting the grpc performance test example
This is the same application and services implemented using grpc, the protocol buffers native RPC generation mechanism. 

For this example, you need to download the java grpc generator and set it to the master pom file as explained in the beginning 

	<properties>
		<protoc.compiler.path>/path/to/protoc</protoc.compiler.path>
		<protoc.grpc.path>/path/to/protoc-gen-grpc-java</protoc.grpc.path>
	</properties>
assuming that everything compiled fine, as described in the beginning, you run the Server and Client in a very similar way as the previous example:

    cd xsrpcj-examples/Grpc-performance 
    mvn exec:java -Dexec.mainClass="TestGRpcServer"
and you should see something like

    [INFO] Scanning for projects...
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] Building grpc-performance 1.0.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO]
    [INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ grpc-performance ---
    Server started
**Now to start the client** 

    cd xsrpcj-examples/Grpc-performance 
    mvn exec:java -Dexec.mainClass="TestGRpcClient" -Dexec.args="hello 2000 20 localhost"
This will perform the same tests as the previous example, with the exception that it will skip the one way interaction calls (not natively supported in Grpc) and report similar results

    2018-07-17 16:52:49,223 [TestGRpcClient.main()] INFO  TestGRpcClient  - It took 484 ms to complete the request responseinteraction test 2000 times
    2018-07-17 16:52:49,676 [TestGRpcClient.main()] INFO  TestGRpcClient  - It took 437 ms to complete the request responseinteraction test 2000 times
    2018-07-17 16:52:50,097 [TestGRpcClient.main()] INFO  TestGRpcClient  - It took 421 ms to complete the request responseinteraction test 2000 times
    2018-07-17 16:52:50,535 [TestGRpcClient.main()] INFO  TestGRpcClient  - It took 438 ms to complete the request responseinteraction test 2000 times

### Starting the simple example
This is a small application demonstrating client / server interactions, in the form of a client making synchronous and asynchronous search queries to a server. The application does not have a particular meaning except to demonstrate the basic interaction patterns. 

**Starting the server**

    cd xsrpcj-examples/xsrpcj-simple
    mvn exec:java -Dexec.mainClass="SearchExampleServer" 

    [INFO] Scanning for projects...
    [INFO]
    [INFO] ------------------------------------------------------------------------
    [INFO] Building xsrpcj simple example 1.0.0-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [INFO]
    [INFO] --- exec-maven-plugin:1.6.0:java (default-cli) @ simple ---
    Listening for connections on port:22100

**Starting the client**

    cd xsrpcj-examples/xsrpcj-simple
    mvn exec:java -Dexec.mainClass="SearchExampleClient" -Dexec.args="localhost" 

and you should see something like 

    ...
    2018-07-17 17:15:13,190 [SearchExampleClient.main()] INFO  SearchExampleClient  - searching for person with a first name:jvilvxqeds
    2018-07-17 17:15:14,510 [SearchExampleClient.main()] INFO  SearchExampleClient  - Received response:jvilvxqeds Smith 0049-7893856-231
    2018-07-17 17:15:14,510 [SearchExampleClient.main()] INFO  SearchExampleClient  - searching for person with a first name:slhyezsyzi
    2018-07-17 17:15:15,587 [SearchExampleClient.main()] INFO  SearchExampleClient  - Received response:slhyezsyzi Glassy 0049-4407199-718
    2018-07-17 17:15:15,603 [SearchExampleClient.main()] INFO  SearchExampleClient  - Starting person notifications search
    2018-07-17 17:15:17,588 [SearchExampleClient.main()] INFO  SearchExampleClient  - requesting notifications (async) for new appearances for a person with first name:lqhvjwsxvv
    2018-07-17 17:15:19,594 [SearchExampleClient.main()] INFO  SearchExampleClient  - requesting notifications (async) for new appearances for a person with first name:xlhjaduisp
    2018-07-17 17:15:19,893 [SocketDataTransceiverReaderThread-1 remote host:localhost/127.0.0.1 port:22100] INFO  SearchExampleClient  - Received asynchronous notification matching person :lqhvjwsxvv Pirina 0049-5259137-247
    2018-07-17 17:15:21,595 [SearchExampleClient.main()] INFO  SearchExampleClient  - requesting notifications (async) for new appearances for a person with first name:ifvjglzjfi
    2018-07-17 17:15:23,028 [SocketDataTransceiverReaderThread-1 remote host:localhost/127.0.0.1 port:22100] INFO  SearchExampleClient  - Received asynchronous notification matching person :xlhjaduisp Stamatiou 0049-2819874-609
    2018-07-17 17:15:23,317 [SocketDataTransceiverReaderThread-1 remote host:localhost/127.0.0.1 port:22100] INFO  SearchExampleClient  - Received asynchronous notification matching person :xlhjaduisp Jones 0049-7166002-926
