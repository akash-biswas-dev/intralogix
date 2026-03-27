package com.biswasakash.accessmanager.service.grpc;

import com.biswasakash.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakash.accessmanager.service.SecurityGroupService;
import com.nexussphere.protocjava.CreateSecurityGroupRequest;
import com.nexussphere.protocjava.CreateSecurityGroupResponse;
import com.nexussphere.protocjava.ReactorAccessManagerGRPCServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SecurityGroupGRPCService extends  ReactorAccessManagerGRPCServiceGrpc.AccessManagerGRPCServiceImplBase{

    private final SecurityGroupService securityGroupService;


    @Override
    public Mono<CreateSecurityGroupResponse> createSecurityGroup(Mono<CreateSecurityGroupRequest> request) {

        return request.flatMap((createSecurityGroupRequest -> {

            String userId = createSecurityGroupRequest.getUserId();
            String workspaceId = createSecurityGroupRequest.getWorkspaceId();

            SecurityGroupRequest securityGroupRequest = new SecurityGroupRequest(
                    "Workspace Admin",
                    "This is primary security group for this workspace."
            );

            return securityGroupService
                    .createSecurityGroup(userId, workspaceId, securityGroupRequest)
                    .then(Mono.just(
                            CreateSecurityGroupResponse.newBuilder()
                                    .setIsSucceed(true)
                                    .build()
                    ));
        }));
    }
}
