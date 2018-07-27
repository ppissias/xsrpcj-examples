package io.github.ppissias.xsrpcj.example.grpcperformance;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 * <pre>
 *service definition
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.13.1)",
    comments = "Source: TestMessages.proto")
public final class ExampleGrpc {

  private ExampleGrpc() {}

  public static final String SERVICE_NAME = "TestMessages.Example";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest,
      io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse> getRequestResponseMethod;

  public static io.grpc.MethodDescriptor<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest,
      io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse> getRequestResponseMethod() {
    io.grpc.MethodDescriptor<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest, io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse> getRequestResponseMethod;
    if ((getRequestResponseMethod = ExampleGrpc.getRequestResponseMethod) == null) {
      synchronized (ExampleGrpc.class) {
        if ((getRequestResponseMethod = ExampleGrpc.getRequestResponseMethod) == null) {
          ExampleGrpc.getRequestResponseMethod = getRequestResponseMethod = 
              io.grpc.MethodDescriptor.<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest, io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "TestMessages.Example", "requestResponse"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ExampleMethodDescriptorSupplier("requestResponse"))
                  .build();
          }
        }
     }
     return getRequestResponseMethod;
  }

  private static volatile io.grpc.MethodDescriptor<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest,
      io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse> getRequestResponseCallbackMethod;

  public static io.grpc.MethodDescriptor<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest,
      io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse> getRequestResponseCallbackMethod() {
    io.grpc.MethodDescriptor<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest, io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse> getRequestResponseCallbackMethod;
    if ((getRequestResponseCallbackMethod = ExampleGrpc.getRequestResponseCallbackMethod) == null) {
      synchronized (ExampleGrpc.class) {
        if ((getRequestResponseCallbackMethod = ExampleGrpc.getRequestResponseCallbackMethod) == null) {
          ExampleGrpc.getRequestResponseCallbackMethod = getRequestResponseCallbackMethod = 
              io.grpc.MethodDescriptor.<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest, io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.SERVER_STREAMING)
              .setFullMethodName(generateFullMethodName(
                  "TestMessages.Example", "requestResponseCallback"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse.getDefaultInstance()))
                  .setSchemaDescriptor(new ExampleMethodDescriptorSupplier("requestResponseCallback"))
                  .build();
          }
        }
     }
     return getRequestResponseCallbackMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ExampleStub newStub(io.grpc.Channel channel) {
    return new ExampleStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ExampleBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new ExampleBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ExampleFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new ExampleFutureStub(channel);
  }

  /**
   * <pre>
   *service definition
   * </pre>
   */
  public static abstract class ExampleImplBase implements io.grpc.BindableService {

    /**
     */
    public void requestResponse(io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request,
        io.grpc.stub.StreamObserver<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRequestResponseMethod(), responseObserver);
    }

    /**
     */
    public void requestResponseCallback(io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request,
        io.grpc.stub.StreamObserver<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getRequestResponseCallbackMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getRequestResponseMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest,
                io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse>(
                  this, METHODID_REQUEST_RESPONSE)))
          .addMethod(
            getRequestResponseCallbackMethod(),
            asyncServerStreamingCall(
              new MethodHandlers<
                io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest,
                io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse>(
                  this, METHODID_REQUEST_RESPONSE_CALLBACK)))
          .build();
    }
  }

  /**
   * <pre>
   *service definition
   * </pre>
   */
  public static final class ExampleStub extends io.grpc.stub.AbstractStub<ExampleStub> {
    private ExampleStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExampleStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExampleStub(channel, callOptions);
    }

    /**
     */
    public void requestResponse(io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request,
        io.grpc.stub.StreamObserver<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getRequestResponseMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void requestResponseCallback(io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request,
        io.grpc.stub.StreamObserver<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse> responseObserver) {
      asyncServerStreamingCall(
          getChannel().newCall(getRequestResponseCallbackMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * <pre>
   *service definition
   * </pre>
   */
  public static final class ExampleBlockingStub extends io.grpc.stub.AbstractStub<ExampleBlockingStub> {
    private ExampleBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExampleBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExampleBlockingStub(channel, callOptions);
    }

    /**
     */
    public io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse requestResponse(io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request) {
      return blockingUnaryCall(
          getChannel(), getRequestResponseMethod(), getCallOptions(), request);
    }

    /**
     */
    public java.util.Iterator<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse> requestResponseCallback(
        io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request) {
      return blockingServerStreamingCall(
          getChannel(), getRequestResponseCallbackMethod(), getCallOptions(), request);
    }
  }

  /**
   * <pre>
   *service definition
   * </pre>
   */
  public static final class ExampleFutureStub extends io.grpc.stub.AbstractStub<ExampleFutureStub> {
    private ExampleFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private ExampleFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ExampleFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new ExampleFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse> requestResponse(
        io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getRequestResponseMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REQUEST_RESPONSE = 0;
  private static final int METHODID_REQUEST_RESPONSE_CALLBACK = 1;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final ExampleImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(ExampleImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REQUEST_RESPONSE:
          serviceImpl.requestResponse((io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest) request,
              (io.grpc.stub.StreamObserver<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageResponse>) responseObserver);
          break;
        case METHODID_REQUEST_RESPONSE_CALLBACK:
          serviceImpl.requestResponseCallback((io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.MessageRequest) request,
              (io.grpc.stub.StreamObserver<io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.CallbackResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class ExampleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ExampleBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return io.github.ppissias.xsrpcj.example.grpcperformance.TestMessages.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("Example");
    }
  }

  private static final class ExampleFileDescriptorSupplier
      extends ExampleBaseDescriptorSupplier {
    ExampleFileDescriptorSupplier() {}
  }

  private static final class ExampleMethodDescriptorSupplier
      extends ExampleBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    ExampleMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ExampleGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ExampleFileDescriptorSupplier())
              .addMethod(getRequestResponseMethod())
              .addMethod(getRequestResponseCallbackMethod())
              .build();
        }
      }
    }
    return result;
  }
}
