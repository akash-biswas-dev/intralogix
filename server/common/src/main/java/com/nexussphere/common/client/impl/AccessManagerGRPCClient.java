package com.nexussphere.common.client.impl;

import com.nexussphere.common.client.AccessManagerClient;
import com.nexussphere.protocjava.CreateSecurityGroupRequest;
import com.nexussphere.protocjava.CreateSecurityGroupResponse;
import com.nexussphere.protocjava.ReactorAccessManagerGRPCServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import reactor.core.publisher.Mono;

public class AccessManagerGRPCClient implements AccessManagerClient {

    private final String host;
    private final int port;

    public AccessManagerGRPCClient(
            String host,
            int port
    ) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Mono<Boolean> createPrimarySecurityGroup(String userId,String workspaceId) {
        CreateSecurityGroupRequest request = CreateSecurityGroupRequest.newBuilder()
                .setWorkspaceId(workspaceId)
                .setUserId(userId)
                .build();

        ManagedChannel channel = createChannel();
        ReactorAccessManagerGRPCServiceGrpc.ReactorAccessManagerGRPCServiceStub stub = ReactorAccessManagerGRPCServiceGrpc.newReactorStub(channel);
        return Mono.just(request)
                .transform(stub::createSecurityGroup)
                .map(CreateSecurityGroupResponse::getIsSucceed);
    }

    private ManagedChannel createChannel() {
       return ManagedChannelBuilder
               .forAddress(host,port)
               .build();
    }
}
