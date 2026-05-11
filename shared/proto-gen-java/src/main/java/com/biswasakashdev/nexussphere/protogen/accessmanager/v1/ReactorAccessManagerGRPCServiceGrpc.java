package com.biswasakashdev.nexussphere.protogen.accessmanager.v1;

import static com.biswasakashdev.nexussphere.protogen.accessmanager.v1.AccessManagerGRPCServiceGrpc.getServiceDescriptor;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;


@javax.annotation.Generated(
value = "by ReactorGrpc generator",
comments = "Source: access_manager/v1/main.proto")
public final class ReactorAccessManagerGRPCServiceGrpc {
    private ReactorAccessManagerGRPCServiceGrpc() {}

    public static ReactorAccessManagerGRPCServiceStub newReactorStub(io.grpc.Channel channel) {
        return new ReactorAccessManagerGRPCServiceStub(channel);
    }

    public static final class ReactorAccessManagerGRPCServiceStub extends io.grpc.stub.AbstractStub<ReactorAccessManagerGRPCServiceStub> {
        private AccessManagerGRPCServiceGrpc.AccessManagerGRPCServiceStub delegateStub;

        private ReactorAccessManagerGRPCServiceStub(io.grpc.Channel channel) {
            super(channel);
            delegateStub = AccessManagerGRPCServiceGrpc.newStub(channel);
        }

        private ReactorAccessManagerGRPCServiceStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            super(channel, callOptions);
            delegateStub = AccessManagerGRPCServiceGrpc.newStub(channel).build(channel, callOptions);
        }

        @Override
        protected ReactorAccessManagerGRPCServiceStub build(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
            return new ReactorAccessManagerGRPCServiceStub(channel, callOptions);
        }

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse> createSecurityGroup(reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest> reactorRequest) {
            return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactorRequest, delegateStub::createSecurityGroup, getCallOptions());
        }

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse> createSecurityGroup(com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest reactorRequest) {
           return com.salesforce.reactorgrpc.stub.ClientCalls.oneToOne(reactor.core.publisher.Mono.just(reactorRequest), delegateStub::createSecurityGroup, getCallOptions());
        }

    }

    public static abstract class AccessManagerGRPCServiceImplBase implements io.grpc.BindableService {

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse> createSecurityGroup(com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest request) {
            return createSecurityGroup(reactor.core.publisher.Mono.just(request));
        }

        public reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse> createSecurityGroup(reactor.core.publisher.Mono<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest> request) {
            throw new io.grpc.StatusRuntimeException(io.grpc.Status.UNIMPLEMENTED);
        }

        @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
            return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
                    .addMethod(
                            com.biswasakashdev.nexussphere.protogen.accessmanager.v1.AccessManagerGRPCServiceGrpc.getCreateSecurityGroupMethod(),
                            asyncUnaryCall(
                                    new MethodHandlers<
                                            com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest,
                                            com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse>(
                                            this, METHODID_CREATE_SECURITY_GROUP)))
                    .build();
        }

        protected io.grpc.CallOptions getCallOptions(int methodId) {
            return null;
        }

        protected Throwable onErrorMap(Throwable throwable) {
            return com.salesforce.reactorgrpc.stub.ServerCalls.prepareError(throwable);
        }
    }

    public static final int METHODID_CREATE_SECURITY_GROUP = 0;

    private static final class MethodHandlers<Req, Resp> implements
            io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
            io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
        private final AccessManagerGRPCServiceImplBase serviceImpl;
        private final int methodId;

        MethodHandlers(AccessManagerGRPCServiceImplBase serviceImpl, int methodId) {
            this.serviceImpl = serviceImpl;
            this.methodId = methodId;
        }

        @java.lang.Override
        @java.lang.SuppressWarnings("unchecked")
        public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
            switch (methodId) {
                case METHODID_CREATE_SECURITY_GROUP:
                    com.salesforce.reactorgrpc.stub.ServerCalls.oneToOne((com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest) request,
                            (io.grpc.stub.StreamObserver<com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse>) responseObserver,
                            serviceImpl::createSecurityGroup, serviceImpl::onErrorMap);
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
