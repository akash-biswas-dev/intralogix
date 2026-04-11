package com.biswasakashdev.nexussphere.protogen.users.v1;

import static com.biswasakashdev.nexussphere.protogen.users.v1.UserGRPCServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;


@javax.annotation.Generated(
value = "by ReactorGrpc generator",
comments = "Source: users/v1/main.proto")
public final class ReactorUserGRPCServiceGrpc {
    private ReactorUserGRPCServiceGrpc() {}

    public static ReactorUserGRPCServiceStub newReactorStub(io.grpc.Channel channel) {
        return new ReactorUserGRPCServiceStub(channel);
    }

    public static final class ReactorUserGRPCServiceStub extends io.grpc.stub.AbstractStub<ReactorUserGRPCServiceStub> {
        private UserGRPCServiceGrpc.UserGRPCServiceStub delegateStub;

        private ReactorUserGRPCServiceStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = UserGRPCServiceGrpc.newStub(channel);
        }

        private ReactorUserGRPCServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = UserGRPCServiceGrpc.newStub(channel).build(channel, callOptions);
        }

        @Override
        protected ReactorUserGRPCServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new ReactorUserGRPCServiceStub(channel, callOptions);
        }

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistResponse> isUserExist(reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistRequest> reactorRequest) {
            return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, delegateStub::isUserExist, getCallOptions());
        }

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistResponse> isUserExist(com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistRequest reactorRequest) {
           return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), delegateStub::isUserExist, getCallOptions());
        }

    }

    public static abstract class UserGRPCServiceImplBase implements io.grpc.BindableService {

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistResponse> isUserExist(com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistRequest request) {
            return isUserExist(reactor.core.publisher.Mono.just(request));
        }

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistResponse> isUserExist(reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            com.biswasakashdev.nexussphere.protogen.users.v1.UserGRPCServiceGrpc.getIsUserExistMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistRequest,
                                            com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistResponse>(
                                            this, METHODID_IS_USER_EXIST)))
                    .build();
        }

        protected io.grpc.CallOptions getCallOptions(int methodId) {
            return null;
        }

        protected Throwable onErrorMap(Throwable throwable) {
            return com.salesforce.reactorgrpc.stub.ServerCalls.prepareError(throwable);
        }
    }

    public static final int METHODID_IS_USER_EXIST = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final UserGRPCServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(UserGRPCServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_IS_USER_EXIST:
                    com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistRequest) request,
                            (io.grpc.stub.StreamObserver<com.biswasakashdev.nexussphere.protogen.users.v1.IsUserExistResponse>) responseObserver,
                            serviceImpl::isUserExist, serviceImpl::onErrorMap);
                    break;
                default:
                    throw new java.lang.AssertionError();
            }
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public io.grpc.stub.StreamObserver<Req> invoke(io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                default:
                    throw new java.lang.AssertionError();
            }
        }
    }

}
