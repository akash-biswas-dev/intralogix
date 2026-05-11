package com.biswasakashdev.nexussphere.accessmanager.controller.grpc;

import com.biswasakashdev.nexussphere.accessmanager.dtos.request.SecurityGroupRequest;
import com.biswasakashdev.nexussphere.accessmanager.service.SecurityGroupService;
import com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupRequest;
import com.biswasakashdev.nexussphere.protogen.accessmanager.v1.CreateSecurityGroupResponse;
import com.biswasakashdev.nexussphere.protogen.accessmanager.v1.ReactorAccessManagerGRPCServiceGrpc;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class SecurityGroupGRPCController extends  ReactorAccessManagerGRPCServiceGrpc.AccessManagerGRPCServiceImplBase{

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
