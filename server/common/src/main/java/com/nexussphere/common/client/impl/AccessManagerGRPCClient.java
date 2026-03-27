package com.nexussphere.common.client.impl;

import com.nexussphere.common.client.AccessManagerClient;
import com.nexussphere.protocjava.CreateSecurityGroupRequest;
import com.nexussphere.protocjava.CreateSecurityGroupResponse;
import com.nexussphere.protocjava.ReactorAccessManagerGRPCServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

public class AccessManagerGRPCClient implements AccessManagerClient {

    private static final Logger LOGGER= LoggerFactory.getLogger(AccessManagerGRPCClient.class);

    private final String host;

    public AccessManagerGRPCClient(
            String host
    ) {
        this.host = host;
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
                .map(CreateSecurityGroupResponse::getIsSucceed)
                .onErrorResume(err->{
                    LOGGER.error("create security group failed", err);
                    return Mono.just(false);
                });
    }

    private ManagedChannel createChannel() {
       return ManagedChannelBuilder
               .forTarget(host)
               .usePlaintext()
               .build();
    }
}
