package com.biswasakashdev.nexussphere.protogen.users.v1;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@io.grpc.stub.annotations.GrpcGenerated
public final class UserGRPCServiceGrpc {

  private UserGRPCServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "users.v1.UserGRPCService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest,
      com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse> getGetUsersMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetUsers",
      requestType = com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest.class,
      responseType = com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest,
      com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse> getGetUsersMethod() {
    io.grpc.MethodDescriptor<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest, com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse> getGetUsersMethod;
    if ((getGetUsersMethod = UserGRPCServiceGrpc.getGetUsersMethod) == null) {
      synchronized (UserGRPCServiceGrpc.class) {
        if ((getGetUsersMethod = UserGRPCServiceGrpc.getGetUsersMethod) == null) {
          UserGRPCServiceGrpc.getGetUsersMethod = getGetUsersMethod =
              io.grpc.MethodDescriptor.<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest, com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetUsers"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse.getDefaultInstance()))
              .setSchemaDescriptor(new UserGRPCServiceMethodDescriptorSupplier("GetUsers"))
              .build();
        }
      }
    }
    return getGetUsersMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static UserGRPCServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceStub>() {
        @java.lang.Override
        public UserGRPCServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserGRPCServiceStub(channel, callOptions);
        }
      };
    return UserGRPCServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static UserGRPCServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceBlockingV2Stub>() {
        @java.lang.Override
        public UserGRPCServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserGRPCServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return UserGRPCServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static UserGRPCServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceBlockingStub>() {
        @java.lang.Override
        public UserGRPCServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserGRPCServiceBlockingStub(channel, callOptions);
        }
      };
    return UserGRPCServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static UserGRPCServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<UserGRPCServiceFutureStub>() {
        @java.lang.Override
        public UserGRPCServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new UserGRPCServiceFutureStub(channel, callOptions);
        }
      };
    return UserGRPCServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getUsers(com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest request,
        io.grpc.stub.StreamObserver<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetUsersMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service UserGRPCService.
   */
  public static abstract class UserGRPCServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return UserGRPCServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service UserGRPCService.
   */
  public static final class UserGRPCServiceStub
      extends io.grpc.stub.AbstractAsyncStub<UserGRPCServiceStub> {
    private UserGRPCServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserGRPCServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserGRPCServiceStub(channel, callOptions);
    }

    /**
     */
    public void getUsers(com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest request,
        io.grpc.stub.StreamObserver<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetUsersMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service UserGRPCService.
   */
  public static final class UserGRPCServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<UserGRPCServiceBlockingV2Stub> {
    private UserGRPCServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserGRPCServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserGRPCServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse getUsers(com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest request) throws io.grpc.StatusException {
      return io.grpc.stub.ClientCalls.blockingV2UnaryCall(
          getChannel(), getGetUsersMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service UserGRPCService.
   */
  public static final class UserGRPCServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<UserGRPCServiceBlockingStub> {
    private UserGRPCServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserGRPCServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserGRPCServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse getUsers(com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetUsersMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service UserGRPCService.
   */
  public static final class UserGRPCServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<UserGRPCServiceFutureStub> {
    private UserGRPCServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected UserGRPCServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new UserGRPCServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse> getUsers(
        com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetUsersMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_USERS = 0;

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
        case METHODID_GET_USERS:
          serviceImpl.getUsers((com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest) request,
              (io.grpc.stub.StreamObserver<com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse>) responseObserver);
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
          getGetUsersMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersRequest,
              com.biswasakashdev.nexussphere.protogen.users.v1.GetUsersResponse>(
                service, METHODID_GET_USERS)))
        .build();
  }

  private static abstract class UserGRPCServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    UserGRPCServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.biswasakashdev.nexussphere.protogen.users.v1.Main.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("UserGRPCService");
    }
  }

  private static final class UserGRPCServiceFileDescriptorSupplier
      extends UserGRPCServiceBaseDescriptorSupplier {
    UserGRPCServiceFileDescriptorSupplier() {}
  }

  private static final class UserGRPCServiceMethodDescriptorSupplier
      extends UserGRPCServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    UserGRPCServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (UserGRPCServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new UserGRPCServiceFileDescriptorSupplier())
              .addMethod(getGetUsersMethod())
              .build();
        }
      }
    }
    return result;
  }
}
