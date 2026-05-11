package com.biswasakashdev.nexussphere.common.client.impl;

import com.biswasakashdev.nexussphere.common.client.AccessManagerClient;
import com.biswasakashdev.nexussphere.common.exceptions.GRPCClientException;
import com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest;
import com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse;
import com.biswasakashdev.nexussphere.protogen.accessmanager.v1.ReactorAccessManagerGRPCServiceGrpc;
import io.grpc.Channel;
import io.grpc.StatusRuntimeException;
import reactor.core.publisher.Mono;

public class AccessManagerGRPCSecureClient implements AccessManagerClient {

    private final ReactorAccessManagerGRPCServiceGrpc.ReactorAccessManagerGRPCServiceStub stub;

    public AccessManagerGRPCSecureClient(
            Channel channel
    ) {
        stub = ReactorAccessManagerGRPCServiceGrpc.newReactorStub(channel);
    }

    @Override
    public Mono<Void> createPrimarySecurityGroup(String userId, String workspaceId) {
        CreateSecurityGroupRequest request = CreateSecurityGroupRequest.newBuilder()
                .setWorkspaceId(workspaceId)
                .setUserId(userId)
                .build();

        return Mono.just(request)
                .transform(stub::createSecurityGroup)
                .map(CreateSecurityGroupResponse::getIsSucceed)
                .onErrorResume(StatusRuntimeException.class, (err) -> Mono.error(new GRPCClientException("Failed to create security group.")))
                .then();
    }

}
