package com.nexussphere.common.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class AccessManagerGRPCServiceGrpc {

  private AccessManagerGRPCServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "AccessManagerGRPCService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.nexussphere.common.grpc.CreateSecurityGroupRequest,
      com.nexussphere.common.grpc.CreateSecurityGroupResponse> getCreateSecurityGroupMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateSecurityGroup",
      requestType = com.nexussphere.common.grpc.CreateSecurityGroupRequest.class,
      responseType = com.nexussphere.common.grpc.CreateSecurityGroupResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.nexussphere.common.grpc.CreateSecurityGroupRequest,
      com.nexussphere.common.grpc.CreateSecurityGroupResponse> getCreateSecurityGroupMethod() {
    io.grpc.MethodDescriptor<com.nexussphere.common.grpc.CreateSecurityGroupRequest, com.nexussphere.common.grpc.CreateSecurityGroupResponse> getCreateSecurityGroupMethod;
    if ((getCreateSecurityGroupMethod = AccessManagerGRPCServiceGrpc.getCreateSecurityGroupMethod) == null) {
      synchronized (AccessManagerGRPCServiceGrpc.class) {
        if ((getCreateSecurityGroupMethod = AccessManagerGRPCServiceGrpc.getCreateSecurityGroupMethod) == null) {
          AccessManagerGRPCServiceGrpc.getCreateSecurityGroupMethod = getCreateSecurityGroupMethod =
              io.grpc.MethodDescriptor.<com.nexussphere.common.grpc.CreateSecurityGroupRequest, com.nexussphere.common.grpc.CreateSecurityGroupResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateSecurityGroup"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.nexussphere.common.grpc.CreateSecurityGroupRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.nexussphere.common.grpc.CreateSecurityGroupResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AccessManagerGRPCServiceMethodDescriptorSupplier("CreateSecurityGroup"))
              .build();
        }
      }
    }
    return getCreateSecurityGroupMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AccessManagerGRPCServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceStub>() {
        @java.lang.Override
        public AccessManagerGRPCServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccessManagerGRPCServiceStub(channel, callOptions);
        }
      };
    return AccessManagerGRPCServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static AccessManagerGRPCServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceBlockingV2Stub>() {
        @java.lang.Override
        public AccessManagerGRPCServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccessManagerGRPCServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return AccessManagerGRPCServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AccessManagerGRPCServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceBlockingStub>() {
        @java.lang.Override
        public AccessManagerGRPCServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccessManagerGRPCServiceBlockingStub(channel, callOptions);
        }
      };
    return AccessManagerGRPCServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AccessManagerGRPCServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AccessManagerGRPCServiceFutureStub>() {
        @java.lang.Override
        public AccessManagerGRPCServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AccessManagerGRPCServiceFutureStub(channel, callOptions);
        }
      };
    return AccessManagerGRPCServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void createSecurityGroup(com.nexussphere.common.grpc.CreateSecurityGroupRequest request,
        io.grpc.stub.StreamObserver<com.nexussphere.common.grpc.CreateSecurityGroupResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateSecurityGroupMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AccessManagerGRPCService.
   */
  public static abstract class AccessManagerGRPCServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AccessManagerGRPCServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AccessManagerGRPCService.
   */
  public static final class AccessManagerGRPCServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AccessManagerGRPCServiceStub> {
    private AccessManagerGRPCServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccessManagerGRPCServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccessManagerGRPCServiceStub(channel, callOptions);
    }

    /**
     */
    public void createSecurityGroup(com.nexussphere.common.grpc.CreateSecurityGroupRequest request,
        io.grpc.stub.StreamObserver<com.nexussphere.common.grpc.CreateSecurityGroupResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateSecurityGroupMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AccessManagerGRPCService.
   */
  public static final class AccessManagerGRPCServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<AccessManagerGRPCServiceBlockingV2Stub> {
    private AccessManagerGRPCServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccessManagerGRPCServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccessManagerGRPCServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public com.nexussphere.common.grpc.CreateSecurityGroupResponse createSecurityGroup(com.nexussphere.common.grpc.CreateSecurityGroupRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getCreateSecurityGroupMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service AccessManagerGRPCService.
   */
  public static final class AccessManagerGRPCServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AccessManagerGRPCServiceBlockingStub> {
    private AccessManagerGRPCServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccessManagerGRPCServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccessManagerGRPCServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.nexussphere.common.grpc.CreateSecurityGroupResponse createSecurityGroup(com.nexussphere.common.grpc.CreateSecurityGroupRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateSecurityGroupMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AccessManagerGRPCService.
   */
  public static final class AccessManagerGRPCServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AccessManagerGRPCServiceFutureStub> {
    private AccessManagerGRPCServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AccessManagerGRPCServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AccessManagerGRPCServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.nexussphere.common.grpc.CreateSecurityGroupResponse> createSecurityGroup(
        com.nexussphere.common.grpc.CreateSecurityGroupRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateSecurityGroupMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_CREATE_SECURITY_GROUP = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_CREATE_SECURITY_GROUP:
          serviceImpl.createSecurityGroup((com.nexussphere.common.grpc.CreateSecurityGroupRequest) request,
              (io.grpc.stub.StreamObserver<com.nexussphere.common.grpc.CreateSecurityGroupResponse>) responseObserver);
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

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getCreateSecurityGroupMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.nexussphere.common.grpc.CreateSecurityGroupRequest,
              com.nexussphere.common.grpc.CreateSecurityGroupResponse>(
                service, METHODID_CREATE_SECURITY_GROUP)))
        .build();
  }

  private static abstract class AccessManagerGRPCServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AccessManagerGRPCServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.nexussphere.common.grpc.AccessManagerProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AccessManagerGRPCService");
    }
  }

  private static final class AccessManagerGRPCServiceFileDescriptorSupplier
      extends AccessManagerGRPCServiceBaseDescriptorSupplier {
    AccessManagerGRPCServiceFileDescriptorSupplier() {}
  }

  private static final class AccessManagerGRPCServiceMethodDescriptorSupplier
      extends AccessManagerGRPCServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AccessManagerGRPCServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (AccessManagerGRPCServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AccessManagerGRPCServiceFileDescriptorSupplier())
              .addMethod(getCreateSecurityGroupMethod())
              .build();
        }
      }
    }
    return result;
  }
}
